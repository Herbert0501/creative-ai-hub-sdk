package top.kangyaocoding.ai.domain.whisper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

/**
 * @Author K·Herbert
 * @Description 类用于构建和管理语音转文本请求的配置。
 * @Date 2024-05-23 19:19
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranscriptionsRequest {

    /**
     * 指定使用的模型。默认为 WHISPER_1。
     * WHISPER_1 是一种特定的语音识别模型代码。
     */
    @Builder.Default
    private String model = WhisperEnum.Model.WHISPER_1.getCode();

    /**
     * 提示语用于引导语音识别的过程。
     * 它是一个字符串，可以为空。
     */
    private String prompt;

    /**
     * 指定响应的格式。默认为 JSON。
     * JSON 是一种广泛使用的数据交换格式。
     */
    @JsonProperty("response_format")
    @Builder.Default
    private String responseFormat = WhisperEnum.ResponseFormat.JSON.getCode();

    /**
     * 温度控制输出的随机性。
     * 取值范围在0到2之间。较高的值将使输出更加随机，而较低的值将使输出更加集中和确定。
     * 默认值为0.2。
     */
    private double temperature = 0.2;

    /**
     * 指定音频的语言。
     * 语言代码遵循 ISO-639-1 标准。
     */
    private String language;

}
