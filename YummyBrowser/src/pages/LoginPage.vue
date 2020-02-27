<template>
    <div id="login" v-bind:style="{height:window_height+'px', paddingTop:(window_height-300)/2+'px'}" >
      <div id="form">
        <div id="title">
          Yummy
        </div>
        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
          <FormItem label="用户名" prop="email">
            <Input v-model="formValidate.email" placeholder="请输入用户名或Email"/>
          </FormItem>
          <FormItem label="密码" prop="password">
            <Input v-model="formValidate.password" placeholder="请输入密码" type="password"/>
          </FormItem>
          <FormItem :label-width="0">
            <Button type="primary" @click="handleSubmit('formValidate')">Submit</Button>
          </FormItem>
        </Form>
      </div>
    </div>
</template>

<script>
export default {
  name: 'LoginPage',
  data () {
    return {
      window_height:this.calculateHeight(),
      formValidate: {
        email: '',
        password:''
      },
      ruleValidate: {
        email: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
        ],
        password: [
          {required: true, message:'密码不能为空',trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    calculateHeight () {
      var height = document.documentElement.clientHeight
      return height
    },
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.$axios.post('/server/register/login',this.formValidate).then(re=>{
            var message=re.data
            if(message=='CustomerUserNameWrong'){
              this.$Message.error("不存在该用户名")
            }
            else if(message=='CustomerPasswordWrong'){
              this.$Message.error("您输入的密码错误")
            }
            else if(message=='CustomerCancelRegister'){
              this.$Message.error("该用户已注销，无法登陆")
            }
            else if(message=='CustomerLoginSuccess'){
              sessionStorage.setItem("customerID",this.formValidate.email)
              this.$router.push('/customer/food')
            }
            else if(message=='CanteenUserNameWrong'){
              this.$Message.error("不存在该用户名")
            }
            else if(message=='CanteenPasswordWrong'){
              this.$Message.error("您输入的密码错误")
            }
            else if(message=='CanteenLoginSuccess'){
              sessionStorage.setItem("canteenID",this.formValidate.email)
              this.$router.push('/canteen/timely')
            }
            else if(message=='AdminPasswordWrong'){
              this.$Message.error("您输入的密码错误")
            }
            else if(message=='AdminLoginSuccess'){
              sessionStorage.setItem("adminID",this.formValidate.email)
              this.$router.push('/manager/check')
            }
          })
        } else {
          this.$Message.error('Fail!');
        }
      })
    },
  }
}
</script>

<style scoped>
#login{
  background-image: url("../assets/login.jpg");
  background-position: center;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  background-attachment: fixed;
  text-align: center;
}
#form{
    width: 30%;
    background-color: rgba(256,256,256,0.5);
    margin: 0 auto;
    padding: 20px;
    height: 300px;
  }
#title{
  -webkit-text-fill-color: #2d8cf0;
  font-size: 50px;
  text-align: left;
  padding-left: 20px;
}
</style>
