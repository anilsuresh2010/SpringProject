package com.example.demo.controller;

import com.example.demo.entities.Employee;
import com.example.demo.services.EmployeeService;
import com.example.demo.services.impl.EmployeeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee emp){
        Employee employee = employeeService.createEmp(emp);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @GetMapping("/{empId}")
    public ResponseEntity<Employee> getEmpById(@Valid @PathVariable Long empId){
        Employee employee =employeeService.getEmpById(empId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployee(){
        List<Employee> employeeList = employeeService.getAllEmp();
        return ResponseEntity.ok(employeeList);
    }

    @PutMapping("/{empId}")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody  Employee employee, @PathVariable  Long empId){
        Employee emp = employeeService.updateEmp(employee,empId);
        return  ResponseEntity.ok(emp);
    }

    @DeleteMapping("/{empId}")
    public  ResponseEntity<Void> deleteEmp(Long empId){
        employeeService.deleteEmp(empId);
        return ResponseEntity.noContent().build();
    }

}
