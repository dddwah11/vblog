import request from "@/utils/request"

export const findList = () => {
    return request({
        url: '/file/list',
        method:'get',
    })
}
export const findListPhoto = () => {
    return request({
        url: '/file/listPhoto',
        method:'get',
    })
}
export const deletedById = (id) =>{
    return request({
        url: '/file/del/'+id,
        method: 'post'
    })
}



