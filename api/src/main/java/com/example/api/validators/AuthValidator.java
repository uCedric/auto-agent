package com.example.api.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.api.utils.JwtUtils;
import com.example.api.utils.Exceptions.ForbiddenException;

import io.jsonwebtoken.Claims;

@Component
public class AuthValidator {

    @Autowired
    private JwtUtils jwtUtils;

    public Claims validateToken(String token) {
        if (isTokenEmpty(token))
            throw new ForbiddenException("Token is empty");
        if (isTokenNotInJwtFormate(token))
            throw new ForbiddenException("Token is not in JWT format");
        Claims tokenContent = isTokenInvalid(token.split(" ")[1]);

        return tokenContent;
    }

    private boolean isTokenEmpty(String token) {
        return token.isEmpty();
    }

    private boolean isTokenNotInJwtFormate(String token) {
        String[] splitedToken = token.split(" ");

        return splitedToken.length != 2 || !splitedToken[0].equals("Bearer");
    }

    private Claims isTokenInvalid(String token) {
        return jwtUtils.isValidToken(token);
    }
}
