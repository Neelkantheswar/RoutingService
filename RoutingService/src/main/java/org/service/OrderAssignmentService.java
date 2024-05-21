package org.service;

import org.notification.Observer;

public interface OrderAssignmentService extends Observer {
    void assignOrdersToDeliveryPartner();
}
