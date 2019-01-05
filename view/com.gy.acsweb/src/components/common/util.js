import Vue from 'vue';
import {Message} from 'element-ui';
import CryptoJS from 'crypto-js';
import axios from 'axios';
import qs from 'qs';
import OSS from 'ali-oss';

//axios.defaults.baseURL = process.env.API_ROOT;
axios.defaults.headers.Authorization = localStorage.getItem('Authorization')
axios.defaults.headers.AppId = localStorage.getItem('AppId')

axios.interceptors.request.use(config=> {
    return config;
}, err=> {
    Message.error('请求超时!');
    return Promise.resolve(err);
})

axios.interceptors.response.use(data=> {
    if (data.status && data.status == 200 && data.data.status == 'error') {
        Message.error(data.data.msg);
        return;
    }
    return data;
}, err=> {
    if (err.response.status == 504||err.response.status == 404) {
        Message.error('服务器被吃了⊙﹏⊙∥');
    } else if (err.response.status == 403) {
        Message.error('权限不足,请联系管理员!');
    }else {
        Message.error('未知错误!');
    }
    return Promise.resolve(err);
})


const util = {
    //通过数据字典dictCode取Items
    getDictItems(dictCode) {
        const data = axios.get('./static/dict.json').then((res) => {
            const dictArr = res.data, items = [];
            for (var i = 0; i < dictArr.length; i++) {
                if (dictCode == dictArr[i].dictCode) {
                    items.push(dictArr[i].dictItems);
                    break;
                }
            }
            return items[0];
        });
        return data;
    },

    //获取数据字典的值的翻译
    getDictItemName(dictItems, val) {
        for (var i = 0; i < dictItems.length; i++) {
            if (val == dictItems[i].itemValue) {
                return dictItems[i].itemName;
            }
        }
    },

    //加密
    encrypt(word, keyStr) {
        if (keyStr.length > 15) {
            var key = CryptoJS.enc.Utf8.parse(keyStr),
                srcs = CryptoJS.enc.Utf8.parse(word),
                iv = CryptoJS.enc.Utf8.parse('M2UsytYCU4FD70y5');

            var encrypted = CryptoJS.AES.encrypt(srcs,
                key,
                {mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7, iv: iv});
            return encrypted.toString();
        } else {
            return "密钥长度必须大于15位";
        }

    },

    //解密
    decrypt(word, keyStr) {
        var key = CryptoJS.enc.Utf8.parse(keyStr),
            iv = CryptoJS.enc.Utf8.parse('M2UsytYCU4FD70y5');
        var decrypt = CryptoJS.AES.decrypt(word,
            key,
            {mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7, iv: iv});
        return CryptoJS.enc.Utf8.stringify(decrypt).toString();
    },


    get(url) {
        return axios({
            method: 'get',
            url: `${apiUrl}${url}`
        });
    },

    post(url, params) {
        return axios({
            method: 'post',
            url: `${apiUrl}${url}`,
            data: params
        });
    },

    postForm(url, params) {
        return axios({
            method: 'post',
            url: `${apiUrl}${url}`,
            data: qs.stringify(params),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });
    },

    put(url, params) {
        return axios({
            method: 'put',
            url: `${apiUrl}${url}`,
            data: params
        });
    },


    delete(url) {
        return axios({
            method: 'delete',
            url: `${apiUrl}${url}`,
        });
    },

    upload(url, params) {
        return axios({
            method: 'post',
            url: `${apiUrl}${url}`,
            data: qs.stringify(params),
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    },


    //创建oss客户端
    ossClient() {
        return new OSS({
            accessKeyId: `${process.env.accessKeyId}`,
            accessKeySecret: `${process.env.accessKeySecret}`,
            bucket: `${process.env.bucket}`,
            region: `${process.env.region}`,
            endpoint: `${process.env.endpoint}`
        });
    },

    getUploadOssUrl(){
        const date = new Date(),
            year = date.getFullYear(),
            month = date.getMonth() + 1,
            day = date.getDate();

        return "acs/"+year+"/"+month+"/"+day+"/";
    },

    uuid(){
        var s = [], hexDigits = "0123456789abcdef", i;
        for (i = 0; i < 36; i++) {
            s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = "4";
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);
        s[8] = s[13] = s[18] = s[23] = "";
        return s.join("");
    }

};

export default util
