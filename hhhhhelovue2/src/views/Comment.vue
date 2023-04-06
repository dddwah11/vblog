<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <!--面包屑-->
    <el-breadcrumb
      separator="/"
      style="padding-left: 10px; padding-bottom: 10px; font-size: 12px"
    >
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>博客管理</el-breadcrumb-item>
      <el-breadcrumb-item>评论管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!--评论列表卡片-->
    <el-card class="box-card">
      <el-table
        border
        stripe
        :data="tableData"
        max-height="500"
        style="width: 100%;border"
      >
        <el-table-column prop="id" label="#" width="50"></el-table-column>
        <el-table-column prop="pid" label="父id" width="110"> </el-table-column>
        <el-table-column prop="blogId" label="关联文章id" width="110">
        </el-table-column>
        <el-table-column prop="content" label="内容" width="150">
        </el-table-column>
        <el-table-column prop="createdTime" label="创建时间" width="150">
        </el-table-column>
        <el-table-column prop="createBy" label="创建者" width="150">
        </el-table-column>
        <el-table-column prop="originId" label="最上级id" width="150">
        </el-table-column>
        <el-table-column prop="email" label="邮箱" width="150">
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
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination
        style="padding-top: 15px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="current"
        :page-sizes="[5, 10, 20, 30]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
import { findAll, delComment } from "@/api/comment/comment";
export default {
  data() {
    return {
      tableData: [],
      //每页显示的条数
      size: 5,
      //总条数
      total: 100,
      //当前页码
      current: 1,
    };
  },
  mounted() {
    this.selectByPage();
  },

  methods: {
    //当每页条数改变的时候
    handleSizeChange(val) {
      //将val赋值给size
      this.size = val;
      //重新去后台查询数据
      this.selectByPage();
    },
    //当前页码改变的时候
    handleCurrentChange(val) {
      this.current = val;
      this.selectByPage();
    },
    async selectByPage() {
      const { data } = await findAll(this.current, this.size);
      (this.tableData = data.data.records), (this.total = data.data.total);
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
        const { data: res } = await delComment(id);
        if (res.success) {
          this.selectByPage();
          this.$notify.success({
            title: "操作成功",
            message: "删除成功",
          });
          // location.reload()
          this.selectByPage();
        } else {
          this.$message.error(res.message);
        }
      }
    },
  },
};
</script>
<style  scoped>
.box-card {
    height: 100%;

}
.dialog-footer {
  text-align: center;
}
</style>