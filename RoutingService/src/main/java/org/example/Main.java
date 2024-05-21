package org.example;

import org.entity.Address;
import org.entity.FoodCategory;
import org.entity.Location;
import org.entity.Route;
import org.repository.CustomerRepository;
import org.repository.DeliveryPartnerRepository;
import org.repository.OrderRepository;
import org.repository.RestaurantRepository;
import org.repository.impl.CustomerRepositoryImpl;
import org.repository.impl.DeliveryPartnerRepositoryImpl;
import org.repository.impl.OrderRepositoryImpl;
import org.repository.impl.RestaurantRepositoryImpl;
import org.service.CustomerService;
import org.service.DeliveryPartnerService;
import org.service.OrderAssignmentService;
import org.service.OrderService;
import org.service.RestaurantService;
import org.service.RoutingService;
import org.service.impl.ShortestRoutingServiceImpl;
import org.service.impl.CustomerServiceImpl;
import org.service.impl.DeliveryPartnerServiceImpl;
import org.service.impl.OrderAssignmentServiceImpl;
import org.service.impl.OrderServiceImpl;
import org.service.impl.RestaurantServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CustomerRepository customerRepository = new CustomerRepositoryImpl();
        RestaurantRepository restaurantRepository = new RestaurantRepositoryImpl();
        DeliveryPartnerRepository deliveryPartnerRepository = new DeliveryPartnerRepositoryImpl();
        OrderRepository orderRepository = new OrderRepositoryImpl();
        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        RestaurantService restaurantService = new RestaurantServiceImpl(restaurantRepository);
        DeliveryPartnerService deliveryPartnerService = new DeliveryPartnerServiceImpl(deliveryPartnerRepository);
        OrderService orderService = new OrderServiceImpl(orderRepository,customerService,restaurantService);
        RoutingService routingService = new ShortestRoutingServiceImpl(deliveryPartnerService);
        OrderAssignmentService orderAssignmentService = new OrderAssignmentServiceImpl(deliveryPartnerService,restaurantService,orderService);
        orderService.registerObserver(orderAssignmentService);
        List<Address> addressList = createAddress();
        List<String> customerIds = createCustomer(customerService, addressList);
        List<String> restaurantIds = createRestaurant(restaurantService, addressList);
        List<String> deliveryPartnerIds = createDeliveryPartner(deliveryPartnerService, addressList);
        createOrder(orderService,restaurantService,orderAssignmentService,customerIds,restaurantIds);
        calculateShortestRoute(deliveryPartnerIds,routingService,deliveryPartnerService);
    }

    private static List<Address> createAddress() {
        List<Address> addressList = new ArrayList<>();
        addressList.add(Address.builder()
                .streetNumber("123 Housing Colony")
                .city("Bengaluru")
                .state("Karnataka")
                .country("India")
                .location(new Location(88.6345,90.432))
                .build()
        );
        addressList.add(Address.builder()
                .streetNumber("223 NayaVihar Apartment")
                .city("Bengaluru")
                .state("Karnataka")
                .country("India")
                .location(new Location(89.6345,89.432))
                .build()
        );
        addressList.add(Address.builder()
                .streetNumber("489 Patiala Nagar")
                .city("Bengaluru")
                .state("Karnataka")
                .country("India")
                .location(new Location(88.9345,90.456))
                .build()
        );
        addressList.add(Address.builder()
                .streetNumber("234 street Kormangala")
                .city("Bengaluru")
                .state("Karnataka")
                .country("India")
                .location(new Location(87.6345,91.432))
                .build()
        );
        addressList.add(Address.builder()
                .streetNumber("854  ChinaTown")
                .city("Bengaluru")
                .state("Karnataka")
                .country("India")
                .location(new Location(87.6345,91.492))
                .build()
        );
        addressList.add(Address.builder()
                .streetNumber("912 RaniGanj Park")
                .city("Bengaluru")
                .state("Karnataka")
                .country("India")
                .location(new Location(85.6345,92.492))
                .build()
        );
        addressList.add(Address.builder()
                .streetNumber("36 Pahari Street")
                .city("Bengaluru")
                .state("Karnataka")
                .country("India")
                .location(new Location(88.747,92.492))
                .build()
        );
        addressList.add(Address.builder()
                .streetNumber("100 Hanuman Mandir Road")
                .city("Bengaluru")
                .state("Karnataka")
                .country("India")
                .location(new Location(88.747,92.492))
                .build()
        );
        return addressList;
    }


    private static List<String> createCustomer(CustomerService customerService, List<Address> addressList) {
        List<String> customerIdList = new ArrayList<>();
        customerIdList.add(customerService.addCustomer("Vashuki Pathak", "vashu12@outlook.com", addressList.get(1)));
        customerIdList.add(customerService.addCustomer("Md Faizal Akhtar", "faizu42@outlook.com", addressList.get(0)));
        customerIdList.add(customerService.addCustomer("Arshdeep Singh", "arshPutar@gmail.com", addressList.get(0)));
        customerIdList.add(customerService.addCustomer("Alex lee", "alee@@alibaba-inc.com", addressList.get(1)));
        return customerIdList;

    }

    private static List<String> createRestaurant(RestaurantService restaurantService, List<Address> addressList) {
        String maharaja_bhoj = restaurantService.addRestaurant("Maharaja Bhoj", addressList.get(5));
        String saffron = restaurantService.addRestaurant("Saffron", addressList.get(2));
        String bombay_chowk = restaurantService.addRestaurant("Bombay Chowk", addressList.get(6));
        String punjabi_tadka = restaurantService.addRestaurant("Punjabi Tadka", addressList.get(3));
        restaurantService.addFoodItemToRestaurant(maharaja_bhoj,
                "Dal Makhani",
                FoodCategory.MAIN_COURSES,
                20);
        restaurantService.addFoodItemToRestaurant(maharaja_bhoj,
                "Bhel Puri",
                FoodCategory.STARTERS,
                10);
        restaurantService.addFoodItemToRestaurant(maharaja_bhoj,
                "Gulab Jamun",
                FoodCategory.DESSERTS,
                5);
        restaurantService.addFoodItemToRestaurant(saffron,
                "Dal Bhaat",
                FoodCategory.MAIN_COURSES,
                21);
        restaurantService.addFoodItemToRestaurant(saffron,
                "Pakodi",
                FoodCategory.STARTERS,
                18);
        restaurantService.addFoodItemToRestaurant(saffron,
                "Gajar Halwa",
                FoodCategory.DESSERTS,
                32);
        restaurantService.addFoodItemToRestaurant(bombay_chowk,
                "Dal Makhani",
                FoodCategory.MAIN_COURSES,
                20);
        restaurantService.addFoodItemToRestaurant(bombay_chowk,
                "Bhel Puri",
                FoodCategory.STARTERS,
                10);
        restaurantService.addFoodItemToRestaurant(punjabi_tadka,
                "Jalebi",
                FoodCategory.DESSERTS,
                15);
        restaurantService.addFoodItemToRestaurant(punjabi_tadka,
                "Naan",
                FoodCategory.MAIN_COURSES,
                20);
        restaurantService.addFoodItemToRestaurant(punjabi_tadka,
                "Chana Masala",
                FoodCategory.STARTERS,
                10);
        restaurantService.addFoodItemToRestaurant(punjabi_tadka,
                "Kachori",
                FoodCategory.STARTERS,
                5);

        List<String> restaurtIdList = new ArrayList<>();
        restaurtIdList.add(maharaja_bhoj);
        restaurtIdList.add(punjabi_tadka);
        restaurtIdList.add(bombay_chowk);
        restaurtIdList.add(saffron);
        return restaurtIdList;
    }

    private static List<String> createDeliveryPartner(DeliveryPartnerService deliveryPartnerService, List<Address> addressList) {
        List<String> deliveryPartnerIdList = new ArrayList<>();
        deliveryPartnerIdList.add(deliveryPartnerService.addDeliveryPartner("Aman","aman@gmail.com","7766565751",addressList.get(1)));
        deliveryPartnerIdList.add(deliveryPartnerService.addDeliveryPartner("Nadeem","nadeem884@gmail.com","8653425781", addressList.get(0)));
        return deliveryPartnerIdList;

    }


    private static void createOrder(OrderService orderService, RestaurantService restaurantService, OrderAssignmentService orderAssignmentService, List<String> customerIds, List<String> restaurantIds) {
        List<Integer> deliveryId = new ArrayList<>();
        orderService.createOrder(customerIds.get(0),restaurantIds.get(0),restaurantService.getAllFoodItemListForARestaurant(restaurantIds.get(0)));
        orderService.createOrder(customerIds.get(3),restaurantIds.get(2),restaurantService.getAllFoodItemListForARestaurant(restaurantIds.get(2)));
        orderAssignmentService.assignOrdersToDeliveryPartner();
        orderService.createOrder(customerIds.get(2),restaurantIds.get(1),restaurantService.getAllFoodItemListForARestaurant(restaurantIds.get(1)));
        orderService.createOrder(customerIds.get(1),restaurantIds.get(3),restaurantService.getAllFoodItemListForARestaurant(restaurantIds.get(3)));
        orderAssignmentService.assignOrdersToDeliveryPartner();
    }



    private static void calculateShortestRoute(List<String> deliveryPartnerIds, RoutingService routingService, DeliveryPartnerService deliveryPartnerService) {
        for(String deliveryPartnerId: deliveryPartnerIds){
             for(int deliveryNumber: deliveryPartnerService.getListOfAllDeliveryNumberForADeliveryPartner(deliveryPartnerId)) {
                 Route shortestRoute = routingService.calculateShortestRoute(deliveryPartnerId, deliveryNumber);
                 System.out.println("--------------------------------------------------");
                 String deliveryName = deliveryPartnerService.getDeliveryPartner(deliveryPartnerId).getName();
                 System.out.println("The shortest Possible time for delivery by "
                         + deliveryName
                         + " of OrderList "
                         + deliveryPartnerService.getAllOrderListForAParticularDelivery(deliveryPartnerId,deliveryNumber)
                         + " takes " + shortestRoute.getTimeTaken() +" minutes.");

                 System.out.println("The Route to Follow by "
                         + deliveryName
                         + " , in sequence."
                 );
                 shortestRoute.displayRouteDetails();
                 System.out.println("--------------------------------------------------");
             }
        }
    }

}