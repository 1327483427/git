<template>
  <div class="app-container">
    <el-table
      :data="list"
      style="width: 100%"
      row-key="id"
      border
      lazy
      :load="load"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="title" label="名称" />
      <el-table-column prop="createTime" label="创建日期" width="300" />
      <el-table-column
        fixed="right"
        label="操作"
        width="150"
      >
        <template slot-scope="node">
          <el-button type="text" size="small" @click="addNode(node,list)">添加</el-button>
          <el-button type="text" size="small" @click="handleClick(scope.row)">编辑</el-button>
          <el-button type="text" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="el-toolbar">
      <div class="el-toolbar-body" style="justify-content: flex-start;">
        <el-button type="text" @click="exportData"><i class="fa fa-plus" />导出</el-button>
        <el-button type="text" @click="importData"><i class="fa fa-plus" />导入</el-button>
      </div>
    </div>

    <el-dialog title="导入文件" :visible.sync="dialogImportVisible" width="30%">
      <el-form label-position="left" label-width="15%">
        <el-form-item label="选择">
          <el-upload
            drag
            :multiple="false"
            :on-success="onUploadSuccess"
            :action="'http://localhost:8301/admin/vod/subject/importData'"
            class="upload-demo"
          >
            <i class="el-icon-upload" />
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div slot="tip" class="el-upload__tip">只能上传xls/xlsx文件,且不超过500kb</div>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="info" @click="dialogImportVisible = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import subjectApi from '@/api/vod/subject'

export default {
  data() {
    return {
      list: [],
      dialogImportVisible: false
    }
  },
  created() {
    this.getSubList(0)
  },
  methods: {
    getSubList(id) {
      subjectApi.getChildSubject(id).then(response => {
        this.list = response.data
      }).catch((response) => {
        this.$message({
          type: 'error',
          message: response
        })
      }
      )
    },
    load(tree, treeNode, resolve) {
      subjectApi.getChildSubject(tree.id).then(response => {
        resolve(response.data)
      })
    },
    exportData() {
      window.open('http://localhost:8301/admin/vod/subject/exportData')
    },
    importData() {
      this.dialogImportVisible = true
    },
    onUploadSuccess(response, file) {
      this.$message.info('上传成功')
      this.dialogImportVisible = false
      this.getSubList(0)
    }
  }
}
</script>

<style></style>
