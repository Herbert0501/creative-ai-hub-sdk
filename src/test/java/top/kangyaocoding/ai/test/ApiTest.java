package top.kangyaocoding.ai.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import top.kangyaocoding.ai.common.Constants;
import top.kangyaocoding.ai.domain.chatgpt.ChatCompletionRequest;
import top.kangyaocoding.ai.domain.chatgpt.ChatCompletionResponse;
import top.kangyaocoding.ai.domain.chatgpt.Message;
import top.kangyaocoding.ai.session.Configuration;
import top.kangyaocoding.ai.session.OpenAiSession;
import top.kangyaocoding.ai.session.OpenAiSessionFactory;
import top.kangyaocoding.ai.session.defaults.DefaultOpenAiSessionFactory;

import java.util.Collections;

/**
 * @Author K·Herbert
 * @Description
 * @Date 2024-05-22 18:52
 */
@Slf4j
public class ApiTest {
    private OpenAiSession openAiSession;

    @Before
    public void test_OpenAiSessionFactory() {
        // 从环境变量中获取Key
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null) {
            throw new RuntimeException("OPENAI_API_KEY is not set");
        }
        // 配置环境
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://gf.nekoapi.com/");
        configuration.setApiKey(apiKey);
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration, null);
        this.openAiSession = factory.openAiSession();
    }

    @Test
    public void test_completions() {
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content("你好！这是我第一次调用OpenAI API。").build()))
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
                .build();
        ChatCompletionResponse chatCompletionResponse = openAiSession.completions(chatCompletionRequest);

        chatCompletionResponse.getChoices().forEach(
                choice -> {
                    log.info("{}", choice);
                }
        );
    }
}
