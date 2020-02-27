<template>
  <div id="whole" v-bind:style="{height:window_height+'px',paddingTop:window_height*0.05+'px'}">
    <div style="background-color: white;padding-top: 20px;text-align: center" v-bind:style="{height:window_height*0.9+'px',paddingTop: 12+'px'}">
      <p style="-webkit-text-fill-color: #2d8cf0;font-size: 40px;font-weight: bold;text-align: left;padding-left: 10%;padding-bottom: 20px">欢迎注册Yummy</p>
      <!--步骤条-->
      <div style="width: 80%;margin: 0 auto;padding-bottom: 20px">
        <Steps :current="current">
          <Step title="申请验证码"></Step>
          <Step title="完善个人信息"></Step>
          <Step title="申请结果"></Step>
        </Steps>
      </div>

      <!--<span></span>-->
      <!--分页显示-->
      <!--户主个人信息-->
      <div style="width: 80%;margin-left: 10%">
        <checkCode ref="checkCode" v-if="showCheckPage" style="padding-top: 50px;margin-left: 20%;margin-right: 20%"></checkCode>
        <customerPage ref="customerPage" v-if="showCustomerPage" style="height: 450px"></customerPage>
        <successPage ref="successPage" v-if="showSuccessPage" style="height: 350px;margin-bottom: 50px"></successPage>
        <Button type="primary" style="margin-top: 5px" @click="next">{{this.buttonMessage}}</Button>
      </div>
    </div>
  </div>
</template>

<script>
  import checkCode from "../../components/register/CheckCode"
  import customerPage from "../../components/register/PersonInfo"
  import successPage from "../../components/register/CustomerSuccess"
    export default {
        name: "CustomerRegisterPage",
      components:{checkCode, customerPage,successPage},
      data () {
        return {
          current: 0,
          window_height:this.calculateHeight(),
          showCheckPage:true,
          showCustomerPage:false,
          showSuccessPage:false,
          buttonMessage:'获取验证码'
        }
      },
      methods:{
        async next () {
          if(this.current==0){
            if(this.buttonMessage=='获取验证码'){
              await this.$refs.checkCode.handleCheck('formValidate1')
              if(this.$refs.checkCode.checkMail){
                this.buttonMessage='下一步'
              }
            }
            else{
              await this.$axios.post('/server/register/customer/check/checkCode',{email:this.$refs.checkCode.formValidate1.mail,checkCode:this.$refs.checkCode.formValidate2.check}).then(re=>{
                if(re.data=='timeCheckFail'){
                  this.$Message.error('抱歉，验证码填写超时！请重新注册！')
                }
                else if(re.data=='checkFail'){
                  this.$Message.error('抱歉，您的验证码不正确！')
                }
                else{
                  this.current++
                  this.showCheckPage=false
                  this.showCustomerPage=true;
                  this.showSuccessPage=false;
                }
              })
            }
          }
          else if(this.current==1){
            this.$refs.customerPage.$refs['formValidate'].validate((valid) => {
              if (valid) {
                this.$axios.post('/server/register/customer/register',this.$refs.customerPage.formValidate).then(re=>{
                  if(re.data=='CustomerRegisterSuccess'){
                    this.current++;
                    this.showCheckPage=false;
                    this.showCustomerPage=false;
                    this.showSuccessPage=true;
                    this.buttonMessage='返回首页登录'
                  }
                  else{
                    this.$Message.error('抱歉，该账号已注册，请更换邮箱注册！')
                  }
                })
              } else {
                this.$Message.error('信息填写不完整！');
              }
            })
          }
          else if(this.current==2){
            this.$router.push("/home")
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
