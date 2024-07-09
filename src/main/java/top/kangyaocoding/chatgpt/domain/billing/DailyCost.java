package top.kangyaocoding.chatgpt.domain.billing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author K·Herbert
 * @Description DailyCost 类用于表示每天的消费详情。它包含了一个时间戳和模型消耗金额的详细列表。
 * @Date 2024-05-23 18:52
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyCost {
    /**
     * 时间戳属性，用于标识记录的具体时间。
     */
    @JsonProperty("timestamp")
    private long timestamp;

    /**
     * 模型消耗金额详情列表，包含了消费的详细信息。
     */
    @JsonProperty("line_items")
    private List<LineItem> lineItems;
}
