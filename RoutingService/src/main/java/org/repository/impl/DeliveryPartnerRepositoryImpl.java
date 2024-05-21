package org.repository.impl;

import org.entity.DeliveryPartner;
import org.entity.DeliveryStatus;
import org.entity.Order;
import org.repository.DeliveryPartnerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryPartnerRepositoryImpl implements DeliveryPartnerRepository {
    private final Map<String, DeliveryPartner> availableDeliveryPartnerMp;

    private final Map<String, DeliveryPartner> notAvailableDeliveryPartnerMp;

    private final Map<String, Map<Integer, List<Order>>> deliveryPartnerToOrderListMp;

    public DeliveryPartnerRepositoryImpl() {
        this.availableDeliveryPartnerMp = new HashMap<>();
        this.notAvailableDeliveryPartnerMp = new HashMap<>();
        this.deliveryPartnerToOrderListMp = new HashMap<>();
    }

    @Override
    public DeliveryPartner findDeliveryPartnerById(String deliveryPartnerId) {
        if (availableDeliveryPartnerMp.containsKey(deliveryPartnerId)) {
            return availableDeliveryPartnerMp.get(deliveryPartnerId);
        } else if (notAvailableDeliveryPartnerMp.containsKey(deliveryPartnerId)) {
            return notAvailableDeliveryPartnerMp.get(deliveryPartnerId);
        }
        throw new RuntimeException("DeliveryPartner with given deliveryId" + deliveryPartnerId + " is not found!");
    }

    @Override
    public void addDeliveryPartner(DeliveryPartner deliveryPartner) {
        availableDeliveryPartnerMp.put(deliveryPartner.getId(), deliveryPartner);
        deliveryPartnerToOrderListMp.put(deliveryPartner.getId(), new HashMap<>());
    }

    @Override
    public void updateDeliveryPartnerStatus(String deliveryPartnerId, DeliveryStatus deliveryStatus) {
        if (!deliveryStatus.equals(DeliveryStatus.AVAILABLE)) {
            notAvailableDeliveryPartnerMp.put(deliveryPartnerId, availableDeliveryPartnerMp.get(deliveryPartnerId));
            availableDeliveryPartnerMp.remove(deliveryPartnerId);
        } else if (notAvailableDeliveryPartnerMp.containsKey(deliveryPartnerId)) {
            availableDeliveryPartnerMp.put(deliveryPartnerId, notAvailableDeliveryPartnerMp.get(deliveryPartnerId));
            notAvailableDeliveryPartnerMp.remove(deliveryPartnerId);
        } else if (!availableDeliveryPartnerMp.containsKey(deliveryPartnerId)) {
            throw new RuntimeException("DeliveryPartner with given deliveryId" + deliveryPartnerId + " is not found!");
        }
    }

    @Override
    public int addNewOrderListToDeliveryPartner(String deliveryPartnerId, List<Order> orderList) {
        if (notAvailableDeliveryPartnerMp.containsKey(deliveryPartnerId)) {
            DeliveryPartner deliveryPartner = notAvailableDeliveryPartnerMp.get(deliveryPartnerId);
            int deliveryNumber = deliveryPartnerToOrderListMp.get(deliveryPartnerId).size() + 1;
            deliveryPartnerToOrderListMp.get(deliveryPartnerId).put(deliveryNumber, orderList);
            return deliveryNumber;

        }
        throw new RuntimeException("DeliveryPartner with given deliveryId" + deliveryPartnerId + " is not found!");
    }

    @Override
    public List<Order> getAllOrderListForAParticularDelivery(String deliveryId, int deliveryNumber) {
        if (!deliveryPartnerToOrderListMp.containsKey(deliveryId)) {
            throw new RuntimeException("DeliveryPartner with given deliveryId" + deliveryId + " is not found!");
        }
        return deliveryPartnerToOrderListMp.get(deliveryId).get(deliveryNumber);
    }

    @Override
    public List<DeliveryPartner> getAllAvailableDeliveryPartner() {
        return availableDeliveryPartnerMp.values().stream().toList();
    }

    @Override
    public List<DeliveryPartner> getAllDeliveryPartner() {
        List<DeliveryPartner> deliveryPartners = notAvailableDeliveryPartnerMp.values().stream().toList();
        deliveryPartners.addAll(getAllAvailableDeliveryPartner());
        return deliveryPartners;
    }

    @Override
    public List<Integer> getListOfAllDeliveryNumberForADeliveryPartner(String deliveryPartnerId) {
        return deliveryPartnerToOrderListMp.get(deliveryPartnerId).keySet().stream().toList();
    }
}
