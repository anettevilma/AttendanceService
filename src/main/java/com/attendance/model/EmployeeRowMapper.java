package com.attendance.model;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
public class EmployeeRowMapper implements RowMapper<Employee>{

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setEmpId(rs.getString("emp_id"));
        employee.setEmpName(rs.getString("emp_name"));
        employee.setEmail(rs.getString("email"));
        employee.setDesignation(rs.getString("designaton"));
        employee.setManagerId(rs.getString("manager_id"));
        employee.setCustomer(rs.getString("customer"));
        employee.setProject(rs.getString("project"));
        employee.setManagername(rs.getString("managername"));
        employee.setPassword(rs.getString("password"));
        return employee;
}
}
