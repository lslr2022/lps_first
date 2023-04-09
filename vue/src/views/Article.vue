<template>
  <div>
    <div style="margin-bottom: 30px">
      <el-breadcrumb spearator="/">
        <el-breadcrumb-item :to="{path:'/Home'}">主页</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      </el-breadcrumb>

    </div>

    <div style="margin: 10px  0">
      <el-input style="width: 200px" placeholder="请输入文章标题"   suffix-icon="el-icon-search" class="ml-5"
                v-model="name"
      ></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">清空</el-button>

    </div>
    <div style="margin: 10px 0"  >
      <el-button type="primary" @click="handleAdd">发布<i class="el-icon-circle-plus-outline"></i></el-button>
      <!--           <el-button type="danger">删除<i class="el-icon-remove-outline"></i></el-button>-->
    </div>

    <el-table :data="tableData" border stripe>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="文章标题" width="140"></el-table-column>
      <!--     <el-table-column prop="password" label="密码" width="120"></el-table-column>-->


<!--      <el-table-column prop="content" label="文章内容" width="120"></el-table-column>-->


      <el-table-column prop="user" label="发布人"></el-table-column>
      <el-table-column prop="time" label="发布时间"></el-table-column>
      <el-table-column>
        <template slot-scope="scope">
          <el-button @click="view(scope.row.content)" type="primary" class="ml-5" >查看</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="280" align="center">

        <template slot-scope="scope">
<!--          <el-button @click="view(scope.row.content)" type="primary" class="ml-5" >查看</el-button>-->
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

    <el-dialog title="文章信息" :visible.sync="dialogFormVisible" width="60%">
      <el-form label-width="75px"  size="small">
        <el-form-item label="文章标题" >
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="文章内容" >
          <mavon-editor ref="md"  v-model="form.content" :ishljs="true" @imgAdd="imgAdd"/>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="文章信息" :visible.sync="viewDialogVis" width="60%">
        <el-card>
          <div>
            <mavon-editor
              class="md"
              :value="content"
              :subfield="false"
              :defaultOpen="'preview'"
              :toolbarsFlag="false"
              :editable="false"
              :scrollStyle="true"
              :ishljs="true"
            />
          </div>
        </el-card>
    </el-dialog>



  </div>
</template>

<script>
import axios from "axios";

export default {
  name:"Article",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:5,
      name:"",
      dialogFormVisible:false,
      form:{},
      content:'',
      viewDialogVis:false
    }
  },
  created() {
    this.load()
  },
  methods:{
    //查看内容
    view(content){
        this.content=content
        this.viewDialogVis=true
    },
    imgAdd(pos,$file){
      let $vm=this.$refs.md
      //第一步，将图片上传到服务器
      const formData=new FormData();
      formData.append('file',$file);
      axios({
        url: 'http://localhost:9090/file/upload',
        method: 'post',
        data:formData,
        headers:{'Content-Type':'multipart/form-data'},
      }).then((res)=>{
        //将返回的url替换文本原位置
        $vm.$img2Url(pos,res.data);
      })
    },
    load(){
      this.request.get("/article/page1",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name
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
      this.request.post("/article",this.form).then(res =>{
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
      this.request.delete("/article/"+id).then(res =>{
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