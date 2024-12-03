package com.course.musicweb3.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 歌手
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Singer implements Serializable {
    private Integer id;
    private String name;
    private String pic;
    private Byte sex;
    private Date birth;
    private String location;
    private String introduction;
}
