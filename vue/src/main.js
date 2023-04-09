import Vue from 'vue'
import App from './App.vue'
import router from './router'
//学习内容
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/global.css'
import request from "@/utils/request.";
//全局注册
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
//use
Vue.use(mavonEditor)

Vue.config.productionTip = false
//学习内容
Vue.use(ElementUI,{size:"mini"});

Vue.prototype.request=request

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
