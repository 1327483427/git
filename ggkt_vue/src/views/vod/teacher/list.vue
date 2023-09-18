<template>
  <div class="app-container">
    <el-card shadow="hover">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="名称">
          <el-input v-model="searchObj.name" placeholder="名称" />
        </el-form-item>
        <el-form-item label="头衔">
          <el-select v-model="searchObj.level" placeholder="头衔">
            <el-option value="1" label="高级讲师" />
            <el-option value="2" label="首席讲师" />
          </el-select>
        </el-form-item>
        <el-form-item label="入驻时间">
          <el-date-picker
            v-model="date"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd"
            @change="handleDatePickerChange"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="fetchData()">查询</el-button>
          <el-button type="default" @click="resetData()">清空</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <div class="rightBtn" style="text-align: right; font-size: 12px" height="20px">
      <router-link :to="'/vod/teacher/create/'">
        <el-button type="success" class="el-icon-plus">新增讲师</el-button>
      </router-link>
      <el-button type="danger" icon="el-icon-delete" @click="batchRemove()">批量删除</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="加载中..."
      border
      stripe
      highlight-current-row
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" />
      <el-table-column label="id" width="50">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="讲师名称" width="80">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>

      <el-table-column label="头衔" width="90" align="center">
        <template slot-scope="scope">
          <el-tag
            v-if="scope.row.level === 1"
            type="success"
            size="mini"
          >高级讲师</el-tag>
          <el-tag v-if="scope.row.level === 0" size="mini">首席讲师</el-tag>
        </template>
      </el-table-column>

      <el-table-column label="简介" align="center">
        <template slot-scope="scope">
          {{ scope.row.intro }}
        </template>
      </el-table-column>
      <el-table-column prop="sort" label="排序" width="60" />
      <el-table-column label="入驻时间" width="160">
        <template slot-scope="scope">
          <i class="el-icon-time" />
          {{ scope.row.joinDate }}
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="120">
        <template slot-scope="scope">
          <router-link :to="'/vod/teacher/edit/' + scope.row.id">
            <el-button
              class="el-icon-edit"
              type="text"
              size="small"
            >修改</el-button>
          </router-link>
          <el-button
            class="el-icon-delete"
            type="text"
            size="small"
            @click="removeById(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      :current-page="page"
      :page-sizes="[5, 10, 20, 30, 40, 50, 100]"
      :page-size="limit"
      style="padding: 30px 0; text-align: center"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />

  </div>
</template>

<script>
import teacher from '@/api/vod/teacher'

export default {
  data() {
    return {
      list: null,
      listLoading: true,
      page: 1, // 页码
      limit: 5, // 每页记录数
      total: 0, // 总记录数
      searchObj: {},
      multipleSelection: [],
      date: ''
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      teacher
        .pageList(this.page, this.limit, this.searchObj)
        .then((response) => {
          this.list = response.data.records
          this.total = response.data.total
          this.listLoading = false
        })
    },
    removeById(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => { return teacher.removeById(id) })
        .then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.fetchData()
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    },
    handleSizeChange(val) {
      this.limit = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.page = val
      this.fetchData()
    },
    handleDatePickerChange() {
      if (this.date !== undefined) {
        this.searchObj.joinDateBegin = this.date[1]
        this.searchObj.joinDateEnd = this.date[2]
      }
    },
    resetData() {
      this.searchObj = {}
      this.fetchData()
    },
    handleSelectionChange(selection) {
      debugger
      console.log(selection)
      this.multipleSelection = selection
    },

    batchRemove() {
      if (this.multipleSelection.length === 0) {
        this.$message.warning('请选择要删除的记录！')
        return
      }
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var idList = []
        this.multipleSelection.forEach(item => {
          idList.push(item.id)
        })
        return teacher.batchRemove(idList)
      }).then((response) => {
        this.page = 1
        this.fetchData()
        this.$message.success(response.message)
      }).catch((error) => {
        if (error === 'cancel') {
          this.$message.info('取消删除')
        }
      })
    }
  }
}
</script>
