package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.createSchedule(convertScheduleDTOToSchedule(scheduleDTO), scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds());
        return convertScheduleToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return schedules.stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
        return schedules.stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
        return schedules.stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
        return schedules.stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    public Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }

    public ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        // Collect list of employeeIds
        List<Long> employeeIds = schedule.getEmployees().stream()
                .map(employee -> employee.getId())
                .collect(Collectors.toList());

        // Collect list of petIds
        List<Long> petIds = schedule.getPets().stream()
                .map(pet -> pet.getId())
                .collect(Collectors.toList());

        // Ids are returned as part of the scheduleDTO
        scheduleDTO.setEmployeeIds(employeeIds);
        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;
    }
}
