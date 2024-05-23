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

    String V_1_CHAT_COMPLETIONS = "v1/chat/completions";

    @POST(V_1_CHAT_COMPLETIONS)
    Single<ChatCompletionResponse> chatCompletion(@Body ChatCompletionRequest request);
}