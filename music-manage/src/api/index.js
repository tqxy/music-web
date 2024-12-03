/* eslint-disable*/
import { get, post, deletes } from './http'

// =======================> 管理员 API
// 是否登录成功
export const getLoginStatus =(params)=>post('/admin/login/status',params);

