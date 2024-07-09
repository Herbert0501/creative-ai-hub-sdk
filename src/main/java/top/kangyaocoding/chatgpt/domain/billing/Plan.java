package top.kangyaocoding.chatgpt.domain.billing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @Author K·Herbert
 * @Description  Plan类用于表示一个计划的简要信息。它包含了计划的标题和唯一标识符。
 * @Date 2024-05-23 18:49
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Plan {
    // 计划的标题
    private String title;
    // 计划的唯一标识符
    private String id;
}
