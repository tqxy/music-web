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

//查询歌曲
export const songOfSingerId =(id)=>get(`/song/singer/detail?singerId=${id}`);
//编辑歌曲
export const updateSong =(params)=>postJson('/song/update',params);
//删除歌曲
export const deleteSong =(id)=>get(`/song/delete?id=${id}`);
//更新歌曲图片
export const updateSongPic =(params)=>post('/song/updateSongPic',params);