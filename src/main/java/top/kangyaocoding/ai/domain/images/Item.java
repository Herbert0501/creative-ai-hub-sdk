package top.kangyaocoding.ai.domain.images;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author K·Herbert
 * @Description
 * @Date 2024-05-23 19:15
 */

@Data
public class Item implements Serializable {

    private String url;
    @JsonProperty("b64_json")
    private String b64Json;

}
