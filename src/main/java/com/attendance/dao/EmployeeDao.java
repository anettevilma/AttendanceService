package com.attendance.dao;
import java.sql.SQLException;
import java.util.List;
import com.attendance.model.*;

import javax.sql.DataSource;

public interface EmployeeDao {

    public List<Employee> getAllEmployees();

    public Employee findEmployeeById(String id);

    public void addEmployee(Employee employee);

    public boolean validateuser(Employeevalidate employeevalidate, DataSource pool) throws SQLException;

  //  public void updateEmployee(Employee employee);

    public void deleteEmployee(int id);
}
