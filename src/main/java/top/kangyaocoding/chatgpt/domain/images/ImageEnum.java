package top.kangyaocoding.chatgpt.domain.images;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author K·Herbert
 * @Description 图片枚举类，用于定义图片的尺寸和响应格式。
 * @Date 2024-05-23 19:10
 */

public class ImageEnum {

    /**
     * 图片尺寸枚举，定义了三种不同的尺寸。
     */
    @Getter
    @AllArgsConstructor
    public enum Size {
        size_256("256x256"), // 256像素乘以256像素
        size_512("512x512"), // 512像素乘以512像素
        size_1024("1024x1024"), // 1024像素乘以1024像素
        ;

        // 尺寸的代码表示，例如"256x256"
        private final String code;
    }

    /**
     * 响应格式枚举，定义了两种不同的响应格式。
     */
    @Getter
    @AllArgsConstructor
    public enum ResponseFormat {
        URL("url"), // URL响应格式
        B64_JSON("b64_json"), // Base64编码的JSON响应格式
        ;

        // 响应格式的代码表示，例如"url"
        private final String code;
    }

}
