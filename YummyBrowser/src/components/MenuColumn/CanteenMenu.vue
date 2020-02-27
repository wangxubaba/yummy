<template>
  <div id="menu">
    <div id="title">
      <div style="float: left">
        <div id="name"><Icon type="md-restaurant" />Yummy</div>
        <ButtonGroup>
          <Button type="text" disabled style="background: none"></Button>
          <Button type="text" class="contentButton" @click="timely">实时详情</Button>
          <Button type="text" class="contentButton" @click="menu">菜品管理</Button>
          <Button type="text" class="contentButton" @click="statistics">综合统计</Button>
          <Button type="text" class="contentButton" @click="canteenInfo">餐厅信息</Button>
          <Button type="text" disabled style="background: none"></Button>
        </ButtonGroup>
      </div>
      <div style="float: right">
        <Button id="avatar" icon="ios-person" type="text" @click="logout">{{loginMessage}}</Button>
      </div>
    </div>
    <div id="canteen">
      <div id="logo">
        <img src="../../assets/canteen_avatar3.png" style="width: 120px;height: 120px"/>
      </div>
      <div id="message">
        <p style="font-size: 20px;font-weight: bold">{{canteenName}}</p>
        <p style="font-size: 15px">{{idNumber}}</p>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "CanteenMenu",
    mounted(){
      this.$axios.post('/server/register/get/canteen',{id:sessionStorage.getItem("canteenID")}).then(re=>{
        var canteen=re.data;
        this.canteenName=canteen.name
        this.idNumber=this.get8bitString(canteen.id)
      })
    },
    data(){
      return{
        canteenName:'未命名餐厅',
        idNumber:'10000001',
        loginMessage:'已登录',
      }
    },
    methods:{
      timely(){
        this.$router.push('/canteen/timely')
      },
      menu(){
        this.$router.push('/canteen/menu')
      },
      statistics(){
        this.$router.push('/canteen/statistics')
      },
      canteenInfo(){
        this.$router.push('/canteen/info')
      },
      get8bitString(str){
        var t=(str+"").length
        for(var i=0;i<8-t;i++){
          str='0'+str
        }
        return str
      },
      logout(){
        this.$Modal.confirm({
          title:'登出Yummy',
          content:'<p>确认登出Yummy吗？</p>',
          okText:'确定',
          cancelText:'取消',
          onOk:()=>{
            sessionStorage.setItem("canteenID","");
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
    background-image: url("../../assets/canteen_title.jpg");
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
