<template>
    <div>
      <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="80">
        <FormItem label="店主姓名" prop="hostName">
          <Input v-model="formValidate.hostName" placeholder="请输入店主姓名" :disabled="valid"></Input>
        </FormItem>
        <FormItem label="身份证号" prop="idNumber">
          <Input v-model="formValidate.idNumber" placeholder="请输入身份证号" :disabled="valid"></Input>
        </FormItem>
        <FormItem label="店主邮箱" prop="email">
          <Input v-model="formValidate.email" placeholder="请输入店主邮箱" :disabled="valid"></Input>
        </FormItem>
        <FormItem label="设置密码" prop="password">
          <Input v-model="formValidate.password" type="password" placeholder="请输入密码" :disabled="valid"></Input>
        </FormItem>
        <FormItem label="银行账户" prop="account">
          <Input v-model="formValidate.account" type="text" placeholder="请输入银行账户" :disabled="valid"></Input>
        </FormItem>
        <FormItem label="餐厅名称" prop="canteenName">
        <Input v-model="formValidate.name" placeholder="请输入餐厅名称" :disabled="valid"></Input>
        </FormItem>
        <FormItem label="所在地区" prop="canteenDistrict">
          <select3 ref="district"></select3>
        </FormItem>
        <FormItem label="餐厅地址" prop="address" id="address">
          <Select placeholder="请填写您要搜索的地址" filterable @on-query-change="changeQuery" @on-change="selectOne">
            <Option v-for="(item,index) in addresses" :value="index" :key="index">{{ item.detail }}</Option>
          </Select>
        </FormItem>
        <FormItem label="餐厅类型" prop="type">
          <Select v-model="formValidate.type" placeholder="请选择餐厅类型" :disabled="valid">
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
                <TimePicker type="time" placeholder="请选择开始时间" v-model="formValidate.beginTime" :disabled="valid"></TimePicker>
              </FormItem>
            </Col>
            <Col span="2" style="text-align: center">-</Col>
            <Col span="11">
              <FormItem prop="endTime">
                <TimePicker type="time" placeholder="请选择结束时间" v-model="formValidate.endTime" :disabled="valid"></TimePicker>
              </FormItem>
            </Col>
          </Row>
        </FormItem>
      </Form>
    </div>
</template>

<script>
  import select3 from '../register/AddressPicker'
    export default {
        name: "CanteenInfo",
      components:{select3},
      data () {
        return {
          addresses:[],
          detail: '',
          valid:false,
          formValidate: {
            hostName:'',
            idNumber:'',
            email:'',
            name: '',
            address:'',
            type:'',
            beginTime:'09：00：00',
            endTime:'21：00：00',
            password:'',
            province:'',
            city:'',
            area:'',
            account:'',
            lat:'',
            lng:''
          },
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
              console.log(re.data)
              this.addresses=re.data
            })
          }
        },
        selectOne(choose){
          this.detail=this.addresses[choose]
          this.formValidate.address=this.detail.detail
          this.formValidate.lat=this.detail.lat
          this.formValidate.lng=this.detail.lng
          console.log(this.formValidate)
        }
      }
    }
</script>

<style scoped>
</style>
