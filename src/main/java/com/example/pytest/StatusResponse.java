package com.example.pytest;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponse {
    private String status;

    private String message;

    public static StatusResponse SUCCESS() {
        return StatusResponse.builder().status(RC.SUCCESS.getCode()).build();
    }

    public static StatusResponse FAILED() {
        return StatusResponse.builder().status(RC.FAIL.getCode()).build();
    }

    @Getter
    @AllArgsConstructor
    public enum RC{
        /**
         * 操作成功
         */
        SUCCESS("0"),
        /**
         * 操作失敗
         */
        FAIL("1");

        private final String code;
    }
}
