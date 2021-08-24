package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public Schedule createSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        List<Pet> pets = petRepository.findAllById(petIds);

        // Find and set employees and pets to the schedule before saving schedule
        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        return scheduleRepository.findAllByEmployeesContaining(employee);
    }

    public List<Schedule> getScheduleForPet(long petId) {
        Pet pet = petRepository.getOne(petId);
        return scheduleRepository.findAllByPetsContaining(pet);
    }

    public List<Schedule> getScheduleForCustomer(long customerId) {
        return scheduleRepository.findAllByPetsOwnerId(customerId);
    }
}
