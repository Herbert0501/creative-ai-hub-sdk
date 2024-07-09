package top.kangyaocoding.chatgpt.domain.images;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author K·Herbert
 * @Description 表示一个图像响应数据模型，用于存储图像相关的响应信息
 * @Date 2024-05-23 19:17
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageResponse implements Serializable {
    private Long created; // 响应创建的时间戳
    private List<Item> data; // 响应包含的数据项列表
}
