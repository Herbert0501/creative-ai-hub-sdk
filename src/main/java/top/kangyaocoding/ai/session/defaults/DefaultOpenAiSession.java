package top.kangyaocoding.ai.session.defaults;

import cn.hutool.http.ContentType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import top.kangyaocoding.ai.IOpenAiApi;
import top.kangyaocoding.ai.domain.chatgpt.ChatCompletionRequest;
import top.kangyaocoding.ai.domain.chatgpt.ChatCompletionResponse;
import top.kangyaocoding.ai.session.Configuration;
import top.kangyaocoding.ai.session.OpenAiSession;

/**
 * @Author K·Herbert
 * @Description OpenAi会话实现类，用于与OpenAI API进行交互。
 * @Date 2024-05-22 18:00
 */

public class DefaultOpenAiSession implements OpenAiSession {
    /* 配置信息 */
    private Configuration configuration;
    /* OpenAi 接口 */
    private IOpenAiApi openAiApi;
    /* 事件源工厂 */
    private EventSource.Factory eventSourceFactory;

    /**
     * 构造函数：初始化OpenAI API的接口实例。
     *
     * @param openAiApi 用于与OpenAI服务进行通信的API接口实例。
     */
    public DefaultOpenAiSession(Configuration configuration) {
        this.configuration = configuration;
        this.openAiApi = configuration.getOpenAiApi();
        this.eventSourceFactory = configuration.createEventSourceFactory();
    }

    /**
     * 发起与OpenAI的聊天完成（completions）请求。
     *
     * @param chatCompletionRequest 聊天完成请求对象，包含请求的参数和设置。
     * @return ChatCompletionResponse 聊天完成的响应对象，包含API返回的结果。
     */
    @Override
    public ChatCompletionResponse completions(ChatCompletionRequest chatCompletionRequest) {
        // 执行聊天完成的API调用，并阻塞直到获取到响应结果。
        return this.openAiApi.chatCompletion(chatCompletionRequest).blockingGet();
    }

    /**
     * 请求聊天完成建议的事件源。
     *
     * @param chatCompletionRequest 聊天完成请求对象，包含必要的参数来构建API请求。
     * @param eventSourceListener 事件源监听器，用于处理来自API的响应事件。
     * @return EventSource 返回一个事件源实例，用于监听聊天完成的建议。
     * @throws JsonProcessingException 当JSON处理（序列化或反序列化）发生错误时抛出。
     * @throws RuntimeException 如果聊天请求中的stream参数为false，抛出此异常。
     */
    @Override
    public EventSource chatCompletions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException {
        // 核心参数校验，特别检查stream参数必须为true
        if (!chatCompletionRequest.isStream()){
            throw new RuntimeException("Illegal parameter stream is false.");
        }

        // 构建向OpenAI API发送的请求
        Request request = new Request.Builder()
                .url(configuration.getApiHost().concat(IOpenAiApi.V_1_CHAT_COMPLETIONS))
                .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()), new ObjectMapper().writeValueAsString(chatCompletionRequest)))
                .build();

        // 返回一个新的事件源实例，用于处理与API的长连接通信
        return eventSourceFactory.newEventSource(request, eventSourceListener);
    }

}

