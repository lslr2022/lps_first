<template>
  <div style="color:#666" >

    <div style="margin: 10px  0">
      <el-input style="width: 300px" placeholder="请输入文章标题"   suffix-icon="el-icon-search" class="ml-5"
                v-model="name"
      ></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">清空</el-button>

    </div>
    <div style="margin: 10px 0"  >
      <el-button type="primary" @click="handleAdd">发布<i class="el-icon-circle-plus-outline"></i></el-button>
      <!--           <el-button type="danger">删除<i class="el-icon-remove-outline"></i></el-button>-->
    </div>

    <div style="margin: 10px 0">
      <div style="padding: 20px 0; border-bottom: 1px dashed #ccc;  " v-for="item in tableData" :key="item.id">
        <div class="pd-10" style="color: darkorange ;cursor:pointer" @click="$router.push('/StuArticleDetail?id='+item.id)">{{ item.name }}</div>
        <div style="font-size: 10px">
          <i class="el-icon-user-solid"></i><span>{{item.user}}</span>
          <i class="el-icon-time" style="margin-left: 10px"></i><span>{{item.time}}</span>
        </div>
      </div>


    </div>

    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, prev, pager, next"
          :total="total">
      </el-pagination>
    </div>




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

  }


}
</script>

<style scoped>



</style>