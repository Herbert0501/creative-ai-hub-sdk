package top.kangyaocoding.chatgpt.domain.images;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @Author K·Herbert
 * @Description 用于定义和处理图片请求相关的参数。
 * @Date 2024-05-23 19:09
 */

@Slf4j
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequest extends ImageEnum implements Serializable {

    /**
     * 提示信息，用于描述图片请求的详细要求。
     * 该字段是必须的，不能为空。
     */
    @NonNull
    private String prompt;

    /**
     * 为每个提示生成的完成次数，默认为1。
     * 如果需要生成多张图片，可以调整该数值。
     */
    @Builder.Default
    private Integer n = 1;

    /**
     * 图片大小，默认为256x256。
     * 可以根据需要选择不同的图片大小。
     */
    @Builder.Default
    private String size = Size.size_256.getCode();

    /**
     * 图片格式化方式，默认为URL格式。
     * 可以选择将图片以URL形式或B64_JSON格式返回。
     */
    @JsonProperty("response_format")
    @Builder.Default
    private String responseFormat = ResponseFormat.URL.getCode();

    /**
     * 用户标识，可用于标记请求的来源用户。
     * 该字段可以为空，具体使用根据业务需求决定。
     */
    @Setter
    private String user;

}
