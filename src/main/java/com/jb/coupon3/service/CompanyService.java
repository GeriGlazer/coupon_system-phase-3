package com.jb.coupon3.service;

import com.jb.coupon3.beans.Category;
import com.jb.coupon3.beans.Company;
import com.jb.coupon3.beans.Coupon;
import com.jb.coupon3.exceptions.CustomExceptions;
import com.jb.coupon3.exceptions.OptionalExceptionMessages;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class CompanyService extends ClientService{
    private int companyId = 0;

    public CompanyService() {
    }

    @Override
    public boolean login(String email, String password) throws CustomExceptions {
        boolean connect = companyRepo.existsByEmailAndPassword(email, password);
        if(connect) {
            Integer id = companyRepo.findByEmailAndPassword(email, password).getId();
            companyId = id;
            System.out.println("Company connected.");
            return true;
        }else{
            throw new CustomExceptions(OptionalExceptionMessages.WRONG_EMAIL_OR_PASSWORD);
        }
    }

    public void updateCompany(Company company) throws CustomExceptions {
        if(!companyRepo.existsById(company.getId())) {
            throw new CustomExceptions(OptionalExceptionMessages.COMPANY_NOT_FOUND);
        }
        if (!Objects.equals(company.getName(), companyRepo.findById(company.getId()).get().getName())) {
            throw new CustomExceptions(OptionalExceptionMessages.CANT_UPDATE_COMPANY_NAME);
        }
        companyRepo.save(company);
        System.out.println("Company updated successfully");
    }

    //add coupon
    public void addCoupon(Coupon coupon) throws CustomExceptions {
        if (companyId == 0){
            throw new CustomExceptions(OptionalExceptionMessages.LOGIN_EXCEPTION);
        }
        coupon.setCompanyId(companyId);
//        validStartDate(coupon.getStartDate());
//        validEndDate(coupon.getEndDate(), coupon);
        if (couponRepo.existsByTitleAndCompanyId(coupon.getTitle(), coupon.getCompanyId())) {
            System.out.println("This coupon title already exist for this company.");
            throw new CustomExceptions(OptionalExceptionMessages.CANT_ADD_COUPON);
        }
        couponRepo.save(coupon);
        System.out.println("Coupon added successfully");
    }

    //update coupon
    public void updateCoupon(Coupon coupon) throws CustomExceptions {
        if (couponRepo.findById(coupon.getId()).isEmpty()) {
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND);
        }
        if (coupon.getCompanyId()!=this.companyId){
            throw new CustomExceptions(OptionalExceptionMessages.DONT_HAVE_PERMISSION);
        }
        validEndDate(coupon.getEndDate(), coupon);
        coupon.setCompanyId(this.companyId);
        couponRepo.save(coupon);
        System.out.println("Coupon updated successfully");
    }

    //delete coupon
    public void deleteCoupon(int couponId) throws CustomExceptions {
        if (couponRepo.findById(couponId).isEmpty()) {
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND);
        }
        if (couponRepo.findById(couponId).get().getCompanyId()!= companyId){
            throw new CustomExceptions(OptionalExceptionMessages.DONT_HAVE_PERMISSION);
        }
        couponRepo.deleteById(couponId);
        System.out.println("Coupon deleted successfully");
    }

    //get company coupons
    public Set<Coupon> getAllCompanyCoupons() throws CustomExceptions {
        if (companyRepo.findCompanyCoupons(companyId).isEmpty()){
            throw new CustomExceptions(OptionalExceptionMessages.EMPTY_LIST);
        }else {
            return companyRepo.findCompanyCoupons(companyId);
        }
    }

    //get one coupon
    public Coupon getOneCoupon(int couponId) throws CustomExceptions {
        if (couponRepo.findById(couponId).isEmpty()) {
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND);
        }
        return couponRepo.findById(couponId).get();
    }

    //get company coupons by category
    public Set<Coupon> getCompanyCouponsByCategory(Category category) throws CustomExceptions {
        Set<Coupon> coupons = companyRepo.findCompanyCouponsByCategory(category, companyId);
        if (coupons.isEmpty()) {
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND_BY_CATEGORY);
        }
        return coupons;
    }

    //get company coupon by max price
    public Set<Coupon> getCompanyCouponByMaxPrice(double maxPrice) throws CustomExceptions {
        Set<Coupon> coupons = companyRepo.findCompanyCouponsByMaxPrice(maxPrice, companyId);
        if (coupons.isEmpty()) {
            throw new CustomExceptions(OptionalExceptionMessages.COUPON_NOT_FOUND_MAX_PRICE);
        }
        return coupons;
    }

    //get company details
    public Company getCompanyDetails() throws CustomExceptions {
        Optional<Company> optionalCompany = companyRepo.findById(companyId);
        if (optionalCompany == null) {
            throw new CustomExceptions(OptionalExceptionMessages.COMPANY_NOT_FOUND);
        }
        return optionalCompany.get();
    }

    //Start date validation
    private void validStartDate(Date date) throws CustomExceptions {
        Date currDate = Date.valueOf(LocalDate.now());
        if (date.before(currDate)) {
            throw new CustomExceptions(OptionalExceptionMessages.START_DATE_EXCEPTION);
        }
    }

    //end date validation
    private void validEndDate(Date date, Coupon coupon) throws CustomExceptions {
        Date StartDate = coupon.getStartDate();
        if (date.before(StartDate) || date.before(Date.valueOf(LocalDate.now()))) {
            throw new CustomExceptions(OptionalExceptionMessages.END_DATE_EXCEPTION);
        }
    }
}
