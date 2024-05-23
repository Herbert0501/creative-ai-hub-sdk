package top.kangyaocoding.ai.domain.whisper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * @Author K·Herbert
 * @Description 用于构建和存储翻译请求的参数
 * @Date 2024-05-23 19:21
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranslationsRequest implements Serializable {

    /**
     * 指定使用的模型，默认为 WHISPER_1。
     * WHISPER_1 是一种预定义的模型代码，代表特定的翻译模型。
     */
    @Builder.Default
    private String model = WhisperEnum.Model.WHISPER_1.getCode();

    /**
     * 提示语字段，用于提供给模型进行翻译的文本输入。
     * 这是用户想要翻译或让模型基于其生成内容的文本。
     */
    private String prompt;

    /**
     * 指定响应的格式，默认为 JSON。
     * 响应格式决定了返回翻译结果的格式，此处的 JSON 为一种预定义的格式代码。
     */
    @JsonProperty("response_format")
    @Builder.Default
    private String responseFormat = WhisperEnum.ResponseFormat.JSON.getCode();

    /**
     * 温度参数控制了输出的随机性。
     * 较高的温度值会使输出更加随机，而较低的温度值则会使输出更加集中和确定。
     * 默认值为 0.2，代表一个平衡随机性和确定性的取值。
     */
    private double temperature = 0.2;

}
