package _2.baitap_25._1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@EnableWebSecurity
public class DemoController {
    @Autowired
    private InMemoryUserDetailsManager userDetailsManager;

    private final List<String> usernames = List.of("user", "admin");

    @GetMapping("/hello")
    public String hello() {
        return "Hello, anyone can access this endpoint!";
    }

    @GetMapping("/secured")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String secured() {
        return "This is a secured endpoint, authentication required!";
    }

    @GetMapping("/customer/all")
    public ResponseEntity<List<UserDetails>> all() {
        List<UserDetails> users = new ArrayList<>();

        // Duyệt qua danh sách username và lấy thông tin người dùng
        for (String username : usernames) {
            UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
            users.add(userDetails);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/customer/{username}")
    public ResponseEntity<UserDetails> getUserByUsername(@PathVariable String username) {
        try {
            UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
            return ResponseEntity.ok(userDetails);
        } catch (Exception e) {
            // Trả về 404 nếu không tìm thấy user
            return ResponseEntity.notFound().build();
        }
    }
}