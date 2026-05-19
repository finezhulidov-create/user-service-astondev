package com.zhulidov.user_service_astondev.vision;

import com.zhulidov.user_service_astondev.config.annotations.AppComponent;

import java.util.Arrays;
public enum Operation {
    CREATE(1), READ_ALL(2),READ_BY_ID(3), UPDATE(4), DELETE(5), EXIT(6);

    private final int code;

    Operation(int code) {
        this.code = code;
    }

    public static Operation fromCode(int code){
        return Arrays.stream(values())
                .filter(op -> op.code == code)
                .findFirst()
                .orElse(null);
    }
}
