package com.jb.coupon3.service;

import com.jb.coupon3.beans.ClientType;
import com.jb.coupon3.beans.UserDetails;
import com.jb.coupon3.exceptions.CustomExceptions;
import com.jb.coupon3.exceptions.OptionalExceptionMessages;
import com.jb.coupon3.security.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;
    private final GuestService guestService;
    private final JWTutil jwTutil;

    public String login (String email, String password, ClientType clientType) throws CustomExceptions {
        ClientService clientService = null;
        boolean isLogin = false;
        switch (clientType){
            case ADMIN:
                clientService=adminService;
                isLogin= clientService.login(email, password);
                break;
            case COMPANY:
                clientService = companyService;
                isLogin = clientService.login(email, password);
                break;
            case CUSTOMER:
                clientService = customerService;
                isLogin = clientService.login(email, password);
                break;
        }
        if(!isLogin){
            throw new CustomExceptions(OptionalExceptionMessages.LOGIN_EXCEPTION);
        }else{
            UserDetails userDetails = new UserDetails(clientType,email,password);
            return jwTutil.generateToken(userDetails);
        }
    }
}
