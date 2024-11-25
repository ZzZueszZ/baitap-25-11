package _2.baitap_25._1.controller;

import _2.baitap_25._1.repository.UserRepository;
import _2.baitap_25._1.entity.User; // Thay thế bằng lớp Entity đại diện cho bảng trong DB.
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DemoController {

    @Autowired
    private UserRepository userRepository;

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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")

    public ResponseEntity<List<User>> getAllUsers() {
        // Lấy tất cả người dùng từ cơ sở dữ liệu.
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/customer/{username}")
    @PreAuthorize("hasAnyAuthority('User')")

    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
