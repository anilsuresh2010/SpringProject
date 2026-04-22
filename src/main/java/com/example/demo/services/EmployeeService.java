package com.example.demo.services;

import com.example.demo.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EmployeeService {

    public Employee getEmpById(Long id);

    public List<Employee> getAllEmp();

    public Employee createEmp(Employee emp);

    public Employee updateEmp(Employee employee, Long empId);

    public  void deleteEmp(Long empId);
}
