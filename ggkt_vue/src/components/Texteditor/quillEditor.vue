<template>
  <div class="editor_wrap">
    <!-- 图片上传组件辅助-->

    <el-upload
      class="avatar-uploader"
      :action="serverUrl"
      name="image"
      :headers="header"
      multiple
      :show-file-list="false"
      :on-success="uploadSuccess"
      :on-error="uploadError"
      :before-upload="beforeUpload"
    />

    <quill-editor
      ref="myQuillEditor"
      v-model="content"
      v-loading="quillUpdateImg"
      class="editor"
      :options="editorOption"
      @blur="onEditorBlur($event)"
      @focus="onEditorFocus($event)"
      @change="onEditorChange($event)"
    />
  </div>
</template>

<script>
import { quillEditor } from 'vue-quill-editor'

import 'quill/dist/quill.core.css'

import 'quill/dist/quill.snow.css'

import 'quill/dist/quill.bubble.css'

// 工具栏配置

const toolbarOptions = [
  ['bold', 'italic', 'underline', 'strike'], // 加粗 斜体 下划线 删除线

  ['blockquote', 'code-block'], // 引用  代码块

  // [{ header: 1 }, { header: 2 }], // 1、2 级标题

  [{ list: 'ordered' }, { list: 'bullet' }], // 有序、无序列表

  [{ script: 'sub' }, { script: 'super' }], // 上标/下标

  [{ indent: '-1' }, { indent: '+1' }], // 缩进  2

  [
    {
      color: [
        '#ffffff',
        '#ffd7d5',
        '#ffdaa9',
        '#fffed5',
        '#d4fa00',
        '#73fcd6',
        '#a5c8ff',
        '#ffacd5',
        '#ff7faa',

        '#d6d6d6',
        '#ffacaa',
        '#ffb995',
        '#fffb00',
        '#73fa79',
        '#00fcff',
        '#78acfe',
        '#d84fa9',
        '#ff4f79',

        '#b2b2b2',
        '#d7aba9',
        '#ff6827',
        '#ffda51',
        '#00d100',
        '#00d5ff',
        '#0080ff',
        '#ac39ff',
        '#ff2941',

        '#888888',
        '#7a4442',
        '#ff4c00',
        '#ffa900',
        '#3da742',
        '#3daad6',
        '#0052ff',
        '#7a4fd6',
        '#d92142',

        '#000000',
        '#7b0c00',
        '#ff0000',
        '#d6a841',
        '#407600',
        '#007aaa',
        '#021eaa',
        '#797baa',
        '#ab1942'
      ]
    },
    {
      background: [
        '#ffffff',
        '#ffd7d5',
        '#ffdaa9',
        '#fffed5',
        '#d4fa00',
        '#73fcd6',
        '#a5c8ff',
        '#ffacd5',
        '#ff7faa',

        '#d6d6d6',
        '#ffacaa',
        '#ffb995',
        '#fffb00',
        '#73fa79',
        '#00fcff',
        '#78acfe',
        '#d84fa9',
        '#ff4f79',

        '#b2b2b2',
        '#d7aba9',
        '#ff6827',
        '#ffda51',
        '#00d100',
        '#00d5ff',
        '#0080ff',
        '#ac39ff',
        '#ff2941',

        '#888888',
        '#7a4442',
        '#ff4c00',
        '#ffa900',
        '#3da742',
        '#3daad6',
        '#0052ff',
        '#7a4fd6',
        '#d92142',

        '#000000',
        '#7b0c00',
        '#ff0000',
        '#d6a841',
        '#407600',
        '#007aaa',
        '#021eaa',
        '#797baa',
        '#ab1942'
      ]
    }
  ], // 字体颜色、字体背景颜色

  [{ size: ['small', false, 'large', 'huge'] }], // 字体大小 2

  [{ header: [1, 2, 3, 4, 5, 6, false] }], // 标题

  [{ font: [] }], // 字体种类 2

  [{ direction: 'rtl' }], // 文本方向  2

  [{ align: [] }], // 对齐方式 2

  ['clean'], // 清除文本格式

  ['link', 'image', 'video'] // 链接、图片、视频
]

export default {
  name: 'QuillEditor',
  components: {
    quillEditor
  },

  props: {
    /* 编辑器的内容*/

    value: null,

    /* 图片大小*/

    maxSize: {
      type: Number,

      default: 4000 // kb
    }
  },

  data() {
    return {
      content: this.value,

      quillUpdateImg: false, // 根据图片上传状态来确定是否显示loading动画，刚开始是false,不显示

      editorOption: {
        theme: 'snow', // or 'bubble'

        placeholder: '您想说点什么？',

        modules: {
          toolbar: {
            container: toolbarOptions,

            handlers: {
              image: function(value) {
                if (value) {
                  // 触发input框选择图片文件

                  document.querySelector('.avatar-uploader input').click()
                } else {
                  this.quill.format('image', false)
                }
              }

              // link: function(value) {

              //   if (value) {

              //     var href = prompt('请输入url');

              //     this.quill.format("link", href);

              //   } else {

              //     this.quill.format("link", false);

              //   }

              // },
            }
          }
        }
      },

      serverUrl: process.env.VUE_APP_API_URL + 'config/upload', // 这里写你要上传的图片服务器地址

      header: {
        token: localStorage.getItem('token')
      }
    }
  },

  watch: {
    value(val) {
      this.content = this.value
    }
  },

  methods: {
    onEditorBlur() {
      // 失去焦点事件
    },

    onEditorFocus() {
      // 获得焦点事件
    },

    onEditorChange({ editor, html, text }) {
      this.content = html

      // 内容改变事件

      this.$emit('textareaData', this.content)
    },

    // 富文本图片上传前

    beforeUpload() {
      // 显示loading动画

      this.quillUpdateImg = true
    },

    uploadSuccess(res, file) {
      // res为图片服务器返回的数据

      // 获取富文本组件实例

      const quill = this.$refs.myQuillEditor.quill

      // 如果上传成功

      if (res.error === 0) {
        // 获取光标所在位置

        const length = quill.getSelection().index

        // 插入图片  res.url为服务器返回的图片地址

        quill.insertEmbed(length, 'image', res.info.img_url)

        // 调整光标到最后

        quill.setSelection(length + 1)
      } else {
        this.$message.error('图片插入失败')
      }

      // loading动画消失

      this.quillUpdateImg = false
    },

    // 富文本图片上传失败

    uploadError() {
      // loading动画消失

      this.quillUpdateImg = false

      this.$message.error('图片插入失败')
    }
  }
}
</script>

<style scoped>
.editor_wrap /deep/ .avatar-uploader {
  display: none;
}

.editor_wrap /deep/ .editor {
  line-height: normal !important;

  height: 270px;

  margin-bottom: 60px;
}

.editor_wrap /deep/ .editor .ql-bubble .ql-editor a {
  color: #136ec2;
}

.editor_wrap /deep/ .editor img {
  max-width: 720px;

  margin: 10px;
}

.editor_wrap /deep/ .ql-snow .ql-color-picker .ql-picker-options {
  padding: 3px 5px;

  width: 192px;
}

.editor_wrap /deep/ .ql-snow .ql-tooltip[data-mode="link"]::before {
  content: "请输入链接地址:";
}

.editor_wrap /deep/ .ql-snow .ql-tooltip.ql-editing a.ql-action::after {
  border-right: 0px;

  content: "保存";

  padding-right: 0px;
}

.editor_wrap /deep/ .ql-snow .ql-tooltip[data-mode="video"]::before {
  content: "请输入视频地址:";
}

.editor_wrap /deep/ .ql-snow .ql-picker.ql-size .ql-picker-label::before,
.editor_wrap /deep/ .ql-snow .ql-picker.ql-size .ql-picker-item::before {
  content: "14px";
}

.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="small"]::before,
.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="small"]::before {
  content: "10px";
}

.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="large"]::before,
.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="large"]::before {
  content: "18px";
}

.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-size
  .ql-picker-label[data-value="huge"]::before,
.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-size
  .ql-picker-item[data-value="huge"]::before {
  content: "32px";
}

.editor_wrap /deep/ .ql-snow .ql-picker.ql-header .ql-picker-label::before,
.editor_wrap /deep/ .ql-snow .ql-picker.ql-header .ql-picker-item::before {
  content: "文本";
}

.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-header
  .ql-picker-label[data-value="1"]::before,
.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-header
  .ql-picker-item[data-value="1"]::before {
  content: "标题1";
}

.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-header
  .ql-picker-label[data-value="2"]::before,
.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-header
  .ql-picker-item[data-value="2"]::before {
  content: "标题2";
}

.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-header
  .ql-picker-label[data-value="3"]::before,
.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-header
  .ql-picker-item[data-value="3"]::before {
  content: "标题3";
}

.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-header
  .ql-picker-label[data-value="4"]::before,
.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-header
  .ql-picker-item[data-value="4"]::before {
  content: "标题4";
}

.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-header
  .ql-picker-label[data-value="5"]::before,
.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-header
  .ql-picker-item[data-value="5"]::before {
  content: "标题5";
}

.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-header
  .ql-picker-label[data-value="6"]::before,
.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-header
  .ql-picker-item[data-value="6"]::before {
  content: "标题6";
}

.editor_wrap /deep/ .ql-snow .ql-picker.ql-font .ql-picker-label::before,
.editor_wrap /deep/ .ql-snow .ql-picker.ql-font .ql-picker-item::before {
  content: "标准字体";
}

.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="serif"]::before,
.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="serif"]::before {
  content: "衬线字体";
}

.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-font
  .ql-picker-label[data-value="monospace"]::before,
.editor_wrap
  /deep/
  .ql-snow
  .ql-picker.ql-font
  .ql-picker-item[data-value="monospace"]::before {
  content: "等宽字体";
}
</style>
