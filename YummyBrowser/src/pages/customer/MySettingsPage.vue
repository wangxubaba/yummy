<template>
 <div>
   <TitleMenu ref="titleMenu" style="height: 200px"></TitleMenu>
   <div style="text-align: left;padding-left: 15%;padding-right: 15%">
     <ButtonGroup style="margin-top: 10px;margin-bottom: 10px">
       <Button type="success" class="selectButton" @click="changePage(1)">编 辑 信 息</Button>
       <Button type="warning" class="selectButton" @click="changePage(2)">充 值 会 员</Button>
       <Button type="error" class="selectButton" @click="changePage(3)">用 户 注 销</Button>
     </ButtonGroup>
     <div v-if="show1" style="background-color: #f8f8f9;padding-left: 5%;padding-right: 5%;margin-bottom: 30px">
       <p class="title">修改个人信息</p>
       <Form style="width: 80%" ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
         <FormItem label="姓名" prop="name">
           <Input v-model="formValidate.name" placeholder="请输入真实姓名"></Input>
         </FormItem>
         <FormItem label="性别" prop="gender">
           <RadioGroup v-model="formValidate.sex">
             <Radio label="男">男</Radio>
             <Radio label="女">女</Radio>
           </RadioGroup>
         </FormItem>
         <FormItem label="邮箱" prop="email">
           <Input v-model="formValidate.email" placeholder="请输入您的e-mail"></Input>
         </FormItem>
         <FormItem label="密码" prop="password">
           <Input type="password" v-model="formValidate.password"></Input>
         </FormItem>
         <FormItem label="手机" prop="cell_phone">
           <Input v-model="formValidate.cell_phone" placeholder="请输入您的手机号"/>
         </FormItem>
         <FormItem label="支付账号" prop="pay">
           <Input v-model="formValidate.pay" placeholder="请输入您的支付账号"/>
         </FormItem>
       </Form>
       <p class="title">修改地址信息</p>
       <p style="font-size: 15px;-webkit-text-fill-color: #2db7f5">您最多可以设置五个地址，地址1为您的默认地址</p>
       <table>
         <tr>
           <td class="short" style="background-color: #ff9900">1.</td>
           <td class="long"><Address ref="address1"></Address></td>
         </tr>
         <tr>
           <td class="short"  style="background-color: #5cadff">2.</td>
           <td class="long"><Address ref="address2"></Address></td>
         </tr>
         <tr>
           <td class="short" style="background-color: #5cadff">3.</td>
           <td class="long"><Address ref="address3"></Address></td>
         </tr>
         <tr>
           <td class="short" style="background-color: #5cadff">4.</td>
           <td class="long"><Address ref="address4"></Address></td>
         </tr>
         <tr>
           <td class="short" style="background-color: #5cadff">5.</td>
           <td class="long"><Address ref="address5"></Address></td>
         </tr>
       </table>
       <Button @click="save" style="width: 79%;margin-top: 30px;margin-bottom: 30px" type="success">保 存 修 改 信 息</Button>
     </div>
     <div v-if="show2" style="background-color: #f8f8f9;margin-top:20px;padding-left: 5%;padding-right: 5%;margin-bottom: 30px">
       <P style="text-align: center;font-size: 40px;font-weight: bold;-webkit-text-fill-color: #2d8cf0">Yummy 会 员 优 惠</P>
       <table style="margin: 0 auto" class="vip">
         <tr>
           <td>
             <p class="vip1">10</p>
             <p class="vip2">RMB</p>
             <p class="vip3">满￥100元使用</p>
           </td>
           <th></th>
           <td>
             <p class="vip1">8</p>
             <p class="vip2">RMB</p>
             <p class="vip3">满￥80元使用</p>
           </td>
           <th></th>
           <td>
             <p class="vip1">6</p>
             <p class="vip2">RMB</p>
             <p class="vip3">满￥60元使用</p>
           </td>
           <th></th>
           <td>
             <p class="vip1">4</p>
             <p class="vip2">RMB</p>
             <p class="vip3">满￥40元使用</p>
           </td>
           <th></th>
           <td>
             <p class="vip1">2</p>
             <p class="vip2">RMB</p>
             <p class="vip3">满￥20元使用</p>
           </td>
         </tr>
         <tr>
           <td colspan="9" style="text-align: center;-webkit-text-fill-color: white">每 人 每 天 仅 允 许 使 用 一 次</td>
         </tr>
       </table>
       <br>
       <p style="text-align: center;font-size: 20px;font-weight: bold;-webkit-text-fill-color: #ff9900">会 员 仅 需 ： <span style="font-size: 40px;-webkit-text-fill-color: #ed4099">￥15</span> 元 / 月</p>
       <Button style="width: 60%;font-weight: bold;font-size: 15px;margin-left: 20%;margin-bottom: 30px" type="warning" @click="pay1">我 要 充 值</Button>
       <p style="text-align: center;font-size: 20px;font-weight: bold;-webkit-text-fill-color: #ff9900">或</p>
       <p style="text-align: center;font-size: 20px;font-weight: bold;-webkit-text-fill-color: #ff9900">会 员 仅 需 ： <span style="font-size: 40px;-webkit-text-fill-color: #ed4099">15000</span> 积分 / 月</p>
       <Button style="width: 60%;font-weight: bold;font-size: 15px;margin-left: 20%;margin-bottom: 30px" type="warning" @click="pay2">我 要 兑 换</Button>
     </div>
     <div v-if="show3" style="background-color: #f8f8f9;margin-top:50px; padding-left: 5%;padding-right: 5%;margin-bottom: 30px">
       <P style="font-weight: bold;font-size: 40px;-webkit-text-fill-color: #2d8cf0;text-align: center">用 户 注 销</P><br>
       <p style="font-size: 20px;font-weight: bold;-webkit-text-fill-color: #2d8cf0;text-align: center">你确定要注销现在的账户吗？</p><br>
       <p style="font-size: 17px;width: 36%;text-align: left;margin-left: 32%">如果注销当前账户，您将无法使用该账户继续登陆，所有的订单，统计数据也将全部删除。</p><br>
       <Button @click="remove" style="width: 36%;margin-left: 32%;margin-bottom: 30px" type="error">注 销 用 户</Button>
     </div>
   </div>
 </div>
</template>

<script>
  import TitleMenu from '../../components/MenuColumn/CustomerMenu'
  import Address from '../../components/settings/Address'
    export default {
        name: "MySettingsPage",
      components:{TitleMenu,Address},
      async mounted(){
          //初始化customer对象
          await this.$axios.post('/server/register/get/customer',{email:sessionStorage.getItem("customerID")}).then(re=>{
            this.formValidate=re.data
            this.customer=re.data
          })
        //初始化地址
        var addresses=this.formValidate.addresses
        if(addresses.length>=1){
          this.$refs.address1.initial(addresses[0])
        }
        if(addresses.length>=2){
          this.$refs.address2.initial(addresses[1])
        }
        if(addresses.length>=3){
          this.$refs.address3.initial(addresses[2])
        }
        if(addresses.length>=4){
          this.$refs.address4.initial(addresses[3])
        }
        if(addresses.length>=5){
          this.$refs.address5.initial(addresses[4])
        }
      },
      data(){
          return{
            formValidate:{},
            ruleValidate: {
              name: [
                { required: true, message: '姓名不能为空', trigger: 'blur' }
              ],
              sex: [
                { required: true, message: '请选择性别', trigger: 'change' }
              ],
              email: [
                { required: true, message: '邮箱不能为空', trigger: 'blur' },
                { type: 'email', message: '邮箱格式不正确', trigger: 'blur' }
              ],
              cell_phone: [
                { required: true, message: '联系电话不能为空', trigger: 'blur' }
              ],
              password: [
                { required: true, message: '密码不能为空', trigger: 'blur' }
              ],
              pay: [
                { required: true, message: '支付账号不能为空', trigger: 'blur' }
              ],
            },
            customer:'',
            show1:true,
            show2:false,
            show3:false
          }
      },
      methods:{
        save(){
          var addresses=[]
          if(this.validAddress(this.$refs.address1.detail)){
            addresses.push(this.$refs.address1.detail)
          }

          if(this.validAddress(this.$refs.address2.detail)){
            addresses.push(this.$refs.address2.detail)
          }

          if(this.validAddress(this.$refs.address3.detail)){
            addresses.push(this.$refs.address3.detail)
          }

          if(this.validAddress(this.$refs.address4.detail)){
            addresses.push(this.$refs.address4.detail)
          }

          if(this.validAddress(this.$refs.address5.detail)){
            addresses.push(this.$refs.address5.detail)
          }

          this.formValidate.addresses=addresses

          console.log(this.formValidate)
          this.$axios.post('/server/register/update/customer',this.formValidate).then(re=>{
            if(re.data='SUCCESS'){
              this.$Message.success('修改成功')
            }
          })

        },
        pay1(){
          this.$Modal.confirm({
            title: '支付Yummy会员',
            content:'<p>账号：'+this.customer.pay+'</p><br>' +
              '密码：<input type="password" id="password" />',
            okText:'确认',
            cancelText:'取消',
            onOk: () => {
              this.$axios.post('/server/register/pay/vip/account',{info:{customerID:sessionStorage.getItem("customerID")},balance:{password:document.getElementById("password").value}}).then(re=>{
                if(re.data==='PaySuccess'){
                  this.$Message.success('您已支付成功')
                  this.$refs.titleMenu.initial()
                }
                else {
                  var title = ''
                  var content = ''
                  if (re.data === 'BalanceNotEnough') {
                    title = '您的账户余额不足'
                    content = '请您先进行账户充值再充值！'
                  }
                  else if (re.data === 'WrongAccount') {
                    title = '不存在该账户'
                    content = '请您核实自己的账户后到个人中心修改问题账户！'
                  }
                  else if (re.data === 'WrongPassword') {
                    title = '账户密码错误'
                    content = '抱歉，您的密码填写错误，请重新充值！'
                  }
                  this.$Message.error(title + ',' + content)
                }
              });
            },
            onCancel: () => {
            }
          });

        },
        pay2(){
          this.$Modal.confirm({
            title:'支付Yummy会员',
            content:'<p>您将选择用积分兑换Yummy会员，这将扣除您1500个积分，是否继续？</p>',
            okText:'确认',
            cancelText:'取消',
            onOk:()=>{
              this.$axios.post('/server/register/pay/vip/credits',{email:sessionStorage.getItem("customerID")}).then(re=>{
                if(re.data==='CreditsNotEnough'){
                  this.$Message.error('您的积分不足！')
                }
                else if(re.data=='PaySuccess'){
                  this.$Message.success('您已支付成功！')
                  this.$refs.titleMenu.initial()
                }
              })
            }
          })
        },
        remove(){
          this.$axios.post('/server/register/cancel/customer/register',{email:sessionStorage.getItem("customerID")}).then(re=>{
            if(re.data=='SUCCESS'){
              this.$Message.success('您已注销成功！')
              this.$router.push('/home')
            }
          })
        },
        validAddress(address){
          if(address===''){
            return false
          }

          if(address.province===''){
            return false
          }

          if(address.city===''){
            return false
          }

          if(address.area===''){
            return false
          }

          if(address.detail===''){
            return false
          }

          return true
        },
        changePage(index){
          if(index==1){
            this.show1=true;
            this.show2=false;
            this.show3=false;
          }
          else if(index==2){
            this.show1=false;
            this.show2=true;
            this.show3=false;
          }
          else if(index==3){
            this.show1=false;
            this.show2=false;
            this.show3=true;
          }
        }
      }
    }
</script>

<style scoped>
.selectButton{
  width: 150px;
}
.short{
  width: 50px;
  text-align: center;
  font-size: 15px;
  font-weight: bold;
  -webkit-text-fill-color: white;
}
  .long{
    width: 700px;
    text-align: center;
  }

  .title{
    border-bottom: 2px solid #2d8cf0;
    font-size: 20px;
    font-weight: bold;
    width: 120px;
    -webkit-text-fill-color: #2d8cf0;
  }

  .vip td{
    background-color: #ed4099;
  }

  .vip1{
    font-size: 60px;
    font-weight: bold;
    -webkit-text-fill-color: white;
    text-align: center;
  }

  .vip2{
    font-size: 12px;
    -webkit-text-fill-color: white;
    text-align: center;
  }

.vip3{
  font-size: 12px;
  -webkit-text-fill-color: white;
  text-align: center;
  margin-left: 5px;
  margin-right: 5px;
}

  .vip th{
    width: 30px;
  }
</style>
