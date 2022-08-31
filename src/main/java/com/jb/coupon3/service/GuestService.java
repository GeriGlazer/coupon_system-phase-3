package com.jb.coupon3.service;

import com.jb.coupon3.beans.Company;
import com.jb.coupon3.beans.Coupon;
import com.jb.coupon3.beans.Customer;
import com.jb.coupon3.exceptions.CustomExceptions;
import com.jb.coupon3.exceptions.OptionalExceptionMessages;
import com.jb.coupon3.repositories.CompanyRepo;
import com.jb.coupon3.repositories.CouponRepo;
import com.jb.coupon3.repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {


    @Autowired
    private CouponRepo couponRepo;
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private CustomerRepo customerRepo;

    public GuestService() {
    }

   public List<Coupon> getAllCoupons() throws CustomExceptions {
        //List<coupons> list = findAll()
       //if(list.isEmptyu)
        if(couponRepo.findAll().isEmpty()){
            throw new CustomExceptions(OptionalExceptionMessages.EMPTY_LIST);
        }
        //return list
        return couponRepo.findAll();
    }

    public void addCompany(Company company) throws CustomExceptions {
        if (companyRepo.existsByName(company.getName()) || companyRepo.existsByEmail(company.getEmail())) {
            throw new CustomExceptions(OptionalExceptionMessages.EMAIL_OR_NAME_EXISTS);
        }
        companyRepo.save(company);
        System.out.println("Company added successfully");
    }

    public void addCustomer(Customer customer) throws CustomExceptions {
        if (customerRepo.existsByEmail(customer.getEmail())) {
            throw new CustomExceptions(OptionalExceptionMessages.EMAIL_EXISTS);
        }
        customerRepo.save(customer);
        System.out.println("Customer added successfully");
    }



}
