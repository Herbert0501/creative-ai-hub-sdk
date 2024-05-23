package top.kangyaocoding.ai.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * @Author K·Herbert
 * @Description 配置信息
 * @Date 2024-05-22 17:55
 */

@Getter
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {
    /**
     * apiKey用于识别调用方的身份。
     * 它是必填的，不能为空。
     */
    @NotNull
    private String apiKey;

    /**
     * apiHost指定外部服务的主机地址。
     * 可以为空，如果不为空，则用于构建请求的URL。
     */
    private String apiHost;

    /**
     * authToken用于授权访问。
     * 它是可选的，如果需要身份验证，应提供authToken。
     */
    private String authToken;
}
