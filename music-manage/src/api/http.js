/* eslint-disable */
import axios from 'axios';
import { path } from 'chromedriver';
import { error } from 'shelljs';
axios.defaults.timeout = 5000;
axios.defaults.withCredentials=true;  //允许跨域
axios.defaults.headers.post['Content-Type']='application/x-www-form-urlencoded;charset=UTF-8';
axios.defaults.baseURL = 'http://localhost:8888';


axios.interceptors.response.use(
    response => {
        //如果response是200
        if(response.status===200){
            return Promise.resolve(response);
        }else{
            return Promise.reject(response);
        }
    },
    error => {
        if(error.response.status){
            switch(error.response.status){
                case 401:
                    router.replace({
                        path:'/',
                        query:{
                          redirect:router.currentRoute.fullPath  
                        }
                    });
                    break;
                case 404:
                    break;     
            }
            return Promise.reject(error.response);
        }
    }
);
/**
 * 封装一个get方法
 */
export function get(url,params={}){
    return new Promise((resolve,reject) => {
        axios.get(url,{
            params:params
        })
        .then(response => {
            resolve(response.data);
        })
        .catch(error => {
            reject(error);
        })
    });
}
/**
 * 封装一个post方法
 */
export function post(url,data={}){
    return new Promise((resolve,reject) => {
        axios.post(url,data)
        .then(response => {
            resolve(response.data);
        },error => {
            reject(error);
        })
    })
}
/**
 * JSON格式的post
 */
export function postJson(url, data = {}) {
    return new Promise((resolve, reject) => {
        // 设置请求头，指定Content-Type为application/json
        axios.post(url, data, {
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            resolve(response.data);
        })
        .catch(error => {
            reject(error);
        });
    });
}