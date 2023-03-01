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
