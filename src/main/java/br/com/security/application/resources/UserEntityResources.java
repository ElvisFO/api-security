package br.com.security.application.resources;

import br.com.security.application.dto.UserDTO;
import br.com.security.application.mapper.entity.UserEntityModelMapper;
import br.com.security.application.model.UserEntity;
import br.com.security.application.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Elvis Fernandes on 21/10/19
 */
@RestController
@RequestMapping("/users")
public class UserEntityResources {

    private final UserEntityRepository service;
    private final UserEntityModelMapper userEntityModelMapper;

    @Autowired
    public UserEntityResources(UserEntityRepository service, UserEntityModelMapper userEntityModelMapper) {
        this.service = service;
        this.userEntityModelMapper = userEntityModelMapper;
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
        UserEntity entity = this.userEntityModelMapper.convertToEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userEntityModelMapper.convertToDto(this.service.save(entity)));
    }
}
