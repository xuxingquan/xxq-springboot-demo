package com.xxq.controller.response;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import lombok.Setter;

/**
 * 暴露秒杀地址DTO
 */
@Getter
@Setter
public class ExposerResp {
    //是否开启秒杀
    private boolean exposed;
    //加密措施，避免用户通过抓包拿到秒杀地址
    private String md5;
    //ID
    private long seckillId;
    //系统当前时间（毫秒）
    private long now;
    //秒杀开启时间
    private long start;
    //秒杀结束时间
    private long end;

    public ExposerResp(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    public ExposerResp(boolean exposed, Long seckillId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public ExposerResp(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("exposed", exposed)
                .add("md5", md5)
                .add("seckillId", seckillId)
                .add("now", now)
                .add("start", start)
                .add("end", end)
                .toString();
    }
}
