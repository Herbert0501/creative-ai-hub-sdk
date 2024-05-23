package top.kangyaocoding.ai.session.defaults;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import top.kangyaocoding.ai.IOpenAiApi;
import top.kangyaocoding.ai.interceptor.OpenAiInterceptor;
import top.kangyaocoding.ai.session.Configuration;
import top.kangyaocoding.ai.session.OpenAiSession;
import top.kangyaocoding.ai.session.OpenAiSessionFactory;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * @Author K·Herbert
 * @Description OpenAI 会话工厂类，用于创建 OpenAiSession 实例。
 * @Date 2024-05-22 18:02
 */

public class DefaultOpenAiSessionFactory implements OpenAiSessionFactory {

    private final Configuration configuration; // 应用配置信息
    private final Proxy proxy;

    /**
     * 构造函数
     *
     * @param configuration 应用的配置信息，包括 API 密钥、认证令牌和 API 主机地址等。
     */
    public DefaultOpenAiSessionFactory(Configuration configuration, Proxy proxy) {
        this.configuration = configuration;
        this.proxy = proxy;
    }

    /**
     * 创建一个新的 OpenAiSession 实例。
     *
     * @return 返回配置好的 OpenAiSession 实例，该实例可用于与 OpenAI API 进行交互。
     */
    @Override
    public OpenAiSession openAiSession() {
        // 配置日志拦截器，用于打印 HTTP 请求的头部信息
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        // 配置 OkHttpClient，添加拦截器以注入 API 密钥和认证令牌，以及设置连接、写入和读取的超时时间
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor) // 日志拦截器
                .addInterceptor(new OpenAiInterceptor(configuration.getApiKey(), configuration.getAuthToken())) // API 密钥和认证令牌拦截器
                .connectTimeout(450, TimeUnit.SECONDS) // 连接超时时间
                .writeTimeout(450, TimeUnit.SECONDS) // 写入超时时间
                .readTimeout(450, TimeUnit.SECONDS) // 读取超时时间
                .build();
        // 如果代理不为空，则创建新的 OkHttpClient 实例，并设置代理
        if (proxy != null) {
            okHttpClient = okHttpClient.newBuilder().proxy(proxy).build();
        }

        // 创建 OpenAI API 服务接口的实例，配置 Retrofit 使用 OKHttp 作为网络客户端，并指定响应适配器和转换器
        IOpenAiApi openAiApi = new Retrofit.Builder()
                .baseUrl(configuration.getApiHost()) // API 主机地址
                .client(okHttpClient) // 使用配置好的 OkHttpClient
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持 RxJava 2 的调用适配器
                .addConverterFactory(JacksonConverterFactory.create()) // 使用 Jackson 作为数据转换器
                .build().create(IOpenAiApi.class);

        // 返回一个新的 OpenAiSession 实例，内部封装了 OpenAI API 的服务接口实例
        return new DefaultOpenAiSession(openAiApi);
    }
}
