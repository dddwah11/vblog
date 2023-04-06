<template>
  <div>
    <!--面包屑-->
    <el-breadcrumb
      separator="/"
      style="padding-left: 10px; padding-bottom: 10px; font-size: 12px"
    >
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>新增文章</el-breadcrumb-item>
    </el-breadcrumb>
    <hr />
    <el-form :inline="true" :model="form" class="demo-form-inline">
      <el-form-item label="文章标题">
        <el-input v-model="form.tittle" placeholder="文章标题"></el-input>
      </el-form-item>
      <el-form-item label="文章描述">
        <el-input v-model="form.description" placeholder="文章描述"></el-input>
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
      <el-button @click="resetForm()">重置</el-button>
      <el-button @click="submit()" type="primary">提交</el-button>
      <!-- 图片上传 -->
      <el-form-item>
        <el-upload 
          class="upload-demo"
          :headers="headers"
          action="http://www.lhzzz-61.top/api/file/upload"
          list-type="picture"
          :on-change="handleChange"
        >
          <el-button size="small" type="primary">点击上传略缩图(阿里云oss)</el-button>
          <div slot="tip" class="el-upload__tip">
            文件不超过10MB
          </div>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-upload 
          class="upload-demo"
          :headers="headers"
          action="http://www.lhzzz-61.top/api/minio/upload"
          list-type="picture"
          :on-change="handleChange"
        >
          <el-button size="small" type="primary">点击上传略缩图(minio)</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item label="选择略缩图">
        <el-select v-model="form.thumbnail" placeholder="请选择略缩图">
            <!-- 滚动条要包裹的内容 -->
              <el-option
                v-for="item in files"
                :key="item.url"
                :label="item.name"
                :value="item.url"
                style="height:auto"
              >
                <el-image
                  fit="fill"
                  :src="item.url"
                  style="float: right; width: 50px; height: 50px"
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
    </el-form>

    <mavon-editor
      ref="md"
      :ishljs="true"
      v-model="form.content"
      @imgAdd="imgAdd"
    >
    </mavon-editor>
  </div>
</template>

<script>
import { save } from "@/api/blog/article";
import { findTagList } from "@/api/blog/tag";
import { getTagList } from "@/api/blog/category";
import { findList } from "@/api/file";
import axios from "axios";
export default {
  data() {
    return {
      form: {
        tittle: undefined,
        description: undefined,
        content: undefined,
        weight: undefined,
        thumbnail: undefined,
        categoryId: 1,
        tagId: 1,
        viewCounts:0,
      },
      categoryList: [],
      options: [],
      files: [],
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
    resetForm() {
      this.form = {
        tittle: undefined,
        description: undefined,
        content: undefined,
        weight: 0,
        thumbnail: undefined,
        categoryId: 1,
        tagId: 1,
        viewCounts:0,
      };
    },
    handleChange(){
      this.selectByPage();
    },
    async submit() {
      this.form.viewCounts = 0
      const { data } = await save(this.form);
      if (data.success === true) {
        this.$message.success("提交成功");
        this.resetForm();
      } else this.$message.error("提交失败");
    },
    async selectByPage() {
      const { data } = await findTagList();
      const { data: res } = await findList();
      const { data: ces } = await getTagList();
      this.files = res.data.records;
      this.options = data.data.records;
      this.categoryList = ces.data.records;
    },
    // 绑定@imgAdd event
    imgAdd(pos, $file) {
      let $vm = this.$refs.md;
      // 第一步.将图片上传到服务器.
      var formData = new FormData();
      formData.append("file", $file);
      axios({
        url: "http://www.lhzzz-61.top/api/file/upload",
        method: "post",
        data: formData,
        headers: { "Content-Type": "multipart/form-data" },
      }).then((res) => {
        console.log(res.data);
        // 第二步.将返回的url替换到文本原位置![...](0) -> ![...](url)
        /**
         * $vm 指为mavonEditor实例，可以通过如下两种方式获取
         * 1. 通过引入对象获取: `import {mavonEditor} from ...` 等方式引入后，`$vm`为`mavonEditor`
         * 2. 通过$refs获取: html声明ref : `<mavon-editor ref=md ></mavon-editor>，`$vm`为 `this.$refs.md`
         */
        $vm.$img2Url(pos, res.data.data.url);
      });
    },
  },
};
</script>
<style lang="less" scoped>
.containview {
  margin: 10px 0px 0px 10px;
  width: 50%;
  height: 50%;
}

.tongzhi {
  position: absolute;
  left: 60%;
  top: 15%;
  width: 500px;
  height: 300px;

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both;
  }
  .box-card {
    width: 100%;
    height: 100%;
  }
}
.shuomin {
  position: absolute;
  left: 60%;
  top: 60%;
  width: 500px;
  height: 280px;
  display: flex;
  justify-content: left;
}
.rightdown {
  position: absolute;
  width: 100%;
  height: 100%;
  line-height: 30px;
}
.leftdown {
  position: absolute;
  left: 15%;
  top: 60%;
  width: 42%;
  height: 37%;
  line-height: 30px;
  font-family: "Helvetica Neue";
}
hr {
  margin-top: 20px;
  margin-bottom: 20px;
  border: 0;
  border-top: 1px solid rgba(55, 45, 45, 0.278);
}
</style>