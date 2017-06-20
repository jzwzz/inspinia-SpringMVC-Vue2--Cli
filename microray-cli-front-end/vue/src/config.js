// 线上配置
let CONFIG
if (process.env.NODE_ENV === 'production') {
  CONFIG = {
    url: 'http://bigdatalab.microray/data-lab',
    casUrl: 'http://microray:8086',
    deploy: 'http://bigdatalab.microray'
  }
} else {
  CONFIG = {
    url: 'http://99.48.6.207:8080',
    casUrl: 'http://99.48.232.122:8280/mr-cas',
    deploy: 'http://99.48.6.207:9080'

  }
}
// 接口API根地址
const backend = CONFIG.url
const cas = CONFIG.casUrl
const frontend = CONFIG.deploy

module.exports = {
  /**
   * 通用状态码
   * */

  SYS_ERR: 'SYS_ERR', // api请求系统错误

  // 登录
  login: `${backend}/user/login`,
  doLogin: `${backend}/api/do_login`,
  backendCasLogin: `${backend}/user/casLogin?redirectUrl=` + encodeURIComponent(encodeURIComponent(`${frontend}/#/redirectLogin`)),
  casLogin: `${cas}/login?service=`,
  casLogout: `${cas}/logout`,
  loginStatus: `${backend}/user/loginStatus`

}
