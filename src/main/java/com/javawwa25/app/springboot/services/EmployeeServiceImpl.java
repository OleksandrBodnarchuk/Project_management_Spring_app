package com.javawwa25.app.springboot.services;

import com.javawwa25.app.springboot.models.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService{
    @Override
    public List<Employee> getAllEmployees() {
        return null;
    }

    @Override
    public void saveEmployee(Employee employee) {

    }

    @Override
    public Employee getEmployeeById(long id) {
        return null;
    }

    @Override
    public void deleteEmployeeById(long id) {

    }

    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        return null;
    }
}
