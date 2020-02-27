<template>
    <div>
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
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
        <FormItem label="地区" prop="district">
          <select3 ref="district"></select3>
        </FormItem>
        <FormItem label="地址" prop="default_address">
          <Select placeholder="请填写您要搜索的地址" filterable @on-query-change="changeQuery" @on-change="selectOne">
            <Option v-for="(item,index) in addresses" :value="index" :key="index">{{ item.detail }}</Option>
          </Select>
        </FormItem>
        <FormItem label="手机" prop="cell_phone">
          <Input v-model="formValidate.cell_phone" placeholder="请输入您的手机号"/>
        </FormItem>
        <FormItem label="支付账号" prop="pay">
          <Input v-model="formValidate.pay" placeholder="请输入您的支付账号"/>
        </FormItem>
      </Form>
    </div>
</template>

<script>
  import select3 from '../register/AddressPicker'
    export default {
        name: "PersonInfo",
      components:{select3},
      data () {
        return {
          addresses:[],
          detail: '',
          formValidate: {
            name: '',
            email: '',
            sex: '',
            cell_phone:'',
            password:'',
            addresses:[{
              province:'',
              city:'',
              area:'',
              detail:'',
            }],
            pay:''
          },
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
          }
        }
      },
      methods:{
        async changeQuery(inputValue){
          var isContain;
          for(var i=0;i<this.addresses.length;i++){
            if(this.addresses[i].detail===inputValue){
              isContain=true
              break
            }
          }
          if((!isContain)&&(inputValue!='')){
            await this.$axios.post('/server/map/get/recommend',{province:this.$refs.district.result.province,city:this.$refs.district.result.city,area:this.$refs.district.result.area,detail:inputValue}).then(re=>{
              this.addresses=re.data
            })
          }
        },
        selectOne(choose){
          this.detail=this.addresses[choose]
          this.formValidate.addresses=[this.detail]
        }
      }
    }
</script>

<style scoped>

</style>
