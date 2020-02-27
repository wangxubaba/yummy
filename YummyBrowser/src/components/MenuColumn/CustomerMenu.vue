<template>
  <div id="menu">
    <div id="title">
      <div style="float: left">
        <div id="name"><Icon type="md-restaurant" />Yummy</div>
        <ButtonGroup>
          <Button type="text" disabled style="background: none"></Button>
          <Button type="text" class="contentButton" @click="food">新鲜美味</Button>
          <Button type="text" class="contentButton" @click="order">查询订单</Button>
          <Button type="text" class="contentButton" @click="statistics">统计信息</Button>
          <Button type="text" class="contentButton" @click="settings">我的设置</Button>
          <Button type="text" disabled style="background: none"></Button>
        </ButtonGroup>
      </div>
      <div style="float: right">
        <Button id="avatar" icon="ios-person" type="text" @click="logout">{{loginMessage}}</Button>
      </div>
    </div>
    <div id="canteen">
      <div id="logo">
        <img v-if="isMale" src="../../assets/customer_avatar1.png" style="width: 120px;height: 120px"/>
        <img v-else src="../../assets/customer_avatar2.png" style="width: 120px;height: 120px"/>
      </div>
      <div id="message">
        <p style="font-size: 20px;font-weight: bold">{{customerName}}</p>
        <p style="font-size: 15px">{{idNumber}}</p>
        <p style="font-size: 15px" v-if="vip">vip有效期至：{{end}}</p>
      </div>
      <div style="font-size: 20px;font-weight: bold" id="time">
        <p>我的积分</p>
        <p>{{credits}}</p>
      </div>
      <div style="font-size: 20px;font-weight: bold" id="deliverPrice">
        <p>会有等级</p>
        <p>{{rank}}</p>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "CustomerMenu",
    async mounted(){
      this.initial()
    },
    data(){
      return{
        isMale:true,
        customerName:'王晢',
        idNumber:'wx8339459@163.com',
        loginMessage:'未登录',
        rank:'白金',
        credits:260,
        vip:false,
        begin:'',
        end:''
      }
    },
    methods:{
      food(){
        this.$router.push('/customer/food')
      },
      order(){
        this.$router.push('/customer/order')
      },
      statistics(){
        this.$router.push('/customer/statistics')
      },
      settings(){
        this.$router.push('/customer/settings')
      },
      async initial(){
        await this.$axios.post('/server/register/get/customer',{email:sessionStorage.getItem("customerID")}).then(re=>{
          var data =re.data;
          if(data.sex=='男'){
            this.isMale=true
          }
          else{
            this.isMale=false
          }

          this.customerName=data.name
          this.idNumber=data.email
          this.loginMessage='已登录'
          this.credits=data.credits
        })
        await this.$axios.post('/server/register/get/vip',{email:sessionStorage.getItem("customerID")}).then(re=>{
          var data=re.data;
          if(data.valid===true){
            this.rank='VIP'
            this.vip=true
            this.begin=data.begin.substr(0,10)
            this.end=data.end.substr(0,10)
          }
          else{
            this.rank='普通'
            this.vip=false
          }
        })
      },
      logout(){
        this.$Modal.confirm({
          title:'登出Yummy',
          content:'<p>确认登出Yummy吗？</p>',
          okText:'确定',
          cancelText:'取消',
          onOk:()=>{
            sessionStorage.setItem("customerID","");
            this.$router.push('/home')
          },
          onCancel:()=>{
            //什么都不做
          }
        })
      },
    }
  }
</script>

<style scoped>
  #menu{
    background-image: url("../../assets/customer_title.jpg");
    background-repeat: no-repeat;
    background-position: center;
    background-size: 100% 200px;
  }

  #title{
    background-color: rgba(0,0,0,0.5);
    height: 50px;
    padding-left: 15%;
    padding-right: 15%;
    text-align: left;
    -webkit-text-fill-color: white;
  }

  #name{
    background: none;
    height: 50px;
    font-size: 20px;
    font-weight: bold;
    display: inline;
  }

  #title .contentButton{
    width: 150px;
    height: 50px;
    font-size: 15px;
    background: none;
  }

  #logo{
    border-radius: 100%;
    width: 126px;
    height: 126px;
    background-color: rgba(256,256,256,0.5);
    padding-top: 3px;
    float: left;
    margin-left: 15%;
    margin-top: 12px;
  }

  #message{
    height: 150px;
    float: left;
    font-weight: bold;
    margin-left: 10px;
    padding-top: 35px;
    -webkit-text-fill-color: white;
  }

  #deliverPrice{
    float: right;
    width: 200px;
    -webkit-text-fill-color: white;
    padding-top: 35px;
  }

  #time{
    float: right;
    margin-right: 15%;
    width: 200px;
    -webkit-text-fill-color: white;
    padding-top: 35px;
  }

  #avatar{
    background: none;
    height: 50px;
    font-size: 15px;
  }
</style>
