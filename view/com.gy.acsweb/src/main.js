import Vue from 'vue';
import App from './App';
import router from './router';
import ElementUI from 'element-ui';
import NProgress from 'nprogress';
import util from './components/common/util';
import api from './components/common/api';

import "nprogress/nprogress.css";
import 'element-ui/lib/theme-chalk/index.css';    // 默认主题
// import '../static/css/theme-green/index.css';       // 浅绿色主题
import '../static/css/icon.css';
import "babel-polyfill";
import '@fortawesome/fontawesome-free/css/all.min.css';

//设置全局变量域名接口
global.apiUrl = process.env.API_ROOT;
global.ossDomain = `https://${process.env.bucket}.${process.env.endpoint}`

Vue.use(ElementUI, { size: 'small' });
Vue.prototype.$util = util;
Vue.prototype.$api = api;

//使用钩子函数对路由进行权限跳转
router.beforeEach((to, from, next) => {
    NProgress.start();
    const role = localStorage.getItem('ms_username');
    if(!role && to.path !== '/login'){
        next('/login');
    }else if(to.meta.permission){
        // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
        role === 'admin' ? next() : next('/403');
    }else{
        // 简单的判断IE10及以下不进入富文本编辑器，该组件不兼容
        if(navigator.userAgent.indexOf('MSIE') > -1 && to.path === '/editor'){
            Vue.prototype.$alert('vue-quill-editor组件不兼容IE10及以下浏览器，请使用更高版本的浏览器查看', '浏览器不兼容通知', {
                confirmButtonText: '确定'
            });
        }else{
            next();
        }
    }
});

router.afterEach(() => {
    NProgress.done()
});

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
