<template>
    <div>
      <Menu style="height: 200px"></Menu>
      <div style="height: 60px">
        <ButtonGroup style="float: left;margin-left: 15%;padding-top: 15px;">
          <Button type="warning" class="Button" @click="notPay">未 支 付</Button>
          <Button type="success" class="Button" @click="pay">已 支 付</Button>
        </ButtonGroup>
      </div>


      <div class="contents" v-if="showNotPay&&notPayed.length>0">
        <Collapse simple class="content">
          <Panel v-for="(order,index1) in notPayed" :name="getName(index1,'')" :key="getName(index1,'')">
            <span style="background-color: #ff9900" class="type">未支付</span>&nbsp&nbsp&nbsp
            订单日期：{{getTime(new Date(order.ordersDate))}}&nbsp&nbsp&nbsp
            菜品合计：{{getPrice(order.dishSum)}}&nbsp&nbsp&nbsp
            配送合计：{{getPrice(order.deliverySum)}}&nbsp&nbsp&nbsp
            优惠合计：{{getPrice(order.discountSum)}}&nbsp&nbsp&nbsp
            转账金额：{{getPrice(order.chargeAmount)}}&nbsp&nbsp&nbsp
            <div slot="content">
              <table>
                <tr>
                  <th>菜品合计：</th>
                  <td>{{getPrice(order.dishSum)}}</td>
                  <td rowspan="4"><Button type="warning" @click="payOrder(index1)">转 账</Button></td>
                </tr>
                <tr>
                  <th>优惠合计：</th>
                  <td>{{getPrice(order.discountSum)}}</td>
                </tr>
                <tr>
                  <th>配送合计：</th>
                  <td>{{getPrice(order.deliverySum)}}</td>
                  <td></td>
                </tr>
                <tr>
                  <th>转账金额：</th>
                  <td>{{getPrice(order.chargeAmount)}}</td>
                  <td></td>
                </tr>
              </table>
            </div>
          </Panel>
        </Collapse>
      </div>
      <div v-if="showNotPay&&notPayed.length==0" style="float: left;background-color: rgb(247,247,247);height: 100px;margin-left: 15%;width: 70%;font-size: 15px;font-weight: bold;padding-top: 40px">
        暂无未支付订单
      </div>

      <div class="contents" v-if="showPay">
        <Collapse simple class="content">
          <Panel v-for="(order,index1) in payed" :name="getName(index1,'')" :key="getName(index1,'')">
            <span style="background-color: #19be6b" class="type">已支付</span>&nbsp&nbsp&nbsp
            订单日期：{{getTime(new Date(order.ordersDate))}}&nbsp&nbsp&nbsp
            菜品合计：{{getPrice(order.dishSum)}}&nbsp&nbsp&nbsp
            配送合计：{{getPrice(order.deliverySum)}}&nbsp&nbsp&nbsp
            优惠合计：{{getPrice(order.discountSum)}}&nbsp&nbsp&nbsp
            转账金额：{{getPrice(order.chargeAmount)}}&nbsp&nbsp&nbsp
            <div slot="content">
              <table>
                <tr>
                  <th>菜品合计：</th>
                  <td>{{getPrice(order.dishSum)}}</td>
                  <td rowspan="5"><p style="-webkit-text-fill-color: #19be6b;font-weight: bold;font-size: 13px;">已支付</p></td>
                </tr>
                <tr>
                  <th>优惠合计：</th>
                  <td>{{getPrice(order.discountSum)}}</td>
                </tr>
                <tr>
                <th>配送合计：</th>
                <td>{{getPrice(order.deliverySum)}}</td>
                <td></td>
                </tr>
                <tr>
                  <th>转账金额：</th>
                  <td>{{getPrice(order.chargeAmount)}}</td>
                  <td></td>
                </tr>
                <tr>
                  <th>支付时间：</th>
                  <td>{{getTime(new Date(order.payDate))}}</td>
                  <td></td>
                </tr>
              </table>
            </div>
          </Panel>
        </Collapse>
      </div>
      <div v-if="showPay&&payed.length==0" style="float: left;background-color: rgb(247,247,247);height: 100px;margin-left: 15%;width: 70%;font-size: 15px;font-weight: bold;padding-top: 40px">
        暂无已支付订单
      </div>
    </div>
</template>

<script>
  import Menu from '../../components/MenuColumn/ManagerMenu'
    export default {
        name: "PayCanteenPage",
      components:{Menu},
      async mounted(){
        this.initial()
      },
      data(){
          return{
            payed:[],
            notPayed:[],
            showPay:false,
            showNotPay:true
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
          return date.getFullYear()+"-"+this.fixNumber(date.getMonth()+1)+"-"+this.fixNumber(date.getDate())
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
        notPay(){
          this.showPay=false;
          this.showNotPay=true;
        },
        pay(){
          this.showPay=true;
          this.showNotPay=false
        },
        async payOrder(index){
          await this.$axios.post('/server/manage/pay',this.notPayed[index]).then(re=>{
            if(re.data==='PaySuccess'){
              this.$Message.success('转账成功')
            }
            else {
              this.$Message.error('转账失败')
            }
          })
          await this.initial()
        },
        async initial(){
          await this.$axios.post('/server/manage/get/unpayed',{}).then(re=>{
            this.notPayed=re.data;
            console.log(this.notPayed)
          })
          await this.$axios.post('/server/manage/get/payed',{}).then(re=>{
            this.payed=re.data;
            console.log(this.payed)
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
    font-size: 13px;
  }

  .content td{
    width: 200px;
    text-align: center;
  }

  .type{
    -webkit-text-fill-color: white;
    font-size: 12px;
    padding: 5px;
    border-radius: 15px;
  }

  .Button{
    width: 120px;
  }
</style>
