import API from '../config.js'
import Vue from 'vue'

export const GetBookList = function () {
  return new Promise(function (resolve, reject) {
    Vue.http.get(API.bookList).then(
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
