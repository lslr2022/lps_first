<template>
 <div>
   <div style="margin-bottom: 30px">
     <el-breadcrumb spearator="/">
       <el-breadcrumb-item :to="{path:'/Home'}">个人中心</el-breadcrumb-item>
       <el-breadcrumb-item>用户管理</el-breadcrumb-item>


     </el-breadcrumb>

   </div>

   <div style="margin: 10px  0">
     <el-input style="width: 200px" placeholder="请输入账户名"   suffix-icon="el-icon-search" class="ml-5"
               v-model="username"
     ></el-input>
     <el-input style="width: 200px" placeholder="请输入用户类型"   suffix-icon="el-icon-message" class="ml-5"
               v-model="nickname"></el-input>
     <el-input style="width: 200px" placeholder="请输入地址"   suffix-icon="el-icon-position" class="ml-5"
               v-model="address"></el-input>
     <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
     <el-button type="warning" @click="reset">清空</el-button>

   </div>
   <div style="margin: 10px 0"  >
     <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
     <!--           <el-button type="danger">删除<i class="el-icon-remove-outline"></i></el-button>-->
    <el-upload
        action="http://localhost:9090/user/import"
        :on-success="handleImportSuccess"
        :show-file-list="false"
        style="display: inline-block">
     <el-button type="danger" class="ml-5">导入<i class="el-icon-bottom-left"></i></el-button>
    </el-upload>
     <el-button type="danger" @click="exp" class="ml-5">导出<i class="el-icon-s-promotion"></i></el-button>

   </div>

   <el-table :data="tableData" border stripe>
     <el-table-column prop="id" label="ID" width="80"></el-table-column>
     <el-table-column prop="username" label="用户名" width="140"></el-table-column>
<!--     <el-table-column prop="password" label="密码" width="120"></el-table-column>-->
     <el-table-column prop="name" label="姓名" width="120"></el-table-column>
     <el-table-column prop="nickname" label="用户类型"></el-table-column>
     <el-table-column prop="email" label="邮箱"></el-table-column>
     <el-table-column prop="phone" label="电话"></el-table-column>
     <el-table-column prop="address" label="地址" ></el-table-column>

     <el-table-column>
       <template slot-scope="scope">
         <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>
         <el-popconfirm
             class="ml-5"
             confirm-button-text='好的'
             cancel-button-text='我在想想'
             icon="el-icon-info"
             icon-color="red"
             title="确定删除？"
             @confirm="handleDel(scope.row.id)"
         >
           <el-button type="danger" slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
         </el-popconfirm>
       </template>
     </el-table-column>
   </el-table>
   <div style="padding: 10px 0">
     <el-pagination
         @size-change="handleSizeChange"
         @current-change="handleCurrentChange"
         :current-page="pageNum"
         :page-sizes="[2, 5, 10, 20]"
         :page-size="pageSize"
         layout="total, sizes, prev, pager, next, jumper"
         :total="total">
     </el-pagination>
   </div>

   <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="25%">
     <el-form label-width="75px"  size="small">
       <el-form-item label="用户名" >
         <el-input v-model="form.username" autocomplete="off"></el-input>
       </el-form-item>
       <el-form-item label="姓名" >
         <el-input v-model="form.name" autocomplete="off"></el-input>
       </el-form-item>

       <el-form-item label="用户类型" >
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
     <div slot="footer" class="dialog-footer">
       <el-button @click="dialogFormVisible = false">取 消</el-button>
       <el-button type="primary" @click="save">确 定</el-button>
     </div>
   </el-dialog>
 </div>
</template>

<script>
export default {
  name: "User1",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:5,
      username:"",
      nickname:"",
      address:"",
      dialogFormVisible:false,
      form:{},
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/user/page1",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          username:this.username,
          nickname:this.nickname,
          address:this.address
        }
      }).then(res =>{
        console.log(res)

        this.tableData=res.records
        this.total=res.total
      })
      /*fetch( "http://localhost:9090/user/page1?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username+"&nickname="+this.nickname)
          .then(res => res.json()).then(res =>{
        console.log(res)
        this.tableData=res.data
        this.total=res.total
      })*/

    },
    /*新增用户的方法 */
    save(){
      this.request.post("/user",this.form).then(res =>{
        if(res){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.success("保存失败")
        }
      })
    },
    /*编辑用户的方法 */
    handleEdit(row){
      this.form=row
      this.dialogFormVisible=true
    },
    /*删除用户的方法 */
    handleDel(id){
      this.request.delete("/user/"+id).then(res =>{
        if(res){
          this.$message.success("删除成功")
          this.load()
        }else{
          this.$message.success("删除失败")
        }
      })
    },

    reset(){
      this.username=""
      this.nickname=""
      this.address=""
      this.load()

    },
    handleAdd(){
      this.dialogFormVisible=true
      this.form={}
    },

    handleSizeChange(pageSize){
      console.log(pageSize)
      this.pageSize=pageSize
      this.load()

    },
    handleCurrentChange(pageNum){
      console.log(pageNum)
      this.pageNum=pageNum
      this.load()
    },
    exp(){
      window.open("http://localhost:9090/user/export")
    },
    handleImportSuccess(){
      this.$message.success("文件导入成功")
      this.load()
    }

  }


}
</script>

<style scoped>

</style>