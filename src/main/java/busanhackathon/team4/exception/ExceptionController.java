package busanhackathon.team4.exception;

import busanhackathon.team4.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    //엔티티 조회 실패
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Result> handlerEntityNotFoundException(Exception e) {
        return ResponseEntity.badRequest().body(new Result(e.getMessage(), "예외처리"));
    }

    //커스텀 예외 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Result> handlerCustomException(Exception e) {
        return ResponseEntity.badRequest().body(new Result(e.getMessage(), "예외처리"));
    }
}
