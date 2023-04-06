import Vue from 'vue'
import App from './App.vue'
import request from '@/utils/request'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from  '@/router'
import  '@/assets/css/global.css'
import '@/assets/iconfont/iconfont.css'
import '@/assets/iconfont/iconfont.js'
import mavonEditor from'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import { vueBaberrage } from "vue-baberrage";
import 'animate.css'
import Viewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'
import APlayer from "@moefe/vue-aplayer";
import VueParticles from '@/lib/vue-particles'

Vue.use(VueParticles)
Vue.use(Viewer)
Vue.use(APlayer);
Vue.use(vueBaberrage);
Vue.config.productionTip = false
Vue.prototype.request=request
Vue.use(ElementUI);
Vue.use(mavonEditor);
new Vue({
  el: '#app',
  router,
  render: h => h(App),
}).$mount('#app')
