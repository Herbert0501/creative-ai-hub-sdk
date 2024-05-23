package top.kangyaocoding.ai.domain.images;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @Author K·Herbert
 * @Description 用于定义和处理图片编辑请求相关的参数。
 * @Date 2024-05-23 19:12
 */
@Slf4j
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ImageEditRequest extends ImageEnum implements Serializable {

    /**
     * 提示信息，用于描述图片编辑请求的具体要求。
     * 不能为空。
     */
    @NonNull
    private String prompt;

    /**
     * 为每个提示生成的完成次数，默认为1。
     * 表示根据提供的提示生成图片的数量。
     */
    @Builder.Default
    private Integer n = 1;

    /**
     * 图片大小，默认为256x256。
     * 通过Size枚举类的getCode方法获取表示图片大小的字符串。
     */
    @Builder.Default
    private String size = Size.size_256.getCode();

    /**
     * 图片格式化方式，默认为URL格式。
     * 通过ResponseFormat枚举类的getCode方法获取表示图片格式的字符串。
     * 可选值为URL或B64_JSON。
     */
    @JsonProperty("response_format")
    @Builder.Default
    private String responseFormat = ResponseFormat.URL.getCode();

    /**
     * 用户标识，可用于标识发起请求的用户。
     * 该属性允许在对象构建后进行设置。
     */
    @Setter
    private String user;
}
