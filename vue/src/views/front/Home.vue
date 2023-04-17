<template>
  <div>
    <div style="margin: 10px 0">
      <el-carousel height="450px">
        <el-carousel-item v-for="item in imgs" :key="item">
          <img :src="item" alt="" style="width: 100%">
        </el-carousel-item >
      </el-carousel>
    </div>
    <div style="margin: 10px 0">
      <el-row :gutter="10">
        <el-col :span="6" v-for="item in files" :key="item.id" style="margin-bottom: 10px">
          <div style="border: 1px solid #ccc ;padding-bottom:10px ">
            <img :src="item.url" alt="" style="width: 100%">
            <div style="color: #666;padding: 10px"><span>{{item.name}}</span></div>
            <div style="padding: 10px" >
                <el-button type="primary" >查看</el-button>
            </div>
          </div>

        </el-col>
      </el-row>
    </div>

  </div>

</template>

<script>
export default {
  name: "FrontHome",
  data(){
   return{
     imgs:[
         'http://localhost:9090/file/e7fe5cad8c6e456eb4b267ea7eb3e93a.jpg',
         'http://localhost:9090/file/1bc0251a18104c7c9a0d3ef05d072e59.jpg'
     ],
     files:[]
   }
  },
  created() {
    this.request.get("/echarts/file/front/all").then(res =>{
      console.log(res.data)
      this.files=res.data.filter(v=>v.type==='png'||v.type==='jpg')
        }
    )
  },
  methods:{}
}
</script>

<style scoped>

</style>