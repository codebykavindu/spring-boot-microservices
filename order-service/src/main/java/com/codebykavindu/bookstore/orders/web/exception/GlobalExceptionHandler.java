package com.codebykavindu.bookstore.orders.web.exception;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ProblemDetail.forStatusAndDetail;

import com.codebykavindu.bookstore.orders.domain.InvalidOrderException;
import com.codebykavindu.bookstore.orders.domain.OrderNotFoundException;
import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Kavindu Perera
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public static final URI NOT_FOUND_TYPE = URI.create("https://api.bookstore.com/errors/not-found");
    public static final URI ISE_FOUND_TYPE = URI.create("https://api.bookstore.com/errors/server-error");
    private static final URI BAD_REQUEST_TYPE = URI.create("https://api.bookstore.com/errors/bad-request");
    public static final String SERVICE_NAME = "order-service";

    public static final String GENERIC = "Generic";
    public static final String TIMESTAMP = "timestamp";
    public static final String ERROR_CATEGORY = "error_category";
    public static final String SERVICE = "service";

    @ExceptionHandler(Exception.class)
    ProblemDetail handleUnhandledException(final Exception e) {
        ProblemDetail problemDetail = forStatusAndDetail(INTERNAL_SERVER_ERROR, e.getMessage());
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(ISE_FOUND_TYPE);
        problemDetail.setProperty(SERVICE, SERVICE_NAME);
        problemDetail.setProperty(ERROR_CATEGORY, GENERIC);
        problemDetail.setProperty(TIMESTAMP, java.time.Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(OrderNotFoundException.class)
    ProblemDetail handleOrderNotFoundException(final OrderNotFoundException e) {
        ProblemDetail problemDetail = forStatusAndDetail(NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Order Not Found");
        problemDetail.setType(NOT_FOUND_TYPE);
        problemDetail.setProperty(SERVICE, SERVICE_NAME);
        problemDetail.setProperty(ERROR_CATEGORY, GENERIC);
        problemDetail.setProperty(TIMESTAMP, java.time.Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(InvalidOrderException.class)
    ProblemDetail handleInvalidOrderException(InvalidOrderException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Invalid Order Creation Request");
        problemDetail.setType(BAD_REQUEST_TYPE);
        problemDetail.setProperty(SERVICE, SERVICE_NAME);
        problemDetail.setProperty(ERROR_CATEGORY, GENERIC);
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return problemDetail;
    }

    @Override
    @Nullable protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            errors.add(errorMessage);
        });
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid request payload");
        problemDetail.setTitle("Bad Request");
        problemDetail.setType(BAD_REQUEST_TYPE);
        problemDetail.setProperty("errors", errors);
        problemDetail.setProperty(SERVICE, SERVICE_NAME);
        problemDetail.setProperty(ERROR_CATEGORY, GENERIC);
        problemDetail.setProperty(TIMESTAMP, Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}
