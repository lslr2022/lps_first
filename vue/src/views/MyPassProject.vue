<template>
  <div>
    <div style="margin-bottom: 30px">
      <el-breadcrumb spearator="/">
        <el-breadcrumb-item :to="{path:'/Home'}">主页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{path:'/MyProject'}">我的项目管理</el-breadcrumb-item>
        <el-breadcrumb-item>已通过项目管理</el-breadcrumb-item>

      </el-breadcrumb>

    </div>

    <div style="margin: 10px  0">
      <el-input style="width: 200px" placeholder="请输入名称"   suffix-icon="el-icon-search" class="ml-5"
                v-model="projectname"
      ></el-input>
      <el-input style="width: 200px" placeholder="请输入类型"   suffix-icon="el-icon-message" class="ml-5"
                v-model="projecttype"></el-input>
      <el-input style="width: 200px" placeholder="请输入状态"   suffix-icon="el-icon-position" class="ml-5"
                v-model="projectcondition"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">清空</el-button>

    </div>


    <el-table :data="tableData" border stripe :cell-style="cellStyle">
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="projectname"  label="竞赛名称" width="140"></el-table-column>
      <el-table-column prop="projecttype" label="竞赛类型" width="120"></el-table-column>
      <el-table-column prop="projectcondition" label="赛况"></el-table-column>
      <el-table-column prop="projectmember" label="竞赛成员"></el-table-column>
      <el-table-column prop="stime" label="开始时间"></el-table-column>
      <el-table-column prop="ltime" label="结束时间"></el-table-column>


<!--      <el-table-column>
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
      </el-table-column>-->

      <el-table-column label="操作">
        <template slot-scope="scope">
<!--          <el-button type="success" @click="handleEdit(scope.row)">上传</el-button>-->
        <el-upload
            action="action"
            :http-request="function(param){uploadSectionFile(param,scope.row.id)}"
            :on-success="handleFileUploadSuccess"
            :show-file-list="false"
            style="display: inline-block">
          <el-button type="danger" class="ml-5">上传作品<i class="el-icon-top-right"></i></el-button>
        </el-upload>

        </template>

      </el-table-column>

      <el-table-column label="作品名称">

        <template slot-scope="scope">
          <el-button @click="look(scope.row.url)">{{scope.row.fileName}}</el-button>
<!--         <el-button @click="look(scope.row.url)" type ="primary">查看</el-button>-->
        </template>
      </el-table-column>

      <el-table-column prop="score" label="分数"></el-table-column>
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

    <el-dialog title="竞赛信息" :visible.sync="dialogFormVisible" width="25%">
      <el-form label-width="75px"  size="small">
        <el-form-item label="竞赛名称" >
          <el-input v-model="form.projectname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="竞赛类型" >
          <el-input v-model="form.projecttype" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="竞赛状态" >
          <el-input v-model="form.projectcondition" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="竞赛成员" >
          <el-input v-model="form.projectmember" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="开始时间" >
          <el-input v-model="form.stime" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="截止时间" >
          <el-input v-model="form.ltime" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="文件名" >
          <el-input v-model="form.fileName" autocomplete="off"></el-input>
        </el-form-item>
        <el-upload
            action="action"
            :http-request="uploadSectionFile"
            :on-success="handleFileUploadSuccess"
            :show-file-list="false"
            style="display: inline-block">
          <el-button type="danger" class="ml-5">上传作品<i class="el-icon-top-right"></i></el-button>
        </el-upload>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {

  name: "MyPassProject",
  data(){
    let s_name=localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")).name:""
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:5,
      projectname:"",
      projecttype:"",
      projectcondition:"",
      name:s_name,
      dialogFormVisible:false,
      form:{},
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){

      this.request.get("/project/page122",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          projectname:this.projectname,
          projecttype:this.projecttype,
          projectcondition:this.projectcondition,
          name: this.name,
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

    /*新增项目的方法 */
    save(){
          this.$message.success("保存成功")
          this.dialogFormVisible=false
          this.load()
    },

    uploadSectionFile(param,id){
        const file=param.file;
        let formData=new FormData();
          formData.append("file",file);
          formData.append("id",id);
          axios({
            url:"http://localhost:9090/project/upload",
            method:"post",
            data:formData
          })
    },

    /*编辑项目的方法 */
    handleEdit(row){
      this.form=row
      this.dialogFormVisible=true
    },
    /*删除项目的方法 */
    handleDel(id){
      this.request.delete("/project/"+id).then(res =>{
        if(res){
          this.$message.success("删除成功")
          this.load()
        }else{
          this.$message.success("删除失败")
        }
      })
    },
    //查看
    look(url){
      window.open(url)
    },

    reset(){
      this.projectname=""
      this.projecttype=""
      this.projectcondition=""
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
    handleFileUploadSuccess(res){
      console.log(res)
      this.$message.success("文件上传成功")
      this.load()

    },
    exp(){
      window.open("http://localhost:9090/user/export")
    },
    handleImportSuccess(){
      this.$message.success("文件导入成功")
      this.load()
    },
    //定义字体颜色
    cellStyle(row,column,rowIndex,columnIndex){
      if (row.column.label==="赛况"&&row.row.projectcondition === "已结束"){
        return 'color:red'
      }
      if (row.column.label==="赛况"&&row.row.projectcondition === "进行中"){
        return 'color:green'
      }
    }

  }


}
</script>

<style scoped>

</style>