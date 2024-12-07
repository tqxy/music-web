package com.course.musicweb3.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 歌曲歌单
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListSong implements Serializable {
    private Integer id;
    private Integer songId;
    private Integer songListId;
}
