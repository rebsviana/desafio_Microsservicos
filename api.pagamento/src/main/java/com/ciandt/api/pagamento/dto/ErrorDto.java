package com.ciandt.api.pagamento.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorDto {
    public String message;
    public Integer code;
}
