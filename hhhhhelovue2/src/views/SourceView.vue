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
        <el-breadcrumb-item>资源管理</el-breadcrumb-item>
      </el-breadcrumb>
      <!--用户列表卡片-->
      <el-card class="box-card">
        <el-button
        type="primary"
        @click="dialogFormVisible = true"
        icon="el-icon-plus"
      >
      添加资源
      </el-button>
        <el-table border stripe :data="tableData" style="width: 100%;border">
          <el-table-column prop="id" label="#" width="50"></el-table-column>
          <el-table-column prop="pic" label="网站图标" width="120">
          <template slot-scope="scope">
          <el-image  fit="fill" :src="scope.row.pic"></el-image>
          </template>
        </el-table-column>
          <el-table-column
            prop="name"
            label="名称"
            width="100"
          ></el-table-column>
          <el-table-column prop="descrption" label="描述" width="350"></el-table-column>
          <el-table-column prop="url" label="网址" width="200"></el-table-column>
          <el-table-column prop="type" label="所属类别" width="120"></el-table-column>
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
      :title="buttonStatus?'编辑':'新增'"
      :visible.sync="dialogFormVisible"
      width="40%"
    >
      <el-row :gutter="15">
        <el-form
          ref="elForm"
          :model="source"
          :rules="rules"
          size="medium"
          label-width="200px"
        >
            <el-form-item label="名称" prop="name">
              <el-input
                v-model="source.name"
                placeholder="请输入名称"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="图标" prop="pic">
              <el-input
                v-model="source.pic"
                placeholder="请输入图标地址"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="描述" prop="descrption">
              <el-input
                v-model="source.descrption"
                placeholder="请输入描述"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="网址" prop="url">
              <el-input
                v-model="source.url"
                placeholder="请输入网址"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="类别" prop="typeId">
        <el-select v-model="source.typeId" placeholder="请选择类别">
          <el-option
            v-for="item in types"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
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
  import { findSourceAll,deletedById,edit,save } from "@/api/source/request";
  import { findAll } from "@/api/type/request";
  export default {
    data() {
        return{
        tableData: [],
        types:[],
        source:{
            typeId:1,
        },
        buttonStatus:false,
        dialogFormVisible:false,
        rules: {
        name: [
          {
            required: true,
            message: "请输入标签名",
            trigger: "blur",
          },
        ],
        typeId: [
          {
            required: true,
            message: "请选择类别",
            trigger: "blur",
          },
        ],
      },
        }
    },
    mounted() {
      this.selectByPage();
    },
  
    methods: {
        async selectByPage(){
            const {data } = await findSourceAll()
            const {data: res} = await findAll()
            this.types = res.data.records
             this.tableData  = data.data.records
        },
        async deleteBy(id){
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
          this.selectByPage()
        } else {
          this.$message.error(res.message);
        }
      }
        },
        handEdit(row){
            this.buttonStatus=true,
            this.source=row,
            this.dialogFormVisible = true
        },
        handelConfirm() {
      this.$refs["elForm"].validate((valid) => {
        if (!valid) {
          return;
        }
        if(this.buttonStatus){
          this.updateBy()
        }else{
        this.addType()
        }
    });
    },
    async updateBy(){
      const { data:res } = await edit(this.source);
      if(res.success===true){
        this.$notify.success({
          title:'操作成功',
          message:'修改成功'  
        })
        this.selectByPage()
        this.dialogFormVisible = false
        this.buttonStatus=false
        this.source = {}
      } else{
        return this.$message.error('修改:'+res.message)
      }
    },
    async addType(){
      const { data:res } = await save(this.source);
      if(res.success===true){
        this.$notify.success({
          title:'操作成功',
          message:'添加成功'  
        })
        this.selectByPage()
        this.dialogFormVisible = false
        this.source = {}
      } else{
        return this.$message.error('添加:'+res.message)
      }
    },
    }
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