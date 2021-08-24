package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetService petService;

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(long customerId) {
        return customerRepository.getOne(customerId);
    }

    public Customer getOwnerByPet(long petId) {
        Pet pet = petService.getPet(petId);
        return customerRepository.findByPetsContaining(pet);
    }
}
