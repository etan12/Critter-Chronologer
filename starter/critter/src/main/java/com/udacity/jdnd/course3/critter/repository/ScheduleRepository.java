package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // Find schedule with employee
    List<Schedule> findAllByEmployeesContaining(Employee employee);
    // Find all schedules that have pet
    List<Schedule> findAllByPetsContaining(Pet pet);
    // Find all schedules that have a pet that is tied to customerId (owner)
    List<Schedule> findAllByPetsOwnerId(long customerId);
}
