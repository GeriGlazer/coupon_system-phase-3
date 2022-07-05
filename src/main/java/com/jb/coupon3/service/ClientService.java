package com.jb.coupon3.service;

import com.jb.coupon3.exceptions.CustomExceptions;
import com.jb.coupon3.repositories.CompanyRepo;
import com.jb.coupon3.repositories.CouponRepo;
import com.jb.coupon3.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClientService {
    @Autowired
    protected CompanyRepo companyRepo;
    @Autowired
    protected CouponRepo couponRepo;
    @Autowired
    protected CustomerRepo customerRepo;

    public abstract boolean login(String email, String password)throws CustomExceptions;
}
