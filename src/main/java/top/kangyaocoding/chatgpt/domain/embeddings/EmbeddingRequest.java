package top.kangyaocoding.chatgpt.domain.embeddings;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * @Author K·Herbert
 * @Description EmbeddingRequest 类用于构建和表示一个嵌入请求。它包含了进行嵌入处理所需的基本配置和数据。
 * @Date 2024-05-23 18:58
 */

@Slf4j
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddingRequest implements Serializable {

    /**
     * 指定使用的模型。
     * 默认为 TEXT_EMBEDDING_ADA_002 模型。
     * 这个字段是必填的，不能为空。
     */
    @NonNull
    @Builder.Default
    private String model = Model.TEXT_EMBEDDING_ADA_002.getCode();

    /**
     * 输入信息列表，包含了需要进行嵌入处理的文本。
     * 这个字段是必填的，不能为空。
     */
    @NonNull
    private List<String> input;

    /**
     * 用户标识，可选字段，用于标识请求的用户。
     */
    @Setter
    private String user;

    /**
     * Model 枚举定义了可用的模型选项。
     * 每个模型都通过一个唯一的代码进行标识。
     */
    @Getter
    @AllArgsConstructor
    public enum Model {
        TEXT_EMBEDDING_ADA_002("text-embedding-ada-002"),
        ;
        private final String code; // 模型的唯一标识代码
    }

}
