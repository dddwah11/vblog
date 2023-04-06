<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <div class="home">
      <!-- banner -->
      <div class="banner">
        <div class="main animated zoomIn">归档</div>
      </div>
      <div class="m-home">
        <div>
          <div class="content animated fadeInLeft">
            <div>
              <div >
                <div style="margin: 10px; color: coral">
                  <h1>目前共计{{ this.total }}篇文章，请继续加油~</h1>
                </div>
              </div>

              <div v-for="item in List" :key="item.id" class="lv-time-section">
                <div class="lv-time-left">
                  <div
                    class="lv-time-time"
                    style="font-size: large; color: rgb(55, 65, 74);"
                   >
                    {{ item.createdTime }}
                  </div>
                </div>
                <div
                  class="lv-time-right"
                >
                  <div class="lv-time-container  " style="cursor:pointer" @click="$router.push('/detail?id=' + item.id)">
                    <div class="lv-time-content" style="color: antiquewhite">
                     📝
                      {{ item.tittle }}
                      <div>
                      <img style="width: 100px;border-radius: 10px;" :src="item.thumbnail" />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
        <!-- 上滑工具 -->
    <div class="tooBar">
      <div style="margin-right:60px">
        <el-button type="primary" @click="scrollUp"><i class="el-icon-arrow-up"></i></el-button>
      </div>
    </div>
  </div>
</template>

<script>
import { findAllBlog } from "@/api/blog/article";

export default {
  data() {
    return {
      List: [],
      total: 0,
    };
  },
  components: {},
  computed: {},

  mounted() {
    this.selectByPage();
  },

  methods: {
    async selectByPage() {
      const { data } = await findAllBlog();
      this.List = data.data.records;
      this.total = data.data.total;
    },
     scrollUp() {
      window.scrollTo({
        behavior: "smooth",
        top: (0,0),
      });
    },
  },
};
</script>
<style lang="less"  scoped>
.tooBar{
    bottom: 20px;
    right: 0;
  position: fixed;
}
.banner {
  height: 50vh;
  font-size: 2.5em;
  background-color: #49b1f5;
  // background: url("https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/03/383c00b3b1ac4f309a503dcb9438d8a4.jpg") center center /cover  ;
  background: url("@/assets/images/gd.jpg") center center /cover  ;
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
  background: #e0e0e0;
}
.content:hover{
  box-shadow:  5px 5px 22px #949494,
             -5px -5px 22px #4b4b4b;
}
.lv-time-section {
  font-size: 0;
  width: 100%;
}
.lv-time-left,
.lv-time-right {
  display: inline-block;
  width: calc(50% - 1px);
  height: 100px;
}

.lv-time-left {
  border-right: 2px solid #8ea8b8;
}

.lv-time-time,
.lv-time-container {
  font-size: 16px;
}

.lv-time-container {
  background-color: #3279a5;
  border-radius: 5px;
}
.lv-time-container:hover{
  box-shadow:  5px 5px 10px #24545a,
             -5px -5px 10px #3c8c96;
}
.lv-time-content {
  padding: 5px;
}

.lv-time-left .lv-time-container,
.lv-time-left .lv-time-time,
.lv-time-left .lv-time-container .lv-time-content {
  float: right;
}

.lv-time-right .lv-time-container {
  float: left;
  margin-left: 15px;
  padding-right: 10px;
  text-align: left;
}

.lv-time-right .lv-time-container:before {
  content: "";
  display: block;
  float: left;
  border-top: 5px solid transparent;
  border-right: 10px solid #125782;
  border-bottom: 5px solid transparent;
  margin-left: -11px;
  margin-top: 5px;
}

</style>