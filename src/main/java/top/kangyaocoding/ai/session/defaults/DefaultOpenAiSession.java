package top.kangyaocoding.ai.session.defaults;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import top.kangyaocoding.ai.IOpenAiApi;
import top.kangyaocoding.ai.common.Constants;
import top.kangyaocoding.ai.domain.billing.BillingUsage;
import top.kangyaocoding.ai.domain.billing.Subscription;
import top.kangyaocoding.ai.domain.chatgpt.ChatCompletionRequest;
import top.kangyaocoding.ai.domain.chatgpt.ChatCompletionResponse;
import top.kangyaocoding.ai.domain.common.OpenAiResponse;
import top.kangyaocoding.ai.domain.edits.EditRequest;
import top.kangyaocoding.ai.domain.edits.EditResponse;
import top.kangyaocoding.ai.domain.embeddings.EmbeddingRequest;
import top.kangyaocoding.ai.domain.embeddings.EmbeddingResponse;
import top.kangyaocoding.ai.domain.files.DeleteFileResponse;
import top.kangyaocoding.ai.domain.files.UploadFileResponse;
import top.kangyaocoding.ai.domain.images.ImageEditRequest;
import top.kangyaocoding.ai.domain.images.ImageRequest;
import top.kangyaocoding.ai.domain.images.ImageResponse;
import top.kangyaocoding.ai.domain.whisper.TranscriptionsRequest;
import top.kangyaocoding.ai.domain.whisper.TranslationsRequest;
import top.kangyaocoding.ai.domain.whisper.WhisperResponse;
import top.kangyaocoding.ai.session.Configuration;
import top.kangyaocoding.ai.session.OpenAiSession;

import java.io.File;
import java.time.LocalDate;
import java.util.*;

/**
 * @Author K·Herbert
 * @Description OpenAi会话实现类，用于与OpenAI API进行交互。
 * @Date 2024-05-22 18:00
 */

@Slf4j
public class DefaultOpenAiSession implements OpenAiSession {
    /* 配置信息 */
    private final Configuration configuration;
    /* OpenAi 接口 */
    private final IOpenAiApi openAiApi;
    /* 事件源工厂 */
    private final EventSource.Factory eventSourceFactory;

    /**
     * 构造函数，用于初始化DefaultOpenAiSession对象。
     *
     * @param configuration 配置对象，包含了与OpenAI API交互所需的信息，如API密钥和事件源设置。
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
     * 这个方法是一个重载方法，用于在不需要用户指定API主机和密钥的情况下调用。
     * 它通过调用另一个重载方法来实现，该方法允许指定API主机和密钥。
     *
     * @param chatCompletionRequest 聊天完成请求的详细参数。
     * @param eventSourceListener 用于监听事件源事件的监听器。
     * @return 一个事件源实例，用于与OpenAI API进行实时通信。
     * @throws JsonProcessingException 如果无法处理JSON序列化和反序列化时抛出。
     */
    @Override
    public EventSource chatCompletions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException {
        return chatCompletions(Constants.NULL, Constants.NULL, chatCompletionRequest, eventSourceListener);
    }

    /**
     * 这个方法允许用户指定API主机和密钥，用于与OpenAI API进行通信。
     * 如果用户没有指定API主机和密钥，将使用默认配置。
     *
     * @param apiHostByUser 用户指定的API主机。
     * @param apiKeyByUser 用户指定的API密钥。
     * @param chatCompletionRequest 聊天完成请求的详细参数。
     * @param eventSourceListener 用于监听事件源事件的监听器。
     * @return 一个事件源实例，用于与OpenAI API进行实时通信。
     * @throws JsonProcessingException 如果无法处理JSON序列化和反序列化时抛出。
     */
    @Override
    public EventSource chatCompletions(String apiHostByUser, String apiKeyByUser, ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException {
        // 核心参数校验，特别检查stream参数必须为true
        if (!chatCompletionRequest.isStream()) {
            throw new RuntimeException("Illegal parameter stream is false.");
        }

        // 获取用户自定义的API主机和API密钥，如果未指定，则使用默认值
        String apiHost = (Constants.NULL.equals(apiHostByUser) || apiHostByUser.isEmpty()) ? configuration.getApiHost() : apiHostByUser;
        String apiKey = (Constants.NULL.equals(apiKeyByUser) || apiKeyByUser.isEmpty()) ? configuration.getApiKey() : apiKeyByUser;

        // 构建请求URL和请求体，准备调用OpenAI API。
        Request request = new Request.Builder()
                .url(apiHost.concat(IOpenAiApi.V_1_CHAT_COMPLETIONS))
                .addHeader("apiKey", apiKey)
                .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()), new ObjectMapper().writeValueAsString(chatCompletionRequest)))
                .build();

        // 根据构建的请求创建并返回一个事件源实例。
        return eventSourceFactory.newEventSource(request, eventSourceListener);
    }


    /**
     * 对给定的编辑请求进行处理。
     *
     * @param editRequest 包含编辑操作详细信息的请求对象。
     * @return 返回编辑操作的响应对象。
     */
    @Override
    public EditResponse edit(EditRequest editRequest) {
        return this.openAiApi.edit(editRequest).blockingGet();
    }

    /**
     * 根据提供的提示文本生成图像。
     *
     * @param prompt 用于生成图像的文本提示。
     * @return 返回图像生成的响应对象。
     */
    @Override
    public ImageResponse genImage(String prompt) {
        return this.genImage(ImageRequest.builder().prompt(prompt).build());
    }

    /**
     * 根据图像请求生成图像。
     *
     * @param imageRequest 包含图像生成详细信息的请求对象。
     * @return 返回图像生成的响应对象。
     */
    @Override
    public ImageResponse genImage(ImageRequest imageRequest) {
        return this.openAiApi.genImage(imageRequest).blockingGet();
    }

    /**
     * 根据提供的图像文件和提示编辑图像。
     *
     * @param image  待编辑的图像文件。
     * @param prompt 用于编辑图像的文本提示。
     * @return 返回图像编辑的响应对象。
     */
    @Override
    public ImageResponse editImage(File image, String prompt) {
        ImageEditRequest imageEditRequest = ImageEditRequest.builder().prompt(prompt).build();
        return this.editImage(image, null, imageEditRequest);
    }

    /**
     * 根据提供的图像文件和编辑请求编辑图像。
     *
     * @param image            待编辑的图像文件。
     * @param imageEditRequest 包含图像编辑详细信息的请求对象。
     * @return 返回图像编辑的响应对象。
     */
    @Override
    public ImageResponse editImage(File image, ImageEditRequest imageEditRequest) {
        return this.editImage(image, null, imageEditRequest);
    }


    /**
     * 编辑图片。
     * 该方法允许用户对提供的图像进行编辑，可选地应用一个掩码，并根据ImageEditRequest中的请求进行特定的编辑。
     *
     * @param image            需要编辑的原始图像文件。
     * @param mask             应用于原始图像的掩码文件，可以为null。
     * @param imageEditRequest 包含编辑请求详细信息的对象，如提示文本、编辑数量、大小、响应格式和可选的用户ID。
     * @return ImageResponse 包含编辑后图像信息的响应对象。
     */
    @Override
    public ImageResponse editImage(File image, File mask, ImageEditRequest imageEditRequest) {
        // 检查原始图像的合法性：存在性、格式和大小
        checkImage(image);
        checkImageFormat(image);
        checkImageSize(image);
        // 如果提供了掩码，同样检查掩码的格式和大小
        if (Objects.nonNull(mask)) {
            checkImageFormat(mask);
            checkImageSize(mask);
        }
        // 创建multipart/form-data类型的图像请求体
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), image);
        MultipartBody.Part imageMultipartBody = MultipartBody.Part.createFormData("image", image.getName(), imageBody);
        // 如果存在掩码，创建multipart/form-data类型的掩码请求体
        MultipartBody.Part maskMultipartBody = null;
        if (Objects.nonNull(mask)) {
            RequestBody maskBody = RequestBody.create(MediaType.parse("multipart/form-data"), mask);
            maskMultipartBody = MultipartBody.Part.createFormData("mask", mask.getName(), maskBody);
        }
        // 准备请求的参数体，包括编辑的提示文本、数量、大小、响应格式和可选的用户ID
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("prompt", RequestBody.create(MediaType.parse("multipart/form-data"), imageEditRequest.getPrompt()));
        requestBodyMap.put("n", RequestBody.create(MediaType.parse("multipart/form-data"), imageEditRequest.getN().toString()));
        requestBodyMap.put("size", RequestBody.create(MediaType.parse("multipart/form-data"), imageEditRequest.getSize()));
        requestBodyMap.put("response_format", RequestBody.create(MediaType.parse("multipart/form-data"), imageEditRequest.getResponseFormat()));
        // 如果用户ID非空，添加到请求体中
        if (!(Objects.isNull(imageEditRequest.getUser()) || imageEditRequest.getUser().isEmpty())) {
            requestBodyMap.put("user", RequestBody.create(MediaType.parse("multipart/form-data"), imageEditRequest.getUser()));
        }
        // 发起编辑图像的API请求，并阻塞直到获取响应
        return this.openAiApi.editImage(imageMultipartBody, maskMultipartBody, requestBodyMap).blockingGet();
    }

    /**
     * 校验图片不能为空的方法。
     * 对传入的图片文件进行非空校验，如果图片为空，则记录错误日志并抛出NullPointerException。
     *
     * @param image 需要校验的图片文件
     */
    private void checkImage(File image) {
        if (Objects.isNull(image)) {
            log.error("image不能为空");
            throw new NullPointerException("image不能为空");
        }
    }

    /**
     * 校验图片格式的方法。
     * 对传入的图片文件进行格式校验，只支持PNG格式的图片，如果不满足则记录错误日志并抛出IllegalArgumentException。
     *
     * @param image 需要校验的图片文件
     */
    private void checkImageFormat(File image) {
        // 校验图片格式是否为PNG
        if (!(image.getName().endsWith("png") || image.getName().endsWith("PNG"))) {
            log.error("image格式错误");
            throw new IllegalArgumentException("image格式错误");
        }
    }

    /**
     * 校验图片大小的方法。
     * 对传入的图片文件进行大小校验，限制图片大小不得超过4MB，如果超过则记录错误日志并抛出IllegalArgumentException。
     *
     * @param image 需要校验的图片文件
     */
    private void checkImageSize(File image) {
        // 校验图片大小是否超过4MB
        if (image.length() > 4 * 1024 * 1024) {
            log.error("image最大支持4MB");
            throw new IllegalArgumentException("image最大支持4MB");
        }
    }


    /**
     * 提供一个字符串作为输入。
     *
     * @param input 单个字符串输入。
     * @return EmbeddingResponse 嵌入响应对象。
     */
    @Override
    public EmbeddingResponse embeddings(String input) {
        // 将单个输入字符串添加到列表中
        List<String> inputList = new ArrayList<>();
        inputList.add(input);
        EmbeddingRequest embeddingRequest = EmbeddingRequest.builder().input(inputList).build();
        return this.embeddings(embeddingRequest);
    }

    /**
     * 提供一个字符串数组作为输入。
     *
     * @param inputs 字符串数组输入。
     * @return EmbeddingResponse 嵌入响应对象。
     */
    @Override
    public EmbeddingResponse embeddings(String... inputs) {
        // 将变长参数转换为列表
        List<String> inputList = Arrays.asList(inputs);
        EmbeddingRequest embeddingRequest = EmbeddingRequest.builder().input(inputList).build();
        return this.embeddings(embeddingRequest);
    }

    /**
     * 提供一个字符串列表作为输入。
     *
     * @param inputs 字符串列表输入。
     * @return EmbeddingResponse 嵌入响应对象。
     */
    @Override
    public EmbeddingResponse embeddings(List<String> inputs) {
        // 直接使用提供的字符串列表构建请求
        EmbeddingRequest embeddingRequest = EmbeddingRequest.builder().input(inputs).build();
        return this.embeddings(embeddingRequest);
    }

    /**
     * 提供一个EmbeddingRequest对象作为输入。
     *
     * @param embeddingRequest 包含嵌入所需全部信息的请求对象。
     * @return EmbeddingResponse 嵌入响应对象。
     */
    @Override
    public EmbeddingResponse embeddings(EmbeddingRequest embeddingRequest) {
        // 直接通过OpenAI API发送嵌入请求，并阻塞直到获取响应
        return this.openAiApi.embeddings(embeddingRequest).blockingGet();
    }


    /**
     * 获取文件信息。
     *
     * @return OpenAiResponse<File> 返回文件信息的响应对象。
     */
    @Override
    public OpenAiResponse<File> files() {
        return openAiApi.files().blockingGet();
    }

    /**
     * 上传文件到OpenAI。
     *
     * @param file 要上传的文件对象。
     * @return UploadFileResponse 返回文件上传的响应对象。
     */
    @Override
    public UploadFileResponse uploadFile(File file) {
        return uploadFile(file, "fine-tune");
    }

    /**
     * 上传文件到OpenAI，并指定文件用途。
     *
     * @param file    要上传的文件对象。
     * @param purpose 文件的用途说明。
     * @return UploadFileResponse 返回文件上传的响应对象。
     */
    @Override
    public UploadFileResponse uploadFile(File file, String purpose) {
        // 创建multipart/form-data类型的请求体以上传文件
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), fileBody);

        // 创建用途说明的请求体
        RequestBody purposeBody = RequestBody.create(MediaType.parse("multipart/form-data"), purpose);

        // 执行文件上传操作
        return this.openAiApi.uploadFile(multipartBody, purposeBody).blockingGet();
    }

    /**
     * 删除指定的文件。
     *
     * @param fileId 要删除的文件的ID。
     * @return DeleteFileResponse 返回文件删除操作的响应对象。
     */
    @Override
    public DeleteFileResponse deleteFile(String fileId) {
        return this.openAiApi.deleteFile(fileId).blockingGet();
    }


    /**
     * 将语音文件转换为文本转写。
     *
     * @param file                  需要转换的语音文件。
     * @param transcriptionsRequest 包含转写请求的详细参数，如语言、模型、提示语等。
     * @return 返回转写完成的文本响应。
     */
    @Override
    public WhisperResponse speed2TextTranscriptions(File file, TranscriptionsRequest transcriptionsRequest) {
        // 1. 文件封装为MultipartBody.Part 类型，用于上传
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), fileBody);

        // 2. 参数封装
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        // 将请求中的可选参数（如语言、模型、提示语、响应格式和温度）封装到requestBodyMap中
        if (StrUtil.isNotBlank(transcriptionsRequest.getLanguage())) {
            requestBodyMap.put(TranscriptionsRequest.Fields.language, RequestBody.create(MediaType.parse("multipart/form-data"), transcriptionsRequest.getLanguage()));
        }
        if (StrUtil.isNotBlank(transcriptionsRequest.getModel())) {
            requestBodyMap.put(TranscriptionsRequest.Fields.model, RequestBody.create(MediaType.parse("multipart/form-data"), transcriptionsRequest.getModel()));
        }
        if (StrUtil.isNotBlank(transcriptionsRequest.getPrompt())) {
            requestBodyMap.put(TranscriptionsRequest.Fields.prompt, RequestBody.create(MediaType.parse("multipart/form-data"), transcriptionsRequest.getPrompt()));
        }
        if (StrUtil.isNotBlank(transcriptionsRequest.getResponseFormat())) {
            requestBodyMap.put(TranscriptionsRequest.Fields.responseFormat, RequestBody.create(MediaType.parse("multipart/form-data"), transcriptionsRequest.getResponseFormat()));
        }
        requestBodyMap.put(TranscriptionsRequest.Fields.temperature, RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(transcriptionsRequest.getTemperature())));

        // 调用API进行语音转写，并阻塞当前线程直到转写完成
        return this.openAiApi.speed2TextTranscriptions(multipartBody, requestBodyMap).blockingGet();
    }

    /**
     * 将语音文件转换为文本的翻译请求。
     *
     * @param file                语音文件，将被转换为文本并进行翻译。
     * @param translationsRequest 包含翻译请求的详细参数，如模型、提示语、响应格式和温度等。
     * @return 返回翻译的响应对象，包含转换后的文本等信息。
     */
    @Override
    public WhisperResponse speed2TextTranslations(File file, TranslationsRequest translationsRequest) {
        // 1. 上传语音文件
        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), fileBody);

        // 2. 封装请求参数
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        // 根据请求封装模型、提示语、响应格式和温度参数
        if (StrUtil.isNotBlank(translationsRequest.getModel())) {
            requestBodyMap.put(TranslationsRequest.Fields.model, RequestBody.create(MediaType.parse("multipart/form-data"), translationsRequest.getModel()));
        }
        if (StrUtil.isNotBlank(translationsRequest.getPrompt())) {
            requestBodyMap.put(TranslationsRequest.Fields.prompt, RequestBody.create(MediaType.parse("multipart/form-data"), translationsRequest.getPrompt()));
        }
        if (StrUtil.isNotBlank(translationsRequest.getResponseFormat())) {
            requestBodyMap.put(TranslationsRequest.Fields.responseFormat, RequestBody.create(MediaType.parse("multipart/form-data"), translationsRequest.getResponseFormat()));
        }
        requestBodyMap.put(TranslationsRequest.Fields.temperature, RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(translationsRequest.getTemperature())));
        requestBodyMap.put(TranscriptionsRequest.Fields.temperature, RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(translationsRequest.getTemperature())));

        // 发起翻译请求并获取结果
        return this.openAiApi.speed2TextTranscriptions(multipartBody, requestBodyMap).blockingGet();
    }

    /**
     * 查询当前订阅信息, 查询余额。
     *
     * @return Subscription 返回当前的订阅信息。
     */
    @Override
    public Subscription subscription() {
        // 直接调用openAiApi的subscription方法获取订阅信息，并通过blockingGet方法阻塞直到结果返回
        return this.openAiApi.subscription().blockingGet();
    }

    /**
     * 查询指定时间范围内的账单使用情况。
     *
     * @param starDate 开始日期，不可为null。
     * @param endDate  结束日期，不可为null。
     * @return BillingUsage 返回指定时间范围内的账单使用情况。
     */
    @Override
    public BillingUsage billingUsage(@NotNull LocalDate starDate, @NotNull LocalDate endDate) {
        // 调用openAiApi的billingUsage方法查询账单使用情况，传入开始和结束日期，然后通过blockingGet方法阻塞直到结果返回
        return this.openAiApi.billingUsage(starDate, endDate).blockingGet();
    }

}

