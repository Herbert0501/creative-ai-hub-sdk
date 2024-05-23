package top.kangyaocoding.ai.session;

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
     * @param chatCompletionRequest 请求信息
     * @return                      返回结果
     */
    ChatCompletionResponse completions(ChatCompletionRequest chatCompletionRequest);
}