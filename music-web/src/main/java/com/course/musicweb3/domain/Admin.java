package com.course.musicweb3.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 管理员
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {
    private Integer id;
    private String name;
    private String password;

}
