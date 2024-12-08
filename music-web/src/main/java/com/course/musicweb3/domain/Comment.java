package com.course.musicweb3.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    private Integer id;
    private Integer score;
    private Integer userId;
    private Byte type;
    private String content;
    private Integer songId;
    private Integer songListId;
    private Date createTime;
    private Integer up;
}
