package org.service;

import org.entity.Address;
import org.entity.Customer;

public interface CustomerService {
    String addCustomer(String name, String email, Address address);
    void removeCustomer(String customerId);
    Customer getCustomer(String customerId);
}
