// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
import axios from 'axios'
import VDistpicker from 'v-distpicker'
import echarts from 'echarts'

Vue.prototype.$axios = axios
axios.defaults.withCredentials = false

Vue.config.productionTip = false
Vue.use(iView)

Vue.component('v-distpicker', VDistpicker)

Vue.prototype.$echarts = echarts

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
