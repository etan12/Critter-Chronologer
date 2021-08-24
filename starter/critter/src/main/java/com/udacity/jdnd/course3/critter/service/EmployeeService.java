package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployee(long employeeId) {
        return employeeRepository.getOne(employeeId);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public List<Employee> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
        List<Employee> employeesWithSkills = new ArrayList<>();

        // First find all employees who are available on that particular day of week
        List<Employee> employeesAvailable = employeeRepository.findAllByDaysAvailable(employeeDTO.getDate().getDayOfWeek());

        // Reference: https://www.geeksforgeeks.org/set-containsall-method-in-java-with-examples/
        // Then, filter employees who have ALL the necessary skills required
        for (Employee employee : employeesAvailable) {
            if (employee.getSkills().containsAll(employeeDTO.getSkills())) {
                employeesWithSkills.add(employee);
            }
        }

        return employeesWithSkills;
    }
}
