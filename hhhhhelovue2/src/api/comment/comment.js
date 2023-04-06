import request from "@/utils/request"

export const findComment = (id) => {
    return request({
        url: 'comment/tree/'+id,
        method:'get'
    })
}

export const findAll = (current,size,) => {
    return request({
        url: 'comment/list',
        method:'post',
        params: {
            current,
            size
        },
    })
}

export const saveComment = (comment) => {
    return request({
        url: 'comment/reg',
        method:'post',
        data: comment,
    })
}

export const delComment = (id) => {
    return request({
        url: 'comment/del/'+id,
        method:'post',
    })
}