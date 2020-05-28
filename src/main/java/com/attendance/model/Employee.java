package com.attendance.model;

public class Employee {
    private String empId;
    private String empName;
    private String managerId;
    private String email;
    private String customer;
    private String designation;
    private String managername;
    private String project;
    private String password;

    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public String getDesignation() {
        return designation;
    }

    public void setManagername(String managername) {
        this.managername = managername;
    }
    public String getManagername() {
        return managername;
    }



    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }



    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }



   /* public int getDesktopnum() {
        return desktopnum;
    }

    public void setDesktopnum(int desktopnum) {
        this.desktopnum = desktopnum;
    }*/

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }


    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
