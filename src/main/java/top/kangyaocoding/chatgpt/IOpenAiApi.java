package top.kangyaocoding.chatgpt;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.*;
import top.kangyaocoding.chatgpt.domain.billing.BillingUsage;
import top.kangyaocoding.chatgpt.domain.billing.Subscription;
import top.kangyaocoding.chatgpt.domain.chatgpt.ChatCompletionRequest;
import top.kangyaocoding.chatgpt.domain.chatgpt.ChatCompletionResponse;
import top.kangyaocoding.chatgpt.domain.common.OpenAiResponse;
import top.kangyaocoding.chatgpt.domain.edits.EditRequest;
import top.kangyaocoding.chatgpt.domain.edits.EditResponse;
import top.kangyaocoding.chatgpt.domain.embeddings.EmbeddingRequest;
import top.kangyaocoding.chatgpt.domain.embeddings.EmbeddingResponse;
import top.kangyaocoding.chatgpt.domain.files.DeleteFileResponse;
import top.kangyaocoding.chatgpt.domain.files.UploadFileResponse;
import top.kangyaocoding.chatgpt.domain.images.ImageRequest;
import top.kangyaocoding.chatgpt.domain.images.ImageResponse;
import top.kangyaocoding.chatgpt.domain.whisper.WhisperResponse;

import java.time.LocalDate;
import java.io.File;
import java.util.Map;

/**
 * @Author K·Herbert
 * @Description ChatGPT 官网 API 模型接口设计。https://platform.openai.com/playground
 * 如聊天、编辑、图像生成、嵌入、文件操作、语音转文字、语音翻译、账户信息查询等
 * @Date 2024-05-22 16:29
 */

public interface IOpenAiApi {

    // 定义API
    String V_1_CHAT_COMPLETIONS = "v1/chat/completions";

    /**
     * 聊天接口。
     * 使用给定的请求参数生成聊天回复。
     *
     * @param request 包含聊天请求详情的ChatCompletionRequest对象。
     * @return 返回ChatCompletionResponse对象，包含聊天回复的结果。
     */
    @POST(V_1_CHAT_COMPLETIONS)
    Single<ChatCompletionResponse> chatCompletion(@Body ChatCompletionRequest request);

    /**
     * 编辑接口。
     * 根据提供的编辑请求进行模型输出的编辑。
     *
     * @param request 包含编辑请求详情的EditRequest对象。
     * @return 返回EditResponse对象，包含编辑结果。
     */
    @POST("v1/edits")
    Single<EditResponse> edit(@Body EditRequest request);

    /**
     * 图像生成接口。
     * 根据提供的图像请求生成图像。
     *
     * @param request 包含图像请求详情的ImageRequest对象。
     * @return 返回ImageResponse对象，包含生成的图像结果。
     */
    @POST("v1/images/generations")
    Single<ImageResponse> genImage(@Body ImageRequest request);

    /**
     * 图像编辑接口。
     * 编辑或修改已存在的图像。
     *
     * @param image 表示要编辑的图像的MultipartBody.Part对象。
     * @param mask 表示编辑的掩码的MultipartBody.Part对象。
     * @param requestBodyMap 包含额外请求参数的Map。
     * @return 返回ImageResponse对象，包含编辑后的图像结果。
     */
    @Multipart
    @POST("v1/images/edits")
    Single<ImageResponse> editImage(@Part MultipartBody.Part image, @Part MultipartBody.Part mask, @PartMap Map<String, RequestBody> requestBodyMap);

    /**
     * 嵌入接口。
     * 根据提供的嵌入请求生成嵌入向量。
     *
     * @param request 包含嵌入请求详情的EmbeddingRequest对象。
     * @return 返回EmbeddingResponse对象，包含嵌入向量结果。
     */
    @POST("v1/embeddings")
    Single<EmbeddingResponse> embeddings(@Body EmbeddingRequest request);

    /**
     * 文件列表接口。
     *
     * @return 返回包含文件列表的OpenAiResponse<File>对象。
     */
    @GET("v1/files")
    Single<OpenAiResponse<File>> files();

    /**
     * 上传文件接口。
     *
     * @param file 表示要上传文件的MultipartBody.Part对象。
     * @param purpose 文件用途说明。
     * @return 返回UploadFileResponse对象，包含上传文件的结果。
     */
    @Multipart
    @POST("v1/files")
    Single<UploadFileResponse> uploadFile(@Part MultipartBody.Part file, @Part("purpose") RequestBody purpose);

    /**
     * 删除文件接口。
     * 删除指定ID的文件。
     *
     * @param fileId 要删除的文件的ID。
     * @return 返回DeleteFileResponse对象，包含删除文件的结果。
     */
    @DELETE("v1/files/{file_id}")
    Single<DeleteFileResponse> deleteFile(@Path("file_id") String fileId);

    /**
     * 检索文件接口。
     * 根据文件ID检索文件信息。
     *
     * @param fileId 要检索的文件的ID。
     * @return 返回File对象，包含检索的文件信息。
     */
    @GET("v1/files/{file_id}")
    Single<File> retrieveFile(@Path("file_id") String fileId);

    /**
     * 检索文件内容接口。
     * 根据文件ID检索文件的内容。
     *
     * @param fileId 要检索内容的文件的ID。
     * @return 返回ResponseBody对象，包含文件的内容。
     */
    @Streaming
    @GET("v1/files/{file_id}/content")
    Single<ResponseBody> retrieveFileContent(@Path("file_id") String fileId);

    /**
     * 语音转文字接口。
     *
     * @param file 表示音频文件的MultipartBody.Part对象。
     * @param requestBodyMap 包含额外请求参数的Map。
     * @return 返回WhisperResponse对象，包含转换后的文字结果。
     */
    @Multipart
    @POST("v1/audio/transcriptions")
    Single<WhisperResponse> speed2TextTranscriptions(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> requestBodyMap);

    /**
     * 语音翻译接口。
     * 将提供的音频文件翻译为英文。
     *
     * @param file 表示音频文件的MultipartBody.Part对象。
     * @param requestBodyMap 包含额外请求参数的Map。
     * @return 返回WhisperResponse对象，包含翻译后的结果。
     */
    @Multipart
    @POST("v1/audio/translations")
    Single<WhisperResponse> speed2TextTranslations(@Part MultipartBody.Part file, @PartMap() Map<String, RequestBody> requestBodyMap);

    /**
     * 订阅信息查询接口。
     * 查询OpenAI账户的订阅详情，包括总金额等信息。
     *
     * @return 返回Subscription对象，包含订阅的详细信息。
     */
    @GET("v1/dashboard/billing/subscription")
    Single<Subscription> subscription();

    /**
     * 账单使用情况查询接口。
     * 查询指定日期范围内的账单使用情况。
     *
     * @param starDate 查询开始日期。
     * @param endDate 查询结束日期。
     * @return 返回BillingUsage对象，包含账单使用情况的详细信息。
     */
    @GET("v1/dashboard/billing/usage")
    Single<BillingUsage> billingUsage(@Query("start_date") LocalDate starDate, @Query("end_date") LocalDate endDate);
}
