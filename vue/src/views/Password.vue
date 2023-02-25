<template>
  <el-card style="width: 500px;">
    <el-form label-width="120px" size="small":model="form" :rules="rules" ref="pass">
      <el-form-item label="原密码" porp="password">
        <el-input v-model="form.password" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item label="新密码" porp="newPassword">
        <el-input v-model="form.newPassword" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item label="确认新密码" porp="confirmPassword">
        <el-input v-model="form.confirmPassword" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="save">确定</el-button>
        <el-button type="primary" >
          <router-link to="/home" style="text-decoration: none">返回</router-link>
        </el-button>

      </el-form-item>





    </el-form>

  </el-card>
</template>

<script>
export default {
  name: "Password",
  data(){
    return{
      form:{},
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      rules:{
        password:[
          {required: true,message:"请输入原密码",trigger:'blur'},
          {min:3,message: '长度不少于3位',trigger: 'blur'}
        ],
        newPassword:[
          {required: true,message:"请输入新密码",trigger:'blur'},
          {min:3,message: '长度不少于3位',trigger: 'blur'}
        ],
        confirmPassword:[
          {required: true,message:"请输入密码",trigger:'blur'},
          {min:3,message: '长度不少于3位',trigger: 'blur'}
        ],

      }

    }

  },
  created() {
    this.form.username=this.user.username
  },
  methods:{
    save(){
      this.$refs.pass.validate((valid)=>{
        if(valid){
          if(this.form.newPassword!==this.form.confirmPassword){
            this.$message.error("两次输入密码不一致")
            return false
          }
          this.request.post("/user/password",this.form).then(res=>{
            if(res.code==='200'){
              this.$message.success("修改成功")

            }else{
              this.$message.error(res.msg)
            }
          })
        }
      })
    }

  }


}
</script>

<style scoped>

</style>