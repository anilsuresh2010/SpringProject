package com.example.demo.service.impl;

import com.example.demo.entities.Employee;
import com.example.demo.repositories.EmployeeRepository;
import com.example.demo.services.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void testCreateEmployee() {
        Employee employee = new Employee(1L, "Anil", "anil@example.com", 85000.0);

        when(employeeRepository.save(employee)).thenReturn(employee);

        Employee savedEmployee = employeeService.createEmp(employee);

        assertNotNull(savedEmployee);
        assertEquals("Anil", savedEmployee.getName());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    void testGetEmployeeById() {
        Employee employee = new Employee(1L, "Anil", "anil@example.com", 85000.0);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee foundEmployee = employeeService.getEmpById(1L);

        assertNotNull(foundEmployee);
        assertEquals(1L, foundEmployee.getId());
        assertEquals("Anil", foundEmployee.getName());
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = Arrays.asList(
                new Employee(1L, "Anil", "anil@example.com", 85000.0),
                new Employee(2L, "Rahul", "rahul@example.com", 90000.0)
        );

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmp();

        assertEquals(2, result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testUpdateEmployee() {
        Employee existing = new Employee(1L, "Anil", "anil@example.com", 85000.0);
        Employee updated = new Employee(1L, "Anil Updated", "anil.updated@example.com", 95000.0);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(employeeRepository.save(any(Employee.class))).thenReturn(updated);

        Employee result = employeeService.updateEmp(updated, 1L);

        assertNotNull(result);
        assertEquals("Anil Updated", result.getName());
        assertEquals("anil.updated@example.com", result.getEmail());
        assertEquals(95000.0, result.getSalary());
    }

    @Test
    void testDeleteEmployee() {
        Employee employee = new Employee(1L, "Anil", "anil@example.com", 85000.0);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        employeeService.deleteEmp(1L);

        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> employeeService.getEmpById(1L));

        assertEquals(" Employee not found . ", exception.getMessage());
    }
}