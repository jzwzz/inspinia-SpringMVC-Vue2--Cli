<template>
  <div>
    <bread-crumb :paths="breadcrumbs" :title="title"></bread-crumb>
    <div class=" wrapper wrapper-content animated fadeInRight">
      <div class="row">
        <div class="col-md-12">
          <div class="ibox float-e-margins">
            <el-tabs v-model="activeName">
              <el-tab-pane label="手工补录" name="upload"></el-tab-pane>
              <el-tab-pane label="消息查询" name="query"></el-tab-pane>
              <el-tab-pane v-if="this.$store.state.currentRole.roleId===roleId" label="高级查询" name="advance"></el-tab-pane>
              <el-tab-pane v-if="this.$store.state.currentRole.roleId===roleId" label="手动调用"
                           name="manual"></el-tab-pane>
            </el-tabs>
            <div class="ibox-content" v-if="activeName==='upload'">
              <el-card style="text-align:center">
                <el-form>
                  <el-form-item label="下载数据模板">
                    <a :href=downloadSamplePath>点击下载</a>
                  </el-form-item>
                  <el-form-item label="上传数据文件"
                                prop="files">
                    <el-upload class="upload-demo"
                               ref="upload"
                               :data=this.$store.state.user
                               :action='action'
                               :headers="{Authorization:'Bearer '+this.$store.state.user.token}"
                               :auto-upload=false
                               :before-upload="validateFile"
                               :on-success='handleResponse'>
                      <el-button slot="trigger" type="primary">选取文件</el-button>
                      <el-button type="success" @click="submitUpload">确认上传
                      </el-button>
                      <div slot="tip"
                           class="el-upload__tip">请严格按照模板进行命名，排版数据，必须由5个“_”分隔，且最后一项为日期如：20170808
                      </div>
                    </el-upload>
                  </el-form-item>
                </el-form>
              </el-card>
            </div>
            <div class="ibox-content" v-if="activeName==='query' || activeName==='advance'">
              <el-row v-if="activeName==='query'">
                <el-card>
                  <div style="display: inline">
                    <el-input style="width: 60%" v-model="query.sysId" size="large" :autofocus=true
                              placeholder="请输入sysId，查询该用户待补录、已处理（起始日期之后）的消息详情，以免重复补录"></el-input>
                    <span>起始日期</span>
                    <el-date-picker
                      v-model="query.startDate"
                      type="datetime"
                      align="right"
                      size="large"
                      value-format="yyyyMMddHHmmss"
                      placeholder="起始日期">
                    </el-date-picker>
                    <el-button style="float: right" @click="getDetailBySysId" size="large" type="primary">点我查询
                    </el-button>
                  </div>
                </el-card>
              </el-row>
              <el-row v-if="activeName==='advance'">
                <el-card>
                  <div style="display: inline">
                    <el-row>
                      <el-select style="width: 10%" size="large" v-model="queryType"
                                 placeholder="查询类型">
                        <el-option v-for="item, index in queries"
                                   :key="index"
                                   :label='item.type'
                                   :value="item.type">
                        </el-option>
                      </el-select>
                      <el-input v-for="param,index in currentQuery.params" :key=index style="width: 20%"
                                v-model="param.value" size="large"
                                :placeholder=param.name></el-input>
                      <el-button style="float: right" @click="getHBaseDetail" size="large" type="primary">点我查询
                      </el-button>
                    </el-row>
                    <el-row>
                      <el-tag
                        v-for="tag in tables"
                        :key="tag.name">
                        {{tag.name}}
                      </el-tag>
                    </el-row>
                  </div>
                </el-card>
              </el-row>
              <el-row>
                <el-card v-if="guardianData.length>0">
                  <h4>待补消息
                    <el-tooltip content="运营平台中待审核与待放行批次中的消息" placement="top"><i class="el-icon-question"></i>
                    </el-tooltip>
                  </h4>

                  <el-table
                    :data=guardianData
                    :height="450"
                    style="width: 100%"
                    stripe
                    :fit=true
                    v-loading="guardianDataLoading"
                    element-loading-text="拼命加载中"
                    :default-sort="{prop: 'approvalTimestamp', order: 'descending'}"
                    border>
                    <el-table-column prop="sysId" label="客户Id" sortable min-width="118px">
                    </el-table-column>
                    <el-table-column prop="cardNumber" label="卡号" min-width="165px">
                    </el-table-column>
                    <el-table-column prop="transactionCode" label="交易">
                      <el-table-column prop="transactionCode" label="码" min-width="55px" sortable>
                      </el-table-column>
                      <el-table-column prop="transactionAmount" label="金额" min-width="80px" sortable>
                      </el-table-column>
                      <el-table-column prop="transactionType" label="类型" min-width="55px">
                      </el-table-column>
                    </el-table-column>
                    <el-table-column prop="messageType" label="消息类型" sortable min-width="70px">
                    </el-table-column>
                    <el-table-column prop="approvalCode" label="授权码" sortable min-width="85px">
                    </el-table-column>
                    <el-table-column prop="responseCode" label="返回码" sortable min-width="66px">
                    </el-table-column>
                    <el-table-column prop="fillerField" label="预留" min-width="40px">
                    </el-table-column>
                    <el-table-column prop="accountType" label="账户类型" sortable min-width="55px">
                    </el-table-column>
                    <el-table-column prop="retailerId" label="商户号" min-width="155px" sortable>
                    </el-table-column>
                    <el-table-column prop="approvalTimestamp" label="授权时间" min-width="135px" sortable>
                    </el-table-column>
                    <el-table-column prop="sequenceNumber" label="流水号" min-width="135px">
                    </el-table-column>
                    <el-table-column prop="proxyApprovalFlag" label="预授权标识" min-width="70px">
                    </el-table-column>
                  </el-table>
                </el-card>
              </el-row>
              <el-row>
                <el-card v-if="stormData.length>0">
                  <h4>已处理消息
                    <el-tooltip content="该用户在起始时间之后被十元风暴后台处理，并存储在处理库中的消息（包括补偿消息和正常消息,默认显示最近100条）" placement="top"><i
                      class="el-icon-question"></i>
                    </el-tooltip>
                  </h4>
                  <el-collapse>
                    <el-collapse-item v-for="row in stormData " :title=row.rowKey>
                      <el-table :data="row.columnViewList">
                        <el-table-column prop="columnName" label="列名">
                        </el-table-column>
                        <el-table-column prop="value" label="值">
                          <template slot-scope="scope">
                            <div slot="reference" class="name-wrapper">
                              <tree-view :data=(JSON.parse(scope.row.value)) :options="{maxDepth: 1}"></tree-view>
                            </div>
                          </template>
                        </el-table-column>
                      </el-table>
                    </el-collapse-item>
                  </el-collapse>
                </el-card>
              </el-row>
            </div>
            <div class="ibox-content" v-if="activeName==='manual'">
              <el-card style="text-align:center">
                <el-button @click="loadArchive" type="primary">手动调用，同步补偿文件到系统</el-button>
              </el-card>
            </div>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import { UploadArchive, DownloadSample, GetArchiveDetailBySysId, LoadArchive } from '../../api/api_archive'
  import { DevRoleID } from '../../api/api_user'
  import { QueryHBase } from '../../api/api_hbase'

  export default {
    data: function () {
      return {
        title: '补偿批次管理',
        breadcrumbs: [
          {
            name: '运营平台',
            url: '/'
          },
          {
            name: '工具箱',
            url: '/tools'
          }
        ],
        activeName: 'upload',
        files: [],
        action: UploadArchive,
        downloadSamplePath: DownloadSample,
        guardianData: [],
        guardianDataLoading: false,
        stormData: [],
        stormDataLoading: false,
        query: {
          sysId: '',
          startDate: ''
        },
        roleId: DevRoleID,
        queryType: 'scan',
        queries: [{
          id: '1',
          type: 'scan',
          params: [{name: 'tableName', value: 'ams:message_detail', required: true},
            {name: 'startKey', value: '', required: true},
            {name: 'endKey', value: ''},
            {name: 'limit', value: 1}]
        }, {
          id: '2',
          type: 'get',
          params: [{name: 'tableName', value: 'ams:message_detail', required: true},
            {name: 'rowKeyList', value: '', required: true}]
        }],
        tables: [
          { name: 'ams:message_detail', type: '' },
          { name: 'ams:consecutive_progress', type: 'success' },
          { name: 'ams:scenario_record', type: 'info' },
          { name: 'ams:rtm_consecutive_scenario', type: 'warning' },
          { name: 'ams:consecutive_scenario_qualified_record ', type: 'danger' },
          { name: 'ams:rtm_definition  ', type: 'danger' }
        ]
      }
    },
    methods: {
      handleResponse: function (response) {
        let _this = this
        if (!response.success) {
          _this.$message.error(response.respMsg)
        } else {
          _this.$message.success('上传文件成功')
        }
      },
      loadArchive() {
        const _this = this
        LoadArchive().then(() => {
          _this.$message.success('同步成功')
        })
      },
      submitUpload() {
        this.$refs.upload.submit()
      },
      validateFile: function (file) {
        let _this = this
        if (file.name.split('_').length !== 6) {
          _this.$message.error('文件名格式请保持与模板一致')
          return false
        }
      },
      getDetailBySysId() {
        if (!this.query.sysId) {
          this.$message.error('sysId 不能为空')
          return
        }
        let _this = this
        _this.guardianDataLoading = true
        GetArchiveDetailBySysId(_this.query.sysId).then((response) => {
          _this.guardianData = response
          _this.guardianDataLoading = false
        })
        _this.currentQuery.params = [{name: 'tableName', value: 'ams:message_detail', required: true},
          {name: 'startKey', value: _this.query.sysId + _this.query.startDate, required: true},
          {name: 'endKey', value: _this.query.sysId + 3},
          {name: 'limit', value: 100}]
        _this.getHBaseDetail()
      },
      validateQuery(params) {
        return params.filter(param => {
          return param.required ? param.value.trim() === '' : false
        }).length > 0
      },
      getHBaseDetail() {
        if (this.validateQuery(this.currentQuery.params)) {
          this.$message.error('请检查参数，tableName,StartKey,及rowKey不能为空')
        } else {
          QueryHBase(this.currentQuery).then((data) => {
            this.stormData = data
          })
        }
      }
    },
    computed: {
      currentQuery: function () {
        return this.queries.filter(query => query.type === this.queryType)[0]
      }
    }
  }
</script>
<style>
  .el-upload__input {
    display: none !important;
  }


</style>
