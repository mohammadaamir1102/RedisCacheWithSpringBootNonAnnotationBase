package com.centre.lecture.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EmployeeResponse(Long id, String fullName, String email, LocalDate dob, int age) {
}
