package br.com.security.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Elvis Fernandes on 29/10/19
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO implements Serializable {

    private String name;
    private String email;
    private String password;
    private Set<Integer> profiles = new HashSet<>();
}
