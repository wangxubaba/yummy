<template>
    <div style="background-color: #f8f8f9">
      <Menu style="height: 200px"></Menu>
      <div style="height: 60px">
        <ButtonGroup style="float: left;margin-left: 15%;padding-top: 15px">
          <Button type="success" class="Button" @click="changeData('true')">真 实 数 据</Button>
          <Button type="error" class="Button" @click="changeData('false')">演 示 数 据</Button>
        </ButtonGroup>
      </div>

      <div id="canteen" style="margin-bottom: 20px">
        <p class="title">餐厅统计信息</p>
        <div id="canteen1" style="width: 1063px;height: 400px;margin-left: 15%;margin-right: 15%;background-color: white;padding: 10px;margin-top: 10px"></div>
        <div style="width: 1063px;height: 400px;margin-left: 15%;margin-right: 15%;background-color: white;padding: 10px;margin-top: 10px">
          <div id="canteen2" style="width: 690px;height: 380px;float: left"></div>
          <div style="width: 350px;height: 380px;float: left">
            <table>
              <tr v-for="item in canteen2Data">
                <th>{{item.name}}</th>
                <td>{{item.value}}</td>
              </tr>
            </table>
          </div>
        </div>
      </div>

      <div id="customer" style="margin-bottom: 20px">
        <p class="title">会员统计信息</p>
        <div id="customer1" style="width: 1063px;height: 400px;margin-left: 15%;margin-right: 15%;background-color: white;margin-bottom: 10px;padding: 10px;margin-top: 10px"></div>
        <div style="width: 1063px;height: 400px;margin-left: 15%;margin-right: 15%;background-color: white;padding: 10px">
          <div id="customer2" style="width: 680px;height: 380px;float: left"></div>
          <div style="width: 360px;height: 380px;float: left;padding-top: 10px">
            <div style="float: left;height: 42px;width: 90px" v-for="(item,index) in provinces" class="blocks">
              <div style="float: left;height: 42px;width: 45px;font-weight: bold">{{item}}</div>
              <div style="float: left;height: 42px;width: 45px">{{customer2Data[index]}}</div>
            </div>
          </div>
        </div>
      </div>

      <div id="cash">
        <p class="title">金额统计信息</p>
        <div id="cash1" style="width: 1063px;height: 400px;margin-left: 15%;margin-right: 15%;background-color: white;padding: 10px;margin-top: 10px;margin-bottom: 10px"></div>
        <div style="width: 1063px;height: 400px;margin-left: 15%;margin-right: 15%;background-color: white;padding: 10px">
          <div id="cash2" style="width: 690px;height: 380px;float: left"></div>
          <div style="width: 350px;height: 380px;float: left">
            <table>
              <tr v-for="item in cash2Data">
                <th>{{item.name}}</th>
                <td>{{getPrice(item.value)}}</td>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
  import Menu from '../../components/MenuColumn/ManagerMenu'
    export default {
        name: "ViewStatisticsPage",
      components:{Menu},
      data(){
        return{
          fake:false,
          canteen2Data:'',
          provinces:["北京", "天津", "河北", "山西", "内蒙古", "辽宁", "吉林", "黑龙江", "上海", "江苏", "浙江", "安徽", "福建", "江西", "山东", "河南", "湖北", "湖南", "广东", "广西", "海南", "重庆", "四川", "贵州", "云南", "西藏", "陕西", "甘肃", "青海", "宁夏", "新疆", "台湾", "澳门", "香港", "钓鱼岛"],
          customer2Data:'',
          cash2Data:'',
        }
      },
      mounted(){
        this.drawLine1()
        this.drawLine2()
        this.drawLine3()
        this.drawLine4()
        this.drawLine5()
        this.drawLine6()
      },
      methods: {
        async drawLine1(){
          //1.先拿数据
          var xData=[]
          var qita=[]
          var huican=[]
          var kuaican=[]
          var hancan=[]
          var xican=[]

          if(this.fake){
            xData=['2018-08','2018-09','2018-10','2018-11','2018-12','2019-01','2019-02']
            qita=[120, 132, 101, 134, 90, 230, 210]
            huican=[220, 182, 191, 234, 290, 330, 310]
            kuaican=[150, 232, 201, 154, 190, 330, 410]
            hancan=[320, 332, 301, 334, 390, 330, 320]
            xican=[820, 932, 901, 934, 1290, 1330, 1320]
          }
          else{
            await this.$axios.post('/server/statistics/get/new/register/canteens/per/month').then(re=>{
              var data=re.data
              for(var i=0;i<data.length;i++){
                xData.push(data[i].name)
                qita.push(this.getNumByType1('其他',data[i].array))
                huican.push(this.getNumByType1('回餐',data[i].array))
                kuaican.push(this.getNumByType1('快餐',data[i].array))
                hancan.push(this.getNumByType1('汉餐',data[i].array))
                xican.push(this.getNumByType1('西餐',data[i].array))
              }
            })
          }
          // 基于准备好的dom，初始化echarts实例
          let myChart = this.$echarts.init(document.getElementById('canteen1'))
          // 绘制图表
          myChart.setOption({
            title: {
              text: '每月新注册餐厅数'
            },
            tooltip : {
              trigger: 'axis',
              axisPointer: {
                type: 'cross',
                label: {
                  backgroundColor: '#6a7985'
                }
              }
            },
            legend: {
              data:['其他','回餐','快餐','汉餐','西餐']
            },
            toolbox: {
              feature: {
                saveAsImage: {}
              }
            },
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              containLabel: true
            },
            xAxis : [
              {
                type : 'category',
                boundaryGap : false,
                data : xData
              }
            ],
            yAxis : [
              {
                type : 'value'
              }
            ],
            series : [
              {
                name:'其他',
                type:'line',
                stack: '总量',
                areaStyle: {},
                data:qita
              },
              {
                name:'回餐',
                type:'line',
                stack: '总量',
                areaStyle: {},
                data:huican
              },
              {
                name:'快餐',
                type:'line',
                stack: '总量',
                areaStyle: {},
                data:kuaican
              },
              {
                name:'汉餐',
                type:'line',
                stack: '总量',
                areaStyle: {},
                data:hancan
              },
              {
                name:'西餐',
                type:'line',
                stack: '总量',
                areaStyle: {},
                data:xican
              },
            ]
          });
        },
        getNumByType1(type,array){
          var result=0
          for(var i=0;i<array.length;i++){
            if(array[i].name===type){
              result=array[i].int_data
              break
            }
          }
          return result
        },
        async drawLine2(){
          //1.先拿数据
          var data
          if(this.fake){
            data=[
              {value:12, name:'其他'},
              {value:25, name:'回餐'},
              {value:36, name:'快餐'},
              {value:17, name:'汉餐'},
              {value:29, name:'西餐'},
            ]
          }
          else{
            await this.$axios.post('/server/statistics/get/canteens/by/type',{}).then(re=>{
              data=[
                {value:this.getNumByType2('其他',re.data[0],re.data[1]), name:'其他'},
                {value:this.getNumByType2('回餐',re.data[0],re.data[1]), name:'回餐'},
                {value:this.getNumByType2('快餐',re.data[0],re.data[1]), name:'快餐'},
                {value:this.getNumByType2('汉餐',re.data[0],re.data[1]), name:'汉餐'},
                {value:this.getNumByType2('西餐',re.data[0],re.data[1]), name:'西餐'},
              ]
            })
          }
          this.canteen2Data=data
          // 基于准备好的dom，初始化echarts实例
          let myChart = this.$echarts.init(document.getElementById('canteen2'))
          // 绘制图表
          myChart.setOption({
            title: {
              text: '已注册餐厅分类图'
            },
            calculable : true,
            series : [
              {
                name:'面积模式',
                type:'pie',
                radius : [30, 110],
                center : ['50%', '50%'],
                roseType : 'area',
                data:data
              }
            ]
          });
        },
        getNumByType2(type,names,datas){
          var result=0;
          for(var i=0;i<names.length;i++){
            if(names[i]===type){
              result=datas[i]
              break
            }
          }
          return result
        },
        async drawLine3(){
          //1.先拿数据
          var xData=[]
          var data=[]
          if(this.fake){
            xData=['2018-08','2018-09','2018-10','2018-11','2018-12','2019-01','2019-02']
            data=[150, 232, 201, 154, 190, 330, 410]
          }
          else{
            await this.$axios.post('/server/statistics/get/new/register/customer/per/month').then(re=>{
              xData=re.data[0]
              data=re.data[1]
            })
          }
          // 基于准备好的dom，初始化echarts实例
          let myChart = this.$echarts.init(document.getElementById('customer1'))
          // 绘制图表
          myChart.setOption({
            title: {
              text: '每月新注册用户数'
            },
            xAxis: {
              type: 'category',
              boundaryGap: false,
              data: xData
            },
            yAxis: {
              type: 'value'
            },
            series: [{
              data: data,
              type: 'line',
              color:'rgb(145,199,174)',
              areaStyle: {}
            }]
          });
        },
        async drawLine4(){
          //1.先拿数据
          var data=[]
          if(this.fake){
            data=[
              {value:10,name:'北京'},
              {value:5,name:'天津'},
              {value:15,name:'河北'},
              {value:25,name:'山西'},
              {value:20,name:'内蒙古'},
              {value:35,name:'辽宁'},
              {value:30,name:'吉林'},
              {value:40,name:'黑龙江'},
            ]
          }
          else{
            await this.$axios.post('/server/statistics/get/customers/by/province').then(re=>{
              var names=re.data[0]
              var values=re.data[1]
              for(var i=0;i<names.length;i++){
                data.push({value:values[i],name:names[i]})
              }
            })
          }
          var tab=[]
          for(var i=0;i<this.provinces.length;i++){
            tab.push(this.getNumByType4(this.provinces[i],data))
          }
          this.customer2Data=tab

          // 基于准备好的dom，初始化echarts实例
          let myChart = this.$echarts.init(document.getElementById('customer2'))
          // 绘制图表
          myChart.setOption({
            title: {
              text: '已注册用户省份分布图'
            },
            calculable : true,
            series : [
              {
                name:'面积模式',
                type:'pie',
                radius : [30, 110],
                center : ['50%', '50%'],
                roseType : 'area',
                data:data
              }
            ]
          });
        },
        getNumByType4(type,data){
          var result=0
          for(var i=0;i<data.length;i++){
            if(data[i].name===type){
              result=data[i].value
              break
            }
          }
          return result
        },
        async drawLine5(){
          //1.先拿数据
          var xData=[]
          var data=[]
          if(this.fake){
            xData=['2018-08','2018-09','2018-10','2018-11','2018-12','2019-01','2019-02']
            data=[150, 232, 201, 154, 190, 330, 410]
          }
          else{
            await this.$axios.post('/server/statistics/get/monthly/cash').then(re=>{
              xData=re.data[0]
              data=re.data[1]
            })
          }
          // 基于准备好的dom，初始化echarts实例
          let myChart = this.$echarts.init(document.getElementById('cash1'))
          // 绘制图表
          myChart.setOption({
            title: {
              text: '每月销售金额'
            },
            xAxis: {
              type: 'category',
              boundaryGap: false,
              data: xData
            },
            yAxis: {
              type: 'value'
            },
            series: [{
              data: data,
              type: 'line',
              color:'rgb(145,199,174)',
              areaStyle: {}
            }]
          });
        },
        async drawLine6(){
          //1.先拿数据
          var data=[]
          if(this.fake){
            data=[
              {value:10, name:'VIP用户开支'},
              {value:5, name:'普通用户开支'},
              {value:15, name:'递送开支'},
            ]
          }
          else{
            await this.$axios.post('/server/statistics/cash/career').then(re=>{
              for(var i=0;i<re.data[0].length;i++){
                data.push({value:re.data[1][i],name:re.data[0][i]})
              }
            })
          }
          this.cash2Data=data
          // 基于准备好的dom，初始化echarts实例
          let myChart = this.$echarts.init(document.getElementById('cash2'))
          // 绘制图表
          myChart.setOption({
            title: {
              text: '上月收入来源分布'
            },
            calculable : true,
            series : [
              {
                name:'面积模式',
                type:'pie',
                radius : [30, 110],
                center : ['50%', '50%'],
                roseType : 'area',
                data:data
              }
            ]
          });
        },
        changeData(fake){
          if(fake=='true'){
            this.fake=false
          }
          else{
            this.fake=true
          }
          this.drawLine1()
          this.drawLine2()
          this.drawLine3()
          this.drawLine4()
          this.drawLine5()
          this.drawLine6()
        },
        getPrice(price){
          return '￥'+parseFloat(price).toFixed(2)
        },
      }
    }
</script>

<style scoped>
.Button{
  width: 150px;
}
  .title{
    font-size: 30px;
    font-weight: bold;
    text-align: left;
    margin-left: 15%;
    border-left: 15px solid #19be6b;
    padding-left: 5px;
  }

  #canteen table{
    font-size: 15px;
    margin-top: 100px;
  }

  #canteen td{
    width: 100px;
    padding: 5px;
  }
  #canteen th{
    width: 100px;
    padding: 5px;
  }

  #cash table{
    font-size: 15px;
    margin-top: 130px;
  }

  #cash td{
    width: 120px;
    padding: 10px;
    text-align: left;
  }

  #cash th{
    width: 120px;
    padding: 10px;
    text-align: left;
  }

  .blocks{
    font-size: 15px;
  }
</style>
