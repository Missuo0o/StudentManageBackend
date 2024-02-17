package zhangshun.exception;

public class AuthException extends RuntimeException {
    private int code;

    public AuthException(int code, String message) {
        super(message);
        this.code = code;
    }

    public AuthException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
