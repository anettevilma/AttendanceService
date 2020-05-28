package com.attendance.controller;

import java.util.List;

import com.attendance.dao.ConnectionPoolContextListener;
import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.attendance.model.Employee;
import com.attendance.model.Employeevalidate;
import com.attendance.service.EmployeeServiceImpl;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.storage.*;
import javax.sql.DataSource;

@RestController
@CrossOrigin

@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;
   // private ConnectionPoolContextListener contextListener;



    @RequestMapping(value= {"/", "/list"}, method=RequestMethod.GET)
    public ModelAndView getAllEmployees() throws Exception{
        ModelAndView model = new ModelAndView();
        List<Employee> list = employeeService.getAllEmployees();

        model.addObject("employee_list", list);
        model.setViewName("employee_list");
        return model;
    }

    /*@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
    public ModelAndView editEmployee(@PathVariable int id) {
        ModelAndView model = new ModelAndView();

        Employee employee = employeeService.findEmployeeById(id);
        model.addObject("employeeForm", employee);

        model.setViewName("employee_form");
        return model;
    }*/
    @RequestMapping(value = "/validate", headers = {
            "content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public String validateEmployee(@RequestBody Employeevalidate employeevalidate, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("application/json");
        DataSource pool = (DataSource) request.getServletContext().getAttribute("my-pool");
        JSONObject respjson = new JSONObject();
        boolean result;
        result=employeeService.validateuser(employeevalidate,pool);
        Employee employee = employeeService.findEmployeeById(employeevalidate.getEmp_id());
        respjson.put("validuser", result);
        respjson.put("empid",employee.getEmpId());
        respjson.put("empname",employee.getEmpName());
        respjson.put("email",employee.getEmail());
        respjson.put("designation",employee.getDesignation());
        respjson.put("managername",employee.getManagername());

        return respjson.toString();
    }

    @RequestMapping(value = "/add", headers = {
            "content-type=application/json" }, consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public String addEmployee(@RequestBody Employee employee, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("application/json");
        JSONObject respjson = new JSONObject();
        employeeService.addEmployee(employee);

        // Employee employee = new Employee(employee);
        respjson.put("status", "Successfully created");
        return respjson.toString();
    }

  /*  @RequestMapping(value="/save", method=RequestMethod.POST)
    public ModelAndView saveOrUpdate(@ModelAttribute("employeeForm") Employee employee) {
        if(employee.getEmployeeId() != null) {
            employeeService.updateEmployee(employee);
        } else {
            employeeService.addEmployee(employee);
        }

        return new ModelAndView("redirect:/employee/list");
    }*/

    @RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
    public ModelAndView deleteEmployee(@PathVariable("id") int id) {
        employeeService.deleteEmployee(id);

        return new ModelAndView("redirect:/employee/list");
    }

}
