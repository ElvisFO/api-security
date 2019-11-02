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
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
    private String exception;
}
