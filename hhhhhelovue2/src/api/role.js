import request from "@/utils/request";

export const findUserList = (current,size) =>{
    return request({
        url: '/role/roleSearch',
        method: 'post',
        params: {
            current,
            size
        }
    })
}

export const deletedById = (id) =>{
    return request({
        url: '/role/'+id,
        method: 'post'
    })
}

export const reg = (role)=>{
    return request({
        url: '/role/reg',
        method: 'post',
        data: role
    })
}

export const updateById = (role) =>{
    return request({
        url: '/role/update',
        method: 'post',
        data: role
    })
}
