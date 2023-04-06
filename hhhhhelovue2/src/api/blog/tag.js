import request from "@/utils/request";

export const findTagList = () =>{
    return request({
        url: '/category/count',
        method: 'get',
    })
}


export const deletedById = (id) =>{
    return request({
        url: '/category/del/'+id,
        method: 'post'
    })
}

export const reg = (tag)=>{
    return request({
        url: '/category/add',
        method: 'post',
        data: tag
    })
}

export const tagTotal = ()=>{
    return request({
        url: '/category/total',
        method: 'get',
    })
}