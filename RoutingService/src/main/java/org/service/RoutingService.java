package org.service;

import org.entity.Route;

public interface RoutingService {
    Route calculateShortestRoute(String deliveryPartnerId, int deliveryNumber);
}
