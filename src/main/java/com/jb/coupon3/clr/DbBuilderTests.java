package com.jb.coupon3.clr;

import com.jb.coupon3.beans.Category;
import com.jb.coupon3.beans.Company;
import com.jb.coupon3.beans.Coupon;
import com.jb.coupon3.beans.Customer;
import com.jb.coupon3.repositories.CompanyRepo;
import com.jb.coupon3.repositories.CouponRepo;
import com.jb.coupon3.repositories.CustomerRepo;
import com.jb.coupon3.service.AdminService;
import com.jb.coupon3.service.CompanyService;
import com.jb.coupon3.thread.CouponExpirationDailyJob;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
/**
 * @author Yoav Hacmon, Guy Endvelt, Niv Pablo and Gery Glazer
 * 05.2022
 */

@Component
@RequiredArgsConstructor
@Order(1)
/**
 * this class is used for creating demo information for the system testings
 * implements CommandLineRunner to indicate that a bean(Entity) should run when SpringApplication is initialized
 */
public class DbBuilderTests implements CommandLineRunner {
    private final CompanyRepo companyRepo;
    private final CustomerRepo customerRepo;
    private final CouponRepo couponRepo;
    private final AdminService adminService;
    private final CouponExpirationDailyJob couponExpirationDailyJob;
    private final CompanyService companyService;
    @Override
    public void run(String... args) throws Exception {

        Company comp1 = Company.builder()
                .name("samsung")
                .email("samsung@samsung.com")
                .password("samsam")
                .build();
        Company comp2 = Company.builder()
                .name("rikushet")
                .email("rikushet@rikushet.com")
                .password("riky")
                .build();
        Company comp3 = Company.builder()
                .name("sano")
                .email("sano@sushi.com")
                .password("sanosushi")
                .build();
        companyRepo.saveAll(List.of(comp1, comp2, comp3));

        Coupon coup1 = Coupon.builder()
                .amount(200)
                .category(Category.ELECTRIC_APPLIANCE)
                .companyId(companyRepo.findByName("samsung").getId())
                .description("The most advance tv you'll ever see")
                .price(12000)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(30L)))
                .title("4k TV")
                .build();
        Coupon coup2 = Coupon.builder()
                .amount(100)
                .category(Category.OUTDOOR)
                .companyId(companyRepo.findByName("rikushet").getId())
                .description("No need to be an engineer, just know how to blow!!, ")
                .price(780)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(30L)))
                .title("6P Air tent")
                .build();
        Coupon coup3 = Coupon.builder()
                .amount(220)
                .category(Category.HOUSEHOLD_SUPPLIES)
                .companyId(companyRepo.findByName("samsung").getId())
                .description("Precise navigation. Hygienic cleaning.")
                .price(1200)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(30L)))
                .title("Robot vacuum S5")
                .build();
        Coupon coup4 = Coupon.builder()
                .amount(1000)
                .category(Category.CLEANING_SUPPLIES)
                .companyId(companyRepo.findByName("sano").getId())
                .description("Organic floor cleaning gel, the best your floor can get!")
                .price(15)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(30L)))
                .title("Green Power")
                .build();
        Coupon coup5 = Coupon.builder()
                .amount(100)
                .category(Category.ENTERTAINMENT)
                .companyId(companyRepo.findByName("samsung").getId())
                .description("Update your Compatible Samsung TV with the Newest Smart Technology")
                .price(150)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(30L)))
                .title("Home theater Evolution Kit")
                .build();
        Coupon coup6 = Coupon.builder()
                .amount(500)
                .category(Category.EXTREME)
                .companyId(companyRepo.findByName("rikushet").getId())
                .description("Junior and adult’s photochromic all-weather snowboarding goggles")
                .price(280)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(30L)))
                .title("Snowboarding goggles")
                .build();
        couponRepo.saveAll(List.of(coup1, coup2, coup3, coup4, coup5, coup6));

        Customer cmr1 = Customer.builder()
                .firstName("zeev")
                .lastName("mindali")
                .email("zeevik@gmail.com")
                .password("12345678")
                .build();
        Customer cmr2 = Customer.builder()
                .firstName("geri")
                .lastName("glazer")
                .email("gerig@gmail.com")
                .password("654321")
                .build();
        customerRepo.saveAll(List.of(cmr1, cmr2));
        couponExpirationDailyJob.deleteByDate();
    }
}
