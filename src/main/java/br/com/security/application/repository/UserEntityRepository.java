package br.com.security.application.repository;

import br.com.security.application.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Elvis Fernandes on 20/10/19
 */
@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    @Transactional(readOnly = true)
    Optional<UserEntity> findByEmailIgnoreCaseAndActiveTrue(String email);

    @Transactional(readOnly = true)
    Optional<UserEntity> findByEmailIgnoreCase(String email);
}
