package org.service;

import org.entity.Address;
import org.entity.DeliveryPartner;
import org.entity.DeliveryStatus;
import org.entity.Order;

import java.util.List;

public interface DeliveryPartnerService {
    String addDeliveryPartner(String name, String email,String phoneNumber,Address address);
    void updateDeliveryPartnerStatus(String deliveryId, DeliveryStatus deliveryStatus);
    DeliveryPartner getDeliveryPartner(String deliveryId);
    List<DeliveryPartner> getAllAvailableDeliveryPartner();
    int addNewOrderListToDeliveryPartner(String deliveryId, List<Order> orderList);
    List<Order> getAllOrderListForAParticularDelivery(String deliveryId, int deliveryNumber);

    List<Integer> getListOfAllDeliveryNumberForADeliveryPartner(String deliveryPartnerId);
}
