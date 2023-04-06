import request from "@/utils/request";

export const login = (user)=>{
    return request({
        url: '/user/login',
        method: 'post',
    data: user
    })
}

export const logout = ()=>{
    return request({
        url: '/user/logout',
        method: 'post',
    })
}