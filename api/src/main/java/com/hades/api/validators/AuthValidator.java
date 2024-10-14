package com.hades.api.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hades.api.utils.JwtUtils;
import com.hades.api.utils.Exceptions.ForbiddenException;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthValidator {

    @Autowired
    private JwtUtils jwtUtils;

    public void validateToken(String token, HttpServletResponse response) {
        if (isTokenEmpty(token))
            throw new ForbiddenException("Token is empty");
        if (isTokenNotInJwtFormate(token))
            throw new ForbiddenException("Token is not in JWT format");
        Claims tokenContent = isTokenInvalid(token.split(" ")[1]);

        response.setHeader("userUuid", tokenContent.get("userUuid", String.class));
    }

    private boolean isTokenEmpty(String token) {
        return token.isEmpty();
    }

    private boolean isTokenNotInJwtFormate(String token) {
        System.out.println("token: " + token);
        String[] splitedToken = token.split(" ");

        return splitedToken.length != 2 || !splitedToken[0].equals("Bearer");
    }

    private Claims isTokenInvalid(String token) {
        return jwtUtils.isValidToken(token);
    }
}
