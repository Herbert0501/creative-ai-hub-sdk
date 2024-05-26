package top.kangyaocoding.ai.test;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.junit.Before;
import org.junit.Test;
import top.kangyaocoding.ai.common.Constants;
import top.kangyaocoding.ai.domain.billing.BillingUsage;
import top.kangyaocoding.ai.domain.billing.Subscription;
import top.kangyaocoding.ai.domain.chatgpt.ChatCompletionRequest;
import top.kangyaocoding.ai.domain.chatgpt.ChatCompletionResponse;
import top.kangyaocoding.ai.domain.chatgpt.Message;
import top.kangyaocoding.ai.domain.edits.EditRequest;
import top.kangyaocoding.ai.domain.edits.EditResponse;
import top.kangyaocoding.ai.domain.embeddings.EmbeddingResponse;
import top.kangyaocoding.ai.domain.files.DeleteFileResponse;
import top.kangyaocoding.ai.domain.files.File;
import top.kangyaocoding.ai.domain.files.UploadFileResponse;
import top.kangyaocoding.ai.domain.images.ImageResponse;
import top.kangyaocoding.ai.session.Configuration;
import top.kangyaocoding.ai.session.OpenAiSession;
import top.kangyaocoding.ai.session.OpenAiSessionFactory;
import top.kangyaocoding.ai.session.defaults.DefaultOpenAiSessionFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        configuration.setApiHost("https://gf.nekoapi.com/");
        configuration.setApiKey(apiKey);
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration, null);
        this.openAiSession = factory.openAiSession();
    }

    @Test
    public void test_completions() {
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder().messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content("你好！这是我第一次调用OpenAI API。").build())).model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode()).build();
        ChatCompletionResponse chatCompletionResponse = openAiSession.completions(chatCompletionRequest);

        chatCompletionResponse.getChoices().forEach(choice -> {
            log.info("{}", choice);
        });
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
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder().messages(Collections.singletonList((Message.builder().role(Constants.Role.USER).content("你好！这是我第五次调用OpenAI API。帮我写一个冒泡排序")).build())).model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode()).stream(true).build();

        // 使用 CountDownLatch 来控制测试的同步
        CountDownLatch latch = new CountDownLatch(1);
        StringBuilder responseBuilder = new StringBuilder();

        // 发起聊天完成请求，并设置事件监听器，用于处理接收到的事件响应
        EventSource eventSource = openAiSession.chatCompletions(chatCompletionRequest, new EventSourceListener() {
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

    @Test
    public void test_chat_completions_context() {
        // 1-1. 创建参数
        ChatCompletionRequest chatCompletion = ChatCompletionRequest.builder().messages(new ArrayList<>()).model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode()).user("testUser01").build();
        // 写入请求信息
        chatCompletion.getMessages().add(Message.builder().role(Constants.Role.USER).content("写一个java冒泡排序").build());

        // 1-2. 发起请求
        ChatCompletionResponse chatCompletionResponse01 = openAiSession.completions(chatCompletion);
        log.info("测试结果：{}", chatCompletionResponse01.getChoices());

        // 写入请求信息
        chatCompletion.getMessages().add(Message.builder().role(Constants.Role.USER).content(chatCompletionResponse01.getChoices().get(0).getMessage().getContent()).build());
        chatCompletion.getMessages().add(Message.builder().role(Constants.Role.USER).content("换一种写法").build());

        ChatCompletionResponse chatCompletionResponse02 = openAiSession.completions(chatCompletion);
        log.info("测试结果：{}", chatCompletionResponse02.getChoices());
    }

    /**
     * 文本修复
     */
    @Test
    public void test_edit() {
        // 文本请求
        EditRequest textRequest = EditRequest.builder().input("inptu a tesx").instruction("帮我修改错字").model(EditRequest.Model.TEXT_DAVINCI_EDIT_001.getCode()).build();
        EditResponse textResponse = openAiSession.edit(textRequest);
        log.info("测试结果：{}", textResponse);

        // 代码请求
        EditRequest codeRequest = EditRequest.builder()
                // j <= 10 应该修改为 i <= 10
                .input("for (int i = 1; j <= 10; i++) {\n" + "    System.out.println(i);\n" + "}").instruction("这段代码执行时报错，请帮我修改").model(EditRequest.Model.CODE_DAVINCI_EDIT_001.getCode()).build();
        EditResponse codeResponse = openAiSession.edit(codeRequest);
        log.info("测试结果：{}", codeResponse);
    }

    @Test
    public void test_genImages() {
        // 方式1，简单调用
        ImageResponse imageResponse01 = openAiSession.genImage("画一个996加班的程序员, 超级累");
        log.info("测试结果：{}", imageResponse01);
    }

    /**
     * 修改图片，有3个方法，入参不同。
     */
    @Test
    public void test_editImages() throws IOException {
        ImageResponse imageResponse = openAiSession.editImage(new java.io.File("C:\\Users\\HarryKL\\Pictures\\123.png"), "去除图片中的文字");
        log.info("测试结果：{}", imageResponse);
    }

    @Test
    public void test_embeddings() {
        EmbeddingResponse embeddingResponse = openAiSession.embeddings("哈喽", "嗨", "hi!");
        log.info("测试结果：{}", embeddingResponse);
    }

    @Test
    public void test_files() {
        List<File> openAiResponse = openAiSession.files();
        log.info("测试结果：{}", openAiResponse);
    }

    @Test
    public void test_uploadFile() {
        UploadFileResponse uploadFileResponse = openAiSession.uploadFile(new java.io.File("C:\\Users\\HarryKL\\Pictures\\ikun.png"));
        log.info("测试结果：{}", uploadFileResponse);
    }

    @Test
    public void test_deleteFile() {
        DeleteFileResponse deleteFileResponse = openAiSession.deleteFile("file id 上传后才能获得");
        log.info("测试结果：{}", deleteFileResponse);
    }

    @Test
    public void test_subscription() {
        Subscription subscription = openAiSession.subscription();
        log.info("测试结果：{}", subscription);
    }

    @Test
    public void test_billingUsage() {
        BillingUsage billingUsage = openAiSession.billingUsage(LocalDate.of(2024, 1, 1), LocalDate.now());
        log.info("测试结果：{}", billingUsage.getTotalUsage());
    }

}
