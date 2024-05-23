package top.kangyaocoding.ai;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;
import top.kangyaocoding.ai.domain.chatgpt.ChatCompletionRequest;
import top.kangyaocoding.ai.domain.chatgpt.ChatCompletionResponse;

/**
 * @Author K·Herbert
 * @Description ChatGPT 官网 API 模型接口设计。https://platform.openai.com/playground
 * @Date 2024-05-22 16:29
 */

public interface IOpenAiApi {
    @POST("v1/chat/completions")
    Single<ChatCompletionResponse> chatCompletion(@Body ChatCompletionRequest request);
}