// 线上配置
let CONFIG
if (process.env.NODE_ENV === 'production') {
  CONFIG = {
    url: 'http://bigdatalab.microray/data-lab'
  }
} else {
  CONFIG = {
    url: 'http://localhost:8080/'
  }
}
// 接口API根地址
const url = CONFIG.url

module.exports = {
  /**
   * 通用状态码
   * */

  SYS_ERR: 'SYS_ERR', // api请求系统错误

  // 登录
  login: `${url}/user/login?systemId=BDLPortal`,
  doLogin: `${url}/api/do_login`,

  // 获取资源
  getResource: `${url}/resource/type`,
  // 获取数据表详情
  getResourceDetail: `${url}/resource/data/table`,
  // 提交申请
  postApply: `${url}/process/apply`,

  getApplyList: `${url}/process/application/list/userId`,

  getApplyDetail: `${url}/process/application/applyId`,

  getApplyLogsByLogId: `${url}/process/log/list/applyId`,

  uploadFile: `${url}/upload/file`,

  // 审核流程
  getProcessLogByUserId: `${url}/process/log/user/userId`,

  // 提交审核
  postApprove: `${url}/process/approve`,

  // 获取审核用户
  getApproveUsers: `${url}/user/approve`,
  // fileUrlPrefix
  fileUrlPrefix: `${url}`
}
