package top.kangyaocoding.ai.domain.chatgpt;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author K·Herbert
 * @Description ChatCompletionRequest 类用于构建和表示一个聊天请求。它包含了与聊天模型交互时所需的所有参数。
 * @Date 2024-05-22 16:41
 */
@Data
@Builder
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ChatCompletionRequest implements Serializable {

    /**
     * 默认使用的模型代码。
     * 如果不指定模型，将使用此默认值。
     */
    private String model = Model.GPT_3_5_TURBO.getCode();
    /**
     * 问题描述，包含与聊天相关的消息历史。
     */
    private List<Message> messages;
    /**
     * 温度参数控制输出的随机性。
     * 值在0到2之间，较高的温度使输出更随机，较低的温度使输出更集中。
     */
    private double temperature = 0.2;
    /**
     * top_p参数用于多样性控制。
     * 它通过考虑具有top_p概率质量的令牌来替代温度采样。
     * 例如，0.1意味着只考虑前10%概率质量的代币。
     */
    @JsonProperty("top_p")
    private Double topP = 1d;
    /**
     * 为每个提示生成的完成次数。
     */
    private Integer n = 1;
    /**
     * 流式输出标志。
     * 如果为true，则输出结果将以流的方式逐段返回。
     */
    private boolean stream = false;
    /**
     * 停止输出标识。
     * 当聊天达到指定的停止条件时，使用此列表中的字符串。
     */
    private List<String> stop;
    /**
     * 输出字符串限制。
     * 指定生成的文本最大长度，范围在0到4096之间。
     */
    @JsonProperty("max_tokens")
    private Integer maxTokens = 2048;
    /**
     * 频率惩罚参数。
     * 用于降低模型重复同一行的可能性。
     */
    @JsonProperty("frequency_penalty")
    private double frequencyPenalty = 0;
    /**
     * 存在惩罚参数。
     * 用于增强模型讨论新话题的可能性。
     */
    @JsonProperty("presence_penalty")
    private double presencePenalty = 0;
    /**
     * logit_bias用于生成多个调用结果，并仅显示最佳结果。
     * 这样会增加API令牌的消耗。
     */
    @JsonProperty("logit_bias")
    private Map<Integer, Integer> logitBias;
    /**
     * 用户标识，用于避免重复调用。
     */
    private String user;

    /**
     * Model 枚举定义了可用的聊天模型。
     * 每个枚举值都关联了一个模型的代码。
     */
    @Getter
    @AllArgsConstructor
    public enum Model {
        /** gpt-3.5-turbo 模型代码 */
        GPT_3_5_TURBO("gpt-3.5-turbo"),
        /** GPT 4.0 模型代码 */
        GPT_4("gpt-4"),
        /** GPT 4.0 超长上下文模型代码 */
        GPT_4_32K("gpt-4-32k"),
        ;
        /**
         * 模型的代码标识。
         */
        private final String code;
    }
}


