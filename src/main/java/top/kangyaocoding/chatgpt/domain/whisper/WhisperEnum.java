package top.kangyaocoding.chatgpt.domain.whisper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * @Author K·Herbert
 * @Description 提供了有关模型和响应格式的枚举定义
 * @Date 2024-05-23 19:22
 */

@Data
public class WhisperEnum implements Serializable {

    /**
     * Model枚举定义了可用的模型类型。
     * 每个模型类型都关联了一个代码字符串，用于外部标识。
     */
    @Getter
    @AllArgsConstructor
    public enum Model {
        WHISPER_1("whisper-1"), // 定义了一个名为WHISPER_1的模型，其代码为"whisper-1"
        ;
        // code字段存储了每个模型类型的代码字符串
        private final String code;
    }

    /**
     * ResponseFormat枚举定义了支持的响应格式类型。
     * 每种响应格式都关联了一个代码字符串，用于在外部标识不同的格式。
     */
    @Getter
    @AllArgsConstructor
    public enum ResponseFormat {
        JSON("json"),
        TEXT("text"),
        SRT("srt"),
        VERBOSE_JSON("verbose_json"),
        VTT("vtt");

        // code字段存储了每个响应格式的代码字符串
        private final String code;
    }
}
