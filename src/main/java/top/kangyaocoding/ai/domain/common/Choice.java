package top.kangyaocoding.ai.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author K·Herbert
 * @Description
 * @Date 2024-05-23 18:40
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Choice implements Serializable {
    private String text;
    private long index;
    private Logprobs logprobs;
    @JsonProperty("finish_reason")
    private String finishReason;
}