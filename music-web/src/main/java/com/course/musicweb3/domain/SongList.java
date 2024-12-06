package com.course.musicweb3.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongList implements Serializable {
    private Integer id;
    private String title;
    private String pic;
    private String introduction;
    private String style;
}
