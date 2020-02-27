<template>
    <div>
      <select3 ref="district" style="width: 50%;float: left"></select3>
      <Select style="width: 50%;float: left" placeholder="请填写您要搜索的地址" filterable @on-query-change="changeQuery" @on-change="selectOne" v-model="detailAddress">
        <Option v-for="(item,index) in addresses" :value="index" :key="index">{{ item.detail }}</Option>
      </Select>
    </div>
</template>

<script>
  import select3 from '../register/AddressPicker'
    export default {
        name: "Address",
      components:{select3},
      data () {
        return {
          addresses:[],
          detail: '',
          detailAddress:0
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
            console.log("pppp")
            await this.$axios.post('/server/map/get/recommend',{province:this.$refs.district.result.province,city:this.$refs.district.result.city,area:this.$refs.district.result.area,detail:inputValue}).then(re=>{
              this.addresses=re.data
              console.log(this.addresses)
            })
          }
        },
        selectOne(choose){
          this.detail=this.addresses[choose]
        },
        initial(address){
          this.$refs.district.initialAddress(address.province,address.city,address.area)
          this.addresses=[address]
          this.detail=address
        }
      }
    }
</script>

<style scoped>

</style>
