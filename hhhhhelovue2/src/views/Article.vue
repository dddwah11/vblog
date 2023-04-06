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
      <el-breadcrumb-item>文章管理</el-breadcrumb-item>
    </el-breadcrumb>
    <!--用户列表卡片-->
    <el-card class="box-card">
      <el-form :inline="true" :model="article" class="demo-form-inline">
        <el-form-item label-width="40px">
          <el-input
            clearable
            v-model="article.tittle"
            placeholder="请输入标题"
          ></el-input>
        </el-form-item>
        <el-form-item style="margin-left: 10px">
          <el-button icon="el-icon-refresh" @click="resetArticle"
            >重置</el-button
          >
          <el-button type="primary" icon="el-icon-search" @click="selectByPage"
            >查询</el-button
          >
        </el-form-item>
      </el-form>
      <el-table border stripe :data="tableData" style="width: 100%;border">
        <el-table-column prop="id" label="#" width="50"></el-table-column>
        <el-table-column
          prop="tittle"
          label="标题"
          width="100"
        ></el-table-column>
        <el-table-column prop="description" label="描述" width="110">
        </el-table-column>
        <el-table-column prop="content" label="内容" width="110">
          <template slot-scope="scope">
            <el-button @click="view(scope.row)" type="primary">预览</el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop="categoryName"
          label="分类名"
          width="110"
        ></el-table-column>
        <el-table-column
          prop="viewCounts"
          label="浏览量"
          width="110"
        ></el-table-column>
        <el-table-column
          prop="thumbnail"
          label="略缩图"
          width="110"
        ></el-table-column>
        <el-table-column
          prop="status"
          label="状态"
          width="80"
        ></el-table-column>
        <el-table-column
          prop="isCommit"
          label="是否提交"
          width="80"
        ></el-table-column>
        <el-table-column
          prop="weight"
          label="是否置顶"
          width="80"
        ></el-table-column>
        <el-table-column
          prop="createdTime"
          label="发布时间"
          width="100"
        ></el-table-column>
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
    <!--编辑弹框-->
    <el-dialog title="编辑" :visible.sync="dialogFormVisible" width="80%">
      <el-form :inline="true" :model="form" class="demo-form-inline">
        <el-form-item label="文章标题">
          <el-input v-model="form.tittle" placeholder="文章标题"></el-input>
        </el-form-item>
        <el-form-item label="文章描述">
          <el-input
            v-model="form.description"
            placeholder="文章描述"
          ></el-input>
        </el-form-item>
        <el-form-item label="标签名">
          <el-select v-model="form.categoryId" placeholder="请选择标签">
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否置顶">
          <el-switch
            v-model="form.weight"
            active-value="1"
            inactive-value="0"
          ></el-switch>
        </el-form-item>
        <el-form-item>
          <el-upload
            class="upload-demo"
            :headers="headers"
            action="http://www.lhzzz-61.top/api/file/upload"
            list-type="picture"
          >
            <el-button size="small" type="primary">点击上传略缩图</el-button>
            <div slot="tip" class="el-upload__tip">不超过10MB</div>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-upload
            class="upload-demo"
            :headers="headers"
            action="http://www.lhzzz-61.top/api/minio/upload"
            list-type="picture"
          >
            <el-button size="small" type="primary">点击上传略缩图</el-button>
            <div slot="tip" class="el-upload__tip">不超过10MB</div>
          </el-upload>
        </el-form-item>

        <el-form-item label="选择略缩图">
          <el-select v-model="form.thumbnail" placeholder="请选择略缩图">
            <el-option
              v-for="item in files"
              :key="item.url"
              :label="item.name"
              :value="item.url"
            >
              <el-image
                fit="fill"
                :src="item.url"
                style="margin: 2px; float: right; width: 50px; height: 50px"
              ></el-image>
              <span style="float: left">{{ item.name }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="类别">
        <el-select v-model="form.tagId" placeholder="请选择类别">
          <el-option
            v-for="item in categoryList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" @click="updateBy()">确 定</el-button>
      <el-button @click="dialogFormVisible = false">取 消</el-button>
      </el-form>
      <mavon-editor ref="md" :ishljs="true" v-model="form.content">
      </mavon-editor>
      <div slot="footer" class="dialog-footer">
      </div>
    </el-dialog>
    <el-dialog title="文章预览" :visible.sync="dialogFormVisible1" width="80%">
      <el-card>
        <div>
          <el-image
            fit="fill"
            :src="thumbnail"
            style=
            "border-radius: 20px;
              width: 100%;
              height: 100%;
            "
          ></el-image>
        </div>
        <div>
          <h1 style="font-size: 50px"> {{tittle}} </h1>
        </div>
        <div>
          <mavon-editor
            class="md"
            :value="content"
            :subfield="false"
            :defaultOpen="'preview'"
            :toolbarsFlag="false"
            :editable="false"
            :scrollStyle="true"
            :ishljs="true"
          />
        </div>
      </el-card>
    </el-dialog>
  </div>
</template>

<script>
import { findBlogList, deletedById, edit } from "@/api/blog/article";
import { findTagList } from "@/api/blog/tag";
import { findList } from "@/api/file";
import { getTagList } from "@/api/blog/category";
export default {
  data() {
    return {
      article: {
        tittle: "",
      },
      dialogFormVisible: false,
      dialogFormVisible1: false,
      tableData: [],
      options: [],
      //每页显示的条数
      size: 5,
      //总条数
      total: 100,
      //当前页码
      current: 1,
      form: {
        tittle: undefined,
        description: undefined,
        content: undefined,
        weight: undefined,
        thumbnail: undefined,
        isCommit: undefined,
        categoryId: undefined,
        tagId:undefined,
      },
      categoryList:[],
      files: [],
      content: "",
      thumbnail: "",
      tittle:"",
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
    resetArticle() {
      this.article.tittle = "";
      this.selectByPage();
    },
    async selectByPage() {
      const { data } = await findBlogList(
        this.current,
        this.size,
        this.article
      );
      this.tableData = data.data.records;
      this.total = data.data.total;
      const { data: res } = await findTagList();
      this.options = res.data.records;
      const { data: resp } = await findList();
      this.files = resp.data.records;
      const { data: ces } = await getTagList();
      this.categoryList = ces.data.records;

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
          // location.reload()
          this.selectByPage();
        } else {
          this.$message.error(res.message);
        }
      }
    },
    handEdit(row) {
      (this.dialogFormVisible = true), (this.form = row);
    },
    async updateBy() {
      const { data: res } = await edit(this.form);
      if (res.success === true) {
        this.$notify.success({
          title: "操作成功",
          message: "修改成功",
        });
        // 清空当前用户的信息
        this.$emit("selectByPage");
        this.dialogFormVisible = false;
        this.form = {};
      }
    },
    view(row) {
      this.dialogFormVisible1 = true;
      this.content = row.content;
      this.thumbnail = row.thumbnail;
      this.tittle = row.tittle;
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