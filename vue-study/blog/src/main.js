// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from "element-ui"
import dayjs from  "dayjs";
import 'element-ui/lib/theme-chalk/index.css'
import './style/ali-icon.css'
import { quillEditor } from 'vue-quill-editor'
import axios from 'axios'
import VueAxios from 'vue-axios'
import Utils from './assets/js/utils'
axios.defaults.baseURL = 'http://localhost:8090'
dayjs.locale('zh-cn')
// dayjs.extend(rTime)

Vue.prototype.$axios=axios //修改Vue的原型属性

Vue.config.productionTip = false

Vue.use(ElementUI)

// dayjs的使用
export const relativeTime = value =>{
  return dayjs().to(dayjs(value))
}
export const formatTime=(value,format='YYYY-MM-DD hh:mm:ss')=>{
  return dayjs(value).format(format)
}

// 时间戳转换为date
// Vue.filter('dateFormat', (dataStr) => {
//   var time = new Date(dataStr);
//
//   function timeAdd0(str) {
//     if (str < 10) {
//       str = '0' + str;
//     }
//     return str
//   }
//   var y = time.getFullYear();
//   var m = time.getMonth() + 1;
//   var d = time.getDate();
//   var h = time.getHours();
//   var mm = time.getMinutes();
//   var s = time.getSeconds();
//   return y + '-' + timeAdd0(m) + '-' + timeAdd0(d) + ' ' + timeAdd0(h) + ':' + timeAdd0(mm) + ':' + timeAdd0(s);
// })

Vue.filter('dateFormat',formatTime)
Vue.filter('dateFormat2', (dataStr) => {
  var time = new Date(dataStr);

  function timeAdd0(str) {
    if (str < 10) {
      str = '0' + str;
    }
    return str
  }
  var y = time.getFullYear();
  var m = time.getMonth() + 1;
  var d = time.getDate();
  // var h = time.getHours();
  // var mm = time.getMinutes();
  // var s = time.getSeconds();
  return y + '-' + timeAdd0(m) + '-' + timeAdd0(d);
})
Vue.filter('commentLimit',(s)=>{
  if (s.toString().length>15)
    return s.substring(0,15).toString()+"......"
  else
    return s
})
new Vue({
  el: '#app',
  router,
  render: h => h(App),
})
