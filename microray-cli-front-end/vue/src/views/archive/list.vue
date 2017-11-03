<template>
  <div class="ibox float-e-margins">
    <div class="ibox-title">
      <h5>批次列表</h5>
    </div>
    <div class="ibox-content">
      <el-table :data="currentTableData">
        <el-table-column prop="fileName" label="批次名" min-width="220px">
        </el-table-column>
        <el-table-column prop="creatorName" label="创建者" sortable>
        </el-table-column>
        <el-table-column prop="batchDate" label="创建日期" sortable min-width="120px">
        </el-table-column>
        <el-table-column prop="createDateTime" label="上传时间" sortable min-width="180px">
        </el-table-column>
        <el-table-column prop="sendDateTime" label="放行时间" min-width="180px">
        </el-table-column>
        <el-table-column prop="size" label="行数" sortable>
        </el-table-column>
        <el-table-column prop="status" sortable label="状态">
          <template slot-scope="scope">
            <div slot="reference" class="name-wrapper">
              <el-tag :type='colorState(scope.row.status)'>{{scope.row.status}}
              </el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <router-link :to="{ name: 'archive-detail', params: {id: scope.row.id}}">
              <el-button size="small">查看
              </el-button>
            </router-link>
          </template>
        </el-table-column>
      </el-table>
      <div>
        <el-pagination
          layout="total, prev, pager, next"
          :current-page.sync="currentPage"
          :page-size=pageSize
          :total="total">
        </el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
  import config from '../../config'
  import Vue from 'vue'
  import { GetArchiveListByCreatorId, GetArchiveListByReviewId, GetAllArchive } from '../../api/api_archive'
  import { PMRoleID, ManagerRoleID } from '../../api/api_user'

  export default {
    data: function () {
      return {
        pageData: [],
        currentPage: 1,
        pageSize: 10
      }
    },
    mounted() {
      this.getArchiveList(this.$store.state.currentRole.roleId, this.$store.state.user.employeeId)
    },
    methods: {
      colorState(state) {
        const states = {
          '作废': 'gray',
          '待审核': 'danger',
          '待放行': 'danger',
          '放行通过': 'success'
        }
        return states[state]
      },
      getArchiveList(roleId, userId) {
        if (roleId === PMRoleID) {
          GetArchiveListByCreatorId(userId).then((response) => {
            this.pageData = response
            return response
          })
        } else if (roleId === ManagerRoleID) {
          GetArchiveListByReviewId(userId).then((response) => {
            this.pageData = response
            return response
          })
        } else {
          GetAllArchive().then((response) => {
            this.pageData = response
            return response
          })
        }
      }
    },
    computed: {
      total: function () {
        return this.pageData.length
      },
      currentTableData: function () {
        let currentPage = this.currentPage
        let end = currentPage * this.pageSize >= this.total ? this.total : currentPage * this.pageSize
        if (this.pageData) {
          return this.pageData.slice((currentPage - 1) * this.pageSize, end)
        }
      }
    },
    watch: {
      '$store.state.currentRole': function (role) {
        this.getArchiveList(role.roleId, this.$store.state.user.employeeId)
      }
    }
  }
</script>
