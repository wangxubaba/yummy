<template>
<div>
  <!--标题头-->
  <Menu style="height: 200px"></Menu>

  <div style="margin-top: 5px;margin-bottom: 5px;text-align: center;margin-left: 15%;margin-right: 15%;padding: 5px">
    <Button type="success" style="width: 70%;font-weight: bold;font-size: 15px" @click="save">保 存 餐 厅 信 息</Button>
  </div>

  <div style="background-color: #f8f8f9;margin-left: 15%;margin-right: 15%;padding-left: 5%;padding-right: 5%">
    <Form style="width: 80%;margin-left: 10%" ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
      <FormItem label="店主姓名" prop="hostName">
        <Input v-model="formValidate.hostName" placeholder="请输入店主姓名" ></Input>
      </FormItem>
      <FormItem label="身份证号" prop="idNumber">
        <Input v-model="formValidate.idNumber" placeholder="请输入身份证号" ></Input>
      </FormItem>
      <FormItem label="店主邮箱" prop="email">
        <Input v-model="formValidate.email" placeholder="请输入店主邮箱" ></Input>
      </FormItem>
      <FormItem label="设置密码" prop="password">
        <Input v-model="formValidate.password" type="password" placeholder="请输入密码" ></Input>
      </FormItem>
      <FormItem label="银行账户" prop="account">
        <Input v-model="formValidate.account" type="text" placeholder="请输入银行账户" ></Input>
      </FormItem>
      <FormItem label="餐厅名称" prop="canteenName">
        <Input v-model="formValidate.name" placeholder="请输入餐厅名称" ></Input>
      </FormItem>
      <FormItem label="餐厅地址">
        <Address ref="address"></Address>
      </FormItem>
      <FormItem label="餐厅类型" prop="type">
        <Select v-model="formValidate.type" placeholder="请选择餐厅类型" >
          <Option value="汉餐">汉餐</Option>
          <Option value="回餐">回餐</Option>
          <Option value="西餐">西餐</Option>
          <Option value="快餐">快餐</Option>
          <Option value="其他">其他</Option>
        </Select>
      </FormItem>
      <FormItem label="开业时间">
        <Row>
          <Col span="11">
            <FormItem prop="beginTime">
              <TimePicker type="time" placeholder="请选择开始时间" v-model="formValidate.beginTime" ></TimePicker>
            </FormItem>
          </Col>
          <Col span="2" style="text-align: center">-</Col>
          <Col span="11">
            <FormItem prop="endTime">
              <TimePicker type="time" placeholder="请选择结束时间" v-model="formValidate.endTime" ></TimePicker>
            </FormItem>
          </Col>
        </Row>
      </FormItem>
    </Form>
  </div>
</div>
</template>

<script>
  import Menu from "../../components/MenuColumn/CanteenMenu"
  import Address from '../../components/settings/Address'
    export default {
        name: "DisplayCanteenInfoPage",
      components:{Menu,Address},
      data(){
          return{
            formValidate:{},
            ruleValidate: {
              hostName: [
                { required: true, message: '店主姓名不能为空', trigger: 'blur' }
              ],
              idNumber: [
                { required: true, message: '身份证号不能为空', trigger: 'blur' }
              ],
              email: [
                { required: true, message: '店主邮箱不能为空', trigger: 'blur' },
                {type:'email',message:'邮箱格式不正确',trigger:'blur'}
              ],
              name: [
                { required: true, message: '餐厅名称不能为空', trigger: 'blur' }
              ],
              type: [
                { required: true, message: '餐厅类型不能为空', trigger: 'blur' }
              ],
              beginTime: [
                { required: true, message: '开始时间不能为空', trigger: 'blur' }
              ],
              endTime: [
                { required: true, message: '结束时间不能为空', trigger: 'blur' }
              ],
              password: [
                { required: true, message: '密码不能为空', trigger: 'blur' }
              ],
              account: [
                { required: true, message: '账户不能为空', trigger: 'blur' }
              ],
            }
          }
      },
      async mounted(){
          await this.$axios.post('/server/register/get/canteen',{id:sessionStorage.getItem("canteenID")}).then(re=>{
            this.formValidate=re.data
            var address={
              province:this.formValidate.province,
              city:this.formValidate.city,
              area:this.formValidate.area,
              detail:this.formValidate.address,
              lat:this.formValidate.lat,
              lng:this.formValidate.lng
            }
            this.$refs.address.initial(address)
          })

      },
      methods:{
        save(){
            var address=this.$refs.address.detail
            if(!this.validAddress(address)){
              this.$Message.error('地址信息不正确')
              return
            }
            this.formValidate.province=address.province
            this.formValidate.city=address.city
            this.formValidate.area=address.area
            this.formValidate.address=address.detail
            this.formValidate.lat=address.lat
            this.formValidate.lng=address.lng

            this.$axios.post('/server/register/modify/canteen',this.formValidate).then(re=>{
              console.log(re.data)
              if(re.data=='SUCCESS'){
                this.$Message.success('您已提交申请，请耐心等待管理员审核')
              }else {
                this.$Message.error('您的信息并未更改，无需保存')
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
        }
      }
    }
</script>

<style scoped>

</style>
