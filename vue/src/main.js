import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from "./store"
import axios from './axios'
import VueAxios from 'vue-axios'
// VueAxios 与 axios 的位置不能交换，否则出现 TypeError: Cannot read property 'protocol' of undefined
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.prototype.$axios = axios //
Vue.prototype.$store = store
Vue.config.productionTip = false

Vue.use(ElementUI);
Vue.use( VueAxios , axios)

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
