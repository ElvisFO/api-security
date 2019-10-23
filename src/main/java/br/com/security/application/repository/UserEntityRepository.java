package br.com.security.application.repository;

import br.com.security.application.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Elvis Fernandes on 20/10/19
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmailAndActiveTrue(String email);
}
