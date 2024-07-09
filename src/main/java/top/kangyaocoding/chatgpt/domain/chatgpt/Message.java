package top.kangyaocoding.chatgpt.domain.chatgpt;

import lombok.Data;
import top.kangyaocoding.chatgpt.common.Constants;

/**
 * @Author K·Herbert
 * @Description Message类用于存储消息相关信息
 * @Date 2024-05-22 16:44
 */

@Data
public class Message {

    private String role; // 消息角色
    private String content; // 消息内容
    private String name; // 消息发送者名称

    // 默认构造函数
    public Message() {
    }

    /**
     * 带Builder参数的私有构造函数
     *
     * @param builder Builder对象，用于构建Message实例
     */
    private Message(Builder builder) {
        this.role = builder.role;
        this.content = builder.content;
        this.name = builder.name;
    }

    /**
     * 提供一个Builder实例的静态方法
     *
     * @return 返回一个新的Builder实例
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder类用于构建Message实例，采用建造者模式
     */
    public static final class Builder {

        private String role; // 消息角色
        private String content; // 消息内容
        private String name; // 消息发送者名称

        // 构造函数
        public Builder() {
        }

        /**
         * 设置消息角色
         *
         * @param role 消息角色，类型为Constants.Role枚举
         * @return 返回Builder实例以支持链式调用
         */
        public Builder role(Constants.Role role) {
            this.role = role.getCode();
            return this;
        }

        /**
         * 设置消息内容
         *
         * @param content 消息的内容字符串
         * @return 返回Builder实例以支持链式调用
         */
        public Builder content(String content) {
            this.content = content;
            return this;
        }

        /**
         * 设置消息发送者名称
         *
         * @param name 发送消息的用户名称
         * @return 返回Builder实例以支持链式调用
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * 构建一个Message实例
         *
         * @return 返回构建完成的Message实例
         */
        public Message build() {
            return new Message(this);
        }
    }
}

