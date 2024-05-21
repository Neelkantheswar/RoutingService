package org.repository;

import org.entity.Customer;

import java.util.List;

public interface CustomerRepository {
    Customer findCustomerById(String customerId);
    void addCustomer(Customer customer);
    void updateCustomerName(String customerId, String name);
    void removeCustomer(String customerId);

    List<Customer> getAllCustomer();
}
