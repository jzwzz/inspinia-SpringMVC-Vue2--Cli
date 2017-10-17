import API from '../config.js'
import Vue from 'vue'
import { doError } from './api_doError.js'

export const Login = function(params) {
  return new Promise(function(resolve, reject) {
    Vue.http
      .post(
        API.login + '&username=' + params.name + '&password=' + params.password
      )
      .then(
        response => {
          // success callback
          let result = response.data
          console.log(result)
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

const LOGOUT_URL = 'logout'

