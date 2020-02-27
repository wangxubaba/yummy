<template>
<div>
  <TitleMenu style="height: 200px"></TitleMenu>
  <div style="height: 60px">
    <ButtonGroup style="float: left;margin-left: 15%;padding-top: 15px;">
      <Button type="success" class="Button" @click="timeOrder">按 时 间 排 序</Button>
      <Button type="warning" class="Button" @click="cashOrder">按 金 额 排 序</Button>
      <Button type="info" class="Button" @click="canteenOrder">按 餐 厅 排 序</Button>
    </ButtonGroup>
  </div>
  <div v-if="showTime">
    <h1 style="margin: 15px;text-align: left;margin-left: 17%">我的订单<span style="font-size: 15px;padding-left: 20px">按照时间排序</span></h1>
    <Collapse simple class="content">
      <Panel v-for="(item,index1) in orders1" :name="getName('panel',index1)" :key="index1">
        <span v-if="item.state==='WaitingPay'">
          <span style="background-color: #ed4014" class="type">待支付</span>&nbsp&nbsp&nbsp
          点单时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
        <span v-if="item.state==='WaitingAccept'">
          <span style="background-color: #ed4099" class="type">待接单</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
        <span v-if="item.state==='Making'">
          <span style="background-color: rgb(225,225,128)" class="type">制作中</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
        <span v-if="item.state==='WaitingDelivery'">
          <span style="background-color: #ff9900" class="type">待配送</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
        <span v-if="item.state==='Delivering'">
          <span style="background-color: aquamarine" class="type">配送中</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
          您在&nbsp<Time :time="item.validTimeBegin" :interval="1" style="-webkit-text-fill-color: #2db7f5;font-weight: bold"></Time>&nbsp支付订单，请稍候
        </span>
        <span v-if="item.state==='Arrived'">
          <span style="background-color: #19be6b" class="type">已签收</span>&nbsp&nbsp&nbsp
          签收时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
        <span v-if="item.state==='Cancel'">
          <span style="background-color: #c5c8ce" class="type">已取消</span>&nbsp&nbsp&nbsp
          取消时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
        <span>金额合计：{{getPrice(item.dishSum+item.deliverySum-item.discountSum)}}</span>
        <div slot="content">
          <table class="title">
            <tr>
              <td>
                <span v-if="item.state==='WaitingPay'" style="-webkit-text-fill-color: #ed4014;font-weight: bold">
                  待支付
                </span>
                <span v-if="item.state==='WaitingAccept'" style="-webkit-text-fill-color: #ed4099;font-weight: bold">
                  待接收
                </span>
                <span v-if="item.state==='Making'" style="-webkit-text-fill-color: rgb(225,225,128);font-weight: bold">
                  制作中
                </span>
                <span v-if="item.state==='WaitingDelivery'" style="-webkit-text-fill-color: #ff9900;font-weight: bold">
                  待配送
                </span>
                <span v-if="item.state==='Delivering'" style="-webkit-text-fill-color: aquamarine;font-weight: bold">
                    配送中
                </span>
                <span v-if="item.state==='Arrived'" style="-webkit-text-fill-color: #19be6b;font-weight: bold">
                    已送达
                </span>
                <span v-if="item.state==='Cancel'" style="-webkit-text-fill-color: #c5c8ce;font-weight: bold">
                  已取消
                </span>
              </td>
              <th>联系电话：</th>
              <td>{{customer.cell_phone}}</td>
              <th>合计：</th>
              <td>{{getPrice(item.deliverySum+item.dishSum-item.discountSum)}}</td>
              <th>送餐地址：</th>
              <td>{{item.destination}}</td>
            </tr>
          </table>
          <table class="menu">
            <tr>
              <th>菜品</th>
              <th>数量</th>
              <th>单价</th>
              <th>合计</th>
            </tr>
            <tr v-for="dish in item.orderedDishes.dishes">
              <td>{{dish.name}}</td>
              <td>×{{dish.amount}}</td>
              <td>{{getPrice(dish.price)}}</td>
              <td>{{getPrice(dish.amount*dish.price)}}</td>
            </tr>
            <tr v-for="discount in item.orderedDishes.discounts">
              <td>
                <p style="font-weight: bold">{{discount.name}}:</p>
                <p v-for="dish in discount.dishes">{{dish.name}}</p>
              </td>
              <td>×{{discount.amount}}</td>
              <td>{{getPrice(discount.discount_price)}}</td>
              <td>{{getPrice(discount.discount_price*discount.amount)}}</td>
            </tr>
            <tr>
              <td style="-webkit-text-fill-color: #2db7f5">配送费</td>
              <td colspan="3">{{getPrice(item.deliverySum)}}</td>
            </tr>
            <tr>
              <td style="-webkit-text-fill-color: #2db7f5">菜品总价</td>
              <td colspan="3">{{getPrice(item.dishSum)}}</td>
            </tr>
            <tr>
              <td style="-webkit-text-fill-color: #2db7f5">订单优惠</td>
              <td colspan="3">- {{getPrice(item.discountSum)}}</td>
            </tr>
          </table>
        </div>
      </Panel>
    </Collapse>
  </div>
  <div v-if="showCash">
    <h1 style="margin: 15px;text-align: left;margin-left: 17%">我的订单<span style="font-size: 15px;padding-left: 20px">按照金额排序</span></h1>
    <Collapse simple class="content">
      <Panel v-for="(item,index1) in orders2" :name="getName('panel',index1)" :key="index1">
        <span v-if="item.state==='WaitingPay'">
          <span style="background-color: #ed4014" class="type">待支付</span>&nbsp&nbsp&nbsp
          点单时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
        <span v-if="item.state==='WaitingAccept'">
          <span style="background-color: #ed4099" class="type">待接单</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
        <span v-if="item.state==='Making'">
          <span style="background-color: rgb(225,225,128)" class="type">制作中</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
        <span v-if="item.state==='WaitingDelivery'">
          <span style="background-color: #ff9900" class="type">待配送</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
        <span v-if="item.state==='Delivering'">
          <span style="background-color: aquamarine" class="type">配送中</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
          您在&nbsp<Time :time="item.validTimeBegin" :interval="1" style="-webkit-text-fill-color: #2db7f5;font-weight: bold"></Time>&nbsp支付订单，请稍候
        </span>
        <span v-if="item.state==='Arrived'">
          <span style="background-color: #19be6b" class="type">已签收</span>&nbsp&nbsp&nbsp
          签收时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
        <span v-if="item.state==='Cancel'">
          <span style="background-color: #c5c8ce" class="type">已取消</span>&nbsp&nbsp&nbsp
          取消时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
        <span>金额合计：{{getPrice(item.dishSum+item.deliverySum-item.discountSum)}}</span>
        <div slot="content">
          <table class="title">
            <tr>
              <td>
                <span v-if="item.state==='WaitingPay'" style="-webkit-text-fill-color: #ed4014;font-weight: bold">
                  待支付
                </span>
                <span v-if="item.state==='WaitingAccept'" style="-webkit-text-fill-color: #ed4099;font-weight: bold">
                  待接收
                </span>
                <span v-if="item.state==='Making'" style="-webkit-text-fill-color: rgb(225,225,128);font-weight: bold">
                  制作中
                </span>
                <span v-if="item.state==='WaitingDelivery'" style="-webkit-text-fill-color: #ff9900;font-weight: bold">
                  待配送
                </span>
                <span v-if="item.state==='Delivering'" style="-webkit-text-fill-color: aquamarine;font-weight: bold">
                    配送中
                </span>
                <span v-if="item.state==='Arrived'" style="-webkit-text-fill-color: #19be6b;font-weight: bold">
                    已送达
                </span>
                <span v-if="item.state==='Cancel'" style="-webkit-text-fill-color: #c5c8ce;font-weight: bold">
                  已取消
                </span>
              </td>
              <th>联系电话：</th>
              <td>{{customer.cell_phone}}</td>
              <th>合计：</th>
              <td>{{getPrice(item.deliverySum+item.dishSum-item.discountSum)}}</td>
              <th>送餐地址：</th>
              <td>{{item.destination}}</td>
            </tr>
          </table>
          <table class="menu">
            <tr>
              <th>菜品</th>
              <th>数量</th>
              <th>单价</th>
              <th>合计</th>
            </tr>
            <tr v-for="dish in item.orderedDishes.dishes">
              <td>{{dish.name}}</td>
              <td>×{{dish.amount}}</td>
              <td>{{getPrice(dish.price)}}</td>
              <td>{{getPrice(dish.amount*dish.price)}}</td>
            </tr>
            <tr v-for="discount in item.orderedDishes.discounts">
              <td>
                <p style="font-weight: bold">{{discount.name}}:</p>
                <p v-for="dish in discount.dishes">{{dish.name}}</p>
              </td>
              <td>×{{discount.amount}}</td>
              <td>{{getPrice(discount.discount_price)}}</td>
              <td>{{getPrice(discount.discount_price*discount.amount)}}</td>
            </tr>
            <tr>
              <td style="-webkit-text-fill-color: #2db7f5">配送费</td>
              <td colspan="3">{{getPrice(item.deliverySum)}}</td>
            </tr>
            <tr>
              <td style="-webkit-text-fill-color: #2db7f5">菜品总价</td>
              <td colspan="3">{{getPrice(item.dishSum)}}</td>
            </tr>
            <tr>
              <td style="-webkit-text-fill-color: #2db7f5">订单优惠</td>
              <td colspan="3">- {{getPrice(item.discountSum)}}</td>
            </tr>
          </table>
        </div>
      </Panel>
    </Collapse>
  </div>
  <div v-if="showCanteen">
    <h1 style="margin: 15px;text-align: left;margin-left: 17%">我的订单<span style="font-size: 15px;padding-left: 20px">按照餐厅分类</span></h1>
    <div v-for="canteen in orders3" style="margin-bottom: 20px">
      <p style="text-align: left;margin-left: 20%;font-size: 15px;font-weight: bold;">{{canteen[0].canteen.name}}</p>
      <Collapse simple class="content">
        <Panel v-for="(item,index1) in canteen" :name="getName('panel',index1)" :key="index1">
        <span v-if="item.state==='WaitingPay'">
          <span style="background-color: #ed4014" class="type">待支付</span>&nbsp&nbsp&nbsp
          点单时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
          <span v-if="item.state==='WaitingAccept'">
          <span style="background-color: #ed4099" class="type">待接单</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
          <span v-if="item.state==='Making'">
          <span style="background-color: rgb(225,225,128)" class="type">制作中</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
          <span v-if="item.state==='WaitingDelivery'">
          <span style="background-color: #ff9900" class="type">待配送</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
          <span v-if="item.state==='Delivering'">
          <span style="background-color: aquamarine" class="type">配送中</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
          您在&nbsp<Time :time="item.validTimeBegin" :interval="1" style="-webkit-text-fill-color: #2db7f5;font-weight: bold"></Time>&nbsp支付订单，请稍候
        </span>
          <span v-if="item.state==='Arrived'">
          <span style="background-color: #19be6b" class="type">已签收</span>&nbsp&nbsp&nbsp
          签收时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
          <span v-if="item.state==='Cancel'">
          <span style="background-color: #c5c8ce" class="type">已取消</span>&nbsp&nbsp&nbsp
          取消时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
        </span>
          <span>金额合计：{{getPrice(item.dishSum+item.deliverySum-item.discountSum)}}</span>
          <div slot="content">
            <table class="title">
              <tr>
                <td>
                <span v-if="item.state==='WaitingPay'" style="-webkit-text-fill-color: #ed4014;font-weight: bold">
                  待支付
                </span>
                  <span v-if="item.state==='WaitingAccept'" style="-webkit-text-fill-color: #ed4099;font-weight: bold">
                  待接收
                </span>
                  <span v-if="item.state==='Making'" style="-webkit-text-fill-color: rgb(225,225,128);font-weight: bold">
                  制作中
                </span>
                  <span v-if="item.state==='WaitingDelivery'" style="-webkit-text-fill-color: #ff9900;font-weight: bold">
                  待配送
                </span>
                  <span v-if="item.state==='Delivering'" style="-webkit-text-fill-color: aquamarine;font-weight: bold">
                    配送中
                </span>
                  <span v-if="item.state==='Arrived'" style="-webkit-text-fill-color: #19be6b;font-weight: bold">
                    已送达
                </span>
                  <span v-if="item.state==='Cancel'" style="-webkit-text-fill-color: #c5c8ce;font-weight: bold">
                  已取消
                </span>
                </td>
                <th>联系电话：</th>
                <td>{{customer.cell_phone}}</td>
                <th>合计：</th>
                <td>{{getPrice(item.deliverySum+item.dishSum-item.discountSum)}}</td>
                <th>送餐地址：</th>
                <td>{{item.destination}}</td>
              </tr>
            </table>
            <table class="menu">
              <tr>
                <th>菜品</th>
                <th>数量</th>
                <th>单价</th>
                <th>合计</th>
              </tr>
              <tr v-for="dish in item.orderedDishes.dishes">
                <td>{{dish.name}}</td>
                <td>×{{dish.amount}}</td>
                <td>{{getPrice(dish.price)}}</td>
                <td>{{getPrice(dish.amount*dish.price)}}</td>
              </tr>
              <tr v-for="discount in item.orderedDishes.discounts">
                <td>
                  <p style="font-weight: bold">{{discount.name}}:</p>
                  <p v-for="dish in discount.dishes">{{dish.name}}</p>
                </td>
                <td>×{{discount.amount}}</td>
                <td>{{getPrice(discount.discount_price)}}</td>
                <td>{{getPrice(discount.discount_price*discount.amount)}}</td>
              </tr>
              <tr>
                <td style="-webkit-text-fill-color: #2db7f5">配送费</td>
                <td colspan="3">{{getPrice(item.deliverySum)}}</td>
              </tr>
              <tr>
                <td style="-webkit-text-fill-color: #2db7f5">菜品总价</td>
                <td colspan="3">{{getPrice(item.dishSum)}}</td>
              </tr>
              <tr>
                <td style="-webkit-text-fill-color: #2db7f5">订单优惠</td>
                <td colspan="3">- {{getPrice(item.discountSum)}}</td>
              </tr>
            </table>
          </div>
        </Panel>
      </Collapse>
    </div>

  </div>
</div>
</template>

<script>
  import TitleMenu from '../../components/MenuColumn/CustomerMenu'
    export default {
        name: "MyStatisticsPage",
      components:{TitleMenu},
      async mounted(){
        await this.$axios.post('/server/register/get/customer',{email:sessionStorage.getItem("customerID")}).then(re=>{
          this.customer=re.data;
        })
        await this.$axios.post('/server/order/customer/get/orders',{email:sessionStorage.getItem("customerID")}).then(re=>{
          this.orders1=re.data;
        })
        await this.$axios.post('/server/order/customer/get/orders/cash',{email:sessionStorage.getItem("customerID")}).then(re=>{
          this.orders2=re.data
        })
        await this.$axios.post('/server/order/customer/get/orders/canteen',{email:sessionStorage.getItem("customerID")}).then(re=>{
          this.orders3=re.data
        })
        this.interval=setInterval(this.refreshOrder,5000)
      },
      data(){
          return{
            showTime:true,
            showCash:false,
            showCanteen:false,
            customer:'',
            orders1:[],
            orders2:[],
            orders3:[],
            interval:Number
          }
      },
      methods:{
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
        async refreshOrder(){
          await this.$axios.post('/server/order/customer/get/orders',{email:sessionStorage.getItem("customerID")}).then(re=>{
            this.orders1=re.data;
          })
          await this.$axios.post('/server/order/customer/get/orders/cash',{email:sessionStorage.getItem("customerID")}).then(re=>{
            this.orders2=re.data;
          })
          await this.$axios.post('/server/order/customer/get/orders/canteen',{email:sessionStorage.getItem("customerID")}).then(re=>{
            this.orders3=re.data
          })
        },
        timeOrder(){
          this.showTime=true
          this.showCash=false
          this.showCanteen=false
        },
        cashOrder(){
          this.showTime=false
          this.showCash=true
          this.showCanteen=false
        },
        canteenOrder(){
          this.showTime=false
          this.showCash=false
          this.showCanteen=true
        }
      },
      destroyed() {
        clearInterval(this.interval);
      },
    }
</script>

<style scoped>
  .content{
    text-align: left;
    margin-left: 17%;
    margin-right: 17%;
    font-size: 15px;
  }

  .title td{
    min-width: 100px;
    -webkit-text-fill-color: #2db7f5;
  }

  .title th{
    min-width: 80px;
    text-align: right;
  }

  .menu{
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

  .Button{
    width: 150px;
  }
</style>
