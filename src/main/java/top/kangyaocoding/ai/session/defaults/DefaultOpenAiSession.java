package top.kangyaocoding.ai.session.defaults;

import top.kangyaocoding.ai.IOpenAiApi;
import top.kangyaocoding.ai.domain.chatgpt.ChatCompletionRequest;
import top.kangyaocoding.ai.domain.chatgpt.ChatCompletionResponse;
import top.kangyaocoding.ai.session.OpenAiSession;

/**
 * @Author K·Herbert
 * @Description OpenAi会话实现类，用于与OpenAI API进行交互。
 * @Date 2024-05-22 18:00
 */

public class DefaultOpenAiSession implements OpenAiSession {
    // OpenAI API的接口实例，用于执行API调用。
    private final IOpenAiApi openAiApi;

    /**
     * 构造函数：初始化OpenAI API的接口实例。
     *
     * @param openAiApi 用于与OpenAI服务进行通信的API接口实例。
     */
    public DefaultOpenAiSession(IOpenAiApi openAiApi) {
        this.openAiApi = openAiApi;
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
}

