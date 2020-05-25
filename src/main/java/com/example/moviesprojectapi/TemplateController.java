package com.example.moviesprojectapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {
    @GetMapping(value = "/search")
    public String showSearch() {
        if (SessionDetails.getActiveUserId() == -1) {
            return "login_page/login";
        }
        return "search_page/search";
    }

    @GetMapping(value = "/")
    public String showLogin() {
        return "login_page/login";
    }

    @GetMapping(value = "/register_page")
    public String showRegister() {
        return "register_page/register";
    }
}
