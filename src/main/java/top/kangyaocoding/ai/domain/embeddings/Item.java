package top.kangyaocoding.ai.domain.embeddings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author K·Herbert
 * @Description Item类实现了Serializable接口，用于定义可以被序列化的项目实体。
 * @Date 2024-05-23 18:59
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item implements Serializable {
    private String object;
    private List<BigDecimal> embedding;
    private Integer index;
}
