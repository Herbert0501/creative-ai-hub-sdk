package top.kangyaocoding.ai.domain.billing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author K·Herbert
 * @Description LineItem类用于表示一行项目的详细信息。它包含模型的名称和该模型的消耗金额。
 * @Date 2024-05-23 18:53
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineItem {
    /**
     * 模型名称。用于标识具体的物品或服务名称。
     */
    private String name;

    /**
     * 消耗金额。表示该模型的费用，使用BigDecimal类型来确保精确的货币计算。
     */
    private BigDecimal cost;
}
