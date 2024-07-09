package top.kangyaocoding.chatgpt.domain.chatgpt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author K·Herbert
 * @Description
 * @Date 2024-05-22 16:46
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogprobContent implements Serializable {

    private List<Logprob> content;
}