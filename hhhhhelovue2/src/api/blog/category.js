import request from "@/utils/request"

export const getTagList = () =>{

    return request({
        url:'/tag/list',
        method: 'get'
    })
}

export const addTag = (tag) =>{

    return request({
        url:'/tag/add',
        method: 'post',
        data: tag
    })
}

export const deletedById = (id) =>{
    return request({
        url: '/tag/del/'+id,
        method: 'post'
    })
}

export const catTotal = ()=>{
    return request({
        url: '/tag/all',
        method: 'get',
    })
}