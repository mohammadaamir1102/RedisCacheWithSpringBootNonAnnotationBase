package com.centre.lecture.service;

import com.centre.lecture.entity.Employee;
import com.centre.lecture.response.EmployeeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse saveUser(Employee employee) throws JsonProcessingException;
    EmployeeResponse getEmployeeById(Long id);

    String deleteById(Long id);

    String deleteByMultiIds(List<Long> ids);
}
