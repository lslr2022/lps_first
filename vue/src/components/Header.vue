<template>
  <div style="font-size: 12px;line-height: 60px;display: flex">
    <div style="flex: 1;font-size: 18px">
      <span :class="collapseBtnClass" style="cursor:pointer"@click="collapse"> </span>

    </div>
    <el-dropdown style="width: 70px;cursor: pointer">

      <span>流水</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>



      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>
        <router-link to="/" style="text-decoration: none">个人信息</router-link>
        </el-dropdown-item>
        <el-dropdown-item>
          <router-link to="/password" style="text-decoration: none">修改密码</router-link>
        </el-dropdown-item>


        <el-dropdown-item>
            <span style="text-decoration: none"@click="exit">退出</span>
<!--          <router-link to="/login" style="text-decoration: none" >退出</router-link>-->
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>

</template>

<script>
export default {
  name: "Header",
  props:{
    collapseBtnClass:String,
    collapse:Function
  },
  methods: {
    exit(){
      this.$router.push('/login')
    },
    goin(){
      this.$router.push('/')
    }
  }

}
</script>

<style scoped>

</style>