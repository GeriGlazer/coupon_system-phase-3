package com.jb.coupon2demo.clr.servicesTests;

import com.jb.coupon2demo.beans.Company;
import com.jb.coupon2demo.beans.Customer;
import com.jb.coupon2demo.exceptions.CustomExceptions;
import com.jb.coupon2demo.repositories.CompanyRepo;
import com.jb.coupon2demo.repositories.CustomerRepo;
import com.jb.coupon2demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@Order(5)
@RequiredArgsConstructor
public class AdminTests implements CommandLineRunner {
    private final AdminService adminService;
    private final CompanyRepo companyRepo;
    private final CustomerRepo customerRepo;


    @Override
    public void run(String... args) throws Exception {
        //Admin login
        adminService.login("admin@admin.com", "admin");
        //add new company
        adminService.addCompany(new Company(0, "oneplus", "aone@plusone.com", "1+1", null));
        //update company
        //get one company test
        try {
            Company company = adminService.getOneCompany(companyRepo.findByName("oneplus").getId());
            company.setEmail("one@plusone.com");
            adminService.updateCompany(company);
            System.out.println(company);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
        //get all companies
        System.out.println(adminService.getAllCompanies());
        //delete company
        adminService.deleteCompany(adminService.getOneCompany(companyRepo.findByName("oneplus").getId()).getId());
        //
        //add customer
        adminService.addCustomer(new Customer(
                0, "guy", "endvelt", "guy@gmail.com", "guyguy", null));

        //update customer
        //get one customer
        try {
        Customer customer = adminService.getOneCustomer(customerRepo.findByEmail("guy@gmail.com").getId());
        customer.setPassword("guyendvelt");
        adminService.updateCustomer(customer);
        } catch (CustomExceptions customExceptions) {
            System.out.println(customExceptions.getMessage());
        }
        //get all customers
        System.out.println(adminService.getAllCustomers());
        //delete customer
        adminService.deleteCustomer(adminService.getOneCustomer(customerRepo.findByEmail("guy@gmail.com").getId()).getId());
        System.out.println(adminService.getAllCustomers());
    }

}