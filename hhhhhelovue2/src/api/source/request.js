import request from "@/utils/request";

export const findSourceAll = () =>{
    return request({
        url: '/source/listAll',
        method: 'get',

    })
}

export const deletedById = (id) =>{
    return request({
        url: '/source/del/'+id,
        method: 'post'
    })
}

export const save = (source) =>{
    return request({
        url: '/source/reg',
        method: 'post',
        data: source,
    })
}

export const edit = (source) =>{
    return request({
        url: '/source/edit',
        method: 'post',
        data: source,
    })
}
