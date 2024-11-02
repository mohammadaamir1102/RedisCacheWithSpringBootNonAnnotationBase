package com.centre.lecture.service.impl;

import com.centre.lecture.entity.Employee;
import com.centre.lecture.repo.EmployeeRepository;
import com.centre.lecture.response.EmployeeResponse;
import com.centre.lecture.service.EmployeeService;
import com.centre.lecture.util.CommonUtil;
import com.centre.lecture.util.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RedisService redisService;
    private static final String EMPLOYEE_KEY = "EMP_";

    @Override
    public EmployeeResponse saveUser(Employee employee) throws JsonProcessingException {
        employee.setAge(CommonUtil.calculateAge(employee.getDob().toString()));
        Employee savedEmployee = employeeRepository.save(employee);
        redisService.setValueByKey(EMPLOYEE_KEY + savedEmployee.getId().toString(), savedEmployee, 300l);
        return CommonUtil.entityToEmployeeResponse(savedEmployee);
    }

    @Override
    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = redisService.getValueByKey(EMPLOYEE_KEY + id.toString(), Employee.class);
        if (employee != null) {
            return CommonUtil.entityToEmployeeResponse(employee);
        }
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        return employeeOptional.isPresent() ? CommonUtil.entityToEmployeeResponse(employeeOptional.get()) : null;
    }

    @Override
    public String deleteById(Long id) {
        boolean isDeleted = redisService.deleteFromRedisCache(EMPLOYEE_KEY + id.toString());
        return isDeleted ? id.toString() + " is deleted Successfully !" : "Key not found !";
    }

    @Override
    public String deleteByMultiIds(List<Long> ids) {
        List<String> listOfKeys = ids.stream().map(id -> EMPLOYEE_KEY + id).toList();
        Long deletedCount = redisService.deleteMultiRecordFromRedisCache(listOfKeys);
        return deletedCount > 0 ? deletedCount.toString() + " records deleted !" : "key not found";
    }
}
