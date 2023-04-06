<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <div class="home">
      <!-- banner -->
      <div class="banner">
        <div class="main animated zoomIn">标签</div>
      </div>
      <!-- content -->
      <div class="m-home animated fadeInLeft">
        <el-card class="viewcard">
          <div style="float: left; margin-top: -10px"><h1>标签</h1></div>
          <div style="float: right; margin-top: -10px">
            <h1>
              <i class="el-icon-s-data"></i>
              <div style="color: coral; float: right; margin-left: 10px">
                {{ this.total }}
              </div>
            </h1>
          </div>
        </el-card>

        <!-- 博客标签 -->
        <div>
          <el-card class="card">
            <div
              v-for="item in tagList"
              :key="item.id"
              style="float: left; margin: 10px"
            >
              <div @click="handChange(item.id)" style="cursor: pointer">
                <el-tag type="warning">
                  {{ item.name }}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{
                    item.count
                  }}</el-tag
                >
              </div>
            </div>
          </el-card>
        </div>


        <!-- 内容 -->
        <div>
          <el-card class="content">
            <el-card v-for="item in blogList" :key="item.id" class="box-card">
              <div
                @click="$router.push('/detail?id=' + item.id)"
                style="cursor: pointer"
              >
                <div v-if="item.viewCounts">
                  <i class="el-icon-view" />{{ item.viewCounts }}
                </div>
                <h2 style="margin: 6px">{{ item.tittle }}</h2>
                <div
                  v-if="item.weight === 1"
                  style="
                    float: right;
                    margin-bottom: 20px;
                    margin-top: -50px;
                    width: 80px;
                    height: 50px;
                    border-radius: 20px;
                    background: #50b978;
                    text-align: center;
                    color: #e4dddd;
                  "
                >
                  <h3>TOP</h3>
                </div>
                <img
                  style="width: 560px; height: 280px; border-radius: 20px"
                  :src="item.thumbnail"
                />
                <div
                  style="
                    border-bottom: 1px solid orangered;
                    padding: 5px 0;
                    font-size: 20px;
                  "
                ></div>
                <span>{{ item.description }}</span>
                <div style="font-size: 14px; margin-top: 20px; color: grey">
                  <i class="el-icon-time"></i>创建于
                  <span>{{ item.createdTime }}</span>
                </div>
                <div style="float: right; margin-top: -20px">
                  <el-tag>{{ item.tagName }} </el-tag>
                </div>
              </div>
            </el-card>
          </el-card>
        </div>
        <!-- 分页 -->
        <el-pagination
          style="margin: 10px 550px"
          :current-page="current"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-size="size"
          layout="total,prev, pager, next"
          :total="blogTotal"
        >
        </el-pagination>
      </div>
    </div>

    <Footer />
        <!-- 上滑工具 -->
        <div class="tooBar">
      <div style="margin-right: 60px">
        <el-button type="primary" @click="scrollUp"
          ><i class="el-icon-arrow-up"></i
        ></el-button>
      </div>
    </div>
  </div>
</template>

<script>
import Footer from "@/components/layout/Footer.vue";
import { tagTotal, findTagList } from "@/api/blog/tag";
import { findBlog, findByCa } from "@/api/blog/article";
export default {
  data() {
    return {
      dataList: [],
      total: 0,
      tagList: [],
      blogList: [],
      current: 1,
      size: 4,
      blogTotal: 0,
    };
  },
  components: {
    Footer,
  },
  computed: {},

  mounted() {
    this.selectByPage();
  },

  methods: {
    scrollUp() {
      window.scrollTo({
        behavior: "smooth",
        top: (0, 0),
      });
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
      const { data } = await tagTotal();
      const { data: res } = await findTagList();
      const { data: as } = await findBlog(this.current, this.size);
      this.blogTotal = as.data.total;
      this.blogList = as.data.records;
      this.tagList = res.data.records;
      this.total = data.data.total;
    },
    async handChange(catagoryId) {
      const { data: res } = await findByCa( catagoryId);
      this.blogTotal = res.data.total;
      this.blogList = res.data.records;
    },
  },
};
</script>
<style lang="less"  scoped>
.banner {
  height: 50vh;
  font-size: 2.5em;
  background-color: #49b1f5;
  // background: url("https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/03/896c734fbcb64374b44d837e33975cd7.jpg");
  background: url("@/assets/images/lb.jpg");
  background-size: cover;
  background-attachment: fixed;
  text-align: center;
}

.main {
  position: absolute;
  top: 10%;
  left: 48%;
  font-size: 2.5rem;
  font-family: Teko, sans-serif;
  text-shadow: 0 1px 2px rgb(0 0 0 / 10%);
  text-transform: uppercase;
  color: rgb(223, 218, 218);
  z-index: 999;
}
.m-home {
  margin: 0 auto;
  text-align: center;
}

.viewcard {
  margin: 10px auto;
  width: 60%;
  border-radius: 30px;
  color: grey;
}
.card {
  margin: 10px auto;
  width: 60%;
  border-radius: 30px;
  height: 100%;
}
.content {
  margin: 10px auto;
  width: 60%;
  border-radius: 30px;
  height: 100%;
  .box-card {
    margin: 20px;
  }
}
.lcard{
  width:60%;
  margin: 10px auto;
  border-radius: 30px;
  text-align: center;


}
.tooBar {
  bottom: 20px;
  right: 0;
  position: fixed;
}
</style>