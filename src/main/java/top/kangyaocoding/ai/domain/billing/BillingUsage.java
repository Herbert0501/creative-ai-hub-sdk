package top.kangyaocoding.ai.domain.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author K·Herbert
 * @Description BillingUsage类用于表示账单使用情况。包含了账单的详细信息，如每日消费明细、总使用金额等。
 * @Date 2024-05-23 18:51
 */

@Data
public class BillingUsage {
    @JsonProperty("object")
    private String object; // 表示对象类型的属性

    /**
     * dailyCosts表示账号的每日消费明细列表。
     * 每个DailyCost对象包含了一天内的详细消费信息。
     */
    @JsonProperty("daily_costs")
    private List<DailyCost> dailyCosts;

    /**
     * totalUsage表示总使用金额，以美分为单位。
     * 这个属性总结了账号在一段时间内的总消费金额。
     */
    @JsonProperty("total_usage")
    private BigDecimal totalUsage;
}
