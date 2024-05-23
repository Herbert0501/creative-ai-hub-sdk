package top.kangyaocoding.ai.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import top.kangyaocoding.ai.domain.chatgpt.ChatCompletionRequest;
import top.kangyaocoding.ai.domain.chatgpt.ChatCompletionResponse;

/**
 * @Author K·Herbert
 * @Description OpenAi 会话接口
 * @Date 2024-05-22 17:57
 */
public interface OpenAiSession {
    /**
     * 默认 GPT-3.5 问答模型
     *
     * @param chatCompletionRequest 请求信息
     * @return 返回结果
     */
    ChatCompletionResponse completions(ChatCompletionRequest chatCompletionRequest);

    /**
     * 流式问答模型 Stream 模式
     *
     * @param chatCompletionRequest 聊天完成请求对象，包含请求的详细信息。
     * @param eventSourceListener   事件源监听器，用于监听和处理事件源产生的事件。
     * @return 返回一个配置好的事件源对象，可用于监听聊天完成的事件。
     */
    EventSource chatCompletions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;
}