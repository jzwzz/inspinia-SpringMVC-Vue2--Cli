# 场景营销运营平台上线计划（guardian）
> 2017年11月01日

### 功能
* 批量文件上传、手动上传文件、定时入库、手动入库
* 文件列表、文件详情显示
* 文件审核流程、异步放行、构造JsonMessage
* HBase 查询接口

### 端口检查
* 到数据库服务的端口
* 到Kafka集群的端口
* 到HBase集群的端口
* 到cas的端口
### 上线准备
```shell
	mkdir -p /opt/cmbccd/archive/system，mkdir -p /opt/cmbccd/archive/upload
```
* 放入样本文件`TEN_STORM_UPLOAD_REASON_DESC_20171011` 
* 数据库创建`CREATE DATABASE MR_RTM_GUARDIAN`
* 刷建表脚本s1_init_dll.sql
* Kafka topic创建，guardian-compensation
### 备份

### 放置上线包
将上线dist文件放入/opt/cmbccd/MrRTM-Guardian

### 启动项目

```shell
sudo -u tomcat nohup java -Xms5000m -Xmx5000m -jar MrRTM-Guardian.jar --spring.profiles.active=dev >/dev/null 2>&1 &
```
修改nginx 配置
```
location / {
	root   /opt/cmbccd/MrRTM-Guardian/dist/;
	index  index.html index.htm;
}
```
user tomcat/nginx
```
sudo -u tomcat nginx -s reload
```
## 验证部署
http://localhost:8080/archive/sample
http://localhost:3000/archive/load
查看/var/log/tomcat/MrRTM-Guardian/node01/run_info.log