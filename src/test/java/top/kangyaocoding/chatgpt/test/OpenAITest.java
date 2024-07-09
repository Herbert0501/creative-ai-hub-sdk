package top.kangyaocoding.chatgpt.test;

import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import io.reactivex.Single;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import top.kangyaocoding.chatgpt.IOpenAiApi;
import top.kangyaocoding.chatgpt.common.Constants;
import top.kangyaocoding.chatgpt.domain.chatgpt.ChatCompletionRequest;
import top.kangyaocoding.chatgpt.domain.chatgpt.ChatCompletionResponse;
import top.kangyaocoding.chatgpt.domain.chatgpt.Message;

import java.util.Collections;

/**
 * @Author K·Herbert
 * @Description
 * @Date 2024-05-22 19:24
 */

public class OpenAITest {
    private static final String apiKey = System.getenv("OPENAI_API_KEY");

    public static void main(String[] args) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // InetSocketAddress proxyAddress = new InetSocketAddress("127.0.0.1", 7890);

        // Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddress);

        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    // 从请求中获取 token 参数，并将其添加到请求路径中
                    HttpUrl url = original.url().newBuilder()
                            .addQueryParameter("token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTcxNjM3ODA0MiwiaWF0IjoxNzE2Mzc3NzQyLCJqdGkiOiIyYjA3ZmZlNi1jMTgxLTQ3ZDYtOWNmYS0yYzQwMWM0M2Q4M2QiLCJ1c2VybmFtZSI6ImFkbWluIn0.ysNC6-HL5EYbaB4b9mBt51EcziwwrA0j87Hp77RqX_M")
                            .build();
                    Request request = original.newBuilder()
                            .url(url)
                            .header(Header.AUTHORIZATION.getValue(), "Bearer " + apiKey)
                            .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();

        IOpenAiApi openAiApi = new Retrofit.Builder()
                .baseUrl("https://4.0.wokaai.com/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(IOpenAiApi.class);

        Message message = Message.builder().role(Constants.Role.USER).content("你好！").build();

        ChatCompletionRequest chatCompletion = ChatCompletionRequest
                .builder()
                .messages(Collections.singletonList(message))
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO_16K.getCode())
                .build();

        Single<ChatCompletionResponse> chatCompletionResponseSingle = openAiApi.chatCompletion(chatCompletion);
        ChatCompletionResponse chatCompletionResponse = chatCompletionResponseSingle.blockingGet();

        chatCompletionResponse.getChoices().forEach(e -> {
            System.out.println(e.getMessage());
        });
    }
}
