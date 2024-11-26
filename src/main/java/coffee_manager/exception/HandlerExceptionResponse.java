package coffee_manager.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HandlerExceptionResponse {
    private Integer status;
    private String message;
    private String timestamp;

}
