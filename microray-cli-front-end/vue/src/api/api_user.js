import API from '../config.js'
import Vue from 'vue'
import { doError } from './api_doError.js'

export const GetUserByRole = function (role) {
  return new Promise(function (resolve, reject) {
    Vue.http.get(API.getUserByRole, {params: {roleId: role.split('_')[1]}})
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
export const ManagerRoleID = 'ROLE_RMG02'
export const PMRoleID = 'ROLE_RMG01'
export const DevRoleID = 'ROLE_RMG03'

// API.Mock.mock(API.getUserByRole + '?roleId=RTMG2', {
//   'respCode': '1001',
//   'data|1-10': [{
//     'employeeId': '@natural',
//     'employeeName': '@cname'
//   }]
// })

