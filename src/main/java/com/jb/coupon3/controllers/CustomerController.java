package com.jb.coupon3.controllers;

import com.jb.coupon3.beans.Category;
import com.jb.coupon3.beans.ClientType;
import com.jb.coupon3.beans.Company;
import com.jb.coupon3.beans.Customer;
import com.jb.coupon3.exceptions.CustomExceptions;
import com.jb.coupon3.security.JWTutil;
import com.jb.coupon3.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * @author Yoav Hacmon, Guy Endvelt, Niv Pablo and Gery Glazer
 * 05.2022
 */

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/GYGNcoupons/customer")
/**
 * this class is used for the customers API's methods implementation
 */
public class CustomerController {
    private final JWTutil jwTutil;
    private final CustomerService customerService;

    /**
     * this HTTP method is for sending to the server that a coupon has been purchased by a customer
     * @param id to purchase a coupon is necessary to insert the coupon id
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more customer actions and request status response
     * @throws CustomExceptions in case one or more of the parameters inserted contains an error
     */
    @PutMapping("/purchaseCoupon/{id}")
    public ResponseEntity<?> purchaseCoupon(@PathVariable int id, @RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.CUSTOMER);
        customerService.purchaseCoupon(id);
        return ResponseEntity.ok()
                .header("Authorization",newToken)
                .body("coupon purchased successfully");
    }

    /**
     * this HTTP method is for retrieving all coupons purchased by a specific customer
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return  new token for more customer actions, the list of coupons requested and request status response
     */
    @GetMapping("/getAllCoupons")
    public ResponseEntity<?> getAllCoupons(@RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.CUSTOMER);
        return ResponseEntity.ok()
                .header("Authorization",newToken)
                .body(customerService.getAllCustomerCoupons());
    }



    //add java docs
    @PutMapping("/updateCustomer")
    public ResponseEntity<?> updateCompany (@RequestBody Customer customer, @RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.CUSTOMER);
        customerService.updateCustomer(customer);
        return ResponseEntity.ok()
                .header("Authorization", newToken)
                .body(customer);
    }

    /**
     * this HTTP method is for retrieving customer purchased coupons filtered by coupons category
     * @param category the type of coupon defined by the creating company
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more customer actions, the list of coupons requested and request status response
     * @throws CustomExceptions in case there are no coupons in database by the requested category
     */
    @GetMapping("/getCouponsByCategory/{category}")
    public ResponseEntity<?> getOneCouponByCategory(@RequestParam Category category, @RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.CUSTOMER);
        return ResponseEntity.ok()
                .header("Authorization",newToken)
                .body(customerService.getCustomerCoupons(category));
    }

    /**
     * this HTTP method is for retrieving customer purchased coupons filtered by price (from maximum and below)
     * @param maxPrice defines the maximum price of the requested coupons.
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more customer actions, the list of coupons requested
     *            (the response will be every coupon with a price below the maximum inserted)
     *            and request status response
     * @throws CustomExceptions in case there are no coupons in database by the requested price or below
     */
    @GetMapping("/getCouponsByMaxPrice/{maxPrice}")
    public ResponseEntity<?> getCouponsByMaxPrice(@PathVariable int maxPrice, @RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.CUSTOMER);
        return ResponseEntity.ok()
                .header("Authorization",newToken)
                .body(customerService.getCustomerCoupons(maxPrice));
    }

    /**
     * this HTTP method is for retrieving a specific customer details
     * the requesting customer is identified in the {@link LoginController} process
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return token for more customer actions, customer details and request status response
     * @throws CustomExceptions in case the requesting customer is not found in database.
     * this can happen if the requesting customer did not enter the system through the login process
     */
    @GetMapping("/customerDetails")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.CUSTOMER);
        return ResponseEntity.ok()
                .header("Authorization",newToken)
                .body(customerService.getCustomerDetails());
    }
}
