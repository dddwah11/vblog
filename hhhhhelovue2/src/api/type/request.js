import request from "@/utils/request";

export const findAll = () =>{
    return request({
        url: '/type/listAll',
        method: 'get',

    })
}

export const deletedById = (id) =>{
    return request({
        url: '/type/del/'+id,
        method: 'post'
    })
}

export const save = (type) =>{
    return request({
        url: '/type/reg',
        method: 'post',
        data: type,
    })
}

export const edit = (type) =>{
    return request({
        url: '/type/edit',
        method: 'post',
        data: type,
    })
}
