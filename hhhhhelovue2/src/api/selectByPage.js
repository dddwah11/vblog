import request from "@/utils/request";

export const findUserList = (current,size,userVo) =>{
    return request({
        url: '/user/page',
        method: 'post',
        params: {
            current,
            size
        },
        data: userVo,
    })
}

export const deletedById = (id) =>{
    return request({
        url: '/user/del/'+id,
        method: 'post'
    })
}

export const reg = (user)=>{
    return request({
        url: '/user/reg',
        method: 'post',
        data: user
    })
}

export const updateById = (user) =>{
    return request({
        url: '/user/update',
        method: 'post',
        data: user
    })
}
