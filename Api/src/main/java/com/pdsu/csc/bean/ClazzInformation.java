package com.pdsu.csc.bean;

import lombok.*;

import java.io.Serializable;

/**
 * @author 半梦
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class ClazzInformation implements Serializable {
    private Integer id;

    private String clazzname;

    public ClazzInformation(String clazzname) {
        this(null, clazzname);
    }
}