import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            redirect: '/dashboard'
        },
        {
            path: '/dashboard',
            component: resolve => require(['../components/common/Home.vue'], resolve),
            meta: { title: '自述文件' },
            children:[
                //example===================================================================================
                {
                    path: '/icon',
                    component: resolve => require(['../components/page/example/Icon.vue'], resolve),
                    meta: { title: '自定义图标' }
                },
                {
                    path: '/table',
                    component: resolve => require(['../components/page/example/BaseTable.vue'], resolve),
                    meta: { title: '基础表格' }
                },
                {
                    path: '/tabs',
                    component: resolve => require(['../components/page/example/Tabs.vue'], resolve),
                    meta: { title: 'tab选项卡' }
                },
                {
                    path: '/form',
                    component: resolve => require(['../components/page/example/BaseForm.vue'], resolve),
                    meta: { title: '基本表单' }
                },
                {
                    path: '/editor',
                    component: resolve => require(['../components/page/example/VueEditor.vue'], resolve),
                    meta: { title: '富文本编辑器' }
                },
                {
                    path: '/markdown',
                    component: resolve => require(['../components/page/example/Markdown.vue'], resolve),
                    meta: { title: 'markdown编辑器' }
                },
                {
                    path: '/upload',
                    component: resolve => require(['../components/page/example/Upload.vue'], resolve),
                    meta: { title: '文件上传' }
                },
                {
                    path: '/charts',
                    component: resolve => require(['../components/page/example/BaseCharts.vue'], resolve),
                    meta: { title: 'schart图表' }
                },
                {
                    path: '/drag',
                    component: resolve => require(['../components/page/example/DragList.vue'], resolve),
                    meta: { title: '拖拽列表' }
                },
                {
                    path: '/permission',
                    component: resolve => require(['../components/page/example/Permission.vue'], resolve),
                    meta: { title: '权限测试', permission: true }
                },


                //user 用户管理===================================================================================
                {
                    path: '/roleList',
                    component: resolve => require(['../components/page/user/RoleList.vue'], resolve),
                    meta: { title: '角色信息' }
                },
                {
                    path: '/user',
                    component: resolve => require(['../components/page/user/User.vue'], resolve),
                    meta: { title: '用户信息' }
                },


                //sys 系统管理===================================================================================
                {
                    path: '/dict',
                    component: resolve => require(['../components/page/sys/Dict.vue'], resolve),
                    meta: { title: '数据字典页' }
                },
                {
                    path: '/menuMgr',
                    component: resolve => require(['../components/page/sys/MenuMgr.vue'], resolve),
                    meta: { title: '菜单管理' }
                },

                //系统页面=============================================================================================
                {
                    path: '/dashboard',
                    component: resolve => require(['../components/page/Dashboard.vue'], resolve),
                    meta: { title: '系统首页' }
                },
                {
                    path: '/404',
                    component: resolve => require(['../components/page/404.vue'], resolve),
                    meta: { title: '404' }
                },
                {
                    path: '/403',
                    component: resolve => require(['../components/page/403.vue'], resolve),
                    meta: { title: '403' }
                },


                //test=============================================================================================
                {
                    path: '/test',
                    component: resolve => require(['../components/page/test/Test.vue'], resolve),
                    meta: { title: '测试页' }
                },

                //小程序管理
                {
                    path: '/config/configorgans',
                    component: resolve => require(['../components/page/miniprogram/config/ConfigOrgans.vue'], resolve),
                    meta: { title: '配置小程序整妆' }
                },
                {
                    path: '/config/configmakeup',
                    component: resolve => require(['../components/page/miniprogram/config/ConfigMakeup.vue'], resolve),
                    meta: { title: '配置小程序整妆' }
                },
                {
                    path: '/analysis/organs',
                    component: resolve => require(['../components/page/miniprogram/analysis/Organs.vue'], resolve),
                    meta: { title: '五官编辑' }
                },
                {
                    path: '/analysis/group',
                    component: resolve => require(['../components/page/miniprogram/analysis/Group.vue'], resolve),
                    meta: { title: '排列组合' }
                },
                {
                    path: '/source/source',
                    component: resolve => require(['../components/page/miniprogram/source/Source.vue'], resolve),
                    meta: { title: '整妆库' }
                },
                {
                    name: 'detail',
                    path: '/source/detail',
                    component: resolve => require(['../components/page/miniprogram/source/Detail.vue'], resolve),
                    meta: { title: '整妆详情' }
                },
                {
                    path: '/quote/brand',
                    component: resolve => require(['../components/page/miniprogram/quote/Brand.vue'], resolve),
                    meta: { title: '品牌商' }
                },
                {
                    path: '/quote/kol',
                    component: resolve => require(['../components/page/miniprogram/quote/Kol.vue'], resolve),
                    meta: { title: 'KOL' }
                },
            ]
        },
        {
            path: '/login',
            component: resolve => require(['../components/page/Login.vue'], resolve)
        },
        {
            path: '*',
            redirect: '/404'
        }
    ]
})
