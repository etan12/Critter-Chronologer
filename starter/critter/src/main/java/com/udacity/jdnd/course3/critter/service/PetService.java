package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Pet savePet(Pet pet) {
        Pet savedPet = petRepository.save(pet);

        // Add pet to list of pets that the customer has
        Customer customerOfPet = savedPet.getCustomer();
        if (customerOfPet != null) {
            // This is the customer's first pet
            if (customerOfPet.getPets() == null) {
                customerOfPet.setPets(new ArrayList<>());
            }

            customerOfPet.getPets().add(savedPet);
            customerRepository.save(customerOfPet);
        }

        return savedPet;
    }

    public Pet getPet(long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);
    }
}
