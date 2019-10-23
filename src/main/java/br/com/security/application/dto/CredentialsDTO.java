package br.com.security.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Elvis Fernandes on 22/10/19
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CredentialsDTO implements Serializable {

    private String email;
    private String password;
}
