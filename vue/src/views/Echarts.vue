<template>
  <div>
    <el-row>
      <el-col :span="12">
        <div id="main" style="height: 500px;width: 500px"></div>
      </el-col>
      <el-col :span="12">
        <div id="pie" style="height: 500px;width:500px"></div>
      </el-col>
    </el-row>


  </div>

</template>

<script>
import  * as echarts from 'echarts'


export default {
  name: "Echarts",
  data(){
    return{

    }

  },
  mounted(){   //页面元素渲染之后再触发


    var option = {
      title: {
        text:'各季节用户注册数量',
        subtext: '趋势',
        left:'center'
      },
      xAxis: {
        type: 'category',
        data: ["春","夏","秋","冬"]
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'line'
        },
        { data: [],
          type: 'bar'
        }
      ]
    };
    /*this.request.get("/echarts/column").then(res=>{
      option.xAxis.data=res.data.x;
      option.series[0].data=res.data.y;
      option.series[1].data=res.data.y;
      //数据准备最后set
      myChart.setOption(option);
    })*/



    //饼状图

     var pieOption = {
      title: {
        text: 'Referer of a Website',
        subtext: 'Fake Data',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          name: 'Access From',
          type: 'pie',
          radius: '50%',
          //内部比例显示
          label:{  //饼图文本标签
            normal:{
              show:true,
              position:'inner',//标签位置
              textStyle:{
                fontWeight:250,
                fontSize:15

              },
              formatter:'{d}%'
            }

          },
          data: [
            { value: 1048, name: 'Search Engine' },
            { value: 735, name: 'Direct' },
            { value: 580, name: 'Email' },
            { value: 484, name: 'Union Ads' },
            { value: 300, name: 'Video Ads' }
          ],
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };


    //柱状图
    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);
    //饼图
    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);


    this.request.get("/echarts/members").then(res=>{
      /* option.xAxis.data=res.data.x;*/
      //给折线图和柱状图赋予数据
      option.series[0].data=res.data;
      option.series[1].data=res.data;
      //数据准备最后set
      myChart.setOption(option);
      pieOption.series[0].data=[
        {name:  "春"  , value:res.data[0]   },
        {name:  "夏"  , value:res.data[1]   },
        {name:  "秋"  , value:res.data[2]   },
        {name:  "冬"  , value:res.data[3]   },
      ]
      pieChart.setOption(pieOption)
    })

  }

}
</script>

<style scoped>

</style>