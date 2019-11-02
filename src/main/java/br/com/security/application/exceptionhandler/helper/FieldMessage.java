package br.com.security.application.exceptionhandler.helper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Elvis Fernandes on 01/11/19
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FieldMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fieldName;
    private String message;
}
