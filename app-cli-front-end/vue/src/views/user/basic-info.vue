/* eslint-disable */
<template>
  <div>

    <bread-crumb :paths="breadcrumbs"></bread-crumb>

    <div class="row">
      <div class="col-lg-12">
        <div class="ibox float-e-margins">
          <div class="ibox-title">
            <h5>基本信息配置</h5>
            <div class="ibox-tools">
              <a class="collapse-link">
                <i class="fa fa-chevron-up"></i>
              </a>
              <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="fa fa-wrench"></i>
              </a>
              <ul class="dropdown-menu dropdown-user">
                <li><a href="#">Config option 1</a>
                </li>
                <li><a href="#">Config option 2</a>
                </li>
              </ul>
              <a class="close-link">
                <i class="fa fa-times"></i>
              </a>
            </div>
          </div>

          <div class="ibox-content">


            <form method="get" class="form-horizontal">

              <div class="form-group"><label class="col-sm-2 control-label">场景选择</label>

                <div class="col-sm-3"><select class="form-control m-b" v-model="form.scenario">
                  <option value="" selected disabled>请选择场景</option>
                  <option v-for="s in this.scenarios" label="s.name" value="s.id"></option>
                  <!--<option label="场景 2" value="2"></option>-->
                  <!--<option label="场景 3" value="3"></option>-->
                  <!--<option label="场景 4" value="4"></option>-->
                </select>
                </div>

                <div class="col-sm-3"><select class="form-control m-b" v-model="form.target">
                  <option value="" selected disabled>请选择目标</option>
                  <option value="1">目标 1</option>
                  <option value="2">目标 2</option>
                  <option value="3">目标 3</option>
                  <option value="4">目标 4</option>
                </select>
                </div>

                <div class="col-sm-3 radio"><label class="active"> <input type="radio" value="false"
                                                                          v-model="form.isImported" id="optionsRadios5"
                                                                          checked="" name="optionsRadios">系统建模</label>
                </div>
                <div class="col-sm-3 radio"><label> <input type="radio" value="true" v-model="form.isImported"
                                                           id="optionsRadios6" name="optionsRadios">外部引入模型</label>
                </div>
              </div>
              <div class="hr-line-dashed"></div>
              <div class="form-group"><label class="col-sm-2 control-label">模型使用场景类型</label>

                <div class="col-sm-3"><select class="form-control m-b" v-model="form.scenarioType">
                  <option value="" selected disabled>请选择场景类型</option>
                  <option value="1">批量</option>
                  <option value="2">实时线上预测</option>
                  <option value="3">实时线下预测</option>
                </select>
                </div>
              </div>
              <div class="hr-line-dashed"></div>
              <div class="form-group"><label class="col-sm-2 control-label">模型监控指标</label>

                <div class="col-sm-3"><select class="form-control m-b" v-model="form.monitor">
                  <option value="" selected disabled>请选择监控指标</option>
                  <option value="指标 1">指标 1</option>
                  <option value="指标 2">指标 2</option>
                  <option value="指标 3">指标 3</option>
                </select>
                </div>
                <div class="form-group"><label class="col-sm-1 control-label">监控指标有效期</label>

                  <div class="col-sm-2"><input type="text" class="form-control" v-model="form.monitor_interval"></div>
                  <label class="col-sm-0 control-label">天</label>
                </div>
              </div>
              <div class="hr-line-dashed"></div>
              <div class="form-group"><label class="col-sm-2 control-label">模型名称</label>

                <div class="col-sm-3"><input type="text" class="form-control" v-model="form.name"></div>
              </div>
              <div class="hr-line-dashed"></div>
              <div class="form-group"><label class="col-sm-2 control-label">模型说明</label>
                <div class="col-sm-3"><textarea class="form-control" rows="5" id="comment"
                                                v-model="form.desc"></textarea></div>
              </div>
              <div class="hr-line-dashed"></div>
              <div class="form-group"><label class="col-sm-2 control-label">Help text</label>
                <div class="col-sm-10"><input type="text" class="form-control"> <span class="help-block m-b-none">A block of help text that breaks onto a new line and may extend beyond one line.</span>
                </div>
              </div>

              <div class="hr-line-dashed"></div>
              <div class="form-group"><label class="col-lg-2 control-label">Disabled</label>

                <div class="col-lg-10"><input type="text" disabled="" placeholder="Disabled input here..."
                                              class="form-control"></div>
              </div>

              <div class="hr-line-dashed"></div>
              <div class="form-group"><label class="col-sm-2 control-label">Checkboxes and radios <br/>
                <small class="text-navy">Normal Bootstrap elements</small>
              </label>

                <div class="col-sm-10">

                  <div class="radio"><label> <input type="radio" checked="" value="option1" id="optionsRadios1"
                                                    name="optionsRadios"> Option one is this and that&mdash;be sure to
                    include why it's great </label></div>
                  <div class="radio"><label> <input type="radio" value="option2" id="optionsRadios2"
                                                    name="optionsRadios">
                    Option two can be something else and selecting it will
                    deselect option one </label></div>
                </div>
              </div>

              <div class="hr-line-dashed"></div>
              <div class="form-group"><label class="col-sm-2 control-label">Checkboxes &amp; radios <br/>
                <small class="text-navy">Custom elements</small>
              </label>

                <div class="col-sm-10">

                  <div class="radio i-checks"><label> <input type="radio" value="option1" name="a"> <i></i> Option one </label>
                  </div>
                  <div class="radio i-checks"><label> <input type="radio" checked="" value="option2" name="a"> <i></i>
                    Option two checked </label></div>
                  <div class="radio i-checks"><label> <input type="radio" disabled="" checked="" value="option2">
                    <i></i> Option three checked and disabled </label></div>
                  <div class="radio i-checks"><label> <input type="radio" disabled="" name="a"> <i></i>
                    Option four disabled </label></div>
                </div>
              </div>

              <div class="hr-line-dashed"></div>
              <div class="form-group"><label class="col-sm-2 control-label">Select</label>

                <div class="col-sm-10"><select class="form-control m-b" name="account">
                  <option>option 1</option>
                  <option>option 2</option>
                  <option>option 3</option>
                  <option>option 4</option>
                </select>


                </div>
              </div>
              <div class="hr-line-dashed"></div>
              <div class="form-group has-success"><label class="col-sm-2 control-label">Input with success</label>

                <div class="col-sm-10"><input type="text" class="form-control"></div>
              </div>
              <div class="hr-line-dashed"></div>
              <div class="form-group has-warning"><label class="col-sm-2 control-label">Input with warning</label>

                <div class="col-sm-10"><input type="text" class="form-control"></div>
              </div>
              <div class="hr-line-dashed"></div>
              <div class="form-group has-error"><label class="col-sm-2 control-label">Input with error</label>

                <div class="col-sm-10"><input type="text" class="form-control"></div>
              </div>

              <div class="hr-line-dashed"></div>
              <div class="form-group"><label class="col-sm-2 control-label">Column sizing</label>

                <div class="col-sm-10">
                  <div class="row">
                    <div class="col-md-2"><input type="text" placeholder=".col-md-2" class="form-control"></div>
                    <div class="col-md-3"><input type="text" placeholder=".col-md-3" class="form-control"></div>
                    <div class="col-md-4"><input type="text" placeholder=".col-md-4" class="form-control"></div>
                  </div>
                </div>
              </div>


              <div class="hr-line-dashed"></div>
              <div class="form-group">
                <div class="col-sm-4 col-sm-offset-2">
                  <button class="btn btn-white" type="submit">取消</button>
                  <button class="btn btn-primary" type="submit" @click="save(form)">下一步</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>


  </div>
</template>

<script>
  import { getAllScenarios, postSave } from '../../api/baseInfo.js'
  export default {
    mounted () {
    },
    data () {
      return {
        breadcrumbs: [
          {
            name: '模型管理',
            url: ''
          }],
        form: {
          scenario: '',
          target: '',
          createdInMp: '',
          isImported: '',
          scenarioType: '',
          monitor: '',
          monitor_interval: 3,
          name: '',
          desc: ''
        },
        keyword: '',
        page: 1,
        page_size: 10,
        count: 1,
        users: [],
        scenarios: [],
        isEdit: true,
        entity: {
          id: 0,
          name: '',
          password: '',
          repeat_password: '',
          email: ''
        }
      }
    },
    methods: {
      save (form) {
        postSave(JSON.stringify(form))
      },
      indexData (page = 1, keyword = '') {
        this.$http.get('user', {
          params: {
            keyword: keyword,
            page: page,
            page_size: this.page_size
          }
        }).then((result) => {
          let data = result.data
          if (data.flag === true) {
            this.users = data.data
            this.count = data.count
            this.page = page
          } else {
            this.users = []
          }
        })
      },
      deleteData (id) {
        this.$confirm('确定要退出吗？', () => {
          this.$http.delete('user/' + id).then((result) => {
            let data = result.data
            if (data.flag === true) {
              this.$alert('删除成功')
              this.indexData()
            } else {
              this.$alert('删除失败')
            }
          })
        })
      },
      createData () {

      },
      storeData () {
        this.$http.post('user', this.entity).then((result) => {
          let data = result.data
          if (data.flag === true) {
            this.$alert('添加成功')
            this.indexData()
          } else {
            this.$alert('添加失败')
          }
        })
      },
      eidtData (id) {
        this.$http.get('user/' + id + '/edit').then((result) => {
          let data = result.data
          if (data.flag === true || data.data) {
            this.entity = {
              id: data.data.id,
              name: data.data.name,
              email: data.data.email,
              password: '',
              repeat_password: ''
            }
          }
        })
      },
      updateData () {
        this.$http.put('user/' + this.entity.id, this.entity).then((result) => {
          let data = result.data
          if (data.flag === true) {
            this.$alert('修改成功')
            this.indexData()
          } else {
            this.$alert('修改失败')
          }
        })
      },
      toggleModal (id) {
        if (id) {
          this.isEdit = true
          this.eidtData(id)
        } else {
          this.isEdit = false
          this.entity = {
            id: 0,
            name: '',
            email: '',
            password: '',
            repeat_password: ''
          }
          this.createData()
        }
        this.$refs.form_modal.open()
      },
      saveData () {
        this.isEdit ? this.updateData() : this.storeData()
        this.$refs.form_modal.close()
      }
    },
    computed: {
      modalTitle () {
        return this.isEdit ? '修改用户' : '新增用户'
      }
    }
  }

</script>
