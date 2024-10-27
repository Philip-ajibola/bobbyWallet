//package africa.semicolon.ppay.infrastructure.adapter.output.globalExceptionHandler;
//
//import africa.semicolon.ppay.domain.exception.PPayWalletException;
//import africa.semicolon.ppay.domain.exception.UserNotFoundException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import java.util.Map;
//
//import static org.springframework.http.HttpStatus.BAD_REQUEST;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(PPayWalletException.class)
//    @ResponseBody
//    public ResponseEntity<?> handleUserNotFound(PPayWalletException exception){
//        return ResponseEntity.status(BAD_REQUEST).body(Map.of(
//                "error", exception.getMessage(),
//                "success", false
//        ));
//    }
//    @ExceptionHandler(Exception.class)
//    @ResponseBody
//    public ResponseEntity<?> handleException(Exception exception){
//        return ResponseEntity.status(BAD_REQUEST).body(Map.of(
//                "error", exception.getMessage(),
//                "success", false
//        ));
//    }
//}
