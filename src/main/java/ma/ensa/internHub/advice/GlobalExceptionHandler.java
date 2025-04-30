package ma.ensa.internHub.advice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.persistence.EntityNotFoundException;
import ma.ensa.internHub.domain.dto.response.ApiErrorResponse;
import ma.ensa.internHub.exception.BadRequestException;
import ma.ensa.internHub.exception.DuplicateResourceException;
import ma.ensa.internHub.exception.EmailSendingException;
import ma.ensa.internHub.exception.EmptyResourcesException;
import ma.ensa.internHub.exception.ExpiredVerificationCodeException;
import ma.ensa.internHub.exception.InvalidVerificationCodeException;
import ma.ensa.internHub.exception.ResourceNotFoundException;
import ma.ensa.internHub.exception.TokenAlreadySentException;

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

                ApiErrorResponse errorResponse = buildApiErrorResponse(HttpStatus.BAD_REQUEST,
                                "Validation failed", request);

                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleResourceNotFound(
                        ResourceNotFoundException ex,
                        WebRequest request) {

                ApiErrorResponse errorResponse = buildApiErrorResponse(HttpStatus.NOT_FOUND,
                                ex.getMessage(), request);
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(EmptyResourcesException.class)
        public ResponseEntity<ApiErrorResponse> handleEmptyResources(
                        EmptyResourcesException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = buildApiErrorResponse(HttpStatus.NO_CONTENT,
                                ex.getMessage(), request);
                return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
        }

        @ExceptionHandler(DuplicateResourceException.class)
        public ResponseEntity<ApiErrorResponse> handleDuplicateResource(
                        DuplicateResourceException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = buildApiErrorResponse(HttpStatus.CONFLICT,
                                ex.getMessage(), request);
                return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<ApiErrorResponse> handleBadCredentialsException(BadCredentialsException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = buildApiErrorResponse(HttpStatus.UNAUTHORIZED,
                                "Incorrect username or password", request);
                return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }

        @ExceptionHandler(NoResourceFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = buildApiErrorResponse(HttpStatus.NOT_FOUND,
                                "Resource not found", request);
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<ApiErrorResponse> handleHttpRequestMethodNotSupported(
                        HttpRequestMethodNotSupportedException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = buildApiErrorResponse(HttpStatus.METHOD_NOT_ALLOWED,
                                "Method not allowed", request);
                return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
        }

        @ExceptionHandler(InvalidVerificationCodeException.class)
        public ResponseEntity<ApiErrorResponse> handleInvalidVerificationCode(
                        InvalidVerificationCodeException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = buildApiErrorResponse(HttpStatus.BAD_REQUEST,
                                ex.getMessage(), request);
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ExpiredVerificationCodeException.class)
        public ResponseEntity<ApiErrorResponse> handleExpiredVerificationCode(
                        ExpiredVerificationCodeException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = buildApiErrorResponse(HttpStatus.BAD_REQUEST,
                                ex.getMessage(), request);
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(EmailSendingException.class)
        public ResponseEntity<ApiErrorResponse> handleEmailSendingException(
                        EmailSendingException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = buildApiErrorResponse(HttpStatus.SERVICE_UNAVAILABLE,
                                ex.getMessage(), request);
                return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
        }

        @ExceptionHandler(EntityNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(
                        EntityNotFoundException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = buildApiErrorResponse(HttpStatus.NOT_FOUND,
                                ex.getMessage(), request);
                return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(TokenAlreadySentException.class)
        public ResponseEntity<ApiErrorResponse> handleTokenAlreadySentException(
                        TokenAlreadySentException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = buildApiErrorResponse(HttpStatus.TOO_MANY_REQUESTS,
                                ex.getMessage(), request);
                return new ResponseEntity<>(errorResponse, HttpStatus.TOO_MANY_REQUESTS);
        }

        // bad request exception
        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ApiErrorResponse> handleBadRequestException(
                        BadRequestException ex,
                        WebRequest request) {
                ApiErrorResponse errorResponse = buildApiErrorResponse(HttpStatus.BAD_REQUEST,
                                ex.getMessage(), request);
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleGlobalException(
                        Exception ex,
                        WebRequest request) {
                logger.error("Unhandled exception", ex);
                ApiErrorResponse errorResponse = buildApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                                "An unexpected error occurred", request);
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        private ApiErrorResponse buildApiErrorResponse(HttpStatus status, String message, WebRequest request) {

                /*
                 * the substring(4) is used to remove the "uri=" prefix from the request
                 * description
                 */
                return ApiErrorResponse.builder()
                                .timestamp(LocalDateTime.now())
                                .status(status.value())
                                .error(status.getReasonPhrase())
                                .message(message)
                                .path(request.getDescription(false).substring(4))
                                .build();
        }
}
