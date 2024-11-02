package com.centre.lecture.controller;

import com.centre.lecture.entity.Employee;
import com.centre.lecture.response.EmployeeResponse;
import com.centre.lecture.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/save")
    public ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody Employee employee) {
        EmployeeResponse employeeResponse;
        try {
            employeeResponse = employeeService.saveUser(employee);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable Long id){
        EmployeeResponse employeeResponse;
        try {
            employeeResponse = employeeService.getEmployeeById(id);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable Long id){
        return employeeService.deleteById(id);
    }

    @DeleteMapping("/deleteByMultiId")
    public String deleteByMultiIds(@RequestBody List<Long> ids){
        return employeeService.deleteByMultiIds(ids);
    }
}
