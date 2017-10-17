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

export default {
  /**
   * 通用状态码
   * */

  SYS_ERR: 'SYS_ERR', // api请求系统错误

  // 登录
  login: `${url}/user/login?systemId=BDLPortal`,
  doLogin: `${url}/api/do_login`

}
