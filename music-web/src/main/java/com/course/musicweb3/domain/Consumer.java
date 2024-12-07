package com.course.musicweb3.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 前端用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consumer implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private int sex;
    private String phoneNum;
    private String email;
    private Date birth;
    private String introduction;
    private String location;
    private String avator;
    private Date createTime;
    private Date updateTime;
}
