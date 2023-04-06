<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <!--面包屑-->
    <el-breadcrumb
      separator="/"
      style="padding-left: 10px; padding-bottom: 10px; font-size: 12px"
    >
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>留言板</el-breadcrumb-item>
    </el-breadcrumb>
    <!--用户列表卡片-->
    <el-card >
      <el-form :inline="true" :model="chat" class="demo-form-inline">
        <el-form-item  label-width="40px">
                <el-button
        type="primary"
        @click="dialogFormVisible = true"
        icon="el-icon-plus"
      >
      添加留言
      </el-button>
        </el-form-item>
      </el-form>
      <el-table border  stripe :data="tableData" >
        <el-table-column prop="id" label="#" width="50"></el-table-column>
        <el-table-column prop="content" label="留言" width="510"></el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
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
      title="新增'"
      :visible.sync="dialogFormVisible"
      width="40%"
    >
      <el-row :gutter="15">
        <el-form
          ref="elForm"
          :model="chat"
          :rules="rules"
          size="medium"
          label-width="200px"
        >
          <el-col :span="12">
            <el-form-item label="留言" prop="content">
              <el-input
                v-model="chat.content"
                placeholder="请输入留言"
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
import { findChatList, deletedById,reg } from "@/api/chat/chat";
export default {
  data() {
    
    return {
      chat: {
        content: undefined,
      },
      rules: {
        content: [
          {
            required: true,
            message: "请输入留言",
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
      const { data } = await findChatList(this.current, this.size);
      (this.tableData = data.data.records)
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
        this.addTag()
});
    },
    async addTag(){
      const { data:res } = await reg(this.chat);
      if(res.success===true){
        this.$notify.success({
          title:'操作成功',
          message:'添加成功'  
        })
        this.selectByPage()
        this.dialogFormVisible = false
        this.chat = {}
      } else{
        return this.$message.error('添加:'+res.message)
      }
    },

  },
};
</script>
<style  scoped>
.el-table__body-wrapper{
  height: 100%;
}
.dialog-footer{
  text-align: center;
}
</style>