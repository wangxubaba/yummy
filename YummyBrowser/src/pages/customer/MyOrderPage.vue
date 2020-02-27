<template>
<div>
  <Menu style="height: 200px"></Menu>
  <div id="orders">
    <h1 style="margin: 15px;text-align: left;margin-left: 17%">我的订单</h1>
    <Collapse simple class="content">
      <Panel v-for="(item,index1) in orders" :name="getName('panel',index1)" :key="index1">
        <span v-if="item.state==='WaitingPay'">
          <span style="background-color: #ed4014" class="type">待支付</span>&nbsp&nbsp&nbsp
          点单时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
          您在&nbsp<Time :time="item.validTimeBegin" :interval="1" style="-webkit-text-fill-color: #2db7f5;font-weight: bold"></Time>&nbsp确认菜单，请在{{biggestInterval}}分钟内进行支付
        </span>
        <span v-if="item.state==='WaitingAccept'">
          <span style="background-color: #ed4099" class="type">待接单</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
          您在&nbsp<Time :time="item.validTimeBegin" :interval="1" style="-webkit-text-fill-color: #2db7f5;font-weight: bold"></Time>&nbsp支付订单，请稍候
        </span>
        <span v-if="item.state==='Making'">
          <span style="background-color: rgb(225,225,128)" class="type">制作中</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
          您在&nbsp<Time :time="item.validTimeBegin" :interval="1" style="-webkit-text-fill-color: #2db7f5;font-weight: bold"></Time>&nbsp支付订单，请稍候
        </span>
        <span v-if="item.state==='WaitingDelivery'">
          <span style="background-color: #ff9900" class="type">待配送</span>&nbsp&nbsp&nbsp
          支付时间：{{getTime(new Date(item.validTimeBegin))}}&nbsp&nbsp&nbsp
          订单编号：{{get8bitString(item.orderID)}}&nbsp&nbsp&nbsp
          您在&nbsp<Time :time="item.validTimeBegin" :interval="1" style="-webkit-text-fill-color: #2db7f5;font-weight: bold"></Time>&nbsp支付订单，请稍候
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
        <div slot="content">
          <table class="title">
            <tr>
              <td>
                <span v-if="item.state==='WaitingPay'">
                  <Button type="success" style="-webkit-text-fill-color: white" @click="payBill(index1)">支付订单</Button>
                  <Button type="error" style="-webkit-text-fill-color: white" @click="cancelBill(index1)">取消订单</Button>
                </span>
                <span v-if="item.state==='WaitingAccept'">
                  <Button type="text" style="-webkit-text-fill-color: white;background-color: #ed4099" @click="cancelBill(index1)">取消订单</Button>
                </span>
                <span v-if="item.state==='Making'">
                    <Button type="text" style="-webkit-text-fill-color: white;background-color: rgb(225,225,128)" @click="cancelBill(index1)">取消订单</Button>
                </span>
                <span v-if="item.state==='WaitingDelivery'">
                    <Button type="text" style="-webkit-text-fill-color: white;background-color: #ff9900" @click="cancelBill(index1)">取消订单</Button>
                </span>
                <span v-if="item.state==='Delivering'">
                    <Button type="text" style="-webkit-text-fill-color: white;background-color: aquamarine" @click="cancelBill(index1)">取消订单</Button>
                  <Button type="text" style="-webkit-text-fill-color: white;background-color: aquamarine" @click="signOrder(index1)">接收订单</Button>
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
</template>

<script>
  import Menu from'../../components/MenuColumn/CustomerMenu'
    export default {
        name: "MyOrderPage",
      components:{Menu},
      data(){
          return{
            orders:[
              {
                canteenID: 7,
                customerID: "wx8339459@163.com",
                deliveryID: 156894,
                deliverySum: 7.8,
                destination: "江苏南京鼓楼区南京大学鼓楼校区",
                dishSum: 316,
                orderID: 1,
                orderedDishes: {
                  dishes: [
                    {
                      amount: 2,
                      id: 20,
                      name: "米饭",
                      price: 1,
                      type: "主食"
                    },
                    {
                      amount: 3,
                      id: 16,
                      name: "清炒土豆丝",
                      price: 12,
                      type: "凉菜",
                    },
                    {
                      amount: 1,
                      id: 19,
                      name: "剁椒鱼头",
                      price: 78,
                      type: "热菜"
                    }
                  ],
                  discounts: [
                    {
                      amount: 1,
                      discount_price: 30,
                      name: "鱼香肉丝套餐",
                      id: 2,
                      dishes:[
                        {
                          amount: 3,
                          id: 16,
                          name: "清炒土豆丝",
                          price: 12,
                          type: "凉菜",
                        },
                        {
                          amount: 1,
                          id: 19,
                          name: "剁椒鱼头",
                          price: 78,
                          type: "热菜"
                        }
                      ]
                    }
                  ]},
                state: "WaitingPay",
                validTimeBegin: "Wed Feb 13 2019 17:10:51 GMT+0800"
              }
            ],
            customer:{
              id:'001001',
              name: '王旭',
              email: 'wx8339459@163.com',
              sex: '男',
              default_address:'浙江大学',
              cell_phone:'15952023319',
              password:'',
              rank:'白金',
              credits:260,
              addresses:[]
            },
            biggestInterval:2,
            interval:Number
          }
      },
      async mounted(){
        await this.$axios.post('/server/register/get/customer',{email:sessionStorage.getItem("customerID")}).then(re=>{
          this.customer=re.data;
        })
        await this.$axios.post('/server/order/customer/get/orders',{email:sessionStorage.getItem("customerID")}).then(re=>{
          this.orders=re.data;
        })
        this.interval=setInterval(this.refreshOrder,5000)
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
        stillValid(date){
          var interval=((new Date()).getTime()-date.getTime())*1.0/1000/60
          if(interval>this.biggestInterval){
            return false
          }
          else{
            return true
          }
        },
        get8bitString(str){
          var t=(str+"").length
          for(var i=0;i<8-t;i++){
            str='0'+str
          }
          return str
        },
        payBill(index){
          this.$Modal.confirm({
            title: '支付订单',
            content:'<span>您的账户：'+this.customer.pay+'</span><br>' +
              '<span>支付金额&nbsp&nbsp'+this.getPrice(this.orders[index].dishSum+this.orders[index].deliverySum-this.orders[index].discountSum)+'元</span><br>' +
              '<span>支付密码：<input type="password" id="payPass" /></span><br>'+
              '<span style="font-size: 10px;-webkit-text-fill-color: #ed4014">您可以在修改信息页面修改您的账户</span>',
            okText:'确认',
            cancelText:'取消',
            onOk: () => {
              var balance={
                account:this.customer.pay,
                password: document.getElementById("payPass").value
              }
              var order={
                info:this.orders[index],
                balance:balance
              }
              this.$axios.post('/server/order/customer/pay',order).then(re=>{
                if(re.data==='PaySuccess'){
                  this.$Message.success('您已支付成功，请等待商家接单！')
                  this.refreshOrder()
                }
                else{
                  var title=''
                  var content=''
                  if(re.data==='BalanceNotEnough'){
                    title='您的账户余额不足'
                    content='请您先进行账户充值再支付！'
                  }
                  else if(re.data==='WrongAccount'){
                    title='不存在该账户'
                    content='请您核实自己的账户后到个人中心修改问题账户！'
                  }
                  else if(re.data==='WrongPassword'){
                    title='账户密码错误'
                    content='抱歉，您的密码填写错误，请重新进行支付！'
                  }
                  this.$Message.error(title+','+content)
                }
              })
            },
            onCancel: () => {

            }
          });
        },
        async refreshOrder(){
          await this.$axios.post('/server/order/customer/get/orders',{email:sessionStorage.getItem("customerID")}).then(re=>{
            this.orders=re.data;
          })
        },
        async cancelBill(index){
          var state=this.orders[index].state
          var stateStr=''
          if(state==='WaitingPay'){
            stateStr='待支付'
          }
          else if(state==='WaitingAccept'){
            stateStr='待接单'
          }
          else if(state==='Making'){
            stateStr='制作中'
          }
          else if(state==='WaitingDelivery'){
            stateStr='待配送'
          }
          else if(state==='Delivering'){
            stateStr='配送中'
          }
          var calculateReturnMoney=0;
          await this.$axios.post('/server/order/customer/return/money',this.orders[index]).then(re=>{
            calculateReturnMoney=parseFloat(re.data)
          })
          this.$Modal.confirm({
            title:'取消订单',
            content:'<p>您的订单处于'+stateStr+'状态</p><br>' +
              '<p>如若取消，我们只能返回您￥'+calculateReturnMoney.toFixed(2)+'元</p>',
            okText:'确定',
            cancelText:'取消',
            onOk: () =>{
              this.$axios.post('/server/order/customer/cancel',this.orders[index]).then(re=>{
                this.refreshOrder()
                this.$Message.success('您的退款已到账！')
              })
            }
          })
        },
        signOrder(index){
          var data=this.orders[index]
          this.$axios.post('/server/order/canteen/change/order/state',data).then(re=>{
            this.refreshOrder()
          })
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
</style>
