package top.kangyaocoding.chatgpt.domain.whisper;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author K·Herbert
 * @Description 回复的信息
 * @Date 2024-05-23 19:22
 */

@Data
public class WhisperResponse implements Serializable {
    private String text;
}