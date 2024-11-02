package com.centre.lecture.util;

import com.centre.lecture.entity.Employee;
import com.centre.lecture.response.EmployeeResponse;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class CommonUtil {

    public static int calculateAge(String dob){
        LocalDate birtDate = null;
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            birtDate = LocalDate.parse(dob, dateTimeFormatter);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Period.between(birtDate, LocalDate.now()).getYears();
    }

    public static EmployeeResponse entityToEmployeeResponse(Employee employee){
        if(employee == null){
            return null;
        }
        return EmployeeResponse.builder()
                .id(employee.getId())
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .dob(employee.getDob())
                .age(employee.getAge())
                .build();
    }
}
