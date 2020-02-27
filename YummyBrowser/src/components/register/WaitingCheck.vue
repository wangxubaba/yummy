<template>
<div id="frame">
  <div style="padding-top: 120px">
    <div style="background-color: rgba(0,0,0,0.7);margin-left: 15%;margin-right: 15%">
      <p style="font-size: 40px;-webkit-text-fill-color: #19be6b;font-weight: bold">等待管理员审核</p>
      <p style="font-size: 15px;-webkit-text-fill-color: #19be6b">我们会把结果在三天内发送至您的邮箱，请注意查收！</p>
    </div>
  </div>
</div>
</template>

<script>
    export default {
        name: "WaitingCheck"
    }
</script>

<style scoped>
#frame {
  background-image: url("../../assets/check.jpg");
  background-repeat: no-repeat;
  background-position: center;
  background-size: 100% 100%;
}
</style>
