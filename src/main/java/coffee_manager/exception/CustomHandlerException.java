package coffee_manager.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestControllerAdvice
public class CustomHandlerException {

    HandlerExceptionResponse response = new HandlerExceptionResponse();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String timestamp = dateFormat.format(new Date());

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HandlerExceptionResponse> handlerNoDataFoundException(NotFoundException ex) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.setTimestamp(timestamp);
        response.setMessage(ex.getMessage());
        return ResponseEntity.ok().body(response);
    }
}
