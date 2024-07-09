package top.kangyaocoding.chatgpt.domain.edits;

import lombok.Data;
import top.kangyaocoding.chatgpt.domain.common.Choice;
import top.kangyaocoding.chatgpt.domain.common.Usage;

import java.io.Serializable;

/**
 * @Author K·Herbert
 * @Description EditResponse 类表示一个编辑响应实体。
 * 它包含了与编辑操作相关的各种信息，如ID、对象、模型、对话选择、创建时间以及耗材信息。
 * 这个类实现了 Serializable 接口，因此实例可以被序列化。
 * @Date 2024-05-23 18:55
 */
@Data
public class EditResponse implements Serializable {

    /**
     * 唯一标识符
     */
    private String id;

    /**
     * 相关对象的描述
     */
    private String object;

    /**
     * 使用的模型描述
     */
    private String model;

    /**
     * 提供给用户的对话选项数组
     */
    private Choice[] choices;

    /**
     * 实例创建的时间戳
     */
    private long created;

    /**
     * 关联的耗材信息
     */
    private Usage usage;

}
