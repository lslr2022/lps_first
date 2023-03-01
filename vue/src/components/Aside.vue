<template>
  <el-menu :default-openeds="['1', '3']" style="min-height: 100%;overflow-x:hidden"
           background-color="rgb(48,65,86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :cpllpase="isCollapse"
           router
  >
    <div style="height: 60px;line-height: 60px;text-align: center">
      <img src="../assets/logo.png" alt="" style="width: 20px;position: relative;top:5px;margin-right: 5px">
      <b style="color: white" v-show="logoTextShow">竞赛管理系统</b>

    </div>

     <el-menu-item index="/home" >
       <template slot="title">
         <i class="el-icon-menu"></i>
         <span slot="title">个人中心</span>
       </template>
       </el-menu-item>

    <el-submenu index="2" :disabled="isDisabled">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">系统管理</span>
      </template>

        <el-menu-item index="/user">
          <template slot="title">
            <i class="el-icon-s-custom"></i>
            <span slot="title">用户管理</span>
          </template>
        </el-menu-item>
      <el-menu-item index="/TeaProject">
        <template slot="title">
          <i class="el-icon-s-order"></i>
          <span slot="title">竞赛发布</span>
        </template>
      </el-menu-item>

      <el-menu-item index="/project">
        <template slot="title">
          <i class="el-icon-s-order"></i>
          <span slot="title">竞赛管理</span>
        </template>
      </el-menu-item>

      <el-menu-item index="/file">
        <template slot="title">
          <i class="el-icon-s-order"></i>
          <span slot="title">文件管理</span>
        </template>
      </el-menu-item>
      <el-menu-item index="/echarts">
        <template slot="title">
          <i class="el-icon-s-order"></i>
          <span slot="title">信息统计</span>
        </template>
      </el-menu-item>

    </el-submenu>
    <el-submenu index="3">
      <template slot="title">
        <i class="el-icon-setting"></i>
        <span solt="title">学生中心</span>
      </template>
      <el-menu-item index="/subProject">
        <template slot="title">竞赛申请</template>
      </el-menu-item>
      <el-menu-item index="/MyProject">
        <template slot="title">我的项目管理</template>
      </el-menu-item>
      <el-menu-item index="3-4">
        <template slot="title">信息统计</template>
      </el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props:{
    isCollapse:Boolean,
    logoTextShow:Boolean,
  },
  computed:{
    isDisabled(){
      let nickname=localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")).nickname:""
      if(nickname==="学生"){
        return true
      }else if(nickname==="管理员"){
        return false
      }else{
        return true
      }
    }
  }



}
</script>

<style scoped>

</style>