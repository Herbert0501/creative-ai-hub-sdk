package top.kangyaocoding.ai.interceptor;

import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @Author K·Herbert
 * @Description OpenAiInterceptor 类实现了 Interceptor 接口，用于在 OkHttp 请求中添加认证信息。
 * 这个拦截器会自动为每个请求添加 API 密钥和认证令牌。
 * @Date 2024-05-22 17:40
 */
public class OpenAiInterceptor implements Interceptor{
    private final String apiKey; // API 密钥
    private final String authToken; // 认证令牌

    /**
     * OpenAiInterceptor 构造函数。
     *
     * @param apiKey API 密钥，用于授权访问 OpenAI 服务。
     * @param authToken 认证令牌，用于验证请求的合法性。
     */
    public OpenAiInterceptor(String apiKey, String authToken) {
        this.apiKey = apiKey;
        this.authToken = authToken;
    }

    /**
     * intercept 方法是 Interceptor 接口的必须实现方法，用于在请求发送前拦截请求，并进行认证信息的添加。
     *
     * @param chain 提供了请求和响应的 Chain，允许拦截器修改请求或直接返回响应。
     * @return 返回经过可能修改后的请求产生的响应。
     * @throws IOException 如果发生 I/O 错误。
     */
    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        // 在请求链中继续处理请求，添加认证信息。
        return chain.proceed(auth(this.apiKey, chain.request()));
    }

    /**
     * auth 方法用于为请求添加认证信息。
     *
     * @param apiKey API 密钥。
     * @param originalRequest 原始请求。
     * @return 返回添加了认证信息的请求。
     */
    private Request auth(String apiKey, Request originalRequest){
        // 为 URL 添加认证令牌查询参数
        HttpUrl url = originalRequest.url().newBuilder()
                .addQueryParameter("token", authToken)
                .build();

        // 创建并返回一个新请求，包含认证头和内容类型头。
        return originalRequest.newBuilder()
                .url(url)
                .header(Header.AUTHORIZATION.getValue(), "Bearer " + apiKey)
                .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                .method(originalRequest.method(), originalRequest.body())
                .build();
    }
}

