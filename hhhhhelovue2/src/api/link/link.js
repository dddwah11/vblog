import request from "@/utils/request"

export const getLinkList = () =>{

    return request({
        url:'/link/list',
        method: 'get'
    })
}

export const addLink = (link) =>{

    return request({
        url:'/link/reg',
        method: 'post',
        data: link
    })
}

export const deletedById = (id) =>{
    return request({
        url: '/link/del/'+id,
        method: 'post'
    })
}
