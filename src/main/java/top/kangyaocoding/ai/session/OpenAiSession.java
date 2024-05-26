package top.kangyaocoding.ai.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
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

import java.time.LocalDate;
import java.io.File;
import java.util.List;

/**
 * @Author K·Herbert
 * @Description OpenAi 会话接口
 * @Date 2024-05-22 17:57
 */
public interface OpenAiSession {
    /**
     * 使用默认的 GPT-3.5 问答模型进行问答。
     *
     * @param chatCompletionRequest 包含问答请求的详细信息的对象。
     * @return 返回问答结果的响应对象。
     */
    ChatCompletionResponse completions(ChatCompletionRequest chatCompletionRequest);

    /**
     * 使用流式问答模型进行问答，以 Stream 模式处理。
     *
     * @param chatCompletionRequest 聊天完成请求对象，包含请求的详细信息。
     * @param eventSourceListener   用于监听和处理事件源产生的事件的监听器。
     * @return 返回一个配置好的事件源对象，用于监听聊天完成的事件。
     * @throws JsonProcessingException 当处理 JSON 数据发生错误时抛出。
     */
    EventSource chatCompletions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;

    /**
     * 进行文本编辑操作。
     *
     * @param editRequest 编辑请求对象，包含编辑请求的详细信息。
     * @return 返回编辑操作的结果响应对象。
     */
    EditResponse edit(EditRequest editRequest);

    /**
     * 根据提示生成图像。
     *
     * @param prompt 提示文本，用于指导图像生成。
     * @return 返回图像生成的结果响应对象。
     */
    ImageResponse genImage(String prompt);

    /**
     * 根据图像请求生成图像。
     *
     * @param imageRequest 图像生成请求对象，包含生成请求的详细信息。
     * @return 返回图像生成的结果响应对象。
     */
    ImageResponse genImage(ImageRequest imageRequest);

    /**
     * 对给定图像进行编辑。
     *
     * @param image     要编辑的图像文件。
     * @param prompt    提示文本，用于指导图像编辑。
     * @return 返回图像编辑的结果响应对象。
     */
    ImageResponse editImage(File image, String prompt);

    /**
     * 对给定图像进行编辑。
     *
     * @param image         要编辑的图像文件。
     * @param imageEditRequest 图像编辑请求对象，包含编辑请求的详细信息。
     * @return 返回图像编辑的结果响应对象。
     */
    ImageResponse editImage(File image, ImageEditRequest imageEditRequest);

    /**
     * 对给定图像进行编辑。
     *
     * @param image         要编辑的图像文件。
     * @param mask          标记文件，用于指导图像编辑。
     * @param imageEditRequest 图像编辑请求对象，包含编辑请求的详细信息。
     * @return 返回图像编辑的结果响应对象。
     */
    ImageResponse editImage(File image, File mask, ImageEditRequest imageEditRequest);

    /**
     * 生成嵌入向量。
     *
     * @param input 输入文本，用于生成嵌入向量。
     * @return 返回嵌入向量的结果响应对象。
     */
    EmbeddingResponse embeddings(String input);

    /**
     * 生成嵌入向量。
     *
     * @param inputs 输入文本数组，用于生成嵌入向量。
     * @return 返回嵌入向量的结果响应对象。
     */
    EmbeddingResponse embeddings(String... inputs);

    /**
     * 生成嵌入向量。
     *
     * @param inputs 输入文本列表，用于生成嵌入向量。
     * @return 返回嵌入向量的结果响应对象。
     */
    EmbeddingResponse embeddings(List<String> inputs);

    /**
     * 生成嵌入向量。
     *
     * @param embeddingRequest 嵌入请求对象，包含嵌入请求的详细信息。
     * @return 返回嵌入向量的结果响应对象。
     */
    EmbeddingResponse embeddings(EmbeddingRequest embeddingRequest);

    /**
     * 获取文件信息。
     *
     * @return 返回文件信息的结果响应对象。
     */
    OpenAiResponse<File> files();

    /**
     * 上传文件。
     *
     * @param file 要上传的文件。
     * @return 返回上传文件的结果响应对象。
     */
    UploadFileResponse uploadFile(File file);

    /**
     * 上传文件，并指定文件用途。
     *
     * @param file         要上传的文件。
     * @param purpose      文件用途描述。
     * @return 返回上传文件的结果响应对象。
     */
    UploadFileResponse uploadFile(File file, String purpose);

    /**
     * 删除文件。
     *
     * @param fileId 要删除的文件ID。
     * @return 返回删除文件的结果响应对象。
     */
    DeleteFileResponse deleteFile(String fileId);

    /**
     * 语音转文字。
     *
     * @param file         包含语音数据的文件。
     * @param transcriptionsRequest 转写请求对象，包含转写请求的详细信息。
     * @return 返回语音转文字的结果响应对象。
     */
    WhisperResponse speed2TextTranscriptions(File file, TranscriptionsRequest transcriptionsRequest);

    /**
     * 语音翻译。
     *
     * @param file         包含语音数据的文件。
     * @param translationsRequest 翻译请求对象，包含翻译请求的详细信息。
     * @return 返回语音翻译的结果响应对象。
     */
    WhisperResponse speed2TextTranslations(File file, TranslationsRequest translationsRequest);

    /**
     * 查询账单信息。
     *
     * @return 返回账单信息的结果响应对象。
     */
    Subscription subscription();

    /**
     * 查询消耗信息。
     *
     * @param starDate 开始日期。
     * @param endDate  结束日期。
     * @return 返回消耗信息的结果响应对象。
     */
    BillingUsage billingUsage(@NotNull LocalDate starDate, @NotNull LocalDate endDate);
}
