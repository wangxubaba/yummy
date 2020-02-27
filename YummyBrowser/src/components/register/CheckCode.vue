<template>
    <div>
      <Form ref="formValidate1" :model="formValidate1" :rules="ruleValidate1" :label-width="80">
        <FormItem label="邮箱" prop="mail">
          <Input v-model="formValidate1.mail" placeholder="请输入您的e-mail" :disabled="checkMail"></Input>
        </FormItem>
        <FormItem v-if="checkMail" :label-width="0">
          <p style="font-size: 15px">我们已向您发送验证码，请在三分钟内输入并进行验证</p>
        </FormItem>
      </Form>
      <Form v-if="checkMail" ref="formValidate2" :model="formValidate2" :rules="ruleValidate2" :label-width="80">
        <FormItem label="验证码" prop="check">
          <Input v-model="formValidate2.check" placeholder="请输入您的验证码"></Input>
        </FormItem>
      </Form>
    </div>
</template>

<script>
    export default {
        name: "CheckCode",
      data () {
        return {
          success:false,
          checkMail:false,
          formValidate1: {
            mail: '',
          },
          formValidate2:{
            check:''
          },
          ruleValidate1: {
            mail: [
              { required: true, message: '邮箱不能为空', trigger: 'blur' },
              { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
            ]
          },
          ruleValidate2:{
            check: [
              { required: true, message: '验证码不能为空', trigger: 'blur' }
            ]
          }
        }
      },
      methods: {
        handleCheck (name) {
          this.$refs[name].validate((valid) => {
            if (valid) {
              this.$axios.post('/server/register/customer/get/checkCode',{email:this.formValidate1.mail})
              this.checkMail=true
            } else {
              this.$Message.error('网络出错！');
              this.checkMail=false
            }
          })
        }
      }
    }
</script>

<style scoped>

</style>
