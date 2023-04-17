<template>
<div>
  <div style="display: flex;height: 60px;line-height: 60px;border-bottom: 1px solid #ccc">
    <div style="width: 280px;display: flex;padding-left: 30px">
      <div style="width: 60px">
        <img src="../../assets/logo.png" alt="" style="width: 50px;position: relative;top:5px;right: 0">
      </div>

      <span style="flex:1">欢迎来到竞赛管理系统</span>
    </div>
    <div style="flex: 1">
      <!--导航菜单-->
      <el-menu :default-active="'1'" class="el-menu-demo" mode="horizontal">
        <el-menu-item index="1">首页</el-menu-item>
        <el-submenu index="2">
          <template slot="title">我的工作台</template>
          <el-menu-item index="2-1">选项1</el-menu-item>
          <el-menu-item index="2-2">选项2</el-menu-item>
          <el-menu-item index="2-3">选项3</el-menu-item>
          <el-submenu index="2-4">
            <template slot="title">选项4</template>
            <el-menu-item index="2-4-1">选项1</el-menu-item>
            <el-menu-item index="2-4-2">选项2</el-menu-item>
            <el-menu-item index="2-4-3">选项3</el-menu-item>
          </el-submenu>
        </el-submenu>
        <el-menu-item index="3" >宣传视频</el-menu-item>
        <el-menu-item index="4"><a href="/echarts" target="_blank">后台管理</a></el-menu-item>
      </el-menu>
    </div>
    <div style="width: 200px">
      <div v-if="!user.username" style="text-align: right;padding-right: 30px">
        <el-button @click="$router.push('/FrontLogin')">登录</el-button>
      </div>
      <div v-else>
        <el-dropdown style="width: 150px;cursor: pointer ;text-align: right">
          <div style="display: inline-block" >
            <img :src="user.avatar" alt=""
                 style="height: 50px;width:50px;border-radius: 100%;position:relative;top:20px;right: 5px">
            <span>{{user.username}}</span><i class="el-icon-arrow-down" style="margin-top: 20px"></i>
          </div>
          <el-dropdown-menu slot="dropdown">
<!--            <el-dropdown-item>
              <router-link to="/home" style="text-decoration: none">个人信息</router-link>
            </el-dropdown-item>
            <el-dropdown-item>
              <router-link to="/password" style="text-decoration: none">修改密码</router-link>
            </el-dropdown-item>


            <el-dropdown-item>
              <span style="text-decoration: none"@click="exit">退出</span>
              &lt;!&ndash;          <router-link to="/login" style="text-decoration: none" >退出</router-link>&ndash;&gt;
            </el-dropdown-item>-->
          </el-dropdown-menu>
        </el-dropdown>
      </div>

    </div>
  </div>
  <div style="width: 1080px;margin: 0 auto">
    <router-view/>
  </div>


</div>
</template>

<script>
export default {
  name: "Front",
  data(){
    return{
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
    }
  },
  created() {
  },
  methods:{
    exit(){
      this.$router.push('/login')
    },
  }
}
</script>

<style scoped>

</style>