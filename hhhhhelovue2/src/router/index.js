import Vue from 'vue'
import VueRouter from 'vue-router'
// import store from '@/store'

Vue.use(VueRouter)



const routes = [ 
  {
    path: '/footer',
    name: 'footer',
    component: () => import('@/components/layout/Footer.vue'),
    
  }, 
  {
    path: '/linkview',
    name: 'link',
    component: () => import('@/views/front/Link.vue'),
    
  }, {
    path: '/backyard',
    name: 'backyard',
    component: () => import('@/views/front/BackYard.vue'),
    
  }, 
  {
    path: '/photo',
    name: 'photo',
    component: () => import('@/views/front/Photo.vue'),
    
  }, 
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/LoginView.vue'),
    
  }, 
  {
    path: '/chat',
    name: 'chat',
    component: () => import('@//views/front/Chat.vue'),
    
  },
  {
    path: '/tags',
    name: 'tags',
    component: () => import('@//views/front/Tags.vue'),
    
  }, 
  {
    path: '/archive',
    name: 'archive',
    component: () => import('@//views/front/Archive.vue'),
    
  },
  {
    path: '/catagories',
    name: 'catagories',
    component: () => import('@//views/front/Catagories.vue'),
    
  }, 
  {
    path: '/topNavBar',
    name: 'TopNavBar',
    component: () => import('@/components/layout/TopNavBar.vue'),
    
  },
  {
    path: '/music',
    name: 'Music',
    component: () => import('@/components/layout/Music.vue'),
    
  },
  {
    path: '/detail',
    name: 'Detail',
    component: () => import('@/views/front/ArticleDetail.vue'),
  },
  {
    path: '/source',
    name: 'Source',
    component: () => import('@/views/front/Source.vue'),
  },
  {
    path: '',
    name: 'home',
    component: () => import('@/views/front/Home.vue'),
  },


  {
    path: '*',
    name: '404',
    component: () => import('@/views/ErrorView.vue'),
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 提供一个重置路由的方法
export const resetRouter = () => {
  router.matcher = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
}



export const setRoutes = () => {
  const storeMenus = sessionStorage.getItem("menus");
  if (storeMenus) {
    // const currentRouteNames = router.getRoutes().map(v => v.name)
    // if (!currentRouteNames.includes('main')) {


    // 拼装动态路由
    const mainRoute = {
      path: '/main',
      name: 'main',
      component: () => import('@/views/MainView.vue'), children: []
    }
    const menus = JSON.parse(storeMenus)
    menus.forEach(item => {
      if (item.path) {  //当且仅仅当path不为空时才去设置路由
        let itemMeun = { path: item.path, name: item.name, component: () => import('@/views/' + item.pagePath + '.vue') }
        mainRoute.children.push(itemMeun)
      } else if (item.children.length) {
        item.children.forEach(item => {
          if (item.path) {
            let itemMeun = { path: item.path, name: item.name, component: () => import('@/views/' + item.pagePath + '.vue') }
            mainRoute.children.push(itemMeun)
          }
        })
      }
    })
      // //  动态添加到现在路由对象
      router.addRoute(mainRoute)
  }
  }
// }

// 将重置掉的路由再设置回去
setRoutes()

export default router