import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
 * hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
 *                                if not set alwaysShow, only more than one route under the children
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noredirect           if `redirect:noredirect` will no redirct in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
 **/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/register', component: () => import('@/views/basic/register/register'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },
  {
    path: '',
    component: Layout,
    redirect: '/home',
    children: [{
      path: 'home',
      name: 'home',
      component: () => import('@/views/home/index'),
      meta: { title: '首页', icon: 'home' }
    }]
  },
  {
    path: '',
    component: Layout,
    redirect: '/user',
    name: 'basic',
    meta: { title: '基础信息', icon: 'product' },
    children: [
      {
        path: 'user',
        name: 'user',
        component: () => import('@/views/basic/user/user-detail'),
        meta: { title: '个人资料', icon: 'product-add' }
      }, {
        path: 'users',
        name: 'users',
        component: () => import('@/views/basic/user/index'),
        meta: { title: '用户管理', icon: 'user' }, hidden: true
      },
      {
        path: '#',
        name: '#',
        // component: () => import(''),
        meta: { title: '', icon: '' }, 
        hidden: false
      }
    ]
  },
  {
    path: '',
    component: Layout,
    redirect: '/house',
    name: 'biz',
    meta: { title: '房屋信息', icon: 'sms' },
    children: [
      {
        path: 'house',
        name: 'house',
        component: () => import('@/views/biz/house/index'),
        meta: { title: '房屋动态', icon: 'sms-flash' }
      },
      {
        path: 'house-detail',
        name: 'house-detail',
        component: () => import('@/views/biz/house/house-detail'),
        meta: { title: '发布房屋', icon: 'sms-new' }
      },
      {
        path: 'house-detail/:id',
        name: 'house-detail1',
        component: () => import('@/views/biz/house/house-detail'),
        meta: { title: '发布房屋', icon: 'sms-new' },
        hidden: true
      },
      {
        path: 'message',
        name: 'message',
        component: () => import('@/views/biz/message/index'),
        meta: { title: '留言管理', icon: 'marker' },
        hidden: false
      },
      {
        path: 'rent',
        name: 'rent',
        component: () => import('@/views/biz/rent/index'),
        meta: { title: '租金日志', icon: 'sms-coupon' },
        hidden: false
      }
    ]
  },
  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})

