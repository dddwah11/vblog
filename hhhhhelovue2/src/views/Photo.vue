<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <!--面包屑-->
    <el-breadcrumb
      separator="/"
      style="padding-left: 10px; padding-bottom: 10px; font-size: 12px"
    >
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>照片墙</el-breadcrumb-item>
    </el-breadcrumb>
    <!--用户列表卡片-->
    <el-button
        type="primary"
        @click="dialogFormVisible2 = true"
        icon="el-icon-plus"
      >
      添加游记
      </el-button>
    <el-card class="box-card">
      <el-table border stripe :data="tableData" style="width: 100%;border">
        <el-table-column prop="id" label="#" width="50"></el-table-column>
        <el-table-column prop="url" label="图片" width="180">
          <template slot-scope="scope">
            <el-image fit="fill" :src="scope.row.url"></el-image>
          </template>
        </el-table-column>
        <el-table-column
          prop="fileName"
          label="文件名"
          width="100"
        ></el-table-column>
        <el-table-column prop="description" label="描述" width="110">
        </el-table-column
        ><el-table-column prop="region" label="地点" width="110">
        </el-table-column>
        <el-table-column prop="url" label="预览" width="110">
          <template slot-scope="scope">
            <el-button @click="view(scope.row.url)" type="primary"
              >预览</el-button
            >
          </template>
        </el-table-column>
        <el-table-column prop="url" label="链接" width="80"></el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scope">
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
              icon="el-icon-delete"
              @click="handEdit(scope.row)"
              >编辑</el-button
            >
          </template>
        </el-table-column>
      </el-table>

    </el-card>
        <!--编辑弹框-->
        <el-dialog
      title="编辑"
      :visible.sync="dialogFormVisible"
      width="40%"
    >
      <el-row :gutter="15">
        <el-form
          ref="elForm"
          :model="photo"
          :rules="rules"
          size="medium"
          label-width="200px"
        >
          <el-col :span="12">
            <el-form-item label="图片名" prop="name">
              <el-input
                v-model="photo.name"
                placeholder="请输入图片名"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="地点和时间" prop="region">
              <el-input
                v-model="photo.region"
                placeholder="请输入地点和时间"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="描述" prop="description">
              <el-input
                v-model="photo.description"
                placeholder="请输入描述"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="图片url" prop="url">
              <el-input
                v-model="photo.url"
                placeholder="请输入图片url"
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

    <!--新增弹框-->
    <el-dialog
      title="新增"
      :visible.sync="dialogFormVisible2"
      width="40%"
    >
      <el-row :gutter="15">
        <el-form
          ref="elForm"
          :model="photos"
          :rules="rules"
          size="medium"
          label-width="200px"
        >
          <el-col :span="12">
            <el-form-item label="图片名" prop="name">
              <el-input
                v-model="photos.name"
                placeholder="请输入图片名"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="地点和时间" prop="region">
              <el-input
                v-model="photos.region"
                placeholder="请输入地点和时间"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="描述" prop="description">
              <el-input
                v-model="photos.description"
                placeholder="请输入描述"
                clearable
                :style="{ width: '200px' }"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="图片url" prop="url">
              <el-input
                v-model="photos.url"
                placeholder="请输入图片url"
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
        <el-button @click="dialogFormVisible2 = false">取 消</el-button>
        <el-button type="primary" @click="handelreg">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
  
  <script>
import { findList, deletedById,updateById,reg } from "@/api/photo";
export default {
  data() {
    return {
      tableData: [],
    photo:{
        id:'',
        name:'',
        region:'',
        description:'',
        url:''
    },
    photos:{
        id:'',
        name:'',
        region:'',
        description:'',
        url:''
    },
    rules: {
        name: [
          {
            required: true,
            message: "请输入图片名",
            trigger: "blur",
          },
        ],
        region: [
          {
            required: true,
            message: "请输入时间和地点",
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
        url: [
          {
            required: true,
            message: "请输入图片url",
            trigger: "blur",
          },
        ],
      },
      url: "",
      dialogFormVisible:false,
      dialogFormVisible2:false
    };
  },
  mounted() {
    this.selectByPage();
  },

  methods: {
    async selectByPage() {
      const { data: res } = await findList();
      this.tableData = res.data.records;
      this.url = res.data.records.url;
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
    handelConfirm() {
      this.$refs["elForm"].validate((valid) => {
        if (!valid) {
          return false;
        } else{
            this.updateBy()
        }
});

    },
    handelreg(){
      this.$refs["elForm"].validate((valid) => {
        if (!valid) {
          return false;
        } else{
            this.reg()
        }
});
    },
    async reg(){
      const { data:res } = await reg(this.photos);
      if(res.success===true){
        
        this.$notify.success({
          title:'操作成功',
          message:'新增成功'  
        })
        // 清空当前用户的信息
        this.$emit('selectByPage')
        this.dialogFormVisible2 = false
        this.photos = {}
        this.selectByPage();

      } else{
        return this.$message.error('新增成功:'+res.message)
      }
    },
    async updateBy(){
      const { data:res } = await updateById(this.photo);
      if(res.success===true){
        
        this.$notify.success({
          title:'操作成功',
          message:'修改成功'  
        })
        // 清空当前用户的信息
        this.$emit('selectByPage')
        this.dialogFormVisible = false
        this.photo = {}
        this.selectByPage();
      } else{
        return this.$message.error('修改:'+res.message)
      }
    },
    handEdit(row){
        this.photo = row
        this.dialogFormVisible=true
    }
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