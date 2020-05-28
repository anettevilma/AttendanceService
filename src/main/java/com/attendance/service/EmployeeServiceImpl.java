package com.attendance.service;
import java.sql.SQLException;
import java.util.List;

import com.attendance.model.Employeevalidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.attendance.model.Employee;
import com.attendance.model.EmployeeRowMapper;
import com.attendance.dao.EmployeeDaoImpl;

import javax.sql.DataSource;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDaoImpl employeeDao;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDao.getAllEmployees();
    }

    @Override
    public Employee findEmployeeById(String id) {
        return employeeDao.findEmployeeById(id);
    }

    @Override
    public void addEmployee(Employee employee) {
         employeeDao.addEmployee(employee);
    }
    @Override
    public boolean validateuser(Employeevalidate employeevalidate, DataSource pool) throws SQLException {
        return employeeDao.validateuser(employeevalidate,pool);
    }

  /*  @Override
    public void updateEmployee(Employee employee) {
        employeeDao.updateEmployee(employee);
    }
*/
    @Override
    public void deleteEmployee(int id) {
        employeeDao.deleteEmployee(id);
    }
}
