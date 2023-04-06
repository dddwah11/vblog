import request from "@/utils/request";

export const findBlogList = (current,size,article) =>{
    return request({
        url: '/blog/list',
        method: 'post',
        params: {
            current,
            size
        },
        data: article,
    })
}


export const findBlog = (current,size) =>{
    return request({
        url: '/blog/page',
        method: 'post',
        params: {
            current,
            size
        },
    })
}

export const findAllBlog = () =>{
    return request({
        url: '/blog/findAllBlog',
        method: 'get',

    })
}

export const deletedById = (id) =>{
    return request({
        url: '/blog/del/'+id,
        method: 'post'
    })
}

export const searchId = (id) =>{
    return request({
        url: '/blog/'+id,
        method: 'get'
    })
}

export const save = (blog) =>{
    return request({
        url: '/blog/save',
        method: 'post',
        data: blog,
    })
}

export const edit = (blog) =>{
    return request({
        url: '/blog/edit',
        method: 'post',
        data: blog,
    })
}

export const findByCa = (catagoryId) =>{
    return request({
        url: '/blog/findByCa/'+catagoryId,
        method: 'get',
        params: {
            catagoryId
        },
    })
}

export const findByTag = (tagId) =>{
    return request({
        url: '/blog/findByTag/'+tagId,
        method: 'get',
        params: {
            tagId
        },
    })
}