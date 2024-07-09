package top.kangyaocoding.chatgpt.domain.chatgpt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
/**
 * @Author K·Herbert
 * @Description
 * @Date 2024-05-22 16:46
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Logprob implements Serializable {
    private String token;

    private BigDecimal logprob;

    private List<Integer> bytes;

    @JsonProperty("top_logprobs")
    private List<Logprob> topLogprobs;
}