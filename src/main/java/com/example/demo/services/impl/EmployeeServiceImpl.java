package com.example.demo.services.impl;

import com.example.demo.entities.Employee;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.services.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Employee getEmpById(Long empId){
        return employeeRepository.findById(empId).orElseThrow(() -> new RuntimeException(" Employee not found . "));
    }

    public List<Employee> getAllEmp(){
        return employeeRepository.findAll();
    }

    public Employee createEmp(Employee employee){

        return employeeRepository.save(employee);
    }

    public Employee updateEmp(Employee employee, Long empId){
        Employee existingEmp = employeeRepository.findById(empId).orElseThrow(() -> new RuntimeException( "Employee not found . "));
        existingEmp.setId(employee.getId());
        existingEmp.setName(employee.getName());
        existingEmp.setEmail(employee.getEmail());
        existingEmp.setSalary(employee.getSalary());
        return employeeRepository.save(existingEmp);
    }

    public void deleteEmp(Long empId){
        Employee employeeNotFoundToDelete = employeeRepository.findById(empId).orElseThrow(() -> new RuntimeException("Employee not found to delete "));
            employeeRepository.delete(employeeNotFoundToDelete);
    }

}
