<template>
  <div style="font-size: 12px;line-height: 60px;display: flex">
    <div style="flex: 1;font-size: 25px">
      <span :class="collapseBtnClass" style="cursor:pointer"@click="collapse"  > </span>

    </div>
    <el-dropdown style="width: 150px;cursor: pointer ;text-align: right">
      <div style="display: inline-block" >
      <img :src="user.avatar" alt=""
           style="height: 50px;width:50px;border-radius: 100%;position:relative;top:20px;right: 5px">
      <span>{{user.username}}</span><i class="el-icon-arrow-down" style="margin-top: 20px"></i>
      </div>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item>
        <router-link to="/home" style="text-decoration: none">个人信息</router-link>
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
    collapse:Function,
    user:Object


  },
  data(){
    return{


    }
  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName();
    }
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

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

</style>