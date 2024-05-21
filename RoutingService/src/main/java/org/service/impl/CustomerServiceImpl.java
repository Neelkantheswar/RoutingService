package org.service.impl;

import org.entity.Address;
import org.entity.Customer;
import org.repository.CustomerRepository;
import org.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public String addCustomer(String name, String email, Address address) {
        Customer customer = new Customer(name, email, address);
        customerRepository.addCustomer(customer);
        return customer.getId();
    }

    @Override
    public void removeCustomer(String customerId) {
        customerRepository.removeCustomer(customerId);
    }

    @Override
    public Customer getCustomer(String customerId) {
        return customerRepository.findCustomerById(customerId);
    }
}
