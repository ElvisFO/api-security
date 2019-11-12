package br.com.security.application.service;

import br.com.security.application.model.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * @author Elvis Fernandes on 05/11/19
 */

public interface UserEntityService {

    Optional<UserEntity> findById(Long id);

    List<UserEntity> findAll();

    Optional<UserEntity> findByEmailIgnoreCaseAndActiveTrue(String email);

    Optional<UserEntity> findByEmailIgnoreCase(String email);

    void deleteById(Long id);

    UserEntity save(UserEntity entity);
}
