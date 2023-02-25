<template>
 <div style="height: 100%">
   <el-container style="height: 100vh;">
     <el-aside :width="sideWidth+'px'" style="background-color: rgb(238, 241, 246);height: 100%">
      <Aside  :isCollapse="isCollapse"  :logoTextShow="logoTextShow"/>
     </el-aside>

     <el-container>
       <el-header style=" border-bottom: 1px solid #ccc;">
        <Header :collapseBtnClass="collapseBtnClass" :collapse="collapse"/>
       </el-header>

       <el-main>
         <router-view></router-view>
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
      logoTextShow:true
    }
  },
  //练习

  components:{
    Aside,
    Header
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
    }
  }
}
</script>
