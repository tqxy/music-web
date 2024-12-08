package com.course.musicweb3.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rank implements Serializable {
    private Integer id;
    private Integer songListId;
    private Integer consumerId;
    private Integer score;
}
