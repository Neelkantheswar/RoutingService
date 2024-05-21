# ProblemStatment

Imagine a delivery executive called Aman standing idle in Koramangala somewhere when suddenly his phone rings and notifies that he's just been assigned a batch of 2 orders meant to be delivered in the shortest possible timeframe.
All the circles in the figure above represent geo-locations:

• C1: Consumer 1

• C2: Consumer 2

• R1 : Restaurant C1 has ordered from. Average time it takes to prepare a meal is pt1

• R2: Restaurant C2 has ordered from. Average time it takes to prepare a meal is pt2

Since there are multiple ways to go about delivering these orders, your task is to help Aman figure out the best way to finish the batch in the shortest possible time.
For the sake of simplicity, you can assume that Aman, R1 and R2 were informed about these orders at the exact same time and all of them confirm on doing it immediately.
Also, for travel time between any two geo-locations, you can use the haversine formula with an average speed of 20km/hr 
( basically ignore actual road distance or confirmation delays everywhere although the real world is hardly that simple ;))

## Assumptions Made:-

###### Order Assignment: 
Each delivery person is assigned orders from multiple restaurants, and each order is destined for a specific customer.

###### Order Preparation:

Restaurants start preparing meals as soon as they receive the order. Meals are prepared one by one, and the preparation time for all food items in an order is summed.

###### Delivery Process:

A delivery person picks only one order from a particular restaurant for one customer.
Delivery persons follow a _one-to-one mapping between customers and restaurants_.
It's assumed that delivery persons travel alone.

###### Travel Considerations:

Travel speed is assumed to be constant at _20 KM/HR_.
Distance between locations is calculated using the Haversine formula.
Delivery persons have to wait for restaurants to finish cooking if the food isn't ready upon arrival.

###### Path Calculation:

The class computes the shortest path for the delivery person using a recursive depth-first search (DFS) approach.
Dynamic programming is utilized to optimize path exploration.

###### Input Parameters:

deliveryPartnerId: The ID of the delivery person.
deliveryNumber: The order delivery number for which the shortest path is to be calculated.

###### Output:

The service returns the shortest route as a Route object, including the time taken to complete the delivery.

## Testing:-

Used Driver Main program instead of writing unit Test in order to finish the project on time.

