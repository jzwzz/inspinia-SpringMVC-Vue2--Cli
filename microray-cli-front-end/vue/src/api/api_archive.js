import API from '../config.js'
import Vue from 'vue'
import { doError } from './api_doError.js'

export const GetArchiveListByCreatorId = function (creatorId) {
  return new Promise(function (resolve, reject) {
    Vue.http.get(API.archiveListByCreatorId, {
      params: {
        creatorId: creatorId
      }
    }).then(
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
export const GetArchiveListByReviewId = function (reviewerId) {
  return new Promise(function (resolve, reject) {
    Vue.http.get(API.archiveListByReviewId, {
      params: {
        reviewerId: reviewerId
      }
    }).then(
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
export const GetArchiveById = function (id) {
  return new Promise(function (resolve, reject) {
    Vue.http.get(API.archiveById.replace('id', id)).then(
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
export const GetAllArchive = function () {
  return new Promise(function (resolve, reject) {
    Vue.http.get(API.allArchive).then(
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
export const GetArchiveDetailById = function (id, page, pageSize) {
  return new Promise(function (resolve, reject) {
    Vue.http.get(API.archiveDetailById.replace('id', id), {
      params: {
        page: page - 1,
        size: pageSize
      }
    }).then(
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
export const GetLogsByArchiveId = function (id) {
  return new Promise(function (resolve, reject) {
    Vue.http.get(API.logsByArchiveId, {
      params: {
        archiveId: id
      }
    }).then(
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

export const ReviewArchive = function (id, params) {
  return new Promise(function (resolve, reject) {
    let path
    if (params.step === 1) {
      path = API.pmReviewArchive
    } else {
      path = API.supervisorReviewArchive
    }
    Vue.http.post(path.replace('id', id), params).then(
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
export const GetArchiveDetailBySysId = function (sysId) {
  return new Promise(function (resolve, reject) {
    Vue.http.get(API.archiveDetailQuery, {params: {sysId: sysId}}).then(
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

export const LoadArchive = function () {
  return new Promise(function (resolve, reject) {
    Vue.http.get(API.loadArchive).then(
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

export const UploadArchive = API.uploadArchive
export const DownloadSample = API.downloadSample
// API.Mock.mock(API.archiveListByCreatorId, {
//   'respCode': '1001',
//   'data|1-30': [{
//     'id|+1': 1,
//     'fileName': '@natural',
//     'creator': '@cname',
//     'batchDate': '@datetime("yyyy-MM-dd")',
//     'createDateTime': '@datetime("yyyy-MM-dd HH:mm:ss")',
//     'sendDateTime': '@datetime("yyyy-MM-dd HH:mm:ss")',
//     'size|1000-100000': 1000,
//     'status|1': ['待审核', '待放行']
//   }]
// })
//
// API.Mock.mock(API.archiveById.replace('id', '1'), {
//   'respCode': '1001',
//   'data': {
//     'id|+1': 1,
//     'fileName': '@natural',
//     'creator': '@cname',
//     'batchDate': '@datetime("yyyy-MM-dd")',
//     'createDateTime': '@datetime("yyyy-MM-dd HH:mm:ss")',
//     'sendDateTime': '@datetime("yyyy-MM-dd HH:mm:ss")',
//     'size|1000-100000': 1000,
//     'status|1': ['待审核', '待放行']
//   }
// })
//
// API.Mock.mock('http://99.48.6.207:8080/archive/detail/1?page=1&size=100', {
//   'respCode': '1001',
//   'data': {
//     'content|20': [{
//       'batchId': 1,
//       'cardNumber': /\d{16}/,
//       'transactionCode|10-100': 10,
//       'transactionAmount|1-10000.2': 1,
//       'messageType': /\d{4,10}/,
//       'approvalCode': /\d{5,10}/,
//       'responseCode': /\d{2,10}/,
//       'fillerField': /\d{2,10}/,
//       'accountType': /\d{2,10}/,
//       'retailerId': /\d{2,10}/,
//       'sysId': /\d{10,10}/,
//       'transactionType': /\d{2,10}/,
//       'approvalTimestamp': '@datetime("yyyyMMdd")',
//       'sequenceNumber': /\d{18}/
//     }],
//     'last': false,
//     'totalPages': 200,
//     'totalElements': 2000,
//     'size': 50,
//     'number': 0,
//     'first': true,
//     'numberOfElements': 10
//   }
// })
// API.Mock.mock(API.logsByArchiveId.replace('id', 1), {
//   'respCode': '1001',
//   'data|3': [{
//     'id|+1': 1,
//     'archiveId|1-10': 1,
//     'userId': /\d{5,10}/,
//     'remark': '@csentence',
//     'operation|1': ['检核通过', '放行通过', '此批作废'],
//     'operationDateTime': '@datetime("yyyy-MM-dd HH:mm:ss")'
//   }]
// })
