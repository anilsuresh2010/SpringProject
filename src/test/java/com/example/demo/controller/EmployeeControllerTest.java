package com.example.demo.controller;

import com.example.demo.entities.Employee;
import com.example.demo.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateEmployee() throws Exception {
        Employee employee = new Employee(1L, "Anil", "anil@example.com", 85000.0);

        when(employeeService.createEmp(org.mockito.ArgumentMatchers.any(Employee.class)))
                .thenReturn(employee);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Anil"))
                .andExpect(jsonPath("$.email").value("anil@example.com"))
                .andExpect(jsonPath("$.salary").value(85000.0));
    }

    @Test
    void testGetEmployeeById() throws Exception {
        Employee employee = new Employee(1L, "Anil", "anil@example.com", 85000.0);

        when(employeeService.getEmpById(1L)).thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Anil"))
                .andExpect(jsonPath("$.email").value("anil@example.com"))
                .andExpect(jsonPath("$.salary").value(85000.0));
    }

    @Test
    void testGetAllEmployees() throws Exception {
        List<Employee> employees = Arrays.asList(
                new Employee(1L, "Anil", "anil@example.com", 85000.0),
                new Employee(2L, "Rahul", "rahul@example.com", 90000.0)
        );

        when(employeeService.getAllEmp()).thenReturn(employees);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(employees.size()))
                .andExpect(jsonPath("$[0].name").value("Anil"))
                .andExpect(jsonPath("$[1].name").value("Rahul"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        Employee employee = new Employee(1L, "Anil Updated", "anil.updated@example.com", 95000.0);

        when(employeeService.updateEmp(org.mockito.ArgumentMatchers.any(Employee.class), org.mockito.ArgumentMatchers.eq(1L)))
                .thenReturn(employee);

        mockMvc.perform(put("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Anil Updated"))
                .andExpect(jsonPath("$.email").value("anil.updated@example.com"))
                .andExpect(jsonPath("$.salary").value(95000.0));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmp(1L);

        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());
    }
}