<template>
<div>
  <!--标题头-->
  <Menu style="height: 200px"></Menu>

  <div id="canteen" v-if="showCanteen">
    <div class="sketch" v-for="(item,index1) in canteens" :id="getName(index1,'canteen')" @click="takeOrder(index1)">
      <div style="float: left">
        <img v-if="item.type=='汉餐'" src="../../assets/canteen_avatar1.png" style="width: 100px;height: 100px"/>
        <img v-if="item.type=='回餐'" src="../../assets/canteen_avatar2.png" style="width: 100px;height: 100px"/>
        <img v-if="item.type=='西餐'" src="../../assets/canteen_avatar3.png" style="width: 100px;height: 100px"/>
        <img v-if="item.type=='快餐'" src="../../assets/canteen_avatar4.png" style="width: 100px;height: 100px"/>
        <img v-if="item.type=='其他'" src="../../assets/canteen_avatar5.png" style="width: 100px;height: 100px"/>
      </div>
      <div style="float: left;text-align: left;margin-left: 20px;padding-top: 10px;">
        <p style="font-size: 15px;font-weight: bold;margin-bottom: 10px">{{item.name}}</p>
        <p>据您：<span style="-webkit-text-fill-color: #ed4014;font-weight: bold">{{distances[index1]}}</span>公里</p>
        <p>配送：<span style="-webkit-text-fill-color: #ed4014;font-weight: bold">{{getPrice(distances[index1]*deliverPricePerKM)}}</span>元</p>
      </div>
    </div>
  </div>
  <div id="detail" v-if="showDetail">
    <div id="dishes">
      <Tabs value="name1">
        <TabPane label="单点菜品" name="name1" style="padding-left: 13px">
          <div class="type" v-for="(item,index2) in canteen.menu.types" :id="getName(index2,'type')"  >
            <p style="text-align: left;font-size: 15px;font-weight: bold">{{item.name}}</p>
            <div class="dish" v-for="(dish,index3) in item.dishes" :id="getName('dish'+index3,'type'+index2)">
              <div style="float: left">
                <img v-if="canteen.type=='汉餐'" src="../../assets/canteen_avatar1.png" style="width: 100px;height: 100px"/>
                <img v-if="canteen.type=='回餐'" src="../../assets/canteen_avatar2.png" style="width: 100px;height: 100px"/>
                <img v-if="canteen.type=='西餐'" src="../../assets/canteen_avatar3.png" style="width: 100px;height: 100px"/>
                <img v-if="canteen.type=='快餐'" src="../../assets/canteen_avatar4.png" style="width: 100px;height: 100px"/>
                <img v-if="canteen.type=='其他'" src="../../assets/canteen_avatar5.png" style="width: 100px;height: 100px"/>
              </div>
              <div style="float: left;text-align: left;margin-left: 20px;padding-top: 10px;">
                <p style="font-size: 15px;font-weight: bold;margin-bottom: 10px">{{dish.name}}</p>
                <p>单价：<span style="-webkit-text-fill-color: #ed4014;font-weight: bold">{{getPrice(dish.price)}}</span>元</p>
                <p>数量：<InputNumber style="width: 50px" :min="0" v-model="ordered.types[index2].dishes[index3]"></InputNumber>个</p>
              </div>
            </div>
          </div>
        </TabPane>
        <TabPane label="实惠套餐" name="name2">
          <div class="discount" v-for="(item,index4) in canteen.menu.dishDiscount" :id="getName(index4,'discount')">
            <p style="text-align:left;font-size: 15px;font-weight: bold;padding-left: 15px">{{item.name}}</p>
            <div style="width: 40%;float: left">
              <img src="../../assets/canteen_avatar1.png" style="width: 100px"/>
            </div>
            <div style="width: 60%;float: left">
              <table style="margin: 0 auto">
                <tr>
                  <th>菜品</th>
                  <th>单价</th>
                  <th>数量</th>
                </tr>
                <tr v-for="(dish,index5) in item.dishes">
                  <td>{{dish.name}}</td>
                  <td>{{getPrice(dish.price)}}</td>
                  <td>{{dish.total_amount}}</td>
                </tr>
                <tr>
                  <td colspan="3" style="font-weight: bold">套餐价格：{{getPrice(item.discount_price)}}</td>
                </tr>
              </table>
            </div>
            <p>
              选购：
              <InputNumber :min="0" v-model="ordered.discounts[index4]" style="width: 50px"></InputNumber>份
            </p>
          </div>
        </TabPane>
      </Tabs>
    </div>
    <div id="orderedDishes">
      <h2 style="margin-bottom: 5px">我的订单</h2>
      <p style="font-size: 15px;text-align: left;padding-left: 29px" v-if="customer.sex=='男'">{{customer.name}}先生 {{customer.cell_phone}}</p>
      <p style="font-size: 15px;text-align: left;padding-left: 29px" v-else>{{customer.name}}女士 {{customer.cell_phone}}</p>
      <span style="text-align: left;font-size: 15px;padding-left: 10px">目标地址：</span>
      <Select v-model="deliverAddress" @on-change="changeAddress" style="width: 70%">
        <Option v-for="(item,index) in customer.addresses" :value="index" :key="index">{{item.province+item.city+item.detail}}</Option>
      </Select>
      <p style="font-size: 15px;text-align: left;padding-left: 29px" >配送价格：<span style="-webkit-text-fill-color: #2db7f5">{{getPrice(deliverPrice)}}</span></p>
      <Divider></Divider>
      <table style="margin: 0 auto">
        <div>
          <tr>
            <th>名称</th>
            <th>单价</th>
            <th>数量</th>
            <th>总价</th>
          </tr>
        </div>
        <div v-for="(item, index5) in canteen.menu.types">
           <tr v-for="(dish,index6) in item.dishes" v-if="ordered.types[index5].dishes[index6]!=0">
             <td>{{dish.name}}</td>
             <td>{{getPrice(dish.price)}}</td>
             <td><InputNumber :min="0" style="width: 50px" v-model="ordered.types[index5].dishes[index6]"></InputNumber></td>
             <td>{{getPrice(dish.price*ordered.types[index5].dishes[index6])}}</td>
           </tr>
        </div>
        <div v-for="(item, index7) in canteen.menu.dishDiscount">
          <tr v-if="ordered.discounts[index7]!=0">
            <td>{{item.name}}</td>
            <td>{{getPrice(item.discount_price)}}</td>
            <td><InputNumber :min="0" style="width: 50px" v-model="ordered.discounts[index7]"></InputNumber></td>
            <td>{{getPrice(item.discount_price*ordered.discounts[index7])}}</td>
          </tr>
        </div>
        <div>
          <tr>
            <td></td>
            <td style="font-weight: bold">菜品合计</td>
            <td style="font-weight: bold;-webkit-text-fill-color: #2db7f5">{{calculateDishSum()}}</td>
            <td></td>
          </tr>
        </div>
      </table>
      <Divider></Divider>
      <p style="font-size: 18px;font-weight: bold;-webkit-text-fill-color: #2db7f5;margin-bottom: 5px">合计：{{calculateTotalPay()}}</p>
      <Button type="primary" style="width: 80%" @click="pay" :disabled="confirmButton">确 认 菜 单</Button>
    </div>
  </div>
</div>
</template>

<script>
  import Menu from '../../components/MenuColumn/CustomerMenu'
    export default {
        name: "SearchFoodPage",
      components:{Menu},
      async mounted(){
        await this.$axios.post('/server/order/get/near/canteens',{email:sessionStorage.getItem("customerID")}).then(re=>{
            this.canteens=re.data
          })

        await this.$axios.post('/server/register/get/customer',{email:sessionStorage.getItem("customerID")}).then(re=>{
          this.customer=re.data;
        })

        var address2=this.customer.addresses[0]
        for(var i=0;i<this.canteens.length;i++){
          var address1={
            province:this.canteens[i].province,
            city:this.canteens[i].city,
            area:this.canteens[i].area,
            detail:this.canteens[i].address,
            lat:this.canteens[i].lat,
            lng:this.canteens[i].lng
          }

          var distance;
          await this.$axios.post('/server/map/calculate/distance',[address1,address2]).then(re=>{
            distance=parseFloat(re.data).toFixed(2)
          })
          this.distances.push(distance)
        }

      },
      data(){
          return{
            deliverPricePerKM:3,
            showCanteen:true,
            showDetail:false,
            canteens:'',
            canteen:'',
            canteenIndex:'',
            ordered:{
              types:[],
              discounts: []
            },
            customer:'',
            deliverAddress:'',
            distances:[],
            confirmButton:false,
            deliverPrice:'',
          }
      },
      methods:{
          getName(index,name){
            return name+index;
          },
        getPrice(price){
            return '￥'+price.toFixed(2)
        },
        takeOrder(name){
          this.canteen=this.canteens[name]
          this.canteenIndex=name
          var typeArray=[]
          for(var i=0;i<this.canteen.menu.types.length;i++){
            var dishes=[]
            for(var j=0;j<this.canteen.menu.types[i].dishes.length;j++){
              dishes.push(0)
            }
            var tempType={dishes:dishes}
            typeArray.push(tempType)
          }

          var discountArray=[]
          for(var i=0;i<this.canteen.menu.dishDiscount.length;i++){
            discountArray.push(0)
          }
          this.ordered.types=typeArray
          this.ordered.discounts=discountArray
          var address=this.customer.addresses[0]
          this.deliverAddress=0
          this.showCanteen=false
          this.showDetail=true
          this.deliverPrice=this.distances[this.canteenIndex]*this.deliverPricePerKM

          if((this.distances[this.canteenIndex]==1000)||(this.distances[this.canteenIndex]>10)){
            this.$Message.error('抱歉，您输入的地址不在配送范围内')
            this.confirmButton=true
            this.deliverPrice=0
          }
        },
        calculateDishSum(){
            var price=0;
            var i=0;
            for(i;i<this.canteen.menu.types.length;i++){
              var j=0;
              var temp_type=this.canteen.menu.types[i];
              for(j;j<temp_type.dishes.length;j++){
                price=price+temp_type.dishes[j].price*this.ordered.types[i].dishes[j];
              }
            }

            i=0;
            for(i;i<this.canteen.menu.dishDiscount.length;i++){
              price=price+this.canteen.menu.dishDiscount[i].discount_price*this.ordered.discounts[i];
            }

            return this.getPrice(price)
        },
        calculateTotalPay(){
          var price=0;
          var i=0;
          for(i;i<this.canteen.menu.types.length;i++){
            var j=0;
            var temp_type=this.canteen.menu.types[i];
            for(j;j<temp_type.dishes.length;j++){
              price=price+temp_type.dishes[j].price*this.ordered.types[i].dishes[j];
            }
          }

          i=0;
          for(i;i<this.canteen.menu.dishDiscount.length;i++){
            price=price+this.canteen.menu.dishDiscount[i].discount_price*this.ordered.discounts[i];
          }

          price=price+this.deliverPrice;
          return this.getPrice(price)
        },
        changeAddress(t){
          var address1={
            province:this.canteens[this.canteenIndex].province,
            city:this.canteens[this.canteenIndex].city,
            area:this.canteens[this.canteenIndex].area,
            detail:this.canteens[this.canteenIndex].address,
            lat:this.canteens[this.canteenIndex].lat,
            lng:this.canteens[this.canteenIndex].lng
          }
          var address2=this.customer.addresses[t]
          this.$axios.post('/server/map/calculate/distance',[address1,address2]).then(re=>{
            if((re.data==1000)||(re.data>10)){
              this.$Message.error('抱歉，您输入的地址不在配送范围内')
              this.confirmButton=true
              this.deliverPrice=0
            }
            else{
              this.confirmButton=false
              this.distances[this.canteenIndex]=re.data
              this.deliverPrice=this.distances[this.canteenIndex]*this.deliverPricePerKM
            }
          })
        },
        pay(){
          var dishes=[]
          for(var i=0;i<this.ordered.types.length;i++){
            var temp=this.ordered.types[i].dishes
            for(var j=0;j<temp.length;j++){
              if(temp[j]>0){
                var tempDish=this.canteen.menu.types[i].dishes[j]
                tempDish.amount=temp[j]
                dishes.push(tempDish)
              }
            }
          }

          var discounts=[]
          for(var i=0;i<this.ordered.discounts.length;i++){
            if(this.ordered.discounts[i]>0){
              var tempDiscount=this.canteen.menu.dishDiscount[i]
              tempDiscount.amount=this.ordered.discounts[i]
              discounts.push(tempDiscount)
            }
          }

            var orderedDishes={
              dishes:dishes,
              discounts:discounts
            }
            var order={
              canteenID:this.canteen.id,
              customerID:this.customer.email,
              state:'WaitingPay',
              destination:this.customer.addresses[this.deliverAddress].area+this.customer.addresses[this.deliverAddress].detail,
              deliveryID:'156894',
              dishSum:this.calculateDishSum().substr(1),
              deliverySum:this.deliverPrice,
              orderedDishes:orderedDishes
            }

            this.$axios.post('/server/order/finish/order',order).then(re=>{
              var array=re.data
              if(array.length==0){
                this.$router.push('/customer/order')
              }
              else{
                var result=''
                for(var i=0;i<array.length;i++){
                  result=result+'<p>'+array[i]+'</p>'
                }
                this.$Modal.error({
                  title:'菜品不足',
                  content:result,
                  okText:'确认'
                })
              }
            })
        }
      }
    }
</script>

<style scoped>
#canteen{
  padding-left: 15%;
  padding-right: 15%;
  padding-top: 10px;
}

#detail{
  padding-left: 15%;
  padding-right: 15%;
  padding-top: 10px;
}

  .sketch{
    width: 32%;
    height: 100px;
    float: left;
    margin-right: 1%;
    margin-bottom: 10px;
    padding-left: 10px;
    border: 1px solid #dcdee2;
  }


.dish{
  width: 48%;
  height: 100px;
  float: left;
  margin-right: 1%;
  margin-bottom: 10px;
  padding-left: 10px;
  border: 1px solid #dcdee2;
}

  .type{
    width: 100%;
    float: left;
    margin-bottom: 10px;
  }

  .discount{
    margin-bottom: 10px;
    width: 98%;
    margin-right: 1%;
    margin-left: 1%;
    text-align: center;
    padding: 10px;
    float: left;
  }

  .discount td{
    width: 100px;
    border-bottom: 1px solid #dcdee2;
  }

  .discount th{
    border-bottom: 1px solid #dcdee2;
    border-top: 1px solid #dcdee2;
  }

  .discount table{
    border-left: 1px solid #dcdee2;
    border-right: 1px solid #dcdee2;
  }

  #dishes{
    float: left;
    width: 60%;
    border: 1px solid #dcdee2;
  }

  #orderedDishes{
    float: left;
    width: 40%;
    text-align: center;
    padding-bottom: 20px;
    border: 1px solid #dcdee2;
  }

  #orderedDishes td,th{
    width: 100px;
    text-align: center;
  }

</style>
