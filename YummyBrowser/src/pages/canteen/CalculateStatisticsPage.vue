<template>
<div>
  <!--标题-->
  <TitleMenu style="height: 200px"></TitleMenu>
  <!--选择按钮-->
  <div style="height: 50px;margin-left: 15%;margin-right: 15%;padding-top: 15px">
    <ButtonGroup style="float: left">
      <Button type="success" class="Button" @click="time">时 间</Button>
      <Button type="warning" class="Button" @click="cash">金 额</Button>
      <Button type="error" class="Button" @click="type">类 别</Button>
    </ButtonGroup>
    <DatePicker type="daterange" split-panels placeholder="选择时间段" style="width: 200px;float: left;margin-left: 100px" @on-change="changeTime" v-model="timeInterval"></DatePicker>
  </div>

  <div id="time" v-if="showTime">
    <div v-if="day.infos.length>0" v-for="(day, index1) in timeData"  style="margin-bottom: 20px">
      <div style="height: 80px;margin-left: 15%;margin-right: 15%">
        <p style="float: left;text-align: left;padding-left: 20px;padding-right: 20px;font-size: 30px;font-weight: bold;padding-top: 20px">{{day.name}}</p>
        <div class="dailyStatistics1" style="float:left;">
          <table style="margin: 0 auto">
            <tr>
              <th>新接单</th>
              <th>处理中</th>
              <th>待配送</th>
              <th>配送中</th>
              <th>已签收</th>
              <th>被取消</th>
            </tr>
            <tr>
              <td style="-webkit-text-fill-color: #ed4014">{{day.statistics[1].int_data}}</td>
              <td style="-webkit-text-fill-color: #ed4099">{{day.statistics[2].int_data}}</td>
              <td style="-webkit-text-fill-color: #ff9900">{{day.statistics[3].int_data}}</td>
              <td style="-webkit-text-fill-color: aquamarine">{{day.statistics[4].int_data}}</td>
              <td style="-webkit-text-fill-color: #19be6b">{{day.statistics[5].int_data}}</td>
              <td style="-webkit-text-fill-color: #c5c8ce">{{day.statistics[6].int_data}}</td>
            </tr>
          </table>
        </div>
      </div>
      <Collapse simple class="content">
        <Panel v-for="(item,index2) in day.infos" :name="getName(index1+'-',index2)" :key="getName(index1+'-',index2)">
          <span v-if="item.state==='WaitingAccept'" style="background-color: #ed4014" class="type">新接单</span>
          <span v-if="item.state==='Making'"style="background-color: #ed4099" class="type">制作中</span>
          <span v-if="item.state==='WaitingDelivery'"style="background-color: #ff9900" class="type">待配送</span>
          <span v-if="item.state==='Delivering'"style="background-color: aquamarine" class="type">配送中</span>
          <span v-if="item.state==='Arrived'"style="background-color: #19be6b" class="type">已签收</span>
          <span v-if="item.state==='Cancel'"style="background-color: #c5c8ce" class="type">已取消</span>
          预定时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
          客户手机:{{item.customer.cell_phone}}&nbsp&nbsp&nbsp
          客户地址：{{getAddress(item.customer.addresses[0])}}
          <div slot="content">
            <table class="title">
              <tr>
                <th>配送员：</th>
                <td>{{item.deliveryID}}</td>
                <th>配送时间：</th>
                <td>{{item.predictTime}}</td>
                <th>配送距离：</th>
                <td>{{item.distance}}</td>
              </tr>
            </table>
            <table class="menu">
              <tr>
                <th>菜品</th>
                <th>数量</th>
                <th>单价</th>
                <th>合计</th>
              </tr>
              <tr v-for="(dish,index2) in item.orderedDishes.dishes" :id="getName(index2,item.id+'dish')">
                <td>{{dish.name}}</td>
                <td>×{{dish.amount}}</td>
                <td>{{getPrice(dish.price)}}</td>
                <td>{{getPrice(dish.amount*dish.price)}}</td>
              </tr>
              <tr v-for="(discount,index3) in item.orderedDishes.discounts" :id="getName(index3,item.id+'discount')">
                <td>
                  <p style="font-weight: bold">{{discount.name}}:</p>
                  <p v-for="(dish,index4) in discount.dishes" :id="getName(index4,item.id+'discountName')">{{dish.name}}</p>
                </td>
                <td>×{{discount.amount}}</td>
                <td>{{getPrice(discount.discount_price)}}</td>
                <td>{{getPrice(discount.discount_price*discount.amount)}}</td>
              </tr>
              <tr>
                <td>总计</td>
                <td colspan="3">{{getPrice(item.dishSum)}}</td>
              </tr>
            </table>
          </div>
        </Panel>
      </Collapse>
    </div>
  </div>

  <div id="cash" v-if="showCash">
    <div v-if="cashData.infos.length>0">
      <div class="dailyStatistics2">
        <table style="margin: 0 auto">
          <tr>
            <th>{{this.cashData.statistics[0].name}}</th>
            <th>{{this.cashData.statistics[1].name}}</th>
            <th>{{this.cashData.statistics[2].name}}</th>
            <th>{{this.cashData.statistics[3].name}}</th>
            <th>{{this.cashData.statistics[4].name}}</th>
            <th>{{this.cashData.statistics[5].name}}</th>
            <th>{{this.cashData.statistics[6].name}}</th>
          </tr>
          <tr>
            <td style="-webkit-text-fill-color: #ed4014">{{this.cashData.statistics[0].int_data}}</td>
            <td style="-webkit-text-fill-color: #ed4099">{{this.cashData.statistics[1].int_data}}</td>
            <td style="-webkit-text-fill-color: #ff00ff">{{this.cashData.statistics[2].int_data}}</td>
            <td style="-webkit-text-fill-color: #ffff80">{{this.cashData.statistics[3].int_data}}</td>
            <td style="-webkit-text-fill-color: #ff9900">{{this.cashData.statistics[4].int_data}}</td>
            <td style="-webkit-text-fill-color: aquamarine">{{this.cashData.statistics[5].int_data}}</td>
            <td style="-webkit-text-fill-color: #19be6b">{{this.cashData.statistics[6].int_data}}</td>
          </tr>
        </table>
      </div>
      <Collapse simple class="content">
        <Panel v-for="(item,index2) in cashData.infos" :name="getName('cash-',index2)" :key="getName('cash-',index2)">
          <span v-if="item.state==='WaitingAccept'" style="background-color: #ed4014" class="type">新接单</span>
          <span v-if="item.state==='Making'"style="background-color: #ed4099" class="type">制作中</span>
          <span v-if="item.state==='WaitingDelivery'"style="background-color: #ff9900" class="type">待配送</span>
          <span v-if="item.state==='Delivering'"style="background-color: aquamarine" class="type">配送中</span>
          <span v-if="item.state==='Arrived'"style="background-color: #19be6b" class="type">已签收</span>
          <span v-if="item.state==='Cancel'"style="background-color: #c5c8ce" class="type">已取消</span>
          预定时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
          客户手机:{{item.customer.cell_phone}}&nbsp&nbsp&nbsp
          客户地址：{{getAddress(item.customer.addresses[0])}}
          <div slot="content">
            <table class="title">
              <tr>
                <th>配送员：</th>
                <td>{{item.deliveryID}}</td>
                <th>配送时间：</th>
                <td>{{item.predictTime}}</td>
                <th>配送距离：</th>
                <td>{{item.distance}}</td>
              </tr>
            </table>
            <table class="menu">
              <tr>
                <th>菜品</th>
                <th>数量</th>
                <th>单价</th>
                <th>合计</th>
              </tr>
              <tr v-for="(dish,index2) in item.orderedDishes.dishes" :id="getName(index2,item.id+'dish')">
                <td>{{dish.name}}</td>
                <td>×{{dish.amount}}</td>
                <td>{{getPrice(dish.price)}}</td>
                <td>{{getPrice(dish.amount*dish.price)}}</td>
              </tr>
              <tr v-for="(discount,index3) in item.orderedDishes.discounts" :id="getName(index3,item.id+'discount')">
                <td>
                  <p style="font-weight: bold">{{discount.name}}:</p>
                  <p v-for="(dish,index4) in discount.dishes" :id="getName(index4,item.id+'discountName')">{{dish.name}}</p>
                </td>
                <td>×{{discount.amount}}</td>
                <td>{{getPrice(discount.discount_price)}}</td>
                <td>{{getPrice(discount.discount_price*discount.amount)}}</td>
              </tr>
              <tr>
                <td>总计</td>
                <td colspan="3">{{getPrice(item.dishSum)}}</td>
              </tr>
            </table>
          </div>
        </Panel>
      </Collapse>
    </div>
    <div v-else style="float: left;background-color: rgb(247,247,247);height: 100px;margin-left: 15%;width: 70%;font-size: 15px;font-weight: bold;padding-top: 40px">
      该时间段暂无订单
    </div>
  </div>

  <div id="type" v-if="showType">
    <div class="dailyStatistics2">
      <table style="margin: 0 auto">
        <tr>
          <th>新接单</th>
          <th>处理中</th>
          <th>待配送</th>
          <th>配送中</th>
          <th>已签收</th>
          <th>被取消</th>
        </tr>
        <tr>
          <td style="-webkit-text-fill-color: #ed4014">{{this.typeData.newOrder.length}}</td>
          <td style="-webkit-text-fill-color: #ed4099">{{this.typeData.solving.length}}</td>
          <td style="-webkit-text-fill-color: #ff9900">{{this.typeData.waitingDelivery.length}}</td>
          <td style="-webkit-text-fill-color: aquamarine">{{this.typeData.delivering.length}}</td>
          <td style="-webkit-text-fill-color: #19be6b">{{this.typeData.sign.length}}</td>
          <td style="-webkit-text-fill-color: #c5c8ce">{{this.typeData.cancel.length}}</td>
        </tr>
      </table>
    </div>
    <Collapse simple class="content" v-if="typeData.newOrder.length>0">
      <Panel v-for="(item,index1) in typeData.newOrder" :name="getName(index1,'newOrder')" :key="getName(index1,'newOrder')">
        <span style="background-color: #ed4014" class="type">新接单</span>&nbsp&nbsp&nbsp
        预定时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
        订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        客户手机:{{item.customer.cell_phone}}&nbsp&nbsp&nbsp
        客户地址：{{getAddress(item.customer.addresses[0])}}
        <div slot="content">
          <table class="title">
            <tr>
              <th>配送员：</th>
              <td>{{item.deliveryID}}</td>
              <th>配送时间：</th>
              <td>{{item.predictTime}}</td>
              <th>配送距离：</th>
              <td>{{item.distance}}</td>
            </tr>
          </table>
          <table class="menu">
            <tr>
              <th>菜品</th>
              <th>数量</th>
              <th>单价</th>
              <th>合计</th>
            </tr>
            <tr v-for="(dish,index2) in item.orderedDishes.dishes" :id="getName(index2,item.id+'dish')">
              <td>{{dish.name}}</td>
              <td>×{{dish.amount}}</td>
              <td>{{getPrice(dish.price)}}</td>
              <td>{{getPrice(dish.amount*dish.price)}}</td>
            </tr>
            <tr v-for="(discount,index3) in item.orderedDishes.discounts" :id="getName(index3,item.id+'discount')">
              <td>
                <p style="font-weight: bold">{{discount.name}}:</p>
                <p v-for="(dish,index4) in discount.dishes" :id="getName(index4,item.id+'discountName')">{{dish.name}}</p>
              </td>
              <td>×{{discount.amount}}</td>
              <td>{{getPrice(discount.discount_price)}}</td>
              <td>{{getPrice(discount.discount_price*discount.amount)}}</td>
            </tr>
            <tr>
              <td>总计</td>
              <td colspan="3">{{getPrice(item.dishSum)}}</td>
            </tr>
          </table>
        </div>
      </Panel>
    </Collapse>
    <Collapse simple class="content" v-if="typeData.solving.length>0">
      <Panel v-for="(item,index1) in typeData.solving" :name="getName(index1,'solving')" :key="getName(index1,'solving')">
        <span style="background-color: #ed4099" class="type">制作中</span>&nbsp&nbsp&nbsp
        预定时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
        订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        客户手机:{{item.customer.cell_phone}}&nbsp&nbsp&nbsp
        客户地址：{{getAddress(item.customer.addresses[0])}}
        <div slot="content">
          <table class="title">
            <tr>
              <th>配送员：</th>
              <td>{{item.deliveryID}}</td>
              <th>配送时间：</th>
              <td>{{item.predictTime}}</td>
              <th>配送距离：</th>
              <td>{{item.distance}}</td>
            </tr>
          </table>
          <table class="menu">
            <tr>
              <th>菜品</th>
              <th>数量</th>
              <th>单价</th>
              <th>合计</th>
            </tr>
            <tr v-for="(dish,index2) in item.orderedDishes.dishes" :id="getName(index2,item.id+'dish')">
              <td>{{dish.name}}</td>
              <td>×{{dish.amount}}</td>
              <td>{{getPrice(dish.price)}}</td>
              <td>{{getPrice(dish.amount*dish.price)}}</td>
            </tr>
            <tr v-for="(discount,index3) in item.orderedDishes.discounts" :id="getName(index3,item.id+'discount')">
              <td>
                <p style="font-weight: bold">{{discount.name}}:</p>
                <p v-for="(dish,index4) in discount.dishes" :id="getName(index4,item.id+'discountName')">{{dish.name}}</p>
              </td>
              <td>×{{discount.amount}}</td>
              <td>{{getPrice(discount.discount_price)}}</td>
              <td>{{getPrice(discount.discount_price*discount.amount)}}</td>
            </tr>
            <tr>
              <td>总计</td>
              <td colspan="3">{{getPrice(item.dishSum)}}</td>
            </tr>
          </table>
        </div>
      </Panel>
    </Collapse>
    <Collapse simple class="content" v-if="typeData.waitingDelivery.length>0">
      <Panel v-for="(item,index1) in typeData.waitingDelivery" :name="getName(index1,'waitingDelivery')" :key="getName(index1,'waitingDelivery')">
        <span style="background-color: #ff9900" class="type">待配送</span>&nbsp&nbsp&nbsp
        预定时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
        订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        客户手机:{{item.customer.cell_phone}}&nbsp&nbsp&nbsp
        客户地址：{{getAddress(item.customer.addresses[0])}}
        <div slot="content">
          <table class="title">
            <tr>
              <th>配送员：</th>
              <td>{{item.deliveryID}}</td>
              <th>配送时间：</th>
              <td>{{item.predictTime}}</td>
              <th>配送距离：</th>
              <td>{{item.distance}}</td>
            </tr>
          </table>
          <table class="menu">
            <tr>
              <th>菜品</th>
              <th>数量</th>
              <th>单价</th>
              <th>合计</th>
            </tr>
            <tr v-for="(dish,index2) in item.orderedDishes.dishes" :id="getName(index2,item.id+'dish')">
              <td>{{dish.name}}</td>
              <td>×{{dish.amount}}</td>
              <td>{{getPrice(dish.price)}}</td>
              <td>{{getPrice(dish.amount*dish.price)}}</td>
            </tr>
            <tr v-for="(discount,index3) in item.orderedDishes.discounts" :id="getName(index3,item.id+'discount')">
              <td>
                <p style="font-weight: bold">{{discount.name}}:</p>
                <p v-for="(dish,index4) in discount.dishes" :id="getName(index4,item.id+'discountName')">{{dish.name}}</p>
              </td>
              <td>×{{discount.amount}}</td>
              <td>{{getPrice(discount.discount_price)}}</td>
              <td>{{getPrice(discount.discount_price*discount.amount)}}</td>
            </tr>
            <tr>
              <td>总计</td>
              <td colspan="3">{{getPrice(item.dishSum)}}</td>
            </tr>
          </table>
        </div>
      </Panel>
    </Collapse>
    <Collapse simple class="content" v-if="typeData.delivering.length>0">
      <Panel v-for="(item,index1) in typeData.delivering" :name="getName(index1,'delivering')" :key="getName(index1,'delivering')">
        <span style="background-color: aquamarine" class="type">配送中</span>&nbsp&nbsp&nbsp
        预定时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
        订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        客户手机:{{item.customer.cell_phone}}&nbsp&nbsp&nbsp
        客户地址：{{getAddress(item.customer.addresses[0])}}
        <div slot="content">
          <table class="title">
            <tr>
              <th>配送员：</th>
              <td>{{item.deliveryID}}</td>
              <th>配送时间：</th>
              <td>{{item.predictTime}}</td>
              <th>配送距离：</th>
              <td>{{item.distance}}</td>
            </tr>
          </table>
          <table class="menu">
            <tr>
              <th>菜品</th>
              <th>数量</th>
              <th>单价</th>
              <th>合计</th>
            </tr>
            <tr v-for="(dish,index2) in item.orderedDishes.dishes" :id="getName(index2,item.id+'dish')">
              <td>{{dish.name}}</td>
              <td>×{{dish.amount}}</td>
              <td>{{getPrice(dish.price)}}</td>
              <td>{{getPrice(dish.amount*dish.price)}}</td>
            </tr>
            <tr v-for="(discount,index3) in item.orderedDishes.discounts" :id="getName(index3,item.id+'discount')">
              <td>
                <p style="font-weight: bold">{{discount.name}}:</p>
                <p v-for="(dish,index4) in discount.dishes" :id="getName(index4,item.id+'discountName')">{{dish.name}}</p>
              </td>
              <td>×{{discount.amount}}</td>
              <td>{{getPrice(discount.discount_price)}}</td>
              <td>{{getPrice(discount.discount_price*discount.amount)}}</td>
            </tr>
            <tr>
              <td>总计</td>
              <td colspan="3">{{getPrice(item.dishSum)}}</td>
            </tr>
          </table>
        </div>
      </Panel>
    </Collapse>
    <Collapse simple class="content" v-if="typeData.sign.length>0">
      <Panel v-for="(item,index1) in typeData.sign" :name="getName(index1,'sign')" :key="getName(index1,'sign')">
        <span style="background-color: #19be6b" class="type">已签收</span>&nbsp&nbsp&nbsp
        预定时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
        订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        客户手机:{{item.customer.cell_phone}}&nbsp&nbsp&nbsp
        客户地址：{{getAddress(item.customer.addresses[0])}}
        <div slot="content">
          <table class="title">
            <tr>
              <th>配送员：</th>
              <td>{{item.deliveryID}}</td>
              <th>配送时间：</th>
              <td>{{item.predictTime}}</td>
              <th>配送距离：</th>
              <td>{{item.distance}}</td>
            </tr>
          </table>
          <table class="menu">
            <tr>
              <th>菜品</th>
              <th>数量</th>
              <th>单价</th>
              <th>合计</th>
            </tr>
            <tr v-for="(dish,index2) in item.orderedDishes.dishes" :id="getName(index2,item.id+'dish')">
              <td>{{dish.name}}</td>
              <td>×{{dish.amount}}</td>
              <td>{{getPrice(dish.price)}}</td>
              <td>{{getPrice(dish.amount*dish.price)}}</td>
            </tr>
            <tr v-for="(discount,index3) in item.orderedDishes.discounts" :id="getName(index3,item.id+'discount')">
              <td>
                <p style="font-weight: bold">{{discount.name}}:</p>
                <p v-for="(dish,index4) in discount.dishes" :id="getName(index4,item.id+'discountName')">{{dish.name}}</p>
              </td>
              <td>×{{discount.amount}}</td>
              <td>{{getPrice(discount.discount_price)}}</td>
              <td>{{getPrice(discount.discount_price*discount.amount)}}</td>
            </tr>
            <tr>
              <td>总计</td>
              <td colspan="3">{{getPrice(item.dishSum)}}</td>
            </tr>
          </table>
        </div>
      </Panel>
    </Collapse>
    <Collapse simple class="content" v-if="typeData.cancel.length>0">
      <Panel v-for="(item,index1) in typeData.cancel" :name="getName(index1,'cancel')" :key="getName(index1,'cancel')">
        <span style="background-color: #c5c8ce" class="type">已取消</span>&nbsp&nbsp&nbsp
        预定时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
        订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        客户手机:{{item.customer.cell_phone}}&nbsp&nbsp&nbsp
        客户地址：{{getAddress(item.customer.addresses[0])}}
        <div slot="content">
          <table class="title">
            <tr>
              <th>配送员：</th>
              <td>{{item.deliveryID}}</td>
              <th>配送时间：</th>
              <td>{{item.predictTime}}</td>
              <th>配送距离：</th>
              <td>{{item.distance}}</td>
            </tr>
          </table>
          <table class="menu">
            <tr>
              <th>菜品</th>
              <th>数量</th>
              <th>单价</th>
              <th>合计</th>
            </tr>
            <tr v-for="(dish,index2) in item.orderedDishes.dishes" :id="getName(index2,item.id+'dish')">
              <td>{{dish.name}}</td>
              <td>×{{dish.amount}}</td>
              <td>{{getPrice(dish.price)}}</td>
              <td>{{getPrice(dish.amount*dish.price)}}</td>
            </tr>
            <tr v-for="(discount,index3) in item.orderedDishes.discounts" :id="getName(index3,item.id+'discount')">
              <td>
                <p style="font-weight: bold">{{discount.name}}:</p>
                <p v-for="(dish,index4) in discount.dishes" :id="getName(index4,item.id+'discountName')">{{dish.name}}</p>
              </td>
              <td>×{{discount.amount}}</td>
              <td>{{getPrice(discount.discount_price)}}</td>
              <td>{{getPrice(discount.discount_price*discount.amount)}}</td>
            </tr>
            <tr>
              <td>总计</td>
              <td colspan="3">{{getPrice(item.dishSum)}}</td>
            </tr>
          </table>
        </div>
      </Panel>
    </Collapse>
  </div>
</div>
</template>

<script>
  import TitleMenu from '../../components/MenuColumn/CanteenMenu'
    export default {
        name: "CalculateStatisticsPage",
      components:{TitleMenu},
      async mounted(){
          var date=new Date()
        var date1=date.getFullYear()+"-"+this.fixNumber(date.getMonth()+1)+"-01"
        var date2=date.getFullYear()+"-"+this.fixNumber(date.getMonth()+1)+"-"+this.fixNumber(date.getDate())
        this.timeInterval=[date1,date2]
        await this.$axios.post('/server/order/canteen/get/time/orders',[sessionStorage.getItem("canteenID"),date1,date2]).then(re=>{
            this.timeData=re.data;
          })
        await this.$axios.post('/server/order/canteen/get/cash/orders',[sessionStorage.getItem("canteenID"),date1,date2]).then(re=>{
            this.cashData=re.data;
          })
        await this.$axios.post('/server/order/canteen/get/type/orders',[sessionStorage.getItem("canteenID"),date1,date2]).then(re=>{
          this.typeData=re.data;
        })
      },
      data(){
          return{
            timeInterval:['2019-02-01','2019-02-03'],
            timeData:'',
            cashData:'',
            typeData:'',
            showTime:true,
            showCash:false,
            showType:false
          }
      },
      methods:{
        async changeTime(t){
          await this.$axios.post('/server/order/canteen/get/time/orders',[sessionStorage.getItem("canteenID"),t[0],t[1]]).then(re=>{
            this.timeData=re.data;
          })
          await this.$axios.post('/server/order/canteen/get/cash/orders',[sessionStorage.getItem("canteenID"),t[0],t[1]]).then(re=>{
            this.cashData=re.data;
          })
          await this.$axios.post('/server/order/canteen/get/type/orders',[sessionStorage.getItem("canteenID"),t[0],t[1]]).then(re=>{
            this.typeData=re.data;
          })
        },
        getName(index, name){
          return name+index;
        },
        getPrice(price){
          return '￥'+price.toFixed(2)
        },
        getTime(date){
          return date.getFullYear()+"-"+this.fixNumber(date.getMonth()+1)+"-"+this.fixNumber(date.getDate())+" "+this.fixNumber(date.getHours())+":"+this.fixNumber(date.getMinutes())+":"+this.fixNumber(date.getSeconds())
        },
        fixNumber(number){
          if(number==0){
            return '00'
          }
          else if(number<10){
            return '0'+number
          }
          else {
            return number
          }
        },
        get8bitString(str){
          var t=(str+"").length
          for(var i=0;i<8-t;i++){
            str='0'+str
          }
          return str
        },
        getAddress(address){
          return address.province+address.city+address.area+address.detail
        },
        time(){
          this.showTime=true;
          this.showCash=false;
          this.showType=false;
        },
        cash(){
          this.showTime=false;
          this.showCash=true;
          this.showType=false;
        },
        type(){
          this.showTime=false;
          this.showCash=false;
          this.showType=true;
        }
      }
    }
</script>

<style scoped>
.Button{
  width: 100px;
}
.dailyStatistics1{
  text-align: center;
  margin-top: 10px;
}

.dailyStatistics1 th{
  width: 100px;
  font-size: 13px;
  background-color: rgb(247,247,247);
  -webkit-text-fill-color: #17233d;
}

.dailyStatistics1 td{
  font-size: 25px;
  background-color: rgba(247,247,247,0.5);
}

.dailyStatistics2{
  margin-left: 15%;
  margin-right: 15%;
  height: 100px;
  text-align: center;
  margin-top: 10px;
}

.dailyStatistics2 th{
  width: 170px;
  font-size: 15px;
  background-color: rgb(247,247,247);
  -webkit-text-fill-color: #17233d;
}

.dailyStatistics2 td{
  font-size: 40px;
  background-color: rgba(247,247,247,0.5);
}

.content{
  text-align: left;
  margin-left: 15%;
  margin-right: 15%;
}

.title{
  margin: 0 auto;
  font-size: 15px;
}

.title td{
  width: 150px;
  -webkit-text-fill-color: #2db7f5;
}

.menu{
  margin: 0 auto;
  font-size: 15px;
  border-left: 1px solid #dcdee2;
  border-right: 1px solid #dcdee2;
}

.menu th{
  text-align: center;
  width: 200px;
  border-bottom: 1px solid #dcdee2;
  border-top: 1px solid #dcdee2;
}

.menu td{
  text-align: center;
  border-bottom: 1px solid #dcdee2;
}

.type{
  -webkit-text-fill-color: white;
  font-size: 12px;
  padding: 5px;
  border-radius: 15px;
}
</style>
