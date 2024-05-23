package top.kangyaocoding.ai.domain.edits;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @Author K·Herbert
 * @Description EditRequest类用于定义编辑请求的参数。
 * 它包含模型信息、输入内容、修改描述、温度设置、多样性控制、生成完成次数等字段。
 * 支持通过Builder模式进行构建。
 * @Date 2024-05-23 18:54
 */

@Slf4j
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditRequest implements Serializable {

    /**
     * 模型字段，指定使用的编辑模型。默认为 CODE_DAVINCI_EDIT_001。
     * Model枚举定义了可用的编辑模型选项。
     */
    @NonNull
    private String model = Model.CODE_DAVINCI_EDIT_001.getCode();

    /**
     * 输入字段，提供需要编辑的文本内容。
     */
    @NonNull
    private String input;

    /**
     * 修改描述字段，说明这次编辑任务的具体要求。
     */
    @NonNull
    private String instruction;

    /**
     * 温度字段控制输出的随机性。取值范围在0到2之间。
     * 较高的温度值会使输出更加随机，而较低的温度值则使输出更加集中和确定。
     */
    @Builder.Default
    private double temperature = 0.2;

    /**
     * top_p字段用于多样性控制。它通过控制考虑的概率质量的令牌来影响输出。
     * 例如，0.1意味着只考虑包含前10%概率质量的代币。
     */
    @JsonProperty("top_p")
    private Double topP = 1d;

    /**
     * n字段指定为每个提示生成的完成次数。默认值为1。
     */
    private Integer n = 1;

    /**
     * Model枚举定义了可用的编辑模型。
     * 每个枚举常量都关联了一个代码字符串。
     */
    @Getter
    @AllArgsConstructor
    public enum Model {
        /**
         * TEXT_DAVINCI_EDIT_001表示文本编辑模型。
         */
        TEXT_DAVINCI_EDIT_001("text-davinci-edit-001"),
        /**
         * CODE_DAVINCI_EDIT_001表示代码编辑模型。
         */
        CODE_DAVINCI_EDIT_001("code-davinci-edit-001"),
        ;
        /**
         * 模型的代码字符串。
         */
        private final String code;
    }
}
