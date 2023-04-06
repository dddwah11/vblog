<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <div class="home">
      <!-- banner -->
      <div class="banner">
        <div class="main animated zoomIn">友链</div>
      </div>
      <div class="m-home animated fadeInLeft">
        <div>
          <div class="content">
              <div>
                <h1>网站要求</h1>
                <div style="color: black">
                  <p>无色情内容，无政治敏感内容，网站要能长期正常访问</p>
                  <p>有个人原创文章，两个月内有新文章更新</p>
                  <p>原创博客、技术博客、游记博客优先</p>
                </div>
                <h1>申请格式</h1>
                <p>例如</p>
                <p>博客名称：百度</p>
                <p>博客网址:https://www.baidu.com</p>
                <p>博客图片地址：图片url</p>
                <div style="color:chocolate">联系我：qq1937589397</div>
              </div>

            <!-- 地址 -->
            <div v-for="item in List" :key="item.id" style="float: left; margin: 10px 10px;">
            <a :href="item.site" target="_blank">
              <el-card style="border-radius: 20px;">
                <img
                  style="width: 200px;height: 200px;border-radius: 50%"
                  :src="item.photoUrl"
                />
                <div>{{item.title}}</div>
                <div style="color:gray">
                  <i class="el-icon-monitor"/>
                  {{item.site}}
                </div>

              </el-card>
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
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
import { getLinkList } from "@/api/link/link";

export default {
  data() {
    return {
      List: [],
    };
  },
  components: {},
  computed: {},

  mounted() {
    this.selectByPage();
  },

  methods: {
    async selectByPage() {
      const { data } = await getLinkList();
      this.List = data.data.records;
    },
    scrollUp() {
      window.scrollTo({
        behavior: "smooth",
        top: (0, 0),
      });
    },
  },
};
</script>
<style lang="less"  scoped>
.tooBar {
  bottom: 20px;
  right: 0;
  position: fixed;
}
.banner {
  height: 50vh;
  font-size: 2.5em;
  background-color: #49b1f5;
  // background: url("https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/05/8b95ef5b132b464192f4079f4ea21e82.jpg");
  background: url("@/assets/images/yl.png");
  background-size: cover;
  background-attachment: fixed;
  text-align: center;
}

.main {
  position: absolute;
  top: 10%;
  left: 47%;
  font-size: 2.5rem;
  font-family: Teko, sans-serif;
  text-shadow: 0 1px 2px rgba(222, 79, 79, 0.1);
  text-transform: uppercase;
  color: rgb(223, 218, 218);
  z-index: 999;
}
.m-home {
  margin: 20px auto;
  text-align: center;
}
.content {
  margin: 0 auto;
  width: 60%;
  border-radius: 20px;
  background: rgba(255, 255, 255, 0.164);

}

</style>