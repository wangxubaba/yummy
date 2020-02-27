<template>
  <div id="dish">
    <div style="float: left">
      <img :src="imgSRC" style="width: 100px;height: 100px"/>
    </div>
    <div id="edit" v-if="isEdit">
      <p style="padding-top: 5px;padding-bottom: 5px;height: 40px">
        <Input v-model="name" style="float: left;width: 50%"/>
        <Button size="small" style="margin-top: 5px;margin-left: 5px" type="primary" @click="doFinish">确定</Button>
        <Button size="small" style="margin-top: 5px" type="primary" @click="doDelete">删除</Button>
      </p>
      <p style="padding-bottom: 2px">每日提供数量：<InputNumber min="1" size="small" v-model="amount"/> 份</p>
      <p>价格：<InputNumber min="0" size="small" v-model="price"/>元/份</p>
    </div>
    <div id="look" v-else style="float: left;text-align: left;margin-left: 20px;padding-top: 10px;">
      <p style="font-size: 15px;font-weight: bold;margin-bottom: 10px">{{name}}<span><Button size="small" type="primary" style="margin-left: 5px" @click="doEdit">编辑</Button></span></p>
      <p>每日提供数量：<span style="-webkit-text-fill-color: #ed4014;font-weight: bold">{{amount}}</span>份</p>
      <p>价格：<span style="-webkit-text-fill-color: #ed4014;font-weight: bold">{{price}}</span>元/份</p>
    </div>
  </div>
</template>

<script>
  export default {
    name: "DishEdit",
    data(){
      return{
        isEdit:false,
        imgSRC:require('../../assets/canteen_avatar3.png'),
        name:'汉堡包',
        distance:'200',
        deliverPrice:'25.00'
      }
    },
    methods:{
      doEdit(){
        this.isEdit=true;
      },
      doFinish(){
         this.isEdit=false;
      },
      doDelete(){

      }
    }
  }
</script>

<style scoped>
  #dish{
    background-color: white;
    height: 100px;
    width: 26%;;
    border: 1px solid #dcdee2;
  }
  #edit{
    float: left;
    text-align: left;
    margin-left: 10px;
    width: 55%;
  }
</style>
