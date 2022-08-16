package com.ciandt.api.pagamento.controller;

import com.ciandt.api.pagamento.dto.ErrorDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException exception){
        log.error("Validation Exception: {}", exception.getMessage());
        var errorDto =
                ErrorDto.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(exception.getFieldError().getDefaultMessage())
                        .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
    }


//    @ExceptionHandler(value = Exception.class)
//    public ResponseEntity<ErrorDto> handleValidationException(Exception exception){
//        log.error("Error Exception: {}", exception.getMessage());
//        var errorDto =
//                ErrorDto.builder()
//                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                        .message(exception.getMessage())
//                        .build();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
//    }

//    @ExceptionHandler(value = DataIntegrityViolationException.class)
//    public ResponseEntity<ErrorDto> handleValidationException(DataIntegrityViolationException exception){
//        log.error("DataIntegrityViolationException: {}", exception.getMessage());
//        var errorDto =
//                ErrorDto.builder()
//                        .code(HttpStatus.BAD_REQUEST.value())
//                        .message(exception.getMessage())
//                        .build();
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDto);
//    }
}
