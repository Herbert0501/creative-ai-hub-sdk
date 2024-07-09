package top.kangyaocoding.chatgpt.domain.files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author K·Herbert
 * @Description
 * @Date 2024-05-23 19:07
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class File implements Serializable {

    private String id;
    private Long bytes;
    @JsonProperty("created_at")
    private Long createdAt;
    private String filename;
    private String object;
    private String purpose;
    @Deprecated
    private String status;
    @Deprecated
    @JsonProperty("status_details")
    private String statusDetails;
}