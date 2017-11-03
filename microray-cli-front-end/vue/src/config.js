// 线上配置
import config from './profile.js'
// 接口API根地址
const url = config.server

export default {
  // mock时 解注，将拦截所有http请求
  // Mock: require('mockjs'),
  SYS_ERR: 'SYS_ERR', // api请求系统错误
  // 登录
  login: `${url}/user/login?systemId=MR_RTMG`,
  doLogin: `${url}/api/do_login`,

  // archive
  archiveListByCreatorId: `${url}/archive/listByCreatorId`,
  archiveListByReviewId: `${url}/archive/listByReviewerId`,
  allArchive: `${url}/archive/list`,
  archiveById: `${url}/archive/id`,
  archiveDetailById: `${url}/archive/detail/id`,
  archiveDetailQuery: `${url}/archive/query`,
  logsByArchiveId: `${url}/workflow/list`,
  pmReviewArchive: `${url}/archive/projectManager/review/id/`,
  supervisorReviewArchive: `${url}/archive/superior/review/id/`,
  uploadArchive: `${url}/archive/upload`,
  downloadSample: `${url}/archive/sample`,
  loadArchive: `${url}/archive/load`,
  // user
  getUserByRole: `${url}/user/list`,
  // HBase qurey
  scanHBase: `${url}/hbase/scan`,
  getHBase: `${url}/hbase/get`
}

