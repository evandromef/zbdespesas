package com.evandro.zbdespesas.exception;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.evandro.zbdespesas.category.exception.CategoryNotFoundException;
import com.evandro.zbdespesas.category.exception.InvalidCategoryException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleTypeMismatchException(
        MethodArgumentTypeMismatchException exception
    ) {
        String message = UUID.class.equals(exception.getRequiredType())
            ? "O ID informado não é válido."
            : "Um dos parâmetros não é válido.";

        ProblemDetail problem = createProblemDetail(HttpStatus.BAD_REQUEST, "Requisição inválida", message);

        return ResponseEntity
                    .badRequest()
                    .body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(
        MethodArgumentNotValidException exception
    ) {
        Map<String, List<String>> errors = new LinkedHashMap<>();

        exception.getBindingResult()
            .getFieldErrors()
            .forEach(fieldError -> {
                String message = fieldError.getDefaultMessage() != null
                    ? fieldError.getDefaultMessage()
                    : "Valor inválido";

                errors.computeIfAbsent(
                    fieldError.getField(),
                    key -> new ArrayList<>()
                ).add(message);
            });

        ProblemDetail problem = createProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Dados inválidos",
            "Um ou mais campos são inválidos."
        );

        problem.setProperty("errors", errors);

        return ResponseEntity.badRequest().body(problem);
    }

    /* JSON malformado gera HttpMessageNotReadableException
     *
     * Uma requisição como:
     *   {
     *       "name":
     *   }
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleUnreadableMessage(
        HttpMessageNotReadableException exception
    ) {
        ProblemDetail problem = createProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Requisição inválida",
            "O corpo da requisição está ausente ou possui JSON inválido."
        );

        return ResponseEntity
            .badRequest()
            .body(problem);
    }

    @ExceptionHandler(InvalidCategoryException.class)
    public ResponseEntity<ProblemDetail> handleInvalidCategory(
        InvalidCategoryException exception
    ) {
        ProblemDetail problem = createProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Categoria inválida",
            exception.getMessage()
        );

        return ResponseEntity
                    .badRequest()
                    .body(problem);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleCategoryNotFound(
        CategoryNotFoundException exception
    ) {
        ProblemDetail problem = createProblemDetail(
            HttpStatus.NOT_FOUND,
            "Categoria não encontrada",
            exception.getMessage()
        );

        return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(problem);
    }

    private ProblemDetail createProblemDetail(
        HttpStatus status,
        String title,
        String detail
    ) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(title);
        return problem;
    }
}
