<template>
  <div>
    <!--面包屑-->
    <el-breadcrumb
      separator="/"
      style="padding-left: 10px; padding-bottom: 10px; font-size: 12px"
    >
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!--用户列表卡片-->
    <el-card class="box-card">
      <el-button
        type="primary"
        @click="dialogFormVisible = true"
        size="mini"
        icon="el-icon-plus"
      >
      添加角色
      </el-button>
      <el-table border stripe :data="tableData" default-expand-all row-key="id" max-height="500" style="width: 100%;border">
        <el-table-column prop="id" label="#" width="50"></el-table-column>
        <el-table-column prop="name" label="名称" width="110">
        </el-table-column>
        <el-table-column prop="description" label="描述" width="180">
        </el-table-column>
        <el-table-column prop="userKey" label="唯一标识" width="180">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="primary"
              @click="handEdit(scope.row)"
              size="mini"
              icon="el-icon-edit"
            >
              编辑</el-button
            >
            <el-button
              type="danger"
              size="mini"
              icon="el-icon-delete"
              @click="deleteBy(scope.row.id)"
              >删除</el-button
            >
            <el-button
              type="warning"
              size="mini"
              icon="el-icon-s-tools"
              @click="selectMenu(scope.row.id),menuDialogFormVisible=true"
              >分配菜单</el-button
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
        :page-sizes="[7, 10, 20, 30]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </el-card>
    <!--新增弹框-->
    <el-dialog
      :title="buttonStatus ? '编辑' : '新增'"
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
          </el-col>
        </el-form>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handelConfirm">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 分配菜单 -->
    <el-dialog
      title="菜单分配"
      :visible.sync="menuDialogFormVisible"
      width="40%"
    >
      <el-tree
        :props="props"
        :data="menuData"
        show-checkbox
        :check-strictly="true"
        ref="tree"
        node-key="id"
        :default-expanded-keys="depands"
        :default-checked-keys="checks"
        
      >
      <span class="custom-tree-node" slot-scope="{ data }">
        <span><i :class="data.icon">{{ data.name }}</i></span>
        </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { findUserList, deletedById, reg, updateById } from "@/api/role";
import {findAll } from "@/api/menu";
export default {
  data() {
    return {
      roleId: 0,
      depands:[],
      checks: [],
      props:{
        label: "name"
      },
      menuData: [],
      buttonStatus: false,
      user: {
        name: undefined,
        description: undefined,
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
      },
      dialogFormVisible: false,
      menuDialogFormVisible: false,
      tableData: [],
      //每页显示的条数
      size: 7,
      //总条数
      total: 100,
      //当前页码
      current: 1,
    };
  },
  mounted() {
    this.selectByPage();
    this.selectMenu()
    
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
      const { data } = await findUserList(this.current, this.size);
      (this.tableData = data.data.records.records), (this.total = data.data.total);
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
          // location.reload()
        } else {
          this.$message.error(res.message);
        }
      }
    },
    saveRoleMenu(){
      this.request.post("/role/roleMenu/"+this.roleId, this.$refs.tree.getCheckedKeys()).then(res=>{
        if(res.data.code===20000){
          // this.$message.success("绑定成功")
          // this.$refs.tree.getCheckedKeys().concat(this.$refs.tree.getHalfChekedKeys())
          this.menuDialogFormVisible = false
          // this.$store.commit("logout")
          localStorage.removeItem('user')
          localStorage.removeItem('menus')
          this.$router.push('/login')
          this.$message.success('菜单分配成功需重新登录')
        } else{
          this.$message.error("绑定失败")
        }
      })
    },
    handelConfirm() {
      this.$refs["elForm"].validate((valid) => {
        if (!valid) {
          return;
        }
        if (this.buttonStatus) {
          this.updateBy();
        } else {
          this.addUser();
        }
      });
    },
    async addUser() {
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
        this.selectByPage();
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
        // 清空当前角色的信息
        this.$emit("findUserList");
        this.dialogFormVisible = false;
        this.buttonStatus = false;
        this.user = {};
        this.selectByPage();
      } else {
        return this.$message.error("修改:" + res.message);
      }
    },
    // 编辑角色信息
    handEdit(row) {
      this.buttonStatus = true;
      (this.user = row), (this.dialogFormVisible = true);
    },
    // 根据角色id回显
   async selectMenu(roleId) {
     this.roleId = roleId
      const { data } = await  findAll();
      (this.menuData = data.data.parentNode)
      this.depands = this.menuData.map(v=>v.id)
      this.request.get("/role/roleMenu/"+ this.roleId).then(res=>{
        this.checks = res.data.data.checks
      })
    },
  },
};
</script>
<style  scoped>
.box-card {
  height: 625;
}
</style>