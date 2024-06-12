package top.kangyaocoding.ai.domain.files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @Author K·Herbert
 * @Description
 * @Date 2024-05-23 19:08
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadFileResponse extends File implements Serializable {
}