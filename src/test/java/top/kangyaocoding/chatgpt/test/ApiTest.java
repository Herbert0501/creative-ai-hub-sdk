package top.kangyaocoding.chatgpt.test;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.junit.Before;
import org.junit.Test;
import top.kangyaocoding.chatgpt.common.Constants;
import top.kangyaocoding.chatgpt.domain.chatgpt.ChatCompletionRequest;
import top.kangyaocoding.chatgpt.domain.chatgpt.ChatCompletionResponse;
import top.kangyaocoding.chatgpt.domain.chatgpt.Message;
import top.kangyaocoding.chatgpt.session.Configuration;
import top.kangyaocoding.chatgpt.session.OpenAiSession;
import top.kangyaocoding.chatgpt.session.OpenAiSessionFactory;
import top.kangyaocoding.chatgpt.session.defaults.DefaultOpenAiSessionFactory;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
        configuration.setApiHost("https://api.playaichat.cn/");
        configuration.setApiKey(apiKey);
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration, null);
        this.openAiSession = factory.openAiSession();
    }

    @Test
    public void test_completions() {
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(Collections.singletonList(Message.builder()
                        .role(Constants.Role.USER)
                        .content("1+1=")
                        .build()))
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
                .build();
        ChatCompletionResponse chatCompletionResponse = openAiSession.completions(chatCompletionRequest);

        log.info(String.valueOf(chatCompletionResponse));
    }

    /**
     * 使用流式 Stream 模型进行聊天完成测试。
     * 该方法构建一个聊天完成请求，发送给OpenAI，并监听事件响应。
     *
     * @throws Exception 抛出异常的条件不明确，因为具体实现细节未提供，但通常可能由于网络问题、API限制等原因抛出异常。
     */
    @Test
    public void test_chatCompletions() throws Exception {
        // 构建聊天完成请求对象，设置用户消息和使用的模型
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(Collections.singletonList((Message.builder()
                        .role(Constants.Role.USER)
                        .content("你好！帮我写一个Python冒泡排序")).build()))
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
                .stream(true)
                .build();

        // 使用 CountDownLatch 来控制测试的同步
        CountDownLatch latch = new CountDownLatch(1);
        StringBuilder responseBuilder = new StringBuilder();

        String apiHost = "";
        String apiKey = "";

        // 发起聊天完成请求，并设置事件监听器，用于处理接收到的事件响应
        EventSource eventSource = openAiSession.chatCompletions(apiHost, apiKey, chatCompletionRequest, new EventSourceListener() {
            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                try {
                    // 接收到事件时，记录日志信息
                    log.info("接收到事件 - ID: {}, 类型: {}, 数据: {}", id, type, data);

                    // 累积响应数据
                    responseBuilder.append(data);

                    // 检查是否是结束事件，假设事件类型为 "done" 表示流结束
                    if ("done".equals(type)) {
                        latch.countDown();
                    }
                } catch (Exception e) {
                    log.error("处理事件时发生异常", e);
                    latch.countDown(); // 确保异常情况下也能退出等待
                }
            }

            @Override
            public void onFailure(EventSource eventSource, Throwable t, Response response) {
                log.error("事件源发生错误", t);
                latch.countDown();
            }
        });

        try {
            // 等待最多1分钟，如果在这段时间内 latch 没有 countDown，就会超时
            if (!latch.await(1, TimeUnit.MINUTES)) {
                log.warn("等待超时，测试未能在1分钟内完成");
            }
        } finally {
            // 确保在所有情况下资源都能被释放
            eventSource.cancel();
        }

        // 输出完整的响应数据
        log.info("完整的测试结果: {}", responseBuilder.toString());
    }

}
