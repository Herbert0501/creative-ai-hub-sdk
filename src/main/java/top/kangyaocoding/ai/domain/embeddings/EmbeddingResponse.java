package top.kangyaocoding.ai.domain.embeddings;

import lombok.Data;
import top.kangyaocoding.ai.domain.common.Usage;

import java.io.Serializable;
import java.util.List;

/**
 * @Author K·Herbert
 * @Description EmbeddingResponse 类用于表示嵌入式响应的数据结构。包含了关于嵌入对象、数据、模型以及使用信息的详细说明。
 * @Date 2024-05-23 18:58
 */

@Data
public class EmbeddingResponse implements Serializable {
    // 嵌入对象的类型
    private String object;
    // 嵌入数据的列表
    private List<Item> data;
    // 使用的模型标识
    private String model;
    // 嵌入使用的相关信息
    private Usage usage;

}
