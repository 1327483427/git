import request from '@/utils/request'

const api_name = '/admin/vod/teacher'

export default {
  pageList(page, limit, searchObj) {
    return request({
      url: `${api_name}/${page}/${limit}`,
      method: 'post',
      // data 传递json格式
      // param 传递一般格式
      data: searchObj
    })
  },
  removeById(id) {
    return request({
      url: `${api_name}/remove/${id}`,
      method: 'delete'
    })
  },
  saveTeacher(teacher) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: teacher
    })
  },

  getById(id) {
    return request({
      url: `${api_name}/get/${id}`,
      method: 'get'
    })
  },

  update(teacher) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: teacher
    })
  },
  batchRemove(idList) {
    return request({
      url: `${api_name}/batchRemove`,
      method: `delete`,
      data: idList
    })
  }
}
