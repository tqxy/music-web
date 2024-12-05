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