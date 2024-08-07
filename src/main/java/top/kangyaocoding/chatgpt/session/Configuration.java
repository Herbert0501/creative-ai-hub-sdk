package top.kangyaocoding.chatgpt.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSources;
import org.jetbrains.annotations.NotNull;
import top.kangyaocoding.chatgpt.IOpenAiApi;

/**
 * @Author K·Herbert
 * @Description 配置信息
 * @Date 2024-05-22 17:55
 */

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {

    /* OpenAI API的接口实例 */
    private IOpenAiApi openAiApi;

    /* 用于API请求的HTTP客户端 */
    private OkHttpClient okHttpClient;

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
     * 创建一个事件源工厂。
     *
     * @return 返回一个配置好的事件源工厂实例。
     */
    public EventSource.Factory createEventSourceFactory() {
        return EventSources.createFactory(okHttpClient);
    }
}
