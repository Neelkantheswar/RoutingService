package org.service.impl;

import org.entity.Address;
import org.entity.Customer;
import org.entity.DeliveryPartner;
import org.entity.Order;
import org.entity.Restaurant;
import org.entity.Route;
import org.service.DeliveryPartnerService;
import org.service.RoutingService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShortestRoutingServiceImpl implements RoutingService {
    private final DeliveryPartnerService deliveryPartnerService;

    public ShortestRoutingServiceImpl(DeliveryPartnerService deliveryPartnerService) {
        this.deliveryPartnerService = deliveryPartnerService;
    }

    private static final class DPState {
        int currentNode;
        int remainingMask;
        BigDecimal timeSoFar;

        public DPState(int currentNode, int remainingMask, BigDecimal timeSoFar) {
            this.currentNode = currentNode;
            this.remainingMask = remainingMask;
            this.timeSoFar = timeSoFar;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DPState dpState = (DPState) o;
            return currentNode == dpState.currentNode && remainingMask == dpState.remainingMask && timeSoFar.equals(dpState.timeSoFar);
        }

        @Override
        public int hashCode() {
            return Objects.hash(currentNode, remainingMask, timeSoFar);
        }
    }

    private static class Wrapper<T> {
        private T value;

        public Wrapper(T value) {
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }


    private BigDecimal getDP(Map<DPState, BigDecimal> dp,DPState state) {
        return dp.getOrDefault(state, new BigDecimal(-1));
    }

    private BigDecimal updateDP(Map<DPState, BigDecimal> dp, DPState state, BigDecimal value) {
        dp.put(state, value);
        return value;
    }

    /*
    *  This Function is used to find the shortestPath taken by delivery person to delivery the order to customer.
    *  It uses Dynamic Programming Approach to explore all the possible path taken by deliveryPerson and computes the shortestPath.
    *  Each DeliveryPerson d, Restaurants r from where he picks the Order o and Customer c to which he delivers are represented using an Integer
    *  DeliveryPerson -> 0 , Restaurants -> 1 to LengthOfRestaurants In the Order , Customers -> LengthOfRestaurants + 1 to CustomersLength
    *  For the sake of Simplicity, I have assumed that a Delivery Person picks only one order from a particular Restaurants and for one customer and
    *  OrderList can be of the following type -> d -> {  r1 -> c1 , r2 -> c2....} means one to one mapping between Customer and Restaurants
    *  It can never of this format -> d -> {r1 -> c1 , r1 -> c2 }
    *  Also I have assumed Restaurants starts preparing meals as soon as its receives the order.
    *  Also, I have assumed that Restaurants prepares the meals one by one and
    *  hence to calculate prepartion time for all the foodItems in a particular order, we have to sum them.
    *  Params:
               u -> currentNode being Visited ( can be r, d, c)
               mask -> used as mask to represent that which node has been visited and which is not yet visited
               deliveryPartner -> self explaintory
               restaurantToCustomerMp -> this hold the mapping of Restaurant to Customer and Prepartion time of meals what customer has ordered for.
                                         Note this also server as a way to represent that Customer can't be travelled until its dependent Restaurant is visited.
               restaurantMaskMp -> this hold the mapping of integer ( position at mask) to Restaurant.
               customerMaskMp -> this hold the mapping of integer ( position at mask ) to Customer.
               timeTakenSoFar -> this hold the time in min information taken by deliveryPerson following the path in this recursive dfs till now.
               nextDestination -> hold the list of path taken by deliveryPerson
               dp -> For Memoization Purpose
               pathToTake -> Wrapper class to avoid to use the global instance variable
               shortestPathTime -> basically a wrapper class to hold the time the following explored path would have taken. Used for Constructing the pathToTake.
    * return:
              shortestTime to deliver the order.
   */
    private BigDecimal dfs(int u, int mask, DeliveryPartner deliveryPartner, Map<Restaurant, Map<Customer, Integer>> restaurantToCustomerMp,
                           Map<Integer, Restaurant> restaurantMaskMp, Map<Integer, Customer> customerMaskMp,
                           BigDecimal timeTakenSoFar, List<Integer> nextDestination, Map<DPState, BigDecimal> dp, Wrapper<List<Integer>> pathToTake, Wrapper<BigDecimal> shortestPathTime) {
        if (mask == 0) {
            if (timeTakenSoFar.compareTo(shortestPathTime.getValue()) < 0) {
                shortestPathTime.value = timeTakenSoFar;
                pathToTake.value = nextDestination.stream().toList();
            }
            return timeTakenSoFar;
        }
        DPState dpState = new DPState(u, mask, timeTakenSoFar);
        if (!getDP(dp,dpState).equals(new BigDecimal(-1))) {
            return getDP(dp,dpState);
        }
        int orderLength = customerMaskMp.size();
        BigDecimal shortestPossibleTime = new BigDecimal(Double.MAX_VALUE);
        BigDecimal smallestTimeToRestaurant = travelToRestaurant(u, mask, deliveryPartner, restaurantToCustomerMp, restaurantMaskMp, customerMaskMp, timeTakenSoFar, orderLength, nextDestination,dp,pathToTake,shortestPathTime);
        if (smallestTimeToRestaurant.compareTo(shortestPossibleTime) < 0) {
            shortestPossibleTime = smallestTimeToRestaurant;
        }

        // we will not travel to Customer if u is DeliveryPerson, else we can travel to Customer Also.
        if (u != 0) {
            BigDecimal smallestTimeToCustomer = travelToCustomer(u, mask, deliveryPartner, restaurantToCustomerMp, restaurantMaskMp, customerMaskMp, timeTakenSoFar, orderLength, nextDestination,dp,pathToTake,shortestPathTime);
            if (smallestTimeToCustomer.compareTo(shortestPossibleTime) < 0) {
                shortestPossibleTime = smallestTimeToCustomer;
            }
        }
        return updateDP(dp,dpState, shortestPossibleTime);
    }

    private BigDecimal travelToCustomer(int u, int mask, DeliveryPartner deliveryPartner, Map<Restaurant, Map<Customer, Integer>> restaurantToCustomerMp,
                                        Map<Integer, Restaurant> restaurantMaskMp, Map<Integer, Customer> customerMaskMp, BigDecimal timeTakenSoFar,
                                        int orderLength, List<Integer> nextDestination, Map<DPState, BigDecimal> dp, Wrapper<List<Integer>> pathToTake, Wrapper<BigDecimal> shortestPathTime) {
        BigDecimal shortestPossibleTime = new BigDecimal(Double.MAX_VALUE), min1;
        Address address = (u == 0) ? deliveryPartner.getAddress() : (u <= orderLength) ? restaurantMaskMp.get(u).getAddress() : customerMaskMp.get(u).getAddress();
        for (int i = 0; i < orderLength; i++) {
            // This condition check only if the current Customer is not visited and if the restaurant from which he ordered is visited
            // this is done to ensure that Delivery Person first picks the order before going for delivery.
            if ((mask & (1 << (i + 1 + orderLength))) != 0 && (mask & (1 << (i + 1))) == 0) {
                // Calculating the distance between the two Location in KM.
                BigDecimal distanceToReachIfromU = new BigDecimal(address.getLocation()
                        .haversineDistance(customerMaskMp.get(i + 1 + orderLength).getAddress().getLocation()));
                // calculating the timeTakenInMinutes to travel between them assuming the average speed is 20 KM/HR
                // Hardcode Here. Can Introduce Speed in DeliveryPartner Class to make it more modular.
                BigDecimal timeTakenInMinutes = distanceToReachIfromU.multiply(new BigDecimal(3));
                nextDestination.add(i + 1 + orderLength);
                min1 = shortestPossibleTime.min(
                        dfs(i + 1 + orderLength, (mask ^ (1 << (i + 1 + orderLength))), deliveryPartner, restaurantToCustomerMp, restaurantMaskMp, customerMaskMp, timeTakenInMinutes.add(timeTakenSoFar), nextDestination, dp, pathToTake, shortestPathTime)
                );
                nextDestination.remove(nextDestination.size() - 1);
                if (!min1.equals(shortestPossibleTime)) {
                    shortestPossibleTime = min1;
                }
            }
        }
        return shortestPossibleTime;
    }

    private BigDecimal travelToRestaurant(int u, int mask, DeliveryPartner deliveryPartner, Map<Restaurant, Map<Customer, Integer>> restaurantToCustomerMp,
                                          Map<Integer, Restaurant> restaurantMaskMp, Map<Integer, Customer> customerMaskMp, BigDecimal timeTakenSoFar,
                                          int orderLength, List<Integer> nextDestination, Map<DPState, BigDecimal> dp, Wrapper<List<Integer>> pathToTake, Wrapper<BigDecimal> shortestPathTime) {
        BigDecimal shortestPossibleTime = new BigDecimal(Double.MAX_VALUE), min1;
        Address address = (u == 0) ? deliveryPartner.getAddress() : (u <= orderLength) ? restaurantMaskMp.get(u).getAddress() : customerMaskMp.get(u).getAddress();
        for (int i = 0; i < orderLength; i++) {
            // This Condition check whether Restaurant is visited or not.
            if ((mask & (1 << (i + 1))) != 0) {

                // Calculating the distance between the two Location in KM.
                BigDecimal distanceToReachIfromU = new BigDecimal(address.getLocation()
                        .haversineDistance(restaurantMaskMp.get(i + 1).getAddress().getLocation()));

                // calculating the timeTakenInMinutes to travel between them assuming the average speed is 20 KM/HR
                // Hardcode Here. Can Introduce Speed in DeliveryPartner Class to make it more modular.
                BigDecimal timeTakenInMinutes = distanceToReachIfromU.multiply(new BigDecimal(3));

                BigDecimal timeTakenForCooking = new BigDecimal(restaurantToCustomerMp.get(restaurantMaskMp.get(i + 1)).get(customerMaskMp.get(i + 1 + orderLength)));

                // This is done to check whether or not food is already cooked before the arrival of DeliveryPerson or not.
                // If the food is cooked then it is 0 else Delivery Partner have to wait for Restaurants to finish cooking.
                BigDecimal additionalTimeUsedForCooking = (timeTakenInMinutes.add(timeTakenSoFar).compareTo(timeTakenForCooking) >= 0)
                        ? new BigDecimal(0)
                        : timeTakenForCooking.subtract(timeTakenInMinutes.add(timeTakenSoFar));
                nextDestination.add(i + 1);
                min1 = shortestPossibleTime.min(
                        dfs(i + 1, (mask ^ (1 << (i + 1))), deliveryPartner, restaurantToCustomerMp, restaurantMaskMp, customerMaskMp, timeTakenInMinutes.add(additionalTimeUsedForCooking).add(timeTakenSoFar), nextDestination, dp, pathToTake, shortestPathTime)
                );
                nextDestination.remove(nextDestination.size() - 1);
                if (!min1.equals(shortestPossibleTime)) {
                    shortestPossibleTime = min1;
                }
            }
        }
        return shortestPossibleTime;
    }

    /*
        This Function is used for Constructing the Path taken by delivery Partner to fulfill the order delivery in least Possible time.
     */
    private Route constructRoute(Wrapper<BigDecimal> shortestPathTime, Wrapper<List<Integer>> pathToTake, DeliveryPartner partner, Map<Integer, Restaurant> restaurantMaskMp, Map<Integer, Customer> customerMaskMp) {
        Route shortestRoute = new Route();
        shortestRoute.setTimeTaken(shortestPathTime.getValue().setScale(2, RoundingMode.HALF_UP));
        shortestRoute.addRouteComponent(partner);
        for (int next : pathToTake.getValue()) {
            if (restaurantMaskMp.containsKey(next)) {
                shortestRoute.addRouteComponent(restaurantMaskMp.get(next));
            } else {
                shortestRoute.addRouteComponent(customerMaskMp.get(next));
            }
        }
        return shortestRoute;
    }

    /*
        This Function is basically used or called by DeliveryPartner to find the shortest Route to take.
        Params:
               deliveryPartnerId -> the Id of deliveryPerson who called this API
               deliveryNumber -> For which Order Delivery Number of deliveryPartner he wants to compute the shortest Path.
                                Note I have assumed that a deliveryPartner can be assigned tons of delivery and hence avoided to keep that Mapping
                                of deliveryNumber to deliverPartner to OrderList Batch in the deliveryClass and instead exposed an API
                                getAllOrderListForAParticularDelivery on delivery Service to fetch the list on Runtime.
        Returns:
                The Route to take in order to deliver the order in least possible time.
     */
    @Override
    public Route calculateShortestRoute(String deliveryPartnerId, int deliveryNumber) {
        DeliveryPartner deliveryPartner = deliveryPartnerService.getDeliveryPartner(deliveryPartnerId);

        List<Order> orderListForAParticularDelivery = deliveryPartnerService.getAllOrderListForAParticularDelivery(deliveryPartnerId, deliveryNumber);

        Map<Restaurant, Map<Customer, Integer>> restaurantToCustomerMp = orderListForAParticularDelivery
                .stream()
                .collect(HashMap::new, (mp, order) -> {
                    mp.computeIfAbsent(order.getRestaurant(), k -> new HashMap<>())
                            .put(order.getCustomer(), order.getFoodItemOrderedList()
                                    .stream().mapToInt(foodItem -> order.getRestaurant().getFoodItemPreprationTime(foodItem)).sum());
                }, HashMap::putAll);
        Map<Integer, Restaurant> restaurantMaskMp = new HashMap<>();
        Map<Integer, Customer> customerMaskMp = new HashMap<>();
        int index = 1, orderLength = restaurantToCustomerMp.size();
        for (Map.Entry<Restaurant, Map<Customer, Integer>> restaurantCustomerEntry : restaurantToCustomerMp.entrySet()) {
            restaurantMaskMp.put(index, restaurantCustomerEntry.getKey());
            customerMaskMp.put(index + orderLength, restaurantCustomerEntry.getValue().keySet().stream().findFirst().get());
            index++;
        }
        int totalLen = 2 * restaurantMaskMp.size() + 1;
        int mask = (1 << totalLen) - 2;
        Map<DPState, BigDecimal> dp = new HashMap<>();
        Wrapper<List<Integer>> pathToTake = new Wrapper<>(new ArrayList<>());
        Wrapper<BigDecimal> shortestPathTime = new Wrapper<>(BigDecimal.valueOf(Double.MAX_VALUE));
        List<Integer> nextDestination = new ArrayList<>();
        dfs(0, mask, deliveryPartner, restaurantToCustomerMp, restaurantMaskMp, customerMaskMp, BigDecimal.ZERO, nextDestination,dp,pathToTake,shortestPathTime);
        return constructRoute(shortestPathTime,pathToTake,deliveryPartner, restaurantMaskMp, customerMaskMp);
    }
}
