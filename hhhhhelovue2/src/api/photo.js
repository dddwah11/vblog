import request from "@/utils/request"

export const findList = () => {
    return request({
        url: '/photo/list',
        method:'get',

    })
}
export const deletedById = (id) =>{
    return request({
        url: '/photo/del/'+id,
        method: 'post'
    })
}

export const updateById = (photo) =>{
    return request({
        url: '/photo/edit',
        method: 'post',
        data: photo
    })
}

export const reg = (photos) =>{
    return request({
        url: '/photo/reg',
        method: 'post',
        data: photos
    })
}



