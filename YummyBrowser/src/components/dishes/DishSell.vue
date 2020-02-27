<template>
  <div id="dish">
    <div style="float: left">
      <img :src="imgSRC" style="width: 150px;height: 150px"/>
    </div>
    <div style="float: left;text-align: left;margin-left: 20px;padding-top: 10px">
      <p style="font-size: 15px;font-weight: bold;margin-bottom: 10px">{{name}}</p>
      <p>每日提供数量：<span style="-webkit-text-fill-color: #ed4014;font-weight: bold">{{amount}}</span>份</p>
      <p>价格：<span style="-webkit-text-fill-color: #ed4014;font-weight: bold">{{price}}</span>元/份</p>
      <p style="margin-bottom: 10px">份量：<InputNumber min="0" size="small" value="0"></InputNumber> 份</p>
      <Button type="success">确认</Button>&nbsp&nbsp&nbsp&nbsp
      <Button type="error">取消</Button>
    </div>
  </div>
</template>

<script>
  export default {
    name: "DishSell",
    data(){
      return{
        imgSRC:require('../../assets/canteen_avatar3.png'),
        name:'汉堡包',
        distance:'200',
        deliverPrice:'25.00'
      }
    }
  }
</script>

<style scoped>
  #dish{
    background-color: white;
    height: 150px;
    width: 26%;;
    border: 1px solid #dcdee2;
  }
</style>
