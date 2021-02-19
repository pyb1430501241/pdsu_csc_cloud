package com.pdsu.csc.bean;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author 半梦
 * @create 2021-02-19 17:18
 */
@Component
@ConfigurationProperties(prefix = "encrypt")
@PropertySource("classpath:properties/encrypt.properties")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EncryptConfig {

    /**
     * 加密格式
     */
    @NonNull
    private String mode;

    /**
     *  加密次数
     */
    @NonNull
    private Integer number;

}
