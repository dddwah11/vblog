<template>
  <div>
    <!--面包屑-->
    <el-breadcrumb
      separator="/"
      style="padding-left: 10px; padding-bottom: 10px; font-size: 12px"
    >
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>菜单管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!--用户列表卡片-->
    <el-card class="box-card">
      <el-button
        type="primary"
        @click="buttonStatus=1,dialogFormVisible = true"
        size="mini"
        icon="el-icon-plus"
      >
      添加菜单
      </el-button>
      <el-table border stripe  :data="tableData" row-key="id" max-height="500" style="width: 100%;border">
        <el-table-column prop="id" label="#" width="50"></el-table-column>
        <el-table-column prop="name" label="名称" width="110">
        </el-table-column>
        <el-table-column prop="path" label="路径" width="180">
        </el-table-column>
        <el-table-column prop="pagePath" label="页面路径" width="180">
        </el-table-column>
        <el-table-column label="图标" align="center" width="180">
          <template slot-scope="scope">
            <i style="font-size:large" :class="scope.row.icon"></i>
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" width="80">
        </el-table-column>
        <el-table-column sortable prop="sortNum" label="顺序" width="80">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="primary"
              @click="buttonStatus=0,handEdit(scope.row)"
              size="mini"
              icon="el-icon-edit"
            >编辑
            </el-button>
            <el-button
              type="primary"
              @click="buttonStatus=2,handSaveMenu(scope.row.id)"
              size="mini"
              icon="el-icon-plus"
              v-if="!scope.row.pid && !scope.row.path"
            >新增子菜单
            </el-button>
            <el-button
              type="danger"
              size="mini"
              icon="el-icon-delete"
              @click="deleteBy(scope.row.id)"
            >删除</el-button> 
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!--新增弹框-->
    <el-dialog
      :title="buttonStatus==0 ? '编辑' :buttonStatus==1 ? '新增':'新增子菜单'"
      :visible.sync="dialogFormVisible"
      width="40%"
    >
      <el-row :gutter="15">
        <el-form
          ref="elForm"
          :model="user"
          :rules="rules"
          size="medium"
          label-width="80px"
        >
          <el-col :span="12">
            <el-form-item label="名称" prop="name">
              <el-input
                v-model="user.name"
                placeholder="请输入名称"
                clearable
                :style="{ width: '100%' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="描述" prop="description">
              <el-input
                v-model="user.description"
                placeholder="请输入描述"
                clearable
                :style="{ width: '100%' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="路径" prop="path">
              <el-input
                v-model="user.path"
                placeholder="请输入路径,例如/xxx"
                clearable
                :style="{ width: '100%' }"
              >
              </el-input>
            </el-form-item>
              <el-form-item label="页面路径" prop="pagePath">
              <el-input
                v-model="user.pagePath"
                placeholder="请输入页面路径,例如xxxView"
                clearable
                :style="{ width: '100%' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="顺序" prop="sortNum">
              <el-input
                v-model="user.sortNum"
                placeholder="请输入是数字"
                clearable
                :style="{ width: '100%' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="图标" prop="icon">
            <el-select clearable v-model="user.icon" placeholder="请选择">
            <el-option v-for="item in options" :key="item.name" :label="item.name" :value="item.value">
            <i :class="item.value">{{item.name}}</i>
            </el-option>
            </el-select>
            </el-form-item>
          </el-col>
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
import { deletedById, reg, updateById,findAll } from "@/api/menu";
export default {
  data() {
    return {
      options:[],
      buttonStatus: 0,
      user: {
        pid: undefined,
        name: undefined,
        description: undefined,
        icon: undefined,
        path: undefined,
        pagePath: undefined,
        sortNum: undefined
      },
      rules: {
        name: [
          {
            required: true,
            message: "请输入名称",
            trigger: "blur",
          },
        ],
        description: [
          {
            required: true,
            message: "请输入描述",
            trigger: "blur",
          },
        ],
        path: [
          {
            required: true,
            message: "请输入路径，例如/xxx",
            trigger: "blur",
          },
        ],
        icon: [
          {
            required: true,
            message: "请选择图标",
            trigger: "blur",
          },
        ],
      },
      dialogFormVisible: false,
      tableData: [],
    };
  },
  mounted() {
    this.selectByPage()
    // 获取图标
    this.request.get("/menu/icons").then(res=>{
        this.options = res.data.data.icons
      })
  },

  methods: {
    async selectByPage() {
      const { data } = await findAll();
      (this.tableData = data.data.parentNode)
    },
    async deleteBy(id) {
      const res = await this.$confirm(
        "此操作将永久删除, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).catch(() => {
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
        } else {
          this.$message.error(res.message);
        }
      }
    },
    handelConfirm() {
        if (this.buttonStatus==0) {
          this.updateBy();
        } else if(this.buttonStatus==1){
          this.addUser();
        } else if(this.buttonStatus ==2){
          this.SaveMenu();
        }
    },
    async addUser() {
        await reg(this.user);
        this.$notify.success({
          title: "操作成功",
          message: "添加成功",
        });
        // 清空当前用户的信息
        this.selectByPage()
        this.$emit("findUserList");
        this.dialogFormVisible = false;
        this.user = {};
    },
    async SaveMenu() {
          const { data: res } = await reg(this.user);
      if (res.success === true) {
        this.$notify.success({
          title: "操作成功",
          message: "添加成功",
        });
        // 清空当前用户的信息
        this.$emit("findUserList");
        this.dialogFormVisible = false;
        this.user = {};
        this.selectByPage()
      } else {
        return this.$message.error("添加:" + res.message);
      }
    },
    async updateBy() {
      const { data: res } = await updateById(this.user);
      if (res.success === true) {
        this.$notify.success({
          title: "操作成功",
          message: "修改成功",
        });
        // 清空当前用户的信息
        this.$emit("findUserList");
        this.dialogFormVisible = false;
        this.user = {};
        this.selectByPage()
      } else {
        return this.$message.error("修改:" + res.message);
      }
    },
    handEdit(row) {
      (this.user = row), (this.dialogFormVisible = true);
    },
    handSaveMenu(id){
      if (id){
      this.user.pid = id
      }
      this.dialogFormVisible = true
    }
  },
};
</script>
<style  scoped>
.box-card {
  height: 625;
}

</style>