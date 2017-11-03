<template>
  <div>
    <div class="ibox float-e-margins">
      <div class="ibox-title">
        <h5>审核进度</h5>
      </div>
      <div class="ibox-content">
        <el-card>
          <el-row class="mid-nav">
            <el-steps :space="300"
                      :active="activeStep">
              <el-step title="步骤 1"
                       description="新提交"></el-step>
              <el-step title="步骤 2"
                       description="主管审核"></el-step>
              <el-step title="步骤 3"
                       description="放行通过"></el-step>
            </el-steps>
          </el-row>
        </el-card>
      </div>
      <div class="ibox-title"><h5>批次概览</h5></div>
      <div class="ibox-content">
        <el-card>
          <el-table :data="summaryData">
            <el-table-column prop="fileName" label="批次名" min-width="120px">
            </el-table-column>
            <el-table-column prop="creatorName" label="创建者">
            </el-table-column>
            <el-table-column prop="batchDate" label="创建日期">
            </el-table-column>
            <el-table-column prop="createDateTime" label="上传时间">
            </el-table-column>
            <el-table-column prop="sendDateTime" label="放行时间">
            </el-table-column>
            <el-table-column prop="size" label="行数">
            </el-table-column>
            <el-table-column prop="status" label="状态">
            </el-table-column>
          </el-table>
        </el-card>
      </div>
      <div class="ibox-title"><h5>消息明细</h5></div>
      <div class="ibox-content">
        <el-card>
          <el-table :data="this.pageData.content"
                    :height="450"
                    style="width: 100%"
                    stripe
                    :fit=true
                    :default-sort="{prop: 'approvalTimestamp', order: 'descending'}"
                    border>
            <el-table-column prop="sysId" label="客户Id" sortable min-width="80px">
            </el-table-column>
            <el-table-column prop="cardNumber" label="卡号" min-width="130px">
            </el-table-column>
            <el-table-column prop="transactionCode" label="交易">
              <el-table-column prop="transactionCode" label="码" min-width="35px" sortable>
              </el-table-column>
              <el-table-column prop="transactionAmount" label="金额" sortable>
              </el-table-column>
              <el-table-column prop="transactionType" label="类型" min-width="45px">
              </el-table-column>
            </el-table-column>
            <el-table-column prop="messageType" label="消息类型" sortable min-width="50px">
            </el-table-column>
            <el-table-column prop="approvalCode" label="授权码" sortable>
            </el-table-column>
            <el-table-column prop="responseCode" label="返回码" sortable min-width="46px">
            </el-table-column>
            <el-table-column prop="fillerField" label="预留" min-width="30px">
            </el-table-column>
            <el-table-column prop="accountType" label="账户类型" sortable min-width="45px">
            </el-table-column>
            <el-table-column prop="retailerId" label="商户号" min-width="135px" sortable>
            </el-table-column>
            <el-table-column prop="approvalTimestamp" label="授权时间" min-width="115px" sortable>
            </el-table-column>
            <el-table-column prop="sequenceNumber" label="流水号" min-width="125px">
            </el-table-column>
            <el-table-column prop="proxyApprovalFlag" label="预授权标识" min-width="40px">
            </el-table-column>
          </el-table>
          <div>
            <el-pagination
              layout="sizes,total, prev, pager, next"
              :current-page.sync="currentPage"
              :page-sizes="[50,100,200,400]"
              @size-change="handleSizeChange"
              :page-size="pageSize"
              :total="this.pageData.totalElements">
            </el-pagination>
          </div>
        </el-card>
      </div>
      <div v-if="this.logs.length>0">
        <div class="ibox-title"><h5>审核流水</h5></div>
        <div class="ibox-content">
          <el-card>
            <el-table :data="logs"
                      style="width: 100%">
              <el-table-column prop="opUserName"
                               label="处理人"
                               width="100">
              </el-table-column>
              <el-table-column prop="opDateTime"
                               label="处理时间"
                               width="180">
              </el-table-column>
              <el-table-column prop="opType"
                               label="处理结果"
                               width="180">
              </el-table-column>
              <el-table-column prop="remark"
                               label="处理意见">
              </el-table-column>
            </el-table>
          </el-card>

        </div>
      </div>
      <div>
        <el-card
          v-if="(status==='待审核'　&&　currentRole.roleId ==='ROLE_RMG01' )||(status==='待放行' && currentRole.roleId ==='ROLE_RMG02')">
          <el-row>
            <div style="display: inline">
              <el-input style="width: inherit" size='large' v-model="form.remark"
                        placeholder="请输入处理意见">
              </el-input>
              <el-select v-if="status==='待审核'" v-model="form.reviewerId"
                         placeholder="下一步审核人员">
                <el-option v-for="item in managers"
                           :key="item.employeeId"
                           :label='item.employeeId+"/"+item.employeeName'
                           :value="item.employeeId">
                </el-option>
              </el-select>

              <el-button @click='openDialog(true)'
                         size="small"
                         type="info">同意
              </el-button>
              <el-button @click='openDialog(false)'
                         size="small"
                         type="danger">拒绝
              </el-button>
            </div>
          </el-row>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script>
  import config from '../../config'
  import Vue from 'vue'
  import { GetArchiveById, GetArchiveDetailById, GetLogsByArchiveId, ReviewArchive } from '../../api/api_archive'
  import { GetUserByRole, ManagerRoleID } from '../../api/api_user'

  export default {
    data: function () {
      return {
        id: this.$route.params.id,
        summaryData: [],
        pageData: [],
        currentRole: this.$store.state.currentRole,
        currentPage: 1,
        pageSize: 50,
        logs: [],
        managers: '',
        form: {
          remark: '',
          archiveId: this.$route.params.id,
          opUserId: this.$store.state.user.employeeId,
          opUserName: this.$store.state.user.employeeName,
          accept: true,
          reviewerId: '',
          reviewerName: ''
        },
        stepMap: ['作废', '待审核', '待放行', '放行通过']
      }
    },
    created() {
      this.getArchiveById(this.id)
      this.getArchiveDetailById(this.id, this.currentPage, this.pageSize)
      this.getLogsByArchiveId(this.id)
      this.getManager()
    },
    methods: {
      colorState(state) {
        const states = {
          '待审核': 'gray',
          '待放行': 'primary',
          '放行成功': 'success',
          '作废': 'gray'
        }
        return states[state]
      },
      getArchiveById(id) {
        GetArchiveById(id).then((data) => {
          this.summaryData = []
          this.summaryData.push(data)
          return data
        })
      },
      getArchiveDetailById(id, page, pageSize) {
        GetArchiveDetailById(id, page, pageSize).then((data) => {
          this.pageData = data
          return data
        })
      },
      getLogsByArchiveId(id) {
        GetLogsByArchiveId(id).then((data) => {
          this.logs = data
          return data
        })
      },
      getManager() {
        GetUserByRole(ManagerRoleID).then((data) => {
          this.managers = data
        })
      },
      reviewArchive(accept) {
        const _this = this
        _this.form.accept = accept
        if (_this.status === '待审核') {
          _this.form.step = 1
          if (_this.form.reviewerId === '') {
            _this.$message.error('校验失败，请选择审核人')
            return
          }
          ReviewArchive(_this.id, _this.form).then((data) => {
            _this.$message.success('处理成功')
            _this.getLogsByArchiveId(_this.id)
            _this.getArchiveById(_this.id)
          })
        } else if (_this.status === '待放行') {
          _this.form.step = 2
          ReviewArchive(_this.id, _this.form).then((data) => {
            if (data.totalRecords > 0) {
              _this.$message.success('恭喜：总共放行' + data.totalRecords + '条，其中失败了' + data.failedRecords + '条')
            } else {
              _this.$message.success('处理成功')
            }
            _this.getLogsByArchiveId(_this.id)
            _this.getArchiveById(_this.id)
          }).catch(function (error) {
            console.log(error)
            _this.$message.error('放行失败，请稍后再试')
          })
        }
      },
      handleSizeChange(val) {
        this.pageSize = val
      },
      openDialog(accept) {
        this.$confirm('此操作不可逆, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.reviewArchive(accept)
        }).catch((e) => {
          console.log(e)
          this.$message({
            type: 'info',
            message: '已取消'
          })
        })
      }
    },
    watch: {
      currentPage: function (val) {
        this.getArchiveDetailById(this.id, val, this.pageSize)
      },
      pageSize: function (val) {
        this.getArchiveDetailById(this.id, this.currentPage, val)
      },
      'form.reviewerId': function (val) {
        this.form.reviewerName = this.managers.filter(e => e.employeeId === val)[0].employeeName
      }
    },
    computed: {
      status: function (val) {
        if (this.summaryData.length > 0) {
          return this.summaryData[0].status
        }
      },
      activeStep: function () {
        let status = this.status
        if (status) {
          return this.stepMap.indexOf(status)
        }
      }
    }
  }
</script>

<style>
  .mid-nav {
    vertical-align: middle;
    margin-left: 20%;
  }
</style>

