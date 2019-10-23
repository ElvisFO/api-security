package br.com.security.application.model;

import br.com.security.application.model.enums.Profile;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Elvis Fernandes on 20/10/19
 */
@Entity
@Table(name = "tb_users")
@Data
@Audited
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserEntity extends AbstractAuditedEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tb_profiles")
    private Set<Integer> profiles = new HashSet<>();

    public UserEntity() {
        this.addProfile(Profile.USER);
    }

    public Set<Profile> getProfiles() {
        return this.profiles.stream().map(p -> Profile.toEnum(p)).collect(Collectors.toSet());
    }

    public void addProfile(Profile profile) {
        this.profiles.add(profile.getId());
    }
}
