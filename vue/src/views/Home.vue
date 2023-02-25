<template>
<div>
  <el-card style="width: 420px">
    <el-form label-width="75px"  size="small">
      <el-form-item label="用户名" >
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="昵称" >
        <el-input v-model="form.nickname" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" >
        <el-input v-model="form.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话" >
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="地址" >
        <el-input v-model="form.address" autocomplete="off"></el-input>
      </el-form-item>

    </el-form>
    <div style="text-align: center">

      <el-button type="primary" @click="save" >确 定</el-button>


    </div>

  </el-card>

</div>
</template>

<script>
export default {
  name: "Home",
  data(){
    return{
      form:{},
      user:localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")):{}
    }
  },
  created() {
    const username=this.user.username
    if(!username){
      this.$message.error("错误")
      return
    }
    this.request.get("/user/username/"+username).then(res=>{
      console.log(res)
      this.form=res.data
    })


  },
  methods:{
    save(){
      this.request.post("/user",this.form).then(res =>{
        if(res){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
        }else{
          this.$message.success("保存失败")
        }
      })

    }
  }

}
</script>

<style scoped>

</style>