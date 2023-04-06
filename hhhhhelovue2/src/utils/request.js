// import router from '@/router'
// import store from '@/store'
import axios from 'axios'
import Element from 'element-ui'
const request = axios.create({
//  baseURL: 'http://www.lhzzz-61.top/api',
 baseURL: 'http://localhost:8080',
 timeout: 3000
})

// 前置拦截，对请求进行数据进行操作
request.interceptors.request.use(config =>{
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    let user = sessionStorage.getItem("user")? JSON.parse(sessionStorage.getItem("user")) : null
    if(user){
        config.headers['Authorization'] = user.token;
    }
    return config
})
request.interceptors.response.use(response=>{
    let res = response.data
    if(res.code === 20000){
        return response
    } else{
        Element.Message.error(res.message,{duration: 2*1000});
        return Promise.reject(res.message)
    }

},
)
export default request
