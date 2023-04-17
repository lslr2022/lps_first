import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Mane.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name:'Login',
    component: () =>import('../views/Login')
  },
  {
    path:'/front',
    name:'Front',
    component:()=>import('../views/front/Front'),
    children: [
      {
        path: 'home',
        name: 'FrontHome',
        component:()=>import('../views/front/Home')
      }
    ]
  },
  {
    path: '/',
    name: 'Mane',
    component: () =>import('../views/Mane.vue'),
    redirect:"/login",
    children: [
      {
        path: 'home',
        name: 'Home',
        component: () =>import('../views/Home.vue'),
      },
      {
        path: 'user',
        name: 'User1',
        component: () =>import('../views/User1.vue'),
      },
      {
        path:'project',
        name:'Project',
        component:()=>import('../views/Project.vue'),
      },
      {
        path: 'ProjectScore',
        name:'ProjectScore',
        component:()=>import('../views/ProjectScore')
      },
      {
        path: '/password',
        name:'Password',
        component:()=>import('../views/Password')
      },
      {
        path: '/file',
        name:'File',
        component:()=>import('../views/File1')
      },
      {
        path: '/file2',
        name:'File',
        component:()=>import('../views/File2')
      },
      {
        path: '/file3',
        name:'File',
        component:()=>import('../views/RecycleBin')
      },


      {
        path: '/echarts',
        name:'Echarts',
        component:()=>import('../views/Echarts')
      },
      {
        path: '/subProject',
        name:'SubProject',
        component:()=>import('../views/StuProject')
      },
      {
        path: '/TeaProject',
        name:'TeaProject',
        component:()=>import('../views/TeaProject')
      },
      {
        path: '/MyProject',
        name:'MyProject',
        component:()=>import('../views/MyProject')
      },
      {
        path: '/MyPassProject',
        name:'MyPassProject',
        component:()=>import('../views/MyPassProject')
      },
      {
        path: '/Article',
        name:'Article',
        component:()=>import('../views/Article')
      },
      {
        path: '/StuArticle',
        name:'StuArticle',
        component:()=>import('../views/StuArticle')
      },
      {
        path: '/StuArticleDetail',
        name:'StuArticleDetail',
        component:()=>import('../views/StuArticleDetail')

      },
      {
        path:'/PassProject',
        name:'PassProject',
        component:()=>import('../views/PassProject')
      }
    ]

  },




  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})



export default router
