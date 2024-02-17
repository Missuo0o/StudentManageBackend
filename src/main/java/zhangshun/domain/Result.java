package zhangshun.domain;

import lombok.Data;

@Data
public class Result {
    private Object data;
    private int code;
    private String msg;


    public Result() {
    }

    public Result(int code) {
        this.code = code;
    }

    public Result(Object data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Result(Object data, int code) {
        this.data = data;
        this.code = code;
    }

}
