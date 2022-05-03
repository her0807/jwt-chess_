package chess.controller;

import chess.controller.dto.ResponseDto;
import chess.utils.exception.NoExecuteQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class WebChessExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, NoExecuteQuery.class})
    private ResponseEntity<ResponseDto> handleConflict(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(e.getMessage()));
    }

    @ExceptionHandler(value = {Exception.class})
    private ResponseEntity<ResponseDto> generalHandleConflict(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto("서버에서 에러가 발생했습니다."));
    }
}
