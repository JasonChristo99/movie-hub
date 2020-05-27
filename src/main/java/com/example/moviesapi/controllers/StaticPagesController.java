package com.example.moviesapi.controllers;

import com.example.moviesapi.util.SessionDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class StaticPagesController {
    @GetMapping(value = "/search_page")
    public String showSearch() {
        if (SessionDetails.getActiveUserId() == -1) {
            return "login";
        }
        return "search";
    }

    @GetMapping(value = "/login_page")
    public String showLogin() {
        return "login";
    }

    @GetMapping(value = "/register_page")
    public String showRegister() {
        return "register";
    }

    @GetMapping(value = "/")
    public void redirectToLogin(HttpServletResponse httpResponse) {
        try {
            httpResponse.sendRedirect("/login_page");
        } catch (IOException e) {
            System.out.println("Could not reach page.");
        }
    }

    @GetMapping(value = "/favorites_page")
    public String showFavorites() {
        if (SessionDetails.getActiveUserId() == -1) {
            return "login";
        }
        return "favorites";
    }
}
