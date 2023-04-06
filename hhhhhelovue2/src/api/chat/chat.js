import request from "@/utils/request";

export const findChatList = () =>{
    return request({
        url: '/chat/searchChat',
        method: 'post',
    })
}

export const findChatAll = () =>{
    return request({
        url: '/chat/findAll',
        method: 'post',
    })
}


export const deletedById = (id) =>{
    return request({
        url: '/chat/del/'+id,
        method: 'post'
    })
}

export const reg = (chat)=>{
    return request({
        url: '/chat/reg',
        method: 'post',
        data: chat
    })
}