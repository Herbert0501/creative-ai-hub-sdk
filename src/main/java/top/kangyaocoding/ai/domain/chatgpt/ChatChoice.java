package top.kangyaocoding.ai.domain.chatgpt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author K·Herbert
 * @Description ChatChoice 类用于表示聊天选择的结果。
 * 它包含与聊天相关的不同信息，根据请求参数的设置，可以返回不同的消息类型。
 * 实现Serializable接口，允许对象被序列化。
 * @Date 2024-05-22 16:38
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatChoice implements Serializable {

    private long index;

    /**
     * 如果请求参数stream为true，返回的消息类型为delta。
     * Delta通常用于表示增量更新的信息。
     */
    @JsonProperty("delta")
    private Message delta;

    /**
     * 如果请求参数stream为false，返回的消息类型为message。
     * Message包含完整的聊天消息内容。
     */
    @JsonProperty("message")
    private Message message;

    /**
     * 表示聊天结束的原因。
     * 可能的原因包括用户主动结束、系统中断等。
     */
    @JsonProperty("finish_reason")
    private String finishReason;

    private LogprobContent logprobs;
}

