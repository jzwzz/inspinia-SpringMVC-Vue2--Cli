
import Vue from 'vue'
// import {doError} from './api_doError.js'
let postSaveUrl = 'baseInfo'
let getAllScenariosUrl = 'scenarios'

export const postSave = function (params) {
  return new Promise(function (resolve, reject) {
    Vue.http.post(postSaveUrl, params).then((response) => {
      // success callback
      let result = response.data
      if (parseInt(result.respCode) === 1001) {
        resolve(result.data)
      } else {
        reject()
      }
    }, () => {
      reject()
    })
  })
}

export const getAllScenarios = function () {
  Vue.http.post(getAllScenariosUrl).then((response) => {
      // success callback
    return response.data
  })
}

