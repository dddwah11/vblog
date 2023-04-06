<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <!--面包屑-->
    <el-breadcrumb
      separator="/"
      style="padding-left: 10px; padding-bottom: 10px; font-size: 12px"
    >
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>文件管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!--用户列表卡片-->
    <div style="display:flex">
                  <el-upload
          class="upload-demo"
          :headers="headers"
          action="http://www.lhzzz-61.top/api/file/upload"
          list-type="picture"
          :on-change="handleChange"
        >
          <el-button size="small" type="primary">点击上传略缩图(阿里云OSS)</el-button>
          <div slot="tip" class="el-upload__tip">
            只能上传jpg/png文件，且不超过10MB
          </div>
        </el-upload>

        <el-upload
          class="upload-demo"
          :headers="headers"
          action="http://www.lhzzz-61.top/api/minio/upload"
          list-type="picture"
          :on-change="handleChange"

        >
          <el-button size="small" type="primary">点击上传略缩图（Minio）</el-button>
        </el-upload>
        </div>
    <el-card class="box-card">
      <el-table border stripe :data="tableData" style="width: 100%;border;max-height: 100%;">
        <el-table-column prop="id" label="#" width="50"></el-table-column>
        <el-table-column prop="url" label="略缩图" width="180">
          <template slot-scope="scope">
          <el-image  fit="fill" :src="scope.row.url"></el-image>
          </template>
        </el-table-column>
        <el-table-column
          prop="name"
          label="文件名"
          width="100"
        ></el-table-column>
        <el-table-column prop="type" label="类型" width="110">
        </el-table-column>
        <el-table-column prop="url" label="预览" width="110">
          <template slot-scope="scope">
            <el-button @click="view(scope.row.url)" type="primary"
              >预览</el-button
            >
          </template>
        </el-table-column>
        <el-table-column prop="size" label="大小，单位为B" width="130"></el-table-column>
        <el-table-column prop="url" label="链接" width="100">
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button
              type="danger"
              size="mini"
              icon="el-icon-delete"
              @click="deleteBy(scope.row.id)"
              >删除</el-button
            >
          <el-button type="primary" size="mini" plain @click="copy(scope.row.url)">复制url</el-button>
          </template>
        </el-table-column>
      </el-table>

    </el-card>
  </div>
</template>

<script>
import { findList, deletedById } from "@/api/file";
// import Clipboard from 'clipboard';
export default {
  data() {
    return {
      tableData: [],

      url: "",
    };
  },
  mounted() {
    this.selectByPage();
  },
  computed: {
    headers(){
      let userToken = sessionStorage.getItem("user")? JSON.parse(sessionStorage.getItem("user")) : null
      return{
        Authorization:userToken.token
      };
    },
  },
  methods: {
    async selectByPage() {
      const { data: res } = await findList();
      this.tableData = res.data.records;
      this.url = res.data.records.url;
      this.total = res.data.total;
    },
    handleChange(){
      this.selectByPage();
    },
    async deleteBy(id) {
      const res = await this.$confirm("此操作将永久删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消删除",
        });
      });
      if (res === "confirm") {
        const { data: res } = await deletedById(id);
        if (res.success) {
          this.selectByPage();
          this.$notify.success({
            title: "操作成功",
            message: "删除成功",
          });
          this.selectByPage();
        } else {
          this.$message.error(res.message);
        }
      }
    },
   async view(url) {
      window.open(url);

    },
    
    copy(item) {
        // 模拟 输入框
        var cInput = document.createElement("input");
        cInput.value = item;
        document.body.appendChild(cInput);
        cInput.select(); // 选取文本框内容
    
        // 执行浏览器复制命令
        // 复制命令会将当前选中的内容复制到剪切板中（这里就是创建的input标签）
        // Input要在正常的编辑状态下原生复制方法才会生效
    
        document.execCommand("copy");
    
        this.$message({
            type: "success",
            message: "url复制成功"
        });
        // 复制成功后再将构造的标签 移除
        document.body.removeChild(cInput);
        }
    }
}

</script>
<style  scoped>
.box-card {
    height: 100%;

}
.dialog-footer {
  text-align: center;
}
</style>