package mu.tutorial.learnspringboot.controlleradvice;

import mu.tutorial.learnspringboot.exception.ErrorMessage;
import mu.tutorial.learnspringboot.exception.JsonPlaceHolderException;
import mu.tutorial.learnspringboot.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage userNotFoundHandler(UserException ex, WebRequest webRequest) {
        return ErrorMessage.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(webRequest.getDescription(false))
                .build();
    }

    @ExceptionHandler(JsonPlaceHolderException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage jsonPlaceHolderBadRequestHandler(JsonPlaceHolderException ex, WebRequest webRequest) {
            return ErrorMessage.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(webRequest.getDescription(false))
                .build();
    }
}
