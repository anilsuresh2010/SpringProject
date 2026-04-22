package com.example.demo.repositories;

import com.example.demo.entities.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Test save employee")
    void testSaveEmployee() {
        Employee employee = new Employee();
        employee.setName("Anil");
        employee.setEmail("anil@example.com");
        employee.setSalary(85000.0);

        Employee savedEmployee = employeeRepository.save(employee);

        assertNotNull(savedEmployee);
        assertNotNull(savedEmployee.getId());
    }

    @Test
    @DisplayName("Test get employee by id")
    void testGetEmployeeById() {
        Employee employee = new Employee();
        employee.setName("Anil");
        employee.setEmail("anil@example.com");
        employee.setSalary(85000.0);

        Employee savedEmployee = employeeRepository.save(employee);

        Optional<Employee> foundEmployee = employeeRepository.findById(savedEmployee.getId());

        assertTrue(foundEmployee.isPresent());
        assertEquals("Anil", foundEmployee.get().getName());
    }

    @Test
    @DisplayName("Test get all employees")
    void testGetAllEmployees() {
        Employee emp1 = new Employee();
        emp1.setName("Anil");
        emp1.setEmail("anil@example.com");
        emp1.setSalary(85000.0);

        Employee emp2 = new Employee();
        emp2.setName("Rahul");
        emp2.setEmail("rahul@example.com");
        emp2.setSalary(90000.0);

        employeeRepository.save(emp1);
        employeeRepository.save(emp2);

        List<Employee> employees = employeeRepository.findAll();

        assertEquals(2, employees.size());
    }

    @Test
    @DisplayName("Test update employee")
    void testUpdateEmployee() {
        Employee employee = new Employee();
        employee.setName("Anil");
        employee.setEmail("anil@example.com");
        employee.setSalary(85000.0);

        Employee savedEmployee = employeeRepository.save(employee);
        savedEmployee.setName("Anil Updated");

        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        assertEquals("Anil Updated", updatedEmployee.getName());
    }

    @Test
    @DisplayName("Test delete employee")
    void testDeleteEmployee() {
        Employee employee = new Employee();
        employee.setName("Anil");
        employee.setEmail("anil@example.com");
        employee.setSalary(85000.0);

        Employee savedEmployee = employeeRepository.save(employee);
        employeeRepository.deleteById(savedEmployee.getId());

        Optional<Employee> result = employeeRepository.findById(savedEmployee.getId());

        assertFalse(result.isPresent());
    }
}

