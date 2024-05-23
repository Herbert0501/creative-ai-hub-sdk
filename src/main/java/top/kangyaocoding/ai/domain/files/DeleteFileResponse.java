package top.kangyaocoding.ai.domain.files;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author K·Herbert
 * @Description
 * @Date 2024-05-23 19:06
 */

@Data
public class DeleteFileResponse implements Serializable {

    /** 文件ID */
    private String id;
    /** 对象；file */
    private String object;
    /** 删除；true */
    private boolean deleted;

}