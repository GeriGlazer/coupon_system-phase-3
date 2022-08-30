package com.jb.coupon3.controllers;

import com.jb.coupon3.beans.ClientType;
import com.jb.coupon3.beans.Company;
import com.jb.coupon3.beans.Customer;
import com.jb.coupon3.exceptions.CustomExceptions;
import com.jb.coupon3.security.JWTutil;
import com.jb.coupon3.service.AdminService;
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
@RequestMapping("/GYGNcoupons/admin")
/**
 * this class is used for the administrator API's methods implementation
 */
public class AdminController {
    private final JWTutil jwTutil;
    private final AdminService adminService;


    /**
     * this method is for updating a company information
     * @param company to identify the company to update
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more admin actions and request status response
     * @throws CustomExceptions if the field to update is the company's name
     */
    @PutMapping("/updateCompany")
    public ResponseEntity<?> updateCompany (@RequestBody Company company, @RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.ADMIN);
        adminService.updateCompany(company);
        return ResponseEntity.ok()
        .header("Authorization", newToken)
                .body(company);
    }


    /**
     * this method is for deleting a company from the database
     * @param companyId to identify the company to delete
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more admin actions and request status response
     * @throws CustomExceptions in case the company is not found in database
     */
    @DeleteMapping("/deleteCompany/{companyId}")
    public ResponseEntity<?> deleteCompany(
            @PathVariable int companyId, @RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.ADMIN);
        adminService.deleteCompany(companyId);
        return ResponseEntity.ok()
                .header("Authorization", newToken)
                .body("company deleted");
    }

    /**
     * this HTTP method is for retrieving all companies listed in the server database
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return  new token for more admin actions, the list of companies and request status response
     * @throws CustomExceptions if there are no companies in database
     */
    @GetMapping("/allCompanies")
    public ResponseEntity<?> getAllCompanies (@RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.ADMIN);
        return ResponseEntity.ok()
                .header("Authorization", newToken)
                .body(adminService.getAllCompanies()
                );
    }

    /**
     * this HTTP method is for retrieving one company from the server database
     * @param id to identify the company requested
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more admin actions, the details of the requested company and request status response
     * @throws CustomExceptions in case the server did not found the requested company in database
     */
    @GetMapping("/oneCompany/{id}")
    public ResponseEntity<?> getOneCompany (@PathVariable int id, @RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.ADMIN);
        return ResponseEntity.ok()
                .header("Authorization", newToken)
                .body(adminService.getOneCompany(id)
                );
    }

    /**
     * this method is for updating a customer information
     * @param customer to identify the customer to update
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more admin actions and request status response
     * @throws CustomExceptions if the customer to update is not found in database
     */
    @PutMapping("/updateCustomer")
    public ResponseEntity<?> updateCustomer (@RequestBody Customer customer, @RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.ADMIN);
        adminService.updateCustomer(customer);
        return ResponseEntity.ok()
                .header("Authorization", newToken)
                .body(customer);
    }

    /**
     * this HTTP method is for retrieving one customer from the server database
     * @param id to identify the customer requested
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more admin actions, the details of the requested customer and request status response
     * @throws CustomExceptions in case the server did not found the requested customer in database
     */
    @GetMapping("/oneCustomer/{id}")
    public ResponseEntity<?> getOneCustomer(@PathVariable int id, @RequestHeader(name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.ADMIN);
        return ResponseEntity.ok()
                .header("Authorization", newToken)
                .body(adminService.getOneCustomer(id)
                );
    }

    /**
     * this HTTP method is for retrieving all customer listed in the server database
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return  new token for more admin actions, the list of customers and request status response
     * @throws CustomExceptions if there are no customers in database
     */
    @GetMapping("/allCustomers")
    public ResponseEntity<?> getAllCustomers (@RequestHeader (name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.ADMIN);
        return ResponseEntity.ok()
                .header("Authorization", newToken)
                .body(adminService.getAllCustomers()
                );
    }

    /**
     *  this method is for deleting a customer from the database
     * @param id to identify the customer requested to delete
     * @param token is for security, this string is given by the server when login in.
     *              for further information about token please see {@link JWTutil}
     * @return new token for more admin actions and request status response
     * @throws CustomExceptions in case the server did not found the requested customer in database
     */
    @DeleteMapping("/deleteCustomer/{id}")
    public ResponseEntity<?> deleteCustomer (@PathVariable int id, @RequestHeader (name = "Authorization") String token) throws CustomExceptions {
        String newToken = jwTutil.checkUser(token, ClientType.ADMIN);
        adminService.deleteCustomer(id);
        return ResponseEntity.ok()
                .header("Authorization", newToken)
                .body("customer deleted");
    }
}
