/* eslint-disable*/
import { get, post, deletes, postJson } from './http'

// =======================> 管理员 API
// 是否登录成功
export const getLoginStatus =(params)=>post('/admin/login/status',params);

//添加歌手
export const setSinger =(params)=>post('/singer/add',params);
//编辑歌手
export const updateSinger =(params)=>postJson('/singer/update',params);

//查询所有歌手
export const getAllSinger =()=>get('/singer/allSinger');
//删除歌手
export const deleteSinger =(id)=>get(`/singer/delete?id=${id}`);

//查询歌曲通过歌手ID
export const songOfSingerId =(id)=>get(`/song/singer/detail?singerId=${id}`);
//编辑歌曲
export const updateSong =(params)=>postJson('/song/update',params);
//删除歌曲
export const deleteSong =(id)=>get(`/song/delete?id=${id}`);
//根据歌曲ID查询歌曲对象
export const songOfId =(id)=>get(`/song/detail?songId=${id}`);
//跟据歌曲名获取歌曲对象
export const songOfSongName =(songName)=>get(`/song/songOfSongName?songName=${songName}`);
//更新歌曲图片
export const updateSongPic =(params)=>post('/song/updateSongPic',params);

//添加歌手
export const setSongList =(params)=>post('/songList/add',params);
//编辑歌手
export const updateSongList =(params)=>postJson('/songList/update',params);

//查询所有歌手
export const getAllSongList =()=>get('/songList/allSongList');
//删除歌手
export const deleteSongList =(id)=>get(`/songList/delete?id=${id}`);
//根据歌单ID获取歌曲ID列表
export const getSongIdOfSongList =(songListId)=>get(`/listSong/songList/detail?songListId=${songListId}`);
//给歌单增加歌曲
export const listSongAdd =(params)=>post('/listSong/add',params);
//删除歌曲
export const deleteListSong =(id)=>get(`/listSong/delete?id=${id}`);