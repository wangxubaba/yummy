<template>
  <div>
    <!--标题头-->
    <Menu style="height: 200px"></Menu>
    <div id="dailyStatistics">
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
          <td style="-webkit-text-fill-color: #ed4014">{{this.contents.newOrder.length}}</td>
          <td style="-webkit-text-fill-color: #ed4099">{{this.contents.solving.length}}</td>
          <td style="-webkit-text-fill-color: #ff9900">{{this.contents.waitingDelivery.length}}</td>
          <td style="-webkit-text-fill-color: aquamarine">{{this.contents.delivering.length}}</td>
          <td style="-webkit-text-fill-color: #19be6b">{{this.contents.sign.length}}</td>
          <td style="-webkit-text-fill-color: #c5c8ce">{{this.contents.cancel.length}}</td>
        </tr>
      </table>
    </div>
    <Collapse simple class="content" v-if="contents.newOrder.length>0">
      <Panel v-for="(item,index1) in contents.newOrder" :name="getName(index1,'newOrder')" :key="getName(index1,'newOrder')">
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
              <td style="text-align: center;-webkit-text-fill-color: white"><Button type="primary" @click="changeState('WaitingAccept',index1)">开始处理</Button></td>
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
    <Collapse simple class="content" v-if="contents.solving.length>0">
      <Panel v-for="(item,index1) in contents.solving" :name="getName(index1,'newOrder')" :key="getName(index1,'newOrder')">
        <span style="background-color: #ed4099" class="type">处理中</span>&nbsp&nbsp&nbsp
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
              <td style="text-align: center;-webkit-text-fill-color: white"><Button type="primary" @click="changeState('Making',index1)">准备配送</Button></td>
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
    <Collapse simple class="content" v-if="contents.waitingDelivery.length>0">
      <Panel v-for="(item,index1) in contents.waitingDelivery" :name="getName(index1,'newOrder')" :key="getName(index1,'newOrder')">
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
              <td style="text-align: center;-webkit-text-fill-color: white"><Button type="primary" @click="changeState('WaitingDelivery',index1)">开始配送</Button></td>
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
    <Collapse simple class="content" v-if="contents.delivering.length>0">
      <Panel v-for="(item,index1) in contents.delivering" :name="getName(index1,'newOrder')" :key="getName(index1,'newOrder')">
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
              <td style="text-align: center;-webkit-text-fill-color: aquamarine;font-weight: bold">配送中</td>
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
    <Collapse simple class="content" v-if="contents.sign.length>0">
      <Panel v-for="(item,index1) in contents.sign" :name="getName(index1,'newOrder')" :key="getName(index1,'newOrder')">
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
              <td style="text-align: center;-webkit-text-fill-color: #19be6b;font-weight: bold">已签收</td>
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
    <Collapse simple class="content" v-if="contents.cancel.length>0">
      <Panel v-for="(item,index1) in contents.cancel" :name="getName(index1,'newOrder')" :key="getName(index1,'newOrder')">
        <span style="background-color: #c5c8ce" class="type">被取消</span>&nbsp&nbsp&nbsp
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
              <td style="text-align: center;-webkit-text-fill-color: #c5c8ce;font-weight: bold">已取消</td>
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

</template>

<script>
  import Menu from "../../components/MenuColumn/CanteenMenu"
    export default {
        name: "TimelyDetailsPage",
      components:{Menu},
      async mounted(){
          await this.$axios.post('/server/register/get/canteen',{id:sessionStorage.getItem("canteenID")}).then(re=>{
            this.canteen=re.data
          })
        await this.$axios.post('/server/order/canteen/get/orders',{id:sessionStorage.getItem("canteenID")}).then(re=>{
          this.contents=re.data
          console.log(this.contents)
        })
        this.interval=setInterval(this.refreshOrders,5000)
      },
      data(){
        return{
          contents:{
            newOrder:[
              {
                id:'201902021010151',
                address:'南京大学鼓楼校区陶园二舍',
                phone:'159****3319',
                rank:'2019-02-02 10:10:15',
                due:'2019-02-02 10:50:15',
                deliverTime:'30',
                deliverer:'015236',
                order:{
                  dishDiscount:[
                    {
                      id:1,
                      name:'discount1',
                      dishes:[
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                      ],
                      distance:2,
                      discount_price:60.00
                    },
                    {
                      id:1,
                      name:'discount1',
                      dishes:[
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                      ],
                      distance:2,
                      discount_price:60.00
                    }
                  ],
                  dishOnly:[
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      deliverPrice:20.00,
                      total_amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      deliverPrice:20.00,
                      total_amount:2
                    },
                  ],
                  sum:250.26
                }
              },
              {
                id:'201902021010151',
                address:'南京大学鼓楼校区陶园二舍',
                phone:'159****3319',
                rank:'2019-02-02 10:10:15',
                due:'2019-02-02 10:50:15',
                deliverTime:'30',
                deliverer:'015236',
                order:{
                  dishDiscount:[
                    {
                      id:1,
                      name:'discount1',
                      dishes:[
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                      ],
                      distance:2,
                      discount_price:60.00
                    },
                    {
                      id:1,
                      name:'discount1',
                      dishes:[
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                      ],
                      distance:2,
                      discount_price:60.00
                    }
                  ],
                  dishOnly:[
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      deliverPrice:20.00,
                      total_amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      deliverPrice:20.00,
                      total_amount:2
                    },
                  ],
                  sum:250.26
                }
              },
              {
                id:'201902021010151',
                address:'南京大学鼓楼校区陶园二舍',
                phone:'159****3319',
                rank:'2019-02-02 10:10:15',
                due:'2019-02-02 10:50:15',
                deliverTime:'30',
                deliverer:'015236',
                order:{
                  dishDiscount:[
                    {
                      id:1,
                      name:'discount1',
                      dishes:[
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                      ],
                      distance:2,
                      discount_price:60.00
                    },
                    {
                      id:1,
                      name:'discount1',
                      dishes:[
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                      ],
                      distance:2,
                      discount_price:60.00
                    }
                  ],
                  dishOnly:[
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      deliverPrice:20.00,
                      total_amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      deliverPrice:20.00,
                      total_amount:2
                    },
                  ],
                  sum:250.26
                }
              },
              ],
            solving:[{
              id:'201902021010151',
              address:'南京大学鼓楼校区陶园二舍',
              phone:'159****3319',
              rank:'2019-02-02 10:10:15',
              due:'2019-02-02 10:50:15',
              deliverTime:'30',
              deliverer:'015236',
              order:{
                dishDiscount:[
                  {
                    id:1,
                    name:'discount1',
                    dishes:[
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                    ],
                    distance:2,
                    discount_price:60.00
                  },
                  {
                    id:1,
                    name:'discount1',
                    dishes:[
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                    ],
                    distance:2,
                    discount_price:60.00
                  }
                ],
                dishOnly:[
                  {
                    id:'001001001',
                    name:'宫保鸡丁',
                    deliverPrice:20.00,
                    total_amount:2
                  },
                  {
                    id:'001001001',
                    name:'宫保鸡丁',
                    deliverPrice:20.00,
                    total_amount:2
                  },
                ],
                sum:250.26
              }
            },],
            waitingDelivery:[
              {
              id:'201902021010151',
              address:'南京大学鼓楼校区陶园二舍',
              phone:'159****3319',
              rank:'2019-02-02 10:10:15',
              due:'2019-02-02 10:50:15',
              deliverTime:'30',
              deliverer:'015236',
              order:{
                dishDiscount:[
                  {
                    id:1,
                    name:'discount1',
                    dishes:[
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                    ],
                    distance:2,
                    discount_price:60.00
                  },
                  {
                    id:1,
                    name:'discount1',
                    dishes:[
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                    ],
                    distance:2,
                    discount_price:60.00
                  }
                ],
                dishOnly:[
                  {
                    id:'001001001',
                    name:'宫保鸡丁',
                    deliverPrice:20.00,
                    total_amount:2
                  },
                  {
                    id:'001001001',
                    name:'宫保鸡丁',
                    deliverPrice:20.00,
                    total_amount:2
                  },
                ],
                sum:250.26
              }
            },{
              id:'201902021010151',
              address:'南京大学鼓楼校区陶园二舍',
              phone:'159****3319',
              rank:'2019-02-02 10:10:15',
              due:'2019-02-02 10:50:15',
              deliverTime:'30',
              deliverer:'015236',
              order:{
                dishDiscount:[
                  {
                    id:1,
                    name:'discount1',
                    dishes:[
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                    ],
                    distance:2,
                    discount_price:60.00
                  },
                  {
                    id:1,
                    name:'discount1',
                    dishes:[
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                    ],
                    distance:2,
                    discount_price:60.00
                  }
                ],
                dishOnly:[
                  {
                    id:'001001001',
                    name:'宫保鸡丁',
                    deliverPrice:20.00,
                    total_amount:2
                  },
                  {
                    id:'001001001',
                    name:'宫保鸡丁',
                    deliverPrice:20.00,
                    total_amount:2
                  },
                ],
                sum:250.26
              }
            },
            ],
            delivering:[
              {
                id:'201902021010151',
                address:'南京大学鼓楼校区陶园二舍',
                phone:'159****3319',
                rank:'2019-02-02 10:10:15',
                due:'2019-02-02 10:50:15',
                deliverTime:'30',
                deliverer:'015236',
                order:{
                  dishDiscount:[
                    {
                      id:1,
                      name:'discount1',
                      dishes:[
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                      ],
                      distance:2,
                      discount_price:60.00
                    },
                    {
                      id:1,
                      name:'discount1',
                      dishes:[
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                      ],
                      distance:2,
                      discount_price:60.00
                    }
                  ],
                  dishOnly:[
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      deliverPrice:20.00,
                      total_amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      deliverPrice:20.00,
                      total_amount:2
                    },
                  ],
                  sum:250.26
                }
              },{
                id:'201902021010151',
                address:'南京大学鼓楼校区陶园二舍',
                phone:'159****3319',
                rank:'2019-02-02 10:10:15',
                due:'2019-02-02 10:50:15',
                deliverTime:'30',
                deliverer:'015236',
                order:{
                  dishDiscount:[
                    {
                      id:1,
                      name:'discount1',
                      dishes:[
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                      ],
                      distance:2,
                      discount_price:60.00
                    },
                    {
                      id:1,
                      name:'discount1',
                      dishes:[
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                      ],
                      distance:2,
                      discount_price:60.00
                    }
                  ],
                  dishOnly:[
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      deliverPrice:20.00,
                      total_amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      deliverPrice:20.00,
                      total_amount:2
                    },
                  ],
                  sum:250.26
                }
              },
            ],
            sign:[
              {
                id:'201902021010151',
                address:'南京大学鼓楼校区陶园二舍',
                phone:'159****3319',
                rank:'2019-02-02 10:10:15',
                due:'2019-02-02 10:50:15',
                deliverTime:'30',
                deliverer:'015236',
                order:{
                  dishDiscount:[
                    {
                      id:1,
                      name:'discount1',
                      dishes:[
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                      ],
                      distance:2,
                      discount_price:60.00
                    },
                    {
                      id:1,
                      name:'discount1',
                      dishes:[
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                        {
                          id:'001001001',
                          name:'宫保鸡丁',
                          deliverPrice:20.00,
                          total_amount:2
                        },
                      ],
                      distance:2,
                      discount_price:60.00
                    }
                  ],
                  dishOnly:[
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      deliverPrice:20.00,
                      total_amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      deliverPrice:20.00,
                      total_amount:2
                    },
                  ],
                  sum:250.26
                }
              },
            ],
            cancel:[{
              id:'201902021010151',
              address:'南京大学鼓楼校区陶园二舍',
              phone:'159****3319',
              rank:'2019-02-02 10:10:15',
              due:'2019-02-02 10:50:15',
              deliverTime:'30',
              deliverer:'015236',
              order:{
                dishDiscount:[
                  {
                    id:1,
                    name:'discount1',
                    dishes:[
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                    ],
                    distance:2,
                    discount_price:60.00
                  },
                  {
                    id:1,
                    name:'discount1',
                    dishes:[
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                      {
                        id:'001001001',
                        name:'宫保鸡丁',
                        deliverPrice:20.00,
                        total_amount:2
                      },
                    ],
                    distance:2,
                    discount_price:60.00
                  }
                ],
                dishOnly:[
                  {
                    id:'001001001',
                    name:'宫保鸡丁',
                    deliverPrice:20.00,
                    total_amount:2
                  },
                  {
                    id:'001001001',
                    name:'宫保鸡丁',
                    deliverPrice:20.00,
                    total_amount:2
                  },
                ],
                sum:250.26
              }
            },]
          },
          canteen:'',
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
        getAddress(address){
          return address.province+address.city+address.area+address.detail
        },
        changeState(state,index){
          var data=''
          if(state=='WaitingAccept'){
            data=this.contents.newOrder[index]
          }
          else if(state=='Making'){
            data=this.contents.solving[index]
          }
          else if(state=='WaitingDelivery'){
            data=this.contents.waitingDelivery[index]
          }
          this.$axios.post('/server/order/canteen/change/order/state',data).then(re=>{
            this.refreshOrders()
          })
        },
        async refreshOrders(){
          await this.$axios.post('/server/order/canteen/get/orders',{id:sessionStorage.getItem("canteenID")}).then(re=>{
            this.contents=re.data
          })
        }
      }
    }
</script>

<style scoped>
#dailyStatistics{
  margin-left: 15%;
  margin-right: 15%;
  height: 100px;
  text-align: center;
  margin-top: 10px;
}

  #dailyStatistics th{
    width: 170px;
    font-size: 15px;
    background-color: rgb(247,247,247);
    -webkit-text-fill-color: #17233d;
  }

  #dailyStatistics td{
    font-size: 40px;
    background-color: rgba(247,247,247,0.5);
  }

  .content{
    text-align: left;
    margin-left: 17%;
    margin-right: 17%;
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
