package org.repository;

import org.entity.DeliveryPartner;
import org.entity.DeliveryStatus;
import org.entity.Order;

import java.util.List;

public interface DeliveryPartnerRepository {
    DeliveryPartner findDeliveryPartnerById(String deliveryPartnerId);
    void addDeliveryPartner(DeliveryPartner deliveryPartner);
    void updateDeliveryPartnerStatus(String deliveryPartnerId, DeliveryStatus deliveryStatus);

    int addNewOrderListToDeliveryPartner(String deliveryPartnerId, List<Order> orderList);

    List<Order> getAllOrderListForAParticularDelivery(String deliveryId, int deliveryNumber);

    List<DeliveryPartner> getAllAvailableDeliveryPartner();

    List<DeliveryPartner> getAllDeliveryPartner();

    List<Integer> getListOfAllDeliveryNumberForADeliveryPartner(String deliveryPartnerId);
}
