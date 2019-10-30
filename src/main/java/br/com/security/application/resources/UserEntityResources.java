package br.com.security.application.resources;

import br.com.security.application.commons.SecurityUtils;
import br.com.security.application.dto.UserDTO;
import br.com.security.application.model.UserEntity;
import br.com.security.application.repository.UserEntityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody UserDTO dto) {
        Optional<UserDetails> currentUser = SecurityUtils.getCurrentUser();
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(dto, entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(entity));
    }
}
