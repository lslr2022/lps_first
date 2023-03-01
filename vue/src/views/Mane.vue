<template>
 <div style="height: 100%">
   <el-container style="height: 100vh;">
     <el-aside :width="sideWidth+'px'" style="background-color: rgb(238, 241, 246);height: 100%">
      <Aside  :isCollapse="isCollapse"  :logoTextShow="logoTextShow"/>
     </el-aside>

     <el-container>
       <el-header style=" border-bottom: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse" :user="user"/>
       </el-header>

       <el-main>
         <router-view @refreshUser="getUser"></router-view>
       </el-main>


     </el-container>
   </el-container>
 </div>

</template>

<script>
import Aside from "@/components/Aside";
import Header from "@/components/Header";

/*import request from "@/utils/request.";*/

export default {
  name: 'HomeView',

  data(){
    return{

      msg:"开始学习",
      collapseBtnClass:'el-icon-s-fold',
      isCollapse:false,
      sideWidth:200,
      logoTextShow:true,
      user:{}
    }
  },
  //练习
  components:{
    Aside,
    Header
  },
  created() {
    //从后台获取最新数据
    this.getUser()
  },
  methods:{
    collapse(){ //点击收缩按钮触发
     this.isCollapse=!this.isCollapse
      if(this.isCollapse){
        this.sideWidth=100
        this.collapseBtnClass='el-icon-s-unfold'
        this.logoTextShow=false
      }else{
        this.sideWidth=200
        this.collapseBtnClass='el-icon-s-fold'
        this.logoTextShow=true
      }
    },
    //获取用户最新数据
    getUser(){
      let username=localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")).username:""
      if(username){
        //从后台获取User数据
        this.request.get("user/username/"+username).then(res=>{
          //重新赋值后台的user数据
          this.user=res.data;
        })
      }
    }

  }
}
</script>
