package org.repository.impl;

import org.entity.Customer;
import org.repository.CustomerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRepositoryImpl implements CustomerRepository {
    private final Map<String,Customer> customerTable;

    public CustomerRepositoryImpl() {
        this.customerTable = new HashMap<>();
    }


    @Override
    public Customer findCustomerById(String customerId) {
        return customerTable.get(customerId);
    }

    @Override
    public void addCustomer(Customer customer) {
        customerTable.put(customer.getId(), customer);
    }

    @Override
    public void updateCustomerName(String customerId, String name) {
        customerTable.get(customerId).setName(name);
    }

    @Override
    public void removeCustomer(String customerId) {
        customerTable.remove(customerId);
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerTable.values().stream().toList();
    }
}
