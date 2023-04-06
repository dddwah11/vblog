<template>
  <div>
    <!--面包屑-->
    <el-breadcrumb
      separator="/"
      style="padding-left: 10px; padding-bottom: 10px; font-size: 12px"
    >
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!--用户列表卡片-->
    <el-card class="box-card">
      <el-form :inline="true" :model="userVo" class="demo-form-inline">
        <el-form-item  label-width="40px">
          <el-input
            clearable
            v-model="userVo.username"
            placeholder="请输入用户名"
          ></el-input>
        </el-form-item>
        <el-form-item style="margin-left: 10px">
          <el-button icon="el-icon-refresh" @click="resetUserVo"
            >重置</el-button
          >
          <el-button type="primary" icon="el-icon-search" 
          @click="selectByPage"
            >查询</el-button
          >
                <el-button
        type="primary"
        @click="dialogFormVisible = true"
        icon="el-icon-plus"
      >
      添加用户
      </el-button>
        </el-form-item>
      </el-form>
      <el-table border  stripe :data="tableData" max-height="500" style="width: 100%;border">
        <el-table-column prop="id" label="#" width="50"></el-table-column>
        <el-table-column prop="username" label="用户名" width="110">
        </el-table-column>
        <el-table-column prop="role" label="角色" width="110">
          <template slot-scope="scope">
            <el-tag type="primary" v-if="scope.row.role ==='admin' ">管理员</el-tag>
            <el-tag type="warning" v-if="scope.row.role ==='user' ">普通用户</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="password" label="密码" width="150">
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button type="success" @click="handEdit(scope.row)" size="mini" icon="el-icon-edit">
            编辑
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
    <!--新增编辑弹框-->
    <el-dialog
      :title="buttonStatus?'编辑':'新增'"
      :visible.sync="dialogFormVisible"
      width="40%"
    >
      <el-row :gutter="15">
        <el-form
          ref="elForm"
          :model="user"
          :rules="rules"
          size="medium"
          label-width="200px"
        >
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input
                v-model="user.username"
                placeholder="请输入用户名"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input
                v-model="user.password"
                placeholder="请输入密码"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
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
import { findUserList, deletedById,reg,updateById } from "@/api/selectByPage";
export default {
  data() {
    
    return {
      userVo:{
        username:"",
      },
      courseVisible: false,
      buttonStatus:false,
      user: {
        role:undefined,
        username: undefined,
        password: undefined,
      },
      rules: {
        username: [
          {
            required: true,
            message: "请输入用户名",
            trigger: "blur",
          },
        ],
        password: [
          {
            required: true,
            message: "请输入密码",
            trigger: "blur",
          },
        ],
      },
      roles:{},
      dialogFormVisible: false,
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
    resetUserVo(){
      this.userVo.username = "";
      this.userVo.sex = "";
      this.selectByPage();
    },
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
      const { data } = await findUserList(this.current, this.size,this.userVo);
      (this.tableData = data.data.records), (this.total = data.data.total);
      // 查询角色表，回显到添加或编辑的弹出框内
        this.request.post("/role/roleSearch").then(res=>{
          this.roles = res.data.data.records
        })
    },
    async deleteBy(id) {
      const res = await this.$confirm(
        "此操作将永久删除该用户, 是否继续?",
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
            message: "用户删除成功",
          });
          // location.reload()
          this.selectByPage()
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
        if(this.buttonStatus){
          this.updateBy()
        }else{
        this.addUser()
        }
});
    },
    async addUser(){
      const { data:res } = await reg(this.user);
      if(res.success===true){
        this.$notify.success({
          title:'操作成功',
          message:'用户添加成功'  
        })
        this.selectByPage()
        this.dialogFormVisible = false
        this.user = {}
      } else{
        return this.$message.error('用户添加:'+res.message)
      }
    },
    async updateBy(){
      const { data:res } = await updateById(this.user);
      if(res.success===true){
        
        this.$notify.success({
          title:'操作成功',
          message:'用户修改成功'  
        })
        // 清空当前用户的信息
        this.$emit('findUserList')
        this.dialogFormVisible = false
        this.buttonStatus=false
        this.user = {}
      } else{
        return this.$message.error('用户修改:'+res.message)
      }
    },
     handEdit(row){
      this.buttonStatus=true
      this.user=row,
      this.dialogFormVisible = true
    },
  },
};
</script>
<style  scoped>
.box-card {
    height: 100%;

}
.dialog-footer{
  text-align: center;
}
</style>