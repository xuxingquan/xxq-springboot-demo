package cn.tycoding.common;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;

/**
 * 封装JSON返回的结果格式
 *
 */
@Getter
@Setter
public class SeckillResult<T> {

    private T data;
    private String code;// 0000 成功 9999 系统异常
    private String message;

    public SeckillResult(T data) {
        this.code = "0000";
        this.data = data;
        this.message = "成功";
    }

    public SeckillResult(String code, String message,T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> SeckillResult<T> create(String code, String message, T data) {
        return new SeckillResult(code, message, data);
    }

    public static <T> SeckillResult<T> create(String code, String message) {
        return new SeckillResult(code, message, null);
    }

    public static <T> SeckillResult<T> ok(T data) {
        return new SeckillResult(data);
    }

    public static SeckillResult<Void> ok() {
        return ok(null);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("data", data)
                .add("code", code)
                .add("message", message)
                .toString();
    }
}
