package com.jb.coupon3.service;

import com.jb.coupon3.beans.Category;
import com.jb.coupon3.beans.Company;
import com.jb.coupon3.beans.Coupon;
import com.jb.coupon3.beans.Customer;
import com.jb.coupon3.exceptions.CustomExceptions;
import com.jb.coupon3.exceptions.OptionalExceptionMessages;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;


@Service
public class CustomerService extends ClientService{
    private int customerId;

    @Override
    public boolean login(String email, String password) throws CustomExceptions {
        boolean connect = customerRepo.existsByEmailAndPassword(email, password);
        if(connect) {
            Integer id = customerRepo.findByEmailAndPassword(email, password).getId();
            customerId = id;
            System.out.println("Customer connected.");
            return true;
        } else {
            throw new CustomExceptions(OptionalExceptionMessages.WRONG_EMAIL_OR_PASSWORD);
        }
    }


    public void purchaseCoupon(int couponId) throws CustomExceptions {
        if (!couponRepo.existsById(couponId)){
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND);
        }
        Coupon coupon = couponRepo.findById(couponId).get();
        if(customerRepo.isCouponPurchased(customerId, coupon.getId()).isEmpty()){
            customerRepo.addCouponToCustomer(customerId, coupon.getId());
            coupon.setAmount(coupon.getAmount()-1);
            couponRepo.save(coupon);
            System.out.println("Coupon purchased.");
        }
        else{
            throw new CustomExceptions(OptionalExceptionMessages.CANT_ADD_COUPON);
        }
    }

    public Set<Coupon> getAllCustomerCoupons(){
        return customerRepo.findAllCustomerCoupons(customerId);
    }

    public Set<Coupon> getCustomerCoupons(Category category) throws CustomExceptions {
        Set<Coupon> coupons = customerRepo.findAllCustomerCouponsByCategory(customerId, category);
        if(coupons.isEmpty()){
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND_BY_CATEGORY);
        }
        return coupons;
    }

    public Set<Coupon> getCustomerCoupons(double maxPrice) throws CustomExceptions {
        Set<Coupon> coupons = customerRepo.findAllCustomerCouponsMaxPrice(customerId, maxPrice);
        if(coupons.isEmpty()){
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND_MAX_PRICE);
        }
        return coupons;
    }

    public Customer getCustomerDetails() throws CustomExceptions {
        Optional<Customer> optionalCustomer = customerRepo.findById(customerId);
        if (optionalCustomer.isEmpty()){
            throw new CustomExceptions(OptionalExceptionMessages.CUSTOMER_NOT_FOUND);
        }
        return optionalCustomer.get();
    }
    public void updateCustomer(Customer customer) throws CustomExceptions {
        if (!customerRepo.existsById(customer.getId())) {
            throw new CustomExceptions(OptionalExceptionMessages.CUSTOMER_NOT_FOUND);
        }
        if (!Objects.equals(customer.getFirstName(), customerRepo.findById(customer.getId()).get().getFirstName())) {
            throw new CustomExceptions(OptionalExceptionMessages.CANT_UPDATE_COMPANY_NAME);
        }
        customerRepo.save(customer);
    }
}
