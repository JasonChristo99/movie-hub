package com.example.moviesprojectapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class UsersController {

    @Autowired
    private final UserRepository userRepository;

    public UsersController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<HttpStatus> login(@RequestParam(value = "email") String email,
                                            @RequestParam(value = "password") String password,
                                            HttpServletResponse httpResponse) {
        System.out.println("Login request { " + email + ", " + password + " / " + PasswordEncryptor.toSHA1(password) + " } ");
        User resultUser = this.userRepository.findByEmailAndPassword(email, PasswordEncryptor.toSHA1(password));
        if (resultUser != null) {
            System.out.println("Login success");
            SessionDetails.setActiveUserId(resultUser.getId());
            System.out.println("Redirecting " + SessionDetails.getActiveUserId() + " to search page...");
            try {
                httpResponse.sendRedirect("/search");
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<HttpStatus>(HttpStatus.SERVICE_UNAVAILABLE);
            }
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } else {
            System.out.println("Login fail");
            try {
                httpResponse.sendRedirect("/");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<HttpStatus> register(@RequestParam(value = "email") String email,
                                               @RequestParam(value = "password") String password,
                                               HttpServletResponse httpResponse) {
        System.out.println("Register request { " + email + ", " + password + " / " + PasswordEncryptor.toSHA1(password) + " } ");
        if (this.userRepository.findByEmail(email) != null) {
            System.out.println("Register failed");
            try {
                httpResponse.sendRedirect("/register_page");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        } else {
            User user = new User(email, PasswordEncryptor.toSHA1(password));
            this.userRepository.save(user);
            System.out.println("Register success");
            SessionDetails.setActiveUserId(user.getId());
            System.out.println("Redirecting " + SessionDetails.getActiveUserId() + " to search page...");
            try {
                httpResponse.sendRedirect("/search");
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<HttpStatus>(HttpStatus.SERVICE_UNAVAILABLE);
            }
            return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
        }
    }

    @GetMapping(value = "/users")
    public Iterable<User> users() {
        return this.userRepository.findAll();
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<HttpStatus> logout(HttpServletResponse httpResponse) {
        System.out.println("Logout request");
        if (SessionDetails.getActiveUserId() == -1) {
            return new ResponseEntity<HttpStatus>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        System.out.println("Logged out user " + SessionDetails.getActiveUserId() + " successfully");
        System.out.println("Redirecting to login page...");
        SessionDetails.clearActiveUser();
        try {
            httpResponse.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.SERVICE_UNAVAILABLE);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }
}
