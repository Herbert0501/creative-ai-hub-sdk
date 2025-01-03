package top.kangyaocoding.chatgpt.common;

import lombok.Getter;

/**
 * @Author K·Herbert
 * @Description
 * @Date 2024-05-22 17:15
 */

public class Constants {
    public final static String NULL = "NULL";

    @Getter
    public enum Role {

        SYSTEM("system"), USER("user"), ASSISTANT("assistant"),
        ;

        private final String code;

        Role(String code) {
            this.code = code;
        }

    }
}
