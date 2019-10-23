package br.com.security.application.resources;

import br.com.security.application.model.UserEntity;
import br.com.security.application.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Elvis Fernandes on 21/10/19
 */
@RestController
@RequestMapping("/users")
public class UserEntityResources {

    private final UserEntityRepository service;

    @Autowired
    public UserEntityResources(UserEntityRepository service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserEntity>> findAll() {

        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
    }
}
