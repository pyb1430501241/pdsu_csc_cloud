package com.pdsu.csc.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author 半梦
 * @create 2020-12-06 18:46
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum EsIndex {

    USER("user"), BLOB("blob"), FILE("file");

    String name;

}
