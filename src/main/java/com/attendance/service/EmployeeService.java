package com.attendance.service;
import java.sql.SQLException;
import java.util.List;
import com.attendance.model.Employee;
import com.attendance.model.Employeevalidate;

import javax.sql.DataSource;

public interface EmployeeService {

    public List<Employee> getAllEmployees();

    public Employee findEmployeeById(String id);

    public void addEmployee(Employee employee);

    //public void updateEmployee(Employee employee);

    public boolean validateuser(Employeevalidate employeevalidate, DataSource pool) throws SQLException;

    public void deleteEmployee(int id);
}
