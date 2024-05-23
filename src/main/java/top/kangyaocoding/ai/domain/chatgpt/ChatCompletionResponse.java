package top.kangyaocoding.ai.domain.chatgpt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import top.kangyaocoding.ai.domain.common.Usage;

import java.io.Serializable;
import java.util.List;


/**
 * @Author K·Herbert
 * @Description
 * @Date 2024-05-22 16:41
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatCompletionResponse implements Serializable {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<ChatChoice> choices;
    private Usage usage;
    private String warning;
    @JsonProperty("system_fingerprint")
    private String systemFingerprint;
}
