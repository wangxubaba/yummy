import Vue from 'vue'
import Router from 'vue-router'
import CustomerRegisterPage from '@/pages/customer/CustomerRegisterPage'
import CanteenRegisterPage from '@/pages/canteen/CanteenRegisterPage'
import LoginPage from '@/pages/LoginPage'
import HomePage from '@/pages/HomePage'
import TimelyDetailsPage from '@/pages/canteen/TimelyDetailsPage'
import ManageMenuPage from '@/pages/canteen/ManageMenuPage'
import CalculateStatisticsPage from '@/pages/canteen/CalculateStatisticsPage'
import DisplayCanteenInfoPage from '@/pages/canteen/DisplayCanteenInfoPage'
import SearchFoodPage from '@/pages/customer/SearchFoodPage'
import MyOrderPage from '@/pages/customer/MyOrderPage'
import MySettingsPage from '@/pages/customer/MySettingsPage'
import MyStatisticsPage from '@/pages/customer/MyStatisticsPage'
import CheckCanteenPage from '@/pages/manager/CheckCanteenPage'
import PayCanteenPage from '@/pages/manager/PayCanteenPage'
import ViewStatisticsPage from '@/pages/manager/ViewStatisticsPage'


import test from '@/pages/Test'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: '/yummy',
  routes: [
    {
      path:'/customer/register',
      name:'CustomerRegisterPage',
      component:CustomerRegisterPage
    },
    {
      path:'/canteen/register',
      name:'CanteenRegisterPage',
      component:CanteenRegisterPage
    },
    {
      path: '/login',
      name: 'LoginPage',
      component: LoginPage
    },
    {
      path:'/home',
      name:'HomePage',
      component:HomePage
    },
    {
      path:'/canteen/timely',
      name:'TimelyDetailsPage',
      component:TimelyDetailsPage
    },
    {
      path:'/canteen/menu',
      name:'ManageMenuPage',
      component:ManageMenuPage
    },
    {
      path:'/canteen/statistics',
      name:'CalculateStatisticsPage',
      component:CalculateStatisticsPage
    },
    {
      path:'/canteen/info',
      name:'DisplayCanteenInfoPage',
      component:DisplayCanteenInfoPage
    },
    {
      path:'/customer/food',
      name:'SearchFoodPage',
      component:SearchFoodPage
    },
    {
      path:'/customer/order',
      name:'MyOrderPage',
      component:MyOrderPage
    },
    {
      path:'/customer/settings',
      name:'MySettingsPage',
      component:MySettingsPage
    },
    {
      path:'/customer/statistics',
      name:'MyStatisticsPage',
      component:MyStatisticsPage
    },
    {
      path:'/manager/check',
      name:'CheckCanteenPage',
      component:CheckCanteenPage
    },
    {
      path:'/manager/pay',
      name:'PayCanteenPage',
      component:PayCanteenPage
    },
    {
      path:'/manager/statistics',
      name:'ViewStatisticsPage',
      component:ViewStatisticsPage
    },



    {
      path:'/test',
      name:'Test',
      component: test
    }
  ]
})
