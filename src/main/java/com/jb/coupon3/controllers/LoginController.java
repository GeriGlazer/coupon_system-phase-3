package com.jb.coupon3.controllers;

import com.jb.coupon3.beans.ClientType;
import com.jb.coupon3.beans.UserDetails;
import com.jb.coupon3.exceptions.CustomExceptions;
import com.jb.coupon3.service.LoginService;
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
@RequestMapping("/GYGNcoupons")
/**
 * this class is responsible for the implementation of the login method with API's
 * {@link LoginService#login(String, String, ClientType)}
 */
public class LoginController {
    private final LoginService loginService;

    /**
     * this HTTP request method is for sending information from the client to the server in a secured way
     * the input is not saved in browser history, can't be booked marked or cached
     * @param userDetails uses the entity with the required fields
     * @return a response to the client accepting the request and also produces a security key (token)
     * see {@link com.jb.coupon3.security.JWTutil} for further explanation
     * @throws CustomExceptions in case there is an error produced by wrong data entered by the client
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws CustomExceptions {
        String token = loginService.login(userDetails.getEmail(), userDetails.getPass(), userDetails.getClientType());
        return ResponseEntity.ok()
                .header("Authorization", token )
                .body(userDetails.getClientType() + " connected");
    }
}
