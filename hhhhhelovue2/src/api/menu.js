import request from "@/utils/request";

export const deletedById = (id) =>{
    return request({
        url: '/menu/'+id,
        method: 'post'
    })
}

export const reg = (menu)=>{
    return request({
        url: '/menu/reg',
        method: 'post',
        data: menu
    })
}

export const updateById = (menu) =>{
    return request({
        url: '/menu/update',
        method: 'post',
        data: menu
    })
}

export const findAll = () =>{
    return request({
        url: '/menu/findAll',
        method: 'get',
    })
}
