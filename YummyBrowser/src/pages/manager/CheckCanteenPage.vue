<template>
<div>
  <Menu style="height: 200px"></Menu>
  <div class="contents" v-for="(item, index) in content" v-if="item.checked+item.waiting!=0">
    <div style="height: 80px">
      <div class="title">
        <p style="font-size: 20px;font-weight: bold;margin-top: 22px">{{item.date}} 注册/修改餐厅的列表</p>
      </div>
      <div class="title" style="text-align: left">
        <table class="table1">
          <tr>
            <th style="background-color: #ed4099">新注册</th>
            <td style="-webkit-text-fill-color: #ed4099">{{item.waiting+item.checked}}</td>
            <th style="background-color: #ff9900">待审核</th>
            <td style="-webkit-text-fill-color: #ff9900">{{item.waiting}}</td>
            <th style="background-color: #19be6b">审核通过</th>
            <td style="-webkit-text-fill-color: #19be6b">{{item.pass}}</td>
            <th style="background-color: #ed4014">审核拒绝</th>
            <td style="-webkit-text-fill-color: #ed4014">{{item.refuse}}</td>
          </tr>
        </table>
      </div>
    </div>

    <Collapse simple class="content">
      <Panel v-for="(waitingCanteen,index1) in item.waitingCheckCanteens" :name="getName(index1,item.date+'waiting')" :key="getName(index1,item.date+'waiting')">
        <span style="background-color: #ff9900" class="type">待审核</span>&nbsp&nbsp&nbsp
        ID：{{waitingCanteen.id}}&nbsp&nbsp&nbsp
        餐厅名称：{{waitingCanteen.name}}&nbsp&nbsp&nbsp
        餐厅地址:{{waitingCanteen.province}}&nbsp{{waitingCanteen.city}}&nbsp{{waitingCanteen.area}}&nbsp{{waitingCanteen.address}}&nbsp&nbsp&nbsp
        <div slot="content">
          <table>
            <tr>
              <th>申请人</th>
              <td>{{waitingCanteen.hostName}}</td>
              <td rowspan="2"><Button type="success" style="width: 80%" @click="doPass(index,index1)">通过</Button></td>
            </tr>
            <tr>
              <th>身份证号</th>
              <td>{{waitingCanteen.idNumber}}</td>
            </tr>
            <tr>
              <th>邮箱</th>
              <td>{{waitingCanteen.email}}</td>
              <td></td>
            </tr>
            <tr>
              <th>类型</th>
              <td>{{waitingCanteen.type}}</td>
              <td rowspan="2"><Button type="error" style="width: 80%" @click="doRefuse(index,index1)">拒绝</Button></td>
            </tr>
            <tr>
              <th>开业时间</th>
              <td>{{waitingCanteen.beginTime}}--{{waitingCanteen.endTime}}</td>
            </tr>
          </table>
        </div>
      </Panel>
      <Panel v-for="(checkedCanteen,index2) in item.alreadyCheckedCanteens" :name="getName(index2,item.date+'checked')" :key="getName(index2,item.date+'checked')">
        <span style="background-color: #19be6b" class="type">已审核</span>&nbsp&nbsp&nbsp
        ID：{{checkedCanteen.id}}&nbsp&nbsp&nbsp
        餐厅名称：{{checkedCanteen.name}}&nbsp&nbsp&nbsp
        餐厅地址:{{checkedCanteen.province}}&nbsp{{checkedCanteen.city}}&nbsp{{checkedCanteen.area}}&nbsp{{checkedCanteen.address}}&nbsp&nbsp&nbsp
        <div slot="content">
          <table>
            <tr>
              <th>申请人</th>
              <td>{{checkedCanteen.hostName}}</td>
              <td rowspan="5">
                <p>已审核</p>
                <p v-if="checkedCanteen.checkState=='pass'" style="-webkit-text-fill-color: #19be6b">通过</p>
                <p v-if="checkedCanteen.checkState=='refuse'" style="-webkit-text-fill-color: #ed4014">拒绝</p>
              </td>
            </tr>
            <tr>
              <th>身份证号</th>
              <td>{{checkedCanteen.idNumber}}</td>
            </tr>
            <tr>
              <th>邮箱</th>
              <td>{{checkedCanteen.email}}</td>
            </tr>
            <tr>
              <th>类型</th>
              <td>{{checkedCanteen.type}}</td>
            </tr>
            <tr>
              <th>开业时间</th>
              <td>{{checkedCanteen.beginTime}}--{{checkedCanteen.endTime}}</td>
            </tr>
          </table>
        </div>
      </Panel>
    </Collapse>
  </div>
</div>
</template>

<script>
  import Menu from '../../components/MenuColumn/ManagerMenu'
    export default {
        name: "CheckCanteenPage",
      components:{Menu},
      async mounted(){
        await this.$axios.post('/server/manage/checkList',{}).then(re=>{
          this.content=re.data
          console.log(this.content)
        })
      },
      data(){
          return{
            content:''
          }
      },
      methods:{
        getName(index, name){
          return name+index;
        },
        getPrice(price){
          return '￥'+price.toFixed(2)
        },
        async doPass(dateIndex,canteenIndex){
          var canteen=this.content[dateIndex].waitingCheckCanteens[canteenIndex];
          canteen.checkState="pass"
          await this.$axios.post('/server/manage/checkCanteen',canteen).then(re=>{
            console.log(re)
          })
          await this.$axios.post('/server/manage/checkList').then(re=>{
            this.content=re.data
          })
        },
        async doRefuse(dateIndex,canteenIndex){
          var canteen=this.content[dateIndex].waitingCheckCanteens[canteenIndex];
          canteen.checkState="refuse"
          await this.$axios.post('/server/manage/checkCanteen',canteen).then(re=>{
            console.log(re)
          })
          await this.$axios.post('/server/manage/checkList').then(re=>{
            this.content=re.data
            console.log(re.data)
          })
        }
      }
    }
</script>

<style scoped>
.contents{
  margin-left: 17%;
  margin-right: 17%;
}

.content{
  text-align: left;
}

.content p{
  font-size: 15px;
  font-weight: bold;
}

.content th{
  width: 200px;
  text-align: center;
}

.content table{
  margin: 0 auto;
}

.content td{
  width: 200px;
  text-align: center;
}

.table1{
  font-size: 15px;
  margin-top: 25px;
}

.table1 td{
  width: 50px;
  padding-left: 10px;
}

.table1 th{
  -webkit-text-fill-color: white;
}

  .title{
    float: left;
    width: 50%;
    height: 80px;
  }

.type{
  -webkit-text-fill-color: white;
  font-size: 12px;
  padding: 5px;
  border-radius: 15px;
}

</style>
