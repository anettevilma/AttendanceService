package com.attendance.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.attendance.model.Employee;
import com.attendance.model.Employeevalidate;
import com.attendance.model.EmployeeRowMapper;

import javax.servlet.ServletException;
import javax.sql.DataSource;

@Transactional
@Repository
public class EmployeeDaoImpl implements EmployeeDao {


    @Autowired
    private JdbcTemplate jdbcTemplate;
    private EmployeeRowMapper rowmapper;

    @Override
    public List<Employee> getAllEmployees() {
        String query = "SELECT * from employee";
        RowMapper<Employee> rowMapper = new EmployeeRowMapper();
        List<Employee> list = jdbcTemplate.query(query, rowMapper);

        return list;
    }

    @Override
    public Employee findEmployeeById(String id) {
        String query = "SELECT * FROM employee WHERE emp_id = ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        Employee employee = jdbcTemplate.queryForObject(query, rowMapper, id);
        return employee;
    }
    @Override
    public boolean validateuser(Employeevalidate employeevalidate, DataSource pool) throws SQLException {
        String query = "SELECT * FROM employee WHERE emp_id = ?";
         RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
         Employee employee = jdbcTemplate.queryForObject(query, rowMapper, employeevalidate.getEmp_id());

                //System.out.println("Value of password in table::"+employee.getPassword());
                //System.out.println("Value of password in json::"+employeevalidate.getPassword());
                if (employee.getPassword().equals(employeevalidate.getPassword())) {
                    // System.out.println("Both values are equal");
                    return true;
                } else
                    return false;
        }


/*
@Override
    public boolean validateuser(Employeevalidate employeevalidate, DataSource pool) throws SQLException {
        //String query = "SELECT * FROM employee WHERE emp_id = ?";
        // RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        // Employee employee = jdbcTemplate.queryForObject(query, rowMapper, employeevalidate.getEmp_id());
        try {
            // PreparedStatements are compiled by the database immediately and executed at a later date.
            // Most databases cache previously compiled queries, which improves efficiency.
            Connection conn = pool.getConnection();
            String query = "SELECT * FROM employee WHERE emp_id = ?";
                PreparedStatement validatestmt = conn.prepareStatement(query);
                ResultSet validaters = validatestmt.executeQuery();
                Employee employee = rowmapper.mapRow(validaters, 1);
                //System.out.println("Value of password in table::"+employee.getPassword());
                //System.out.println("Value of password in json::"+employeevalidate.getPassword());
                if (employee.getPassword().equals(employeevalidate.getPassword())) {
                    // System.out.println("Both values are equal");
                    return true;
                } else
                    return false;


        }catch (SQLException ex) {
            System.out.println("SQLException");
            return false;
        }
    }

 */



        @Override
    public void addEmployee(Employee employee) {
            String query = "INSERT INTO employee(emp_id, emp_name, email, designation, manager_id,customer,project,managername,password) VALUES(?, ?, ?,?, ?, ?, ?,?,?)";
        jdbcTemplate.update(query, employee.getEmpId(), employee.getEmpName(), employee.getEmail(), employee.getDesignation(), employee.getManagerId(),employee.getCustomer(),employee.getProject(),employee.getManagername(),employee.getPassword());

    }

    /*@Override
    public void updateEmployee(Employee employee) {
        String query = "UPDATE employees SET first_name=?, last_name=?, email=?, phone=?, job_title=? WHERE employee_id=?";
        jdbcTemplate.update(query, employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getPhone(), employee.getJobTitle(), employee.getEmployeeId());

    }*/

    @Override
    public void deleteEmployee(int id) {
        String query = "DELETE FROM employee WHERE employee_id=?";
        jdbcTemplate.update(query, id);
    }
}
