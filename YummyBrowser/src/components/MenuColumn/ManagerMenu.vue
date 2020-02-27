<template>
  <div id="menu">
    <div id="title">
      <div style="float: left">
        <div id="name"><Icon type="md-restaurant" />Yummy</div>
        <ButtonGroup>
          <Button type="text" disabled style="background: none"></Button>
          <Button type="text" class="contentButton" @click="check">审核餐厅</Button>
          <Button type="text" class="contentButton" @click="pay">自动结算</Button>
          <Button type="text" class="contentButton" @click="statistics">统计信息</Button>
          <Button type="text" disabled style="background: none"></Button>
        </ButtonGroup>
      </div>
      <div style="float: right">
        <Button id="avatar" icon="ios-person" type="text">{{loginMessage}}</Button>
      </div>
    </div>
    <div id="canteen">
      <div id="logo">
        <img v-if="isMale" src="../../assets/customer_avatar1.png" style="width: 120px;height: 120px"/>
        <img v-else src="../../assets/customer_avatar2.png" style="width: 120px;height: 120px"/>
      </div>
      <div id="message">
        <p style="font-size: 20px;font-weight: bold">系统管理员</p>
        <p style="font-size: 15px">admin</p>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "ManagerMenu",
    data(){
      return{
        isMale:true,
        customerName:'王晢',
        idNumber:'wx8339459@163.com',
        loginMessage:'已登录',
        rank:'白金',
        credits:260
      }
    },
    methods:{
      check(){
        this.$router.push('/manager/check')
      },
      pay(){
        this.$router.push('/manager/pay')
      },
      statistics(){
        this.$router.push('/manager/statistics')
      }
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

  #avatar{
    background: none;
    height: 50px;
    font-size: 15px;
  }
</style>
