package ma.ensa.internHub.advice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import ma.ensa.internHub.domain.dto.response.ApiErrorResponse;
import ma.ensa.internHub.exception.DuplicateResourceException;
import ma.ensa.internHub.exception.EmptyResourcesException;
import ma.ensa.internHub.exception.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
        private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleValidationExceptions(
                        MethodArgumentNotValidException ex,
                        WebRequest request) {
                Map<String, String> errors = new HashMap<>();
                ex.getBindingResult().getFieldErrors()
                                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

                ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                .message("Validation failed")
                                .path(request.getDescription(false).substring(4))
                                .validationErrors(errors)
                                .build();

                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleResourceNotFound(
                        ResourceNotFoundException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.NOT_FOUND.value())
                                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                                .message(ex.getMessage())
                                .path(request.getDescription(false).substring(4))
                                .build();

                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(EmptyResourcesException.class)
        public ResponseEntity<ApiErrorResponse> handleEmptyResources(
                        EmptyResourcesException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.NO_CONTENT.value())
                                .error(HttpStatus.NO_CONTENT.getReasonPhrase())
                                .message(ex.getMessage())
                                .path(request.getDescription(false).substring(4))
                                .build();

                return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
        }

        @ExceptionHandler(DuplicateResourceException.class)
        public ResponseEntity<ApiErrorResponse> handleDuplicateResource(
                        DuplicateResourceException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CONFLICT.value())
                                .error(HttpStatus.CONFLICT.getReasonPhrase())
                                .message(ex.getMessage())
                                .path(request.getDescription(false).substring(4))
                                .build();

                return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                                .message("Incorrect username or password")
                                .path(request.getDescription(false).substring(4))
                                .build();
                return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleGlobalException(
                        Exception ex,
                        WebRequest request) {
                logger.error("Unhandled exception", ex);

                /*
                 * the substring(4) is used to remove the "uri=" prefix from the request
                 * description
                 */
                ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                                .message("An unexpected error occurred")
                                .path(request.getDescription(false).substring(4))
                                .build();

                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
