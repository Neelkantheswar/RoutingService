package org.service.impl;

import org.entity.Address;
import org.entity.DeliveryPartner;
import org.entity.DeliveryStatus;
import org.entity.Order;
import org.repository.DeliveryPartnerRepository;
import org.service.DeliveryPartnerService;

import java.util.List;

public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {
    private final DeliveryPartnerRepository deliveryPartnerRepository;

    public DeliveryPartnerServiceImpl(DeliveryPartnerRepository deliveryPartnerRepository) {
        this.deliveryPartnerRepository = deliveryPartnerRepository;
    }

    @Override
    public String addDeliveryPartner(String name, String email, String phoneNumber,Address address) {
        DeliveryPartner deliveryPartner = new DeliveryPartner(name, email, address);
        deliveryPartner.setPhoneNumber(phoneNumber);
        deliveryPartner.setIsActive(DeliveryStatus.AVAILABLE);
        deliveryPartnerRepository.addDeliveryPartner(deliveryPartner);
        return deliveryPartner.getId();
    }


    @Override
    public void updateDeliveryPartnerStatus(String deliveryId, DeliveryStatus deliveryStatus) {
        deliveryPartnerRepository.updateDeliveryPartnerStatus(deliveryId,deliveryStatus);
    }

    @Override
    public DeliveryPartner getDeliveryPartner(String deliveryId) {
        return deliveryPartnerRepository.findDeliveryPartnerById(deliveryId);
    }

    @Override
    public List<DeliveryPartner> getAllAvailableDeliveryPartner() {
        return deliveryPartnerRepository.getAllAvailableDeliveryPartner();
    }

    @Override
    public int addNewOrderListToDeliveryPartner(String deliveryId, List<Order> orderList) {
        return deliveryPartnerRepository.addNewOrderListToDeliveryPartner(deliveryId,orderList);
    }

    @Override
    public List<Order> getAllOrderListForAParticularDelivery(String deliveryId, int deliveryNumber) {
        return deliveryPartnerRepository.getAllOrderListForAParticularDelivery(deliveryId,deliveryNumber);
    }

    @Override
    public List<Integer> getListOfAllDeliveryNumberForADeliveryPartner(String deliveryPartnerId) {
        return deliveryPartnerRepository.getListOfAllDeliveryNumberForADeliveryPartner(deliveryPartnerId);
    }
}
