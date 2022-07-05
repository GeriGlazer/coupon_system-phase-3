package com.jb.coupon3.security;

import com.jb.coupon3.beans.ClientType;
import com.jb.coupon3.beans.UserDetails;
import com.jb.coupon3.exceptions.CustomExceptions;
import com.jb.coupon3.exceptions.OptionalExceptionMessages;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
/**
 * this class is for securing information sent and retrieved from the server
 */
public class JWTutil {

        //type of encryption
        private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
        //secret key
        private final String secretKey = "this+is+geri+niv+guy+yoav+best+coupon+project+evevrrrrr";
        //private key
        private Key decodedSecretKey = new SecretKeySpec
                (Base64.getDecoder().decode(secretKey),this.signatureAlgorithm);

    /**
     * generate key (token)
     * @param userDetails since the userDetail is an instance of the information in the hashcode
     *                    the token need to get claims which is the information in the hashcode
     * @return a new token used after every API request
     */
        public String generateToken(UserDetails userDetails){
            Map<String,Object> claims = new HashMap<>();
            claims.put("userEmail",userDetails.getEmail());
            return "Bearer "+createToken(claims,userDetails.getClientType().toString());
        }

    /**
     * creation of new token using generateToken data
     * @param claims contains client information (email)
     * @param userName client name defined in the login process
     * @return a new coded secured key (token) based on the  logged client information
     */
        private String createToken(Map<String, Object> claims, String userName) {
            Instant now = Instant.now();
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(userName)
                    .setIssuedAt(Date.from(now))
                    .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                    .signWith(decodedSecretKey)
                    .compact();
        }

    /**
     * extraction of information from the token
     * @param token received after any API request
     * @return the decoded information of the token (userDetails and client type)
     * @throws ExpiredJwtException if the token has expired the given time on creation
     * @throws MalformedJwtException if the token is not entered correctly or missing a part
     */
        private Claims extractAllClaims(String token) throws ExpiredJwtException, MalformedJwtException  {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(decodedSecretKey).build();
            return jwtParser.parseClaimsJws(token).getBody();
        }

    /**
     * verification that the client requesting the query is the client that received the original token
     * @param token to verify the information inside the token against UserDetails
     * @return a new token
     * @throws MalformedJwtException if the token is not entered correctly or missing a part
     */
        public String checkUser(String token, ClientType clientType) throws MalformedJwtException, CustomExceptions {
            if (!isRightClientType(token, clientType)) {
                throw new CustomExceptions(OptionalExceptionMessages.DONT_HAVE_PERMISSION);
            }
            Claims claims = extractAllClaims(token.replace("Bearer ",""));
            UserDetails userDetails = new UserDetails();
            userDetails.setClientType(ClientType.valueOf(claims.getSubject()));
            userDetails.setEmail((String)claims.get("userEmail"));
            return generateToken(userDetails);
    }

        private String extractSignature(String token) throws ExpiredJwtException, MalformedJwtException  {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(decodedSecretKey).build();
            return jwtParser.parseClaimsJws(token).getSignature();
        }

        private String extractSubject(String token)  {
            return extractAllClaims(token.replace("Bearer ","")).getSubject();
        }

        public ClientType extractClientType(String token) {
            return ClientType.valueOf(extractSubject(token));
        }

        private boolean isRightClientType(String token, ClientType clientType) {
            return extractClientType(token).equals(clientType);
        }

    }
