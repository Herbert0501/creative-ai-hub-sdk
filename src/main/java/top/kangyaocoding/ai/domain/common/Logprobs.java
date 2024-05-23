package top.kangyaocoding.ai.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author K·Herbert
 * @Description
 * @Date 2024-05-23 18:41
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Logprobs implements Serializable {
    @JsonProperty("text_offset")
    private List textOffset;
    @JsonProperty("token_logprobs")
    private List tokenLogprobs;
    @JsonProperty("tokens")
    private List tokens;
    @JsonProperty("topLogprobs")
    private List top_logprobs;
}