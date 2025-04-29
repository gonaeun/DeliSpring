package com.example.DeliSpring.global.error;

import com.example.DeliSpring.global.error.response.ErrorResponse;
import com.example.DeliSpring.global.error.response.ErrorType;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @Valid 검증 실패 처리
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();

            if ("password".equals(field)) {
                message = "비밀번호가 조건에 맞지 않습니다.";
            }

            errors.put(field, message);
        });

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse(ErrorType.VALIDATION_FAILED));
    }

    @ExceptionHandler({ApiException.class})
    public ResponseEntity<ErrorResponse> handle_ScheduleException(ApiException e) {
        log.error("[handle_ApiException]", e);
        ErrorResponse response = new ErrorResponse(e.getExceptionStatus());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
