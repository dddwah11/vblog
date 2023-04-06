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
      <el-breadcrumb-item>友链管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!--用户列表卡片-->
    <el-card class="box-card">
      <el-form :inline="true" :model="link" class="demo-form-inline">
        <el-form-item label-width="40px">
          <el-button
            type="primary"
            @click="dialogFormVisible = true"
            icon="el-icon-plus"
          >
            添加友链
          </el-button>
        </el-form-item>
      </el-form>
      <el-table
        border
        stripe
        :data="tableData"
        max-height="400"
        style="width: 100%;border"
      >
        <el-table-column prop="id" label="#" width="50"></el-table-column>
        <el-table-column
          prop="title"
          label="标题"
          width="110"
        ></el-table-column>
        <el-table-column
          prop="photoUrl"
          label="图片地址"
          width="110"
        ></el-table-column>
        <el-table-column prop="site" label="网址" width="110"></el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button
              type="success"
              @click="handEdit(scope.row)"
              size="mini"
              icon="el-icon-edit"
            >
              编辑
            </el-button>
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
    </el-card>
    <!--新增编辑弹框-->
    <el-dialog
      :title="buttonStatus ? '编辑' : '新增'"
      :visible.sync="dialogFormVisible"
      width="60%"
    >
      <el-row :gutter="15">
        <el-form
          ref="elForm"
          :model="link"
          :rules="rules"
          size="medium"
          label-width="200px"
        >
          <el-col :span="12">
            <el-form-item label="博客名称" prop="title">
              <el-input
                v-model="link.title"
                placeholder="请输入博客名称"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="图片" prop="photoUrl">
              <el-input
                v-model="link.photoUrl"
                placeholder="请输入图片url"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="网址" prop="site">
              <el-input
                v-model="link.site"
                placeholder="请输入网址"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12"> </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handelConfirm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getLinkList, deletedById, addLink } from "@/api/link/link";
export default {
  data() {
    return {
      courseVisible: false,
      buttonStatus: false,
      link: {
        id: undefined,
        title: undefined,
        photoUrl: undefined,
        site: undefined,
      },
      rules: {
        title: [
          {
            required: true,
            message: "请输入博客名称",
            trigger: "blur",
          },
        ],
        photoUrl: [
          {
            required: true,
            message: "请输入图片url",
            trigger: "blur",
          },
        ],
        site: [
          {
            required: true,
            message: "请输入网址",
            trigger: "blur",
          },
        ],
      },
      dialogFormVisible: false,
      tableData: [],
    };
  },
  mounted() {
    this.selectByPage();
  },

  methods: {
    async selectByPage() {
      const { data } = await getLinkList(this.current, this.size);
      this.tableData = data.data.records;
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
        console.log(res);
        if (res.code === 20000) {
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
    handelConfirm() {
      this.$refs["elForm"].validate((valid) => {
        if (!valid) {
          return;
        }
        if (this.buttonStatus) {
          this.updateBy();
        } else {
          this.addTag();
        }
      });
    },
    async addTag() {
      const { data: res } = await addLink(this.link);
      if (res.success === true) {
        this.$notify.success({
          title: "操作成功",
          message: "添加成功",
        });
        this.selectByPage();
        this.dialogFormVisible = false;
        this.link = {};
      } else {
        return this.$message.error("标签添加:" + res.message);
      }
    },
    async updateBy() {
      const { data: res } = await addLink(this.link);
      if (res.success === true) {
        this.$notify.success({
          title: "操作成功",
          message: "修改成功",
        });
        // 清空当前用户的信息
        this.$emit("findUserList");
        this.dialogFormVisible = false;
        this.buttonStatus = false;
        this.link = {};
      } else {
        return this.$message.error("修改:" + res.message);
      }
    },
    handEdit(row) {
      this.buttonStatus = true;
      (this.link = row), (this.dialogFormVisible = true);
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