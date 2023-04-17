<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称"   suffix-icon="el-icon-search" class="ml-5"
                v-model="name"
      ></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">清空</el-button>
    </div>
    <el-table :data="tableData" border stripe>
      <el-table-column prop="id" label="ID" width="80"></el-table-column>
      <el-table-column prop="name" label="文件名称" ></el-table-column>
      <el-table-column prop="type" label="文件类型" ></el-table-column>
      <el-table-column prop="size" label="文件大小(kb)"></el-table-column>
      <el-table-column label="下载">
        <template slot-scope="scope">
          <el-button type="primary" @click="download(scope.row.url)">下载</el-button>
        </template>
      </el-table-column >

      <el-table-column label="操作" width="200" align="center">

        <template slot-scope="scope">
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
          <el-button type="success" style="margin-left: 10px" @click="handleRecover(scope.row.id)">复原</el-button>
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
  </div>


</template>

<script>
export default {
  name: "File1",
  data(){
    return{
      tableData:[],
      name:'',
      pageNum:1,
      pageSize:10,
      total:0

    }

  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/file/page111",{
        params:{
          pageNum:this.pageNum,
          pageSize:this.pageSize,
          name:this.name,
        }
      }).then(res =>{
        console.log(res)
        this.tableData=res.records
        this.total=res.total
      })

    },

    handleDel(id){
      this.request.delete("/file/true/"+id).then(res=>{
        if(res.code==='200'){
          this.$message.success("删除成功")
          this.load()
        }else {
          this.$message.success("删除失败")
        }
      })


    },
    handleRecover(id){
      this.request.delete("/file/recover/"+id).then(res=>{
        if(res.code==='200'){
          this.$message.success("恢复成功")
          this.load()
        }else {
          this.$message.success("恢复失败")
        }
      })
    },

    reset(){
      this.name=""
      this.load()

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
      this.load()
    },
    //下载
    download(url){
      window.open(url)
    },
    changeEnable(row){
      this.request.post("/file/update",row).then(res=>{
        if(res.code==='200'){
          this.$message.success("更新成功")
          this.load()
        }else{
          this.$message.success("更新失败")
        }
      })

    }

  }
}
</script>

<style scoped>

</style>