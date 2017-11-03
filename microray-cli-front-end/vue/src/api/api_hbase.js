import API from '../config.js'
import Vue from 'vue'
import { doError } from './api_doError.js'

export const QueryHBase = function (query) {
  return new Promise(function (resolve, reject) {
    let path
    if (query.type === 'scan') {
      path = API.scanHBase
    } else if (query.type === 'get') {
      path = API.getHBase
    }
    let paramMap = {}
    query.params.forEach(function (element) {
      paramMap[element.name] = element.value
    })
    console.log(paramMap)
    Vue.http.get(path, {params: paramMap})
      .then(
        response => {
          // success callback
          let result = response.data

          if (parseInt(result.respCode) === 1001) {
            resolve(result.data)
          } else {
            reject(doError(parseInt(result.respCode)))
          }
        },
        () => {
          reject(API.SYS_ERR)
        }
      )
  })
}
