<template>
  <Select placeholder="请填写您要搜索的地址" filterable @on-query-change="changeQuery" @on-change="selectOne">
    <Option v-for="(item,index) in addresses" :value="index" :key="index">{{ item.detail }}</Option>
  </Select>
</template>

<script>
    export default {
        name: "SelectDetailAddress",
      data () {
        return {
          addresses:[],
          detail: '',
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
              await this.$axios.post('/server/map/get/recommend',{province:this.province,city:this.city,area:this.area,detail:inputValue}).then(re=>{
                this.addresses=re.data
              })
            }
          },
        selectOne(choose){
          this.detail=this.addresses[choose]
        },
      }
    }
</script>

<style scoped>

</style>
