package com.jb.coupon3.controllers;

import com.jb.coupon3.beans.ClientType;
import com.jb.coupon3.beans.Company;
import com.jb.coupon3.beans.Customer;
import com.jb.coupon3.exceptions.CustomExceptions;
import com.jb.coupon3.security.JWTutil;
import com.jb.coupon3.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*")
@RequestMapping("/GYGNcoupons/guest")
public class GuestController {
    private final GuestService guestService;


    @GetMapping
    public ResponseEntity<?> allCoupons() throws CustomExceptions {
        return ResponseEntity.ok()
                .body(guestService.getAllCoupons());
    }

    /**
     * this method is for adding a new company in to the database
     * @param company new company information
     * @return new token for more admin actions and request status response
     * @throws CustomExceptions in case the server found a company with similar data
     */
    //todo: check if @CrossOrigin is necessary in every controller
    @PostMapping("/addCompany")
    public ResponseEntity<?> addCompany (@RequestBody Company company) throws CustomExceptions {
        guestService.addCompany(company);
        return ResponseEntity.ok()
                .body(company);
    }

    /**
     * this method is for adding a new customer in to the database
     * @param customer new customer information
     * @return new token for more admin actions and request status response
     * @throws CustomExceptions in case the server found a customer with similar data
     */
    @PostMapping("/addCustomer")
    public ResponseEntity<?> addCustomer (@RequestBody Customer customer) throws CustomExceptions {

       guestService.addCustomer(customer);
        return ResponseEntity.ok()
                .body(customer);
    }




}
