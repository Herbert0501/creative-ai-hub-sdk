package top.kangyaocoding.chatgpt.domain.billing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author K·Herbert
 * @Description  订阅信息类，用于表示用户的订阅详情。包含订阅的各种状态信息、付款方式、取消状态、计划等。
 * @Date 2024-05-23 18:48
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscription {
    // 订阅对象的类型
    @JsonProperty("object")
    private String object;
    // 是否有付款方式
    @JsonProperty("has_payment_method")
    private boolean hasPaymentMethod;
    // 订阅是否被取消
    @JsonProperty("canceled")
    private boolean canceled;
    // 订阅被取消的时间
    @JsonProperty("canceled_at")
    private Object canceledAt;
    // 是否逾期未付款
    @JsonProperty("delinquent")
    private Object delinquent;
    // 访问权限截止时间
    @JsonProperty("access_until")
    private long accessUntil;
    // 软性额度限制
    @JsonProperty("soft_limit")
    private long softLimit;
    // 硬性额度限制
    @JsonProperty("hard_limit")
    private long hardLimit;
    // 系统硬性额度限制
    @JsonProperty("system_hard_limit")
    private long systemHardLimit;
    // 软性额度限制（美元）
    @JsonProperty("soft_limit_usd")
    private double softLimitUsd;
    // 硬性额度限制（美元）
    @JsonProperty("hard_limit_usd")
    private double hardLimitUsd;
    // 系统硬性额度限制（美元）
    @JsonProperty("system_hard_limit_usd")
    private double systemHardLimitUsd;
    // 订阅计划信息
    @JsonProperty("plan")
    private Plan plan;
    // 账户名称
    @JsonProperty("account_name")
    private String accountName;
    // 购买订单号
    @JsonProperty("po_number")
    private Object poNumber;
    // 账单邮箱
    @JsonProperty("billing_email")
    private Object billingEmail;
    // 税号信息
    @JsonProperty("tax_ids")
    private Object taxIds;
    // 账单地址
    @JsonProperty("billing_address")
    private Object billingAddress;
    // 商务地址
    @JsonProperty("business_address")
    private Object businessAddress;
    // 是否为主要订阅
    @JsonProperty("primary")
    private Boolean primary;
}
