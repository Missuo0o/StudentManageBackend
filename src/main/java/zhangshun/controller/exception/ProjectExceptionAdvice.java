package zhangshun.controller.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zhangshun.domain.Code;
import zhangshun.domain.Result;
import zhangshun.exception.AuthException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

@RestControllerAdvice
public class ProjectExceptionAdvice {
    @ExceptionHandler(AuthException.class)
    public Result doAuthException(AuthException ex) {
        return new Result(null, ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result doSQLException(SQLIntegrityConstraintViolationException ex) {
        return new Result(null, Code.SYSTEM_ERR, "请勿对重复或不规范数据进行操作");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result doValidException(MethodArgumentNotValidException e) {
        return new Result(null, Code.SYSTEM_ERR, Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex) {
        return new Result(null, Code.SYSTEM_ERR, ex.getMessage());
    }
}

