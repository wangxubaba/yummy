<template>
    <div>
      <!--标题头-->
      <Menu style="height: 200px"></Menu>

      <!--选择操作-->
      <div id="selectOperation">
        <div id="buttons">
          <ButtonGroup size="large">
            <Button type="success" @click="changeMenu('today')">今 日 菜 单</Button>
            <Button type="error" @click="changeMenu('edit')">编 辑 菜 单</Button>
          </ButtonGroup>
        </div>
        <div v-if="isEdit" style="padding-top: 5px">
          <Button type="primary" style="margin-right: 10px;margin-left: 200px" @click="saveMenu">保存菜单</Button>
          <span style="font-size: 15px">请选择执行时间：</span>
          <DatePicker type="daterange" placement="bottom-end" style="width: 200px" v-model="validTime2"></DatePicker>
        </div>

        <div v-else style="padding-top: 5px">
          <span style="font-size: 15px">执行时间：</span>
          <DatePicker type="daterange" placement="bottom-end" style="width: 200px" :value="validTime1" disabled></DatePicker>
        </div>
      </div>

      <!--菜品展示-->
      <div v-if="isEdit">
        <span style="font-size: 20px;font-weight: bold;float: left;margin-left: 16%;margin-bottom: 5px">单点菜品&nbsp&nbsp<Button @click="newDishType" type="text" style="-webkit-text-fill-color: #2d8cf0">新建菜品分类</Button></span>
        <div class="editDish" v-for="(item, index1) in editMenu.types">
          <p class="typeName">{{item.name}}&nbsp&nbsp<Button type="primary" @click="newDish(index1)">新建菜品</Button>&nbsp&nbsp<Button type="primary" @click="deleteType(index1)">删除分类</Button></p>
          <div class="dishOnly" v-for="(dish,index2) in item.dishes">
            <div style="float: left;padding-left: 5px">
              <img src="../../assets/canteen_avatar3.png" style="width: 100px;height: 100px"/>
            </div>
            <div class="edit" style="float: left">
              <p style="padding-top: 5px;padding-bottom: 5px;height: 40px">
                <Input v-model="dish.name" style="width: 50%"/>
                <Button size="small" style="margin-top: 5px" type="primary" @click="deleteDish(index1, index2)">删除</Button>
              </p>
              <p style="padding-bottom: 2px">每日提供数量：<InputNumber :min="1" size="small" v-model="dish.amount"/> 份</p>
              <p>价格：<InputNumber :min="0" size="small" v-model="dish.price"/>元/份</p>
            </div>
          </div>
          <div v-if="item.dishes.length==0" style="float: left;background-color: rgb(247,247,247);height: 100px;margin-left: 1%;width: 98%;font-size: 15px;font-weight: bold;padding-top: 40px">
            暂无菜品
          </div>
        </div>
        <div v-if="editMenu.types.length==0" style="float: left;background-color: rgb(247,247,247);height: 100px;margin-left: 15%;width: 70%;font-size: 15px;font-weight: bold;padding-top: 40px">
          暂无菜品分类
        </div>
        <span style="font-size: 20px;font-weight: bold;float: left;margin-left: 16%;margin-bottom: 5px">优惠套餐&nbsp&nbsp<Button @click="newDiscount" type="text" style="-webkit-text-fill-color: #2d8cf0">新建优惠套餐</Button></span>
        <div class="editDiscount" v-for="(item,index3) in editMenu.dishDiscount">
          <div style="height: 150px;margin-left: 31%;padding-top: 5px">
            <div style="float:left;">
              <img src="../../assets/canteen_avatar1.png" style="width: 100px;height: 100px;"/>
              <div style="margin: 0;padding: 0">
                <Button type="error" @click="deleteDiscount(index3)">删 除 该 套 餐</Button>
              </div>
            </div>
            <div style="float:left;padding-top: 15px">
              <table class="disTable3">
                <tr>
                  <th>套餐名称</th>
                  <td><Input v-model="item.name" style="width: 150px"/></td>
                </tr>
                <tr>
                  <th>每日数量</th>
                  <td><InputNumber v-model="item.amount" :min="1" style="width: 150px"/></td>
                </tr>
                <tr>
                  <th>套餐价格</th>
                  <td><InputNumber v-model="item.discount_price" :min="0" style="width: 150px"/></td>
                </tr>
              </table>
            </div>
          </div>

          <div>
            <p style="font-size: 15px;font-weight: bold;-webkit-text-fill-color: white;background-color: #2db7f5;width: 60%;margin-left: 20%;height: 37px;padding-top: 7px">已 选 菜 单 列 表 ：</p>
            <table style="margin: 0 auto" class="disTable1">
              <div>
                <tr>
                  <th></th>
                  <th>菜品名称</th>
                  <th>菜品单价</th>
                  <th>包含数量</th>
                </tr>
              </div>
              <div v-for="(type,index4) in editMenu.types">
                <tr v-for="(dish,index5) in type.dishes" v-if="discountDishNum[index3][index4][index5]!=0">
                  <td><li></li></td>
                  <td>{{dish.name}}</td>
                  <td>{{dish.price}}</td>
                  <td>{{discountDishNum[index3][index4][index5]}}</td>
                </tr>
              </div>
            </table>
          </div>
          <Collapse style="width: 60%;margin-left: 20%;margin-bottom: 20px">
            <Panel style="background-color: #2db7f5">
              <span style="font-size: 15px;-webkit-text-fill-color: white;font-weight: bold">您可以在这里修改您的套餐菜单：</span>
              <div slot="content">
                <table class="disTable2" style="margin: 0 auto">
                  <div v-for="(type, index6) in editMenu.types">
                    <p style="width: 450px;font-size: 14px;font-weight: bold;background-color: #515a6e;-webkit-text-fill-color: white;margin-top: 5px;margin-bottom: 2px">以下是分类{{type.name}}中的菜品</p>
                    <tr>
                      <th>菜品名称</th>
                      <th>菜品单价</th>
                      <th>包含数量</th>
                    </tr>
                    <tr v-for="(dish,index7) in type.dishes">
                      <td>{{dish.name}}</td>
                      <td>{{dish.price}}</td>
                      <td><InputNumber :min="0" v-model="discountDishNum[index3][index6][index7]"/></td>
                    </tr>
                  </div>
                  <tr></tr>
                </table>
              </div>
            </Panel>
          </Collapse>
        </div>
      </div>

      <div v-else>
        <span style="font-size: 20px;font-weight: bold;float: left;margin-left: 16%;margin-bottom: 5px">单点菜品</span>
        <div class="editDish" v-for="item in todayMenu.types">
          <p class="typeName">{{item.name}}</p>
          <div class="dishOnly" v-for="dish in item.dishes">
            <div style="float: left;padding-left: 5px">
              <img src="../../assets/canteen_avatar3.png" style="width: 100px;height: 100px"/>
            </div>
            <div class="edit" style="float: left;margin-left: 10px">
              <p style="font-size: 15px;font-weight: bold;text-align: left;margin-top: 10px">
                {{dish.name}}
              </p>
              <p style="margin-top: 5px;font-size: 13px">
                每日提供数量：<span style="-webkit-text-fill-color: #2db7f5;font-weight: bold">{{dish.amount}}</span> 份
              </p>
              <p style="margin-top: 5px;font-size: 13px">
                价格：<span style="-webkit-text-fill-color: #2db7f5;font-weight: bold">{{getPrice(dish.price)}}</span>&nbsp元/份
              </p>
            </div>
          </div>
          <div v-if="item.dishes.length==0" style="float: left;background-color: rgb(247,247,247);height: 100px;margin-left: 1%;width: 98%;font-size: 15px;font-weight: bold;padding-top: 40px">
            暂无菜品
          </div>
        </div>
        <div v-if="todayMenu.types.length==0" style="float: left;background-color: rgb(247,247,247);height: 100px;margin-left: 15%;width: 70%;font-size: 15px;font-weight: bold;padding-top: 40px">
          暂无菜品分类
        </div>
        <span style="font-size: 20px;font-weight: bold;float: left;margin-left: 16%;margin-bottom: 5px">优惠套餐</span>
        <div class="editDiscount" v-for="item in todayMenu.dishDiscount">
          <div style="height: 120px;margin-left: 31%;padding-top: 5px">
            <div style="float:left;">
              <img src="../../assets/canteen_avatar1.png" style="width: 100px;height: 100px;"/>
            </div>
            <div style="float:left;margin-left: 50px">
              <p style="font-size: 15px;font-weight: bold;text-align: left;margin-top: 10px">
                {{item.name}}
              </p>
              <p style="margin-top: 5px;font-size: 15px">
                每日提供数量：<span style="-webkit-text-fill-color: #2db7f5;font-weight: bold">{{item.amount}}</span> 份
              </p>
              <p style="margin-top: 5px;font-size: 15px">
                价格：<span style="-webkit-text-fill-color: #2db7f5;font-weight: bold">{{getPrice(item.discount_price)}}</span>&nbsp元/份
              </p>
            </div>
          </div>
          <div>
            <table style="margin: 0 auto;border: 1px solid #dcdee2;margin-bottom: 20px" class="disTable1">
              <div>
                <tr>
                  <th></th>
                  <th>菜品名称</th>
                  <th>菜品单价</th>
                  <th>包含数量</th>
                </tr>
              </div>
              <div v-for="dish in item.dishes">
                <tr>
                  <td><li></li></td>
                  <td>{{dish.name}}</td>
                  <td>{{getPrice(dish.price)}}</td>
                  <td>{{dish.amount}}</td>
                </tr>
              </div>
            </table>
          </div>
        </div>
        <div v-if="todayMenu.dishDiscount.length==0" style="float: left;background-color: rgb(247,247,247);height: 100px;margin-left: 15%;width: 70%;font-size: 15px;font-weight: bold;padding-top: 40px">
          暂无优惠套餐
        </div>
      </div>

    </div>
</template>

<script>
  import Menu from "../../components/MenuColumn/CanteenMenu"
    export default {
        name: "ManageMenuPage",
      components:{Menu},
      async mounted(){
          //获得今日菜单
        await this.$axios.post('/server/canteen/dish/today',{id:sessionStorage.getItem("canteenID")}).then(re=>{
          this.todayMenu=re.data
          this.validTime1=[re.data.beginTime,re.data.endTime]
        })
        //获得最后一次菜单
          await this.$axios.post('/server/canteen/dish/latest',{id:sessionStorage.getItem("canteenID")}).then(re=>{
            this.editMenu=re.data
            var array=[]
            for(var i=0;i<this.editMenu.dishDiscount.length;i++){
              var discount=[]
              for(var j=0;j<this.editMenu.types.length;j++){
                var temp=this.editMenu.types[j];
                var count=[]
                for(var k=0;k<temp.dishes.length;k++){
                  var isChosen=false
                  var dishes=this.editMenu.dishDiscount[i].dishes
                  for(var m=0;m<dishes.length;m++){
                    if(dishes[m].id==temp.dishes[k].id){
                      isChosen=true
                      count.push(dishes[m].amount)
                      break
                    }
                  }
                  if(!isChosen){
                    count.push(0)
                  }
                }
                discount.push(count)
              }
              array.push(discount)
            }
            this.discountDishNum=array
            this.validTime2=[re.data.beginTime,re.data.endTime]
          })
      },
      data(){
          return{
            validTime1:[],
            validTime2:[],
            isEdit:false,
            editMenu:{
              types:[
                {
                  name:'type1',
                  dishes:[
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      total_amount:10
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      total_amount:10
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      total_amount:10
                    },
                  ]
                },
                {
                  name:'type1',
                  dishes:[
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      total_amount:10
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      total_amount:10
                    },
                  ]
                }
              ],
              dishDiscount:[
                {
                  id:1,
                  name:'discount1',
                  dishes:[
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                  ],
                  amount:30,
                  discount_price:60.00
                },
                {
                  id:1,
                  name:'discount1',
                  dishes:[
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                  ],
                  amount:30,
                  discount_price:60.00
                },
              ],
              beginTime:'',
              endTime:''
            },
            discountDishNum:[
              [[1,2,3], [5,6]],
              [[0,2,3], [5,6]],
            ],
            todayMenu:{
              types:[
                {
                  name:'type1',
                  dishes:[
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:10
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:10
                    }
                  ]
                },
                {
                  name:'type1',
                  dishes:[
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:10
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:10
                    }
                  ]
                },
              ],
              dishDiscount:[
                {
                  id:1,
                  name:'discount1',
                  dishes:[
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                  ],
                  amount:30,
                  discount_price:60.00
                },
                {
                  id:1,
                  name:'discount1',
                  dishes:[
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                    {
                      id:'001001001',
                      name:'宫保鸡丁',
                      price:20.00,
                      amount:2
                    },
                  ],
                  amount:30,
                  discount_price:60.00
                },
              ],
              beginTime:'',
              endTime:''
            },
            newDishName:'',
            newDishPrice:'',
            newDishAmount:'',
            newDishTypeName:'',
            newDiscountName:'',
            newDiscountPrice:'',
            newDiscountAmount:''
          }
      },
      methods:{
        getPrice(price){
            return '￥'+price.toFixed(2)
          },
        newDish(typeIndex){
          this.$Modal.confirm({
            title: '新建菜品',
            okText:'确认',
            cancelText:'取消',
            render: (h) => {
              return [
                h('Input', {
                props: {
                  value: this.newDishName,
                  autofocus: true,
                  placeholder: '请输入菜名'
                },
                on: {
                  input: (val) => {
                    this.newDishName= val;
                  }
                }
              }),
                h('Input', {
                  props: {
                    value: this.newDishAmount,
                    placeholder: '请输入每日提供数量'
                  },
                  on: {
                    input: (val) => {
                      this.newDishAmount= val;
                    }
                  }
                }),
                h('Input', {
                  props: {
                    value: this.newDishPrice,
                    placeholder: '请输入菜品单价'
                  },
                  on: {
                    input: (val) => {
                      this.newDishPrice= val;
                    }
                  }
                })
              ]
            },
            onOk: () => {
              if(this.isNumber(this.newDishPrice)&&this.isNumber(this.newDishAmount)&&(this.newDishName!='')){
                var dish={
                  name:this.newDishName,
                  price:this.newDishPrice,
                  amount:this.newDishAmount
                }
                this.editMenu.types[typeIndex].dishes.push(dish)
                this.newDishName=''
                this.newDishAmount=''
                this.newDishPrice=''
              }
              else{
                this.$Message.error("您输入的数据格式不对，请重试")
              }
            },
            onCancel: () => {
              this.newDishName=''
              this.newDishAmount=''
              this.newDishPrice=''
            }
          });
        },
        newDishType(){
          this.$Modal.confirm({
            title: '新建菜品分类',
            okText:'确认',
            cancelText:'取消',
            render: (h) => {
              return [
                h('Input', {
                  props: {
                    value: this.newDishTypeName,
                    autofocus: true,
                    placeholder: '请输入菜品分类名'
                  },
                  on: {
                    input: (val) => {
                      this.newDishTypeName= val;
                    }
                  }
                })
              ]
            },
            onOk: () => {
              if(this.newDishTypeName!=''){
                var dishType={
                  name:this.newDishTypeName,
                  dishes:[]
                }
                this.editMenu.types.push(dishType)
                this.newDishTypeName=''
              }
              else{
                this.$Message.error("您输入的数据格式不对，请重试")
              }
            },
            onCancel: () => {
              this.newDishTypeName=''
            }
          });
        },
        deleteType(typeIndex){
          this.$Modal.confirm({
            title: '删除分类',
            content: '<p style="font-weight: bold">确认要删除该分类吗？</p><p>删除该分类，其下菜品将全部被删除。</p>',
            onOk: () => {
              this.editMenu.types.splice(typeIndex,1)
            },
            onCancel: () => {
              //do nothing
            }
          })
        },
        deleteDish(typeIndex,dishIndex){
          this.$Modal.confirm({
            title: '删除菜品',
            content: '<p style="font-weight: bold">确认要删除该菜品吗？</p>',
            onOk: () => {
              this.editMenu.types[typeIndex].dishes.splice(dishIndex,1)
            },
            onCancel: () => {
              //do nothing
            }
          })
        },
        newDiscount(){
          this.$Modal.confirm({
            title: '新建优惠套餐',
            okText:'确认',
            cancelText:'取消',
            render: (h) => {
              return [
                h('Input', {
                  props: {
                    value: this.newDiscountName,
                    autofocus: true,
                    placeholder: '请输入优惠套餐名称'
                  },
                  on: {
                    input: (val) => {
                      this.newDiscountName= val;
                    }
                  }
                }),
                h('Input', {
                  props: {
                    value: this.newDiscountAmount,
                    placeholder: '请输入每日提供数量'
                  },
                  on: {
                    input: (val) => {
                      this.newDiscountAmount= val;
                    }
                  }
                }),
                h('Input', {
                  props: {
                    value: this.newDiscountPrice,
                    placeholder: '请输入套餐价格'
                  },
                  on: {
                    input: (val) => {
                      this.newDiscountPrice= val;
                    }
                  }
                })
              ]
            },
            onOk: () => {
              if(this.isNumber(this.newDiscountPrice)&&this.isNumber(this.newDiscountAmount)&&(this.newDiscountName!='')){
                var array=[]
                for(var i=0;i<this.editMenu.types.length;i++){
                  var temp=[]
                  for(var j=0;j<this.editMenu.types[i].dishes.length;j++){
                    temp.push(0)
                  }
                  array.push(temp)
                }
                this.discountDishNum.push(array)
                var discount={
                  name:this.newDiscountName,
                  discount_price:this.newDiscountPrice,
                  amount:this.newDiscountAmount
                }
                this.editMenu.dishDiscount.push(discount)
                this.newDiscountName=''
                this.newDiscountPrice=''
                this.newDiscountAmount=''
              }
              else{
                this.$Message.error("您输入的数据格式不对，请重试")
              }
            },
            onCancel: () => {
              this.newDiscountName=''
              this.newDiscountPrice=''
              this.newDiscountAmount=''
            }
          });
        },
        deleteDiscount(discountIndex){
          this.$Modal.confirm({
            title: '删除优惠套餐',
            content: '<p style="font-weight: bold">确认要删除该优惠套餐吗？</p>',
            onOk: () => {
              this.editMenu.dishDiscount.splice(discountIndex,1)
              this.discountDishNum.splice(discountIndex,1)
            },
            onCancel: () => {
              //do nothing
            }
          })
        },
        isNumber(number){
            var length=number.length
          if(length==0){
            return false
          }
          for(var i=0;i<length;i++){
            if(!((number[i]>='0' && number[i]<='9')||(number[i]=='.'))){
              return false
            }
          }
          return true
        },
        saveMenu(){
          //1.检查单点菜品名不能为空
          for(var i=0;i<this.editMenu.types.length;i++){
            var type=this.editMenu.types[i]
            for(var j=0;j<type.dishes.length;j++){
              if(type.dishes[j].name==''){
                this.$Message.error("您"+type.name+"分类中有菜品的命名为空！")
                return
              }
            }
          }

          //2.检查套餐名不能为空
          for(var i=0;i<this.editMenu.dishDiscount.length;i++){
            var discount=this.editMenu.dishDiscount[i]
            if(discount.name==''){
              this.$Message.error("您有优惠套餐的命名为空！")
              return
            }
          }

          //3.检查生效时间是否合规
          var timeOrigin=this.editMenu.beginTime
          var date1=this.formatDate(new Date(this.validTime2[0]))
          var date2=this.formatDate(new Date(this.validTime2[1]))
          if(timeOrigin>date1){
            this.$Message.error("您的日期填写不正确，您的菜单只能设置在"+date1+"当天或之后！")
            return
          }
          this.editMenu.beginTime=date1
          this.editMenu.endTime=date2

          //4.将editMenu对象传入后台
          //4.1先修改editMenu对象，把菜传进去
          for(var i=0;i<this.discountDishNum.length;i++){
            var array=this.discountDishNum[i]
            var dishes=[]
            for(var j=0;j<array.length;j++){
              for(var k=0;k<array[j].length;k++){
                if(array[j][k]!=0){
                  var dish={
                    name:this.editMenu.types[j].dishes[k].name,
                    price:this.editMenu.types[j].dishes[k].price,
                    amount:this.discountDishNum[i][j][k]
                  }
                  dishes.push(dish)
                }
              }
            }
            this.editMenu.dishDiscount[i].dishes=dishes;
          }

          console.log(this.editMenu)
          //4.2传editMenu
          this.$axios.post('/server/canteen/set/menu',this.editMenu).then(re=>{
            if(re.data=='SUCCESS'){
              this.$Message.success('保存菜单成功');
            }
          })

        },
        formatDate(date){
          var year=date.getFullYear()
          var month=date.getMonth()+1
          if(month<10){
            month="0"+month
          }
          var day=date.getDate()
          if(day<10){
            day="0"+day
          }
          return parseInt(year+""+month+""+day)
        },
        changeMenu(operation){
          if(operation=='today'){
            this.isEdit=false
          }
          else if(operation=='edit'){
            this.isEdit=true
          }
        }
      }
    }
</script>

<style scoped>
#selectOperation{
  padding-left: 15%;
  padding-right: 15%;
  height: 40px;
  background-color: white;
  margin-top: 5px;
  margin-bottom: 5px;
}

  #buttons{
    float: left;
    margin-top: 2px;
  }

  #buttons Button{
    width: 200px;
  }

  .dishOnly{
    width: 32%;
    float: left;
    height: 100px;
    background-color: rgb(247,247,247);
    margin-left: 1%;
    margin-bottom: 5px;
  }

  .editDish{
    padding-left: 15%;
    padding-right: 15%;
    width: 100%;
    float: left;
    margin-bottom: 20px;
  }

  .typeName{
    font-size: 15px;
    font-weight: bold;
    text-align: left;
    padding-left: 20px;
    margin-bottom: 5px;
  }

  .editDiscount{
    width: 68%;
    margin-left: 16%;
    float: left;
    background-color: rgb(247,247,247);
    min-height: 100px;
    margin-bottom: 20px;
  }

  .disTable1 td, .disTable1 th{
    width: 150px;
    font-size: 14px;
  }

.disTable2 td, .disTable2 th{
  width: 150px;
  font-size: 14px;
}
.disTable3 th{
  font-size: 14px;
  width: 120px;
}


</style>
