<template>

  <div>
    <bread-crumb :paths="breadcrumbs" :title="title"></bread-crumb>

    <div class=" wrapper wrapper-content animated fadeInRight">

      <div class="row">

        <div class="col-md-12">
          <div class="ibox float-e-margins">
            <div class="ibox-title">
              <h5>待办信息</h5>
              <div ibox-tools></div>
            </div>
            <div class="ibox-content">
              <el-table
                :data="tableData"
                border
                style="width: 100%">
                <el-table-column
                  label="日期"
                  width="180">
                  <template scope="scope">
                    <el-icon name="time"></el-icon>
                    <span style="margin-left: 10px">{{ scope.row.date }}</span>
                  </template>
                </el-table-column>
                <el-table-column
                  label="事项"
                  width="180">
                  <template scope="scope">
                    <el-popover trigger="hover" placement="top">
                      <p>事项: {{ scope.row.name }}</p>
                      <p>住址: {{ scope.row.address }}</p>
                      <div slot="reference" class="name-wrapper">
                        <el-tag>{{ scope.row.name }}</el-tag>
                      </div>
                    </el-popover>
                  </template>
                </el-table-column>
                <el-table-column prop="address" label="地址">
                </el-table-column>
                <el-table-column label="操作">
                  <template scope="scope">
                    <el-button
                      size="small"
                      @click="handleEdit(scope.$index, scope.row)">编辑
                    </el-button>
                    <el-button
                      size="small"
                      type="danger"
                      @click="handleDelete(scope.$index, scope.row)">删除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
  export default {
    data: function () {
      return {
        title: '待办列表',
        breadcrumbs: [
          {
            name: 'XX平台',
            url: '/'
          },
          {
            name: '待办列表',
            url: '/todo/list'
          }
        ],
        tableData: [{
          date: '2016-05-02',
          name: '和王小虎吃饭',
          address: '上海市普陀区金沙江路 1518 弄'
        }, {
          date: '2016-05-04',
          name: '和王小虎吃饭',
          address: '上海市普陀区金沙江路 1517 弄'
        }, {
          date: '2016-05-01',
          name: '和王小虎吃饭',
          address: '上海市普陀区金沙江路 1519 弄'
        }, {
          date: '2016-05-03',
          name: '和王小虎吃饭',
          address: '上海市普陀区金沙江路 1516 弄'
        }]
      }
    },
    methods: {
      handleEdit(index, row) {
        console.log(index, row)
      },
      handleDelete(index, row) {
        console.log(index, row)
      }
    }
  }
</script>
