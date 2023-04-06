package net.javaguides.springboot.controller;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository empoyeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees(){
        return empoyeeRepository.findAll();
    }

    //build create employee REST API
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee){
        return empoyeeRepository.save(employee);
    }

    //build get employee by id REST API
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){
        Employee employee = empoyeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id:" +id));
        return ResponseEntity.ok(employee);
    }

    //build update employee REST API
    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employeeDetails){
        Employee updateEmployee = empoyeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " +id));

        updateEmployee.setFirstname(employeeDetails.getFirstname());
        updateEmployee.setLastname(employeeDetails.getLastname());
        updateEmployee.setEmailId(employeeDetails.getEmailId());

        empoyeeRepository.save(updateEmployee);

        return ResponseEntity.ok(updateEmployee);
    }

    //build delete employee REST API
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id){

        Employee employee = empoyeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id: " + id));

        empoyeeRepository.delete(employee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
