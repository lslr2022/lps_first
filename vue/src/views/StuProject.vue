<template>
  <div>
    <div style="margin-bottom: 30px">
      <el-breadcrumb spearator="/">
        <el-breadcrumb-item :to="{path:'/Home'}">主页</el-breadcrumb-item>
        <el-breadcrumb-item>竞赛查看</el-breadcrumb-item>


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
<!--      <el-table-column prop="projectmember" label="竞赛成员"></el-table-column>-->
      <el-table-column prop="stime" label="开始时间"></el-table-column>
      <el-table-column prop="ltime" label="结束时间"></el-table-column>

      <el-table-column>
        <template slot-scope="scope">
<!--          <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i></el-button>-->
          <el-button type="primary" @click="handleAdd(scope.row)" style="text-align: center">申请参加<i class="el-icon-circle-plus-outline"></i></el-button>
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

    <el-dialog title="竞赛信息" :visible.sync="dialogFormVisible" width="25%">
      <el-form label-width="75px"  size="small">
        <el-form-item label="竞赛名称"  >
          <el-input v-model="form.projectname" autocomplete="off" :disabled="true" ></el-input>

<!--         下拉框设计失败，无法获取数据
 <el-select chearable v-model="form.attribute" placeholder="请选择">
            <el-option v-for="item in projects" :key="item.id" :label="item.projectname" :value="item.id"></el-option>

          </el-select>-->
        </el-form-item>

        <el-form-item label="竞赛类型" >
          <el-input v-model="form.projecttype" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="竞赛状态" >
          <el-input v-model="form.projectcondition" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>

        <el-form-item label="竞赛成员" >
          <el-input v-model="form.projectmember" autocomplete="off"  ></el-input>
        </el-form-item>

        <el-form-item label="开始时间" >
          <el-input v-model="form.stime" autocomplete="off" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="截止时间" >
          <el-input v-model="form.ltime" autocomplete="off" :disabled="true"></el-input>
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
  name: "project",
  data(){
    return{
      tableData: [],
      total:0,
      pageNum:1,
      pageSize:5,
      projectname:"",
      projecttype:"",
      projectcondition:"",
      dialogFormVisible:false,
      form:{},
      projects:[],
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{}
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/stuProject/page2",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          projectname:this.projectname,
          projecttype:this.projecttype,
          projectcondition:this.projectcondition
        }
      }).then(res =>{
        console.log(res)

        this.tableData=res.records
        this.total=res.total
      })
     /* //请求单独
      this.request.get("/stuProject/").then(res =>{
        console.log(res)
        this.projects=res.data
      })*/
      /*fetch( "http://localhost:9090/user/page1?pageNum="+this.pageNum+"&pageSize="+this.pageSize+"&username="+this.username+"&nickname="+this.nickname)
          .then(res => res.json()).then(res =>{
        console.log(res)
        this.tableData=res.data
        this.total=res.total
      })*/

    },
    /*新增项目的方法 */
    save(){
      this.request.post("/project",this.form).then(res =>{
        if(res){
          this.$message.success("申请成功")
          this.dialogFormVisible=false
          this.load()
        }else{
          this.$message.success("申请失败")
        }
      })
    },


  /*  /!*编辑项目的方法 *!/
    handleEdit(row){
      this.form=row
     /!* let name=localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")).name:""
      if(this.form.projectname!==name){
        this.form.projectmember=this.form.projectmember+','+name;
      }else if(this.form.projectname===name){
        this.form.name=name;
      }*!/
      this.dialogFormVisible=true
    },*/

    reset(){
      this.projectname=""
      this.projecttype=""
      this.projectcondition=""
      this.load()

    },
    handleAdd(row){
      this.dialogFormVisible=true
      this.form.projectname=row.projectname
      this.form.projecttype=row.projecttype
      this.form.projectcondition=row.projectcondition
      this.form.stime=row.stime
      this.form.ltime=row.ltime

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