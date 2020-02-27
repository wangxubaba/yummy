<template>
  <div id="whole" v-bind:style="{height:window_height+'px'}">
    <div style="background-color: white;padding-top: 20px;text-align: center" v-bind:style="{height:window_height+'px',paddingTop: 5+'px'}">
      <p style="-webkit-text-fill-color: #2d8cf0;font-size: 40px;font-weight: bold;text-align: left;padding-left: 10%;padding-bottom: 20px">欢迎注册Yummy</p>
      <!--步骤条-->
      <div style="width: 80%;margin: 0 auto;padding-bottom: 20px">
        <Steps :current="current">
          <Step title="完善餐厅信息"></Step>
          <Step title="查看审核结果"></Step>
        </Steps>
      </div>

      <!--分页显示-->
      <div style="width: 80%;margin-left: 10%">
        <canteenPage ref="canteenPage" v-if="showCanteenPage" style="height: 550px;"></canteenPage>
        <checkPage ref="checkPage" v-if="showCheckPage" style="height: 350px;margin-bottom: 50px;margin-top: 50px"></checkPage>
        <Button type="primary" style="margin-top: 5px" @click="next">{{nextMessage}}</Button>
      </div>
    </div>
  </div>
</template>

<script>
  import canteenPage from "../../components/register/CanteenInfo"
  import checkPage from "../../components/register/WaitingCheck"
    export default {
        name: "CanteenRegisterPage",
      components:{canteenPage,checkPage},
      data () {
        return {
          nextMessage:'下一步',
          current: 0,
          window_height:this.calculateHeight(),
          showCanteenPage:true,
          showCheckPage:false
        }
      },
      methods: {
        next () {
          if(this.current==0){
            this.$refs.canteenPage.$refs['formValidate'].validate((valid) => {
              if (valid) {
                this.$refs.canteenPage.formValidate.province=this.$refs.canteenPage.$refs.district.result.province
                this.$refs.canteenPage.formValidate.city=this.$refs.canteenPage.$refs.district.result.city
                this.$refs.canteenPage.formValidate.area=this.$refs.canteenPage.$refs.district.result.area
                this.$axios.post('/server/register/canteen/register',this.$refs.canteenPage.formValidate).then(re=>{
                  if(re.data=='CanteenRegisterSuccess'){
                    this.current++;
                    this.showCanteenPage=false;
                    this.showCheckPage=true;
                    this.nextMessage='返回首页'
                  }
                })
              } else {
                this.$Message.error('信息填写不完整！');
              }
            })
          }
          else if(this.current==1){
            this.$router.push('/home')
          }
        },
        calculateHeight () {
          var height = document.documentElement.clientHeight
          return height
        },
      }
    }
</script>

<style scoped>
#whole{
  background-image: url("../../assets/register.jpg");
  background-size: 100% 100%;
  background-repeat: no-repeat;
  background-position: center;
  background-attachment: fixed;

  padding-left: 25%;
  padding-right: 25%;
}

</style>
