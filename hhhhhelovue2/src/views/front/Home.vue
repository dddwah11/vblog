<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <div class="home">
      <!-- banner -->
      <div class="banner">
        <vue-particles
          class="login-background"
          color="#97D0F2"
          :particleOpacity="0.7"
          :particlesNumber="80"
          shapeType="circle"
          :particleSize="4"
          linesColor="#dedede"
          :linesWidth="1"
          :lineLinked="true"
          :lineOpacity="0.4"
          :linesDistance="150"
          :moveSpeed="3"
          :hoverEffect="true"
          hoverMode="grab"
          :clickEffect="false"
        >
        </vue-particles>

        <div class="main animated zoomIn">首页</div>
        <div class="nav animated zoomIn">
          <nav>
            <el-tooltip
              class="item"
              effect="light"
              content="dddwah11"
              placement="bottom"
            >
            <a
              style="float: left"
              href="https://github.com/dddwah11?tab=repositories" target="_blank"
            >
              <svg class="icon" aria-hidden="true">
                <use xlink:href="#icon-GitHub"></use>
              </svg>
              <p style="font-size: 20px; margin: 0 6px">github</p>
            </a>
            </el-tooltip>
            <el-tooltip
              class="item"
              effect="light"
              content="1937589397"
              placement="bottom"
            >
              <div style="float: left; color: white; cursor: pointer">
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-QQ"></use>
                </svg>
                <p style="font-size: 20px; margin: 0 6px">QQ</p>
              </div>
            </el-tooltip>
            <el-tooltip
              class="item"
              effect="light"
              content="lhz18286812764"
              placement="bottom"
            >
              <div style="float: left; color: white; cursor: pointer">
                <svg class="icon" aria-hidden="true">
                  <use xlink:href="#icon-weixin"></use>
                </svg>
                <p style="font-size: 20px; margin: 0 6px">微信</p>
              </div>
            </el-tooltip>
            <el-tooltip
              class="item"
              effect="light"
              content="dwah"
              placement="bottom"
            >
            <a style="float: left" href="https://gitee.com/dwah" target="_blank" >
              <svg class="icon" aria-hidden="true">
                <use xlink:href="#icon-gitee"></use>
              </svg>
              <p style="font-size: 20px; margin: 0 6px">gitee</p>
            </a>
            </el-tooltip>
            <el-tooltip
              class="item"
              effect="light"
              content="小刘"
              placement="bottom"
            >
            <a style="float: left" href="backyard" >
              <svg class="icon" aria-hidden="true">
                <use
                  xlink:href="#icon-xiangsu-zhongguojie
"
                ></use>
              </svg>
              <p style="font-size: 20px; margin: 0 6px">小刘</p>
            </a>
          </el-tooltip>

          </nav>

          <!-- 向下滚动 -->
          <div class="scroll-down" @click="scrollDown">
            <h4>向下滚动<i class="el-icon-arrow-down"></i></h4>
          </div>
        </div>
      </div>

      <!-- content -->
      <div class="m-home animated fadeInLeft">
        <el-card class="card"
          ><div style="float: left"><h1>文章</h1></div>
          <div style="float: right">
            <h1>
              共
              <div style="display: inline; color: coral; margin: 0px 12px">
                {{ this.total }}
              </div>
              篇
            </h1>
          </div></el-card
        >
        <el-card v-for="(item,index) in blogList" :key="index" class="box-card">
          <div
            @click="$router.push('/detail?id=' + item.id)"
            style="cursor: pointer"
          >
            <div style="color: grey; font-size: 1px; margin: 0 5px">
              <i class="el-icon-view" />
              {{ item.viewCounts == null ? "0" : item.viewCounts }}
            </div>
            <h2 style="margin: 1px">{{ item.tittle }}</h2>
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
                padding: 1px 0;
                font-size: 20px;
              "
            ></div>
            <span>{{ item.description }}</span>
            <div style="font-size: 14px; margin-top: 20px; color: grey">
              <i class="el-icon-time"></i>创建于
              <span>{{ item.createdTime }}</span>
            </div>
            <div style="float: right; margin-top: -20px">
              <el-tag v-if="item.tagName">{{ item.tagName }} </el-tag>
            </div>
          </div>
        </el-card>
        <!-- 分页 -->
        <el-pagination
          style="margin: 10px 550px"
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="this.current"
          :page-size="this.size"
          layout="prev, pager, next"
          :total="total"
        >
        </el-pagination>
      </div>

      <!-- 右边头像 -->
      <div class="right animated fadeInLeft">
        <el-card class="rightcard">
          <img
            src="@/assets/images/src.jpeg" 
            style="width: 35%; margin-left: 28%; border-radius: 50%"
          />
          <!-- 姓名 -->
          <div style="margin-left: 40%; font-size: 20px; margin-top: 20px">
            小刘
          </div>
          <!--  一些数据 -->
          <div style="float: left; margin-left: 40px">
            <div style="margin-top: 20px">🎨文章</div>
            <div style="margin: 14px">
              <h2>{{ this.total }}</h2>
            </div>
          </div>
          <div style="float: left; margin-left: 40px">
            <div style="margin-top: 20px">🎯标签</div>
            <div style="margin: 14px">
              <h2>{{ this.tagTotall }}</h2>
            </div>
          </div>
          <div style="float: left; margin-left: 40px">
            <div style="margin-top: 20px">🏆类别</div>
            <div style="margin: 14px">
              <h2>{{ this.catTotal }}</h2>
            </div>
          </div>
        </el-card>
      </div>

      <!-- 右边标签 -->
      <div class="righttag animated fadeInLeft">
        <el-card class="tagcard">
          <div style="float: left; font-size: 25px">🎯标签</div>
          <div
            style="float: right; cursor: pointer; color: dodgerblue"
            @click="$router.push('/catagories')"
          >
            more<i class="el-icon-arrow-right"></i>
          </div>
          <div style="margin-top: 45px">
            <el-tag
              v-for="(item,index) in tagList"
              :key="index"
              style="display: right; margin: 5px; width: 30%"
              type="warning"
            >
              <div @click="$router.push('/catagories')" style="cursor: pointer">
                {{ item.name }}
                <div style="float: right">{{ item.count }}</div>
              </div></el-tag
            >
          </div>
        </el-card>
      </div>

      <!-- 类别 -->
      <div class="lei animated fadeInLeft">
        <el-card class="tagcard">
          <div style="float: left; font-size: 25px">🏆类别</div>
          <div
            style="float: right; cursor: pointer; color: dodgerblue"
            @click="$router.push('/tags')"
          >
            more<i class="el-icon-arrow-right"></i>
          </div>
          <div style="margin-top: 45px">
            <el-tag
              v-for="(item,index) in catagoryList"
              style="display: right; margin: 5px; width: 30%"
              type="success"
              :key="index"
              ><div  @click="$router.push('/tags')" style="cursor: pointer">
                {{ item.name }}
                <div style="float: right">{{ item.count }}</div>
              </div></el-tag
            >
          </div>
        </el-card>
      </div>

      <!-- 友链 -->
      <div class="link animated fadeInLeft">
        <el-card class="linkcard">
          <div style="float: left; font-size: 25px">🎄友链</div>
          <div
            style="float: right; cursor: pointer; color: dodgerblue"
            @click="$router.push('/linkview')"
          >
            more<i class="el-icon-arrow-right"></i>
          </div>
          <div style="margin-top: 45px; border: 2px">
            <div
              v-for="(item,index) in List"
              :key="index"
              style="display: inline-block; margin: 5px 2px; float: left"
            >
              <a :href="item.site" target="_blank">
                <img
                  class="youlian"
                  :src="item.photoUrl"
                  style="
                    cursor: pointer;
                    border-radius: 50%;
                    width: 80px;
                    height: 80px;
                  "
                />
              </a>
              <p style="font-size: 1px;text-align:center">{{ item.title }}</p>
            </div>
          </div>
        </el-card>
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
import { getLinkList } from "@/api/link/link";
import Footer from "@/components/layout/Footer.vue";
import { findBlog, findByCa } from "@/api/blog/article";
import { findTagList, tagTotal } from "@/api/blog/tag";
import { getTagList,catTotal } from "@/api/blog/category";
export default {
  data() {
    return {
      List: [],
      tagList: [],
      blogList: [],
      current: 1,
      size: 6,
      total: 0,
      tagTotall: 0,
      catagoryList: [],
      catTotal:0,
    };
  },
  components: {
    Footer
  },
  computed: {},

  mounted() {
    this.selectByPage();
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
    // 初始化
    scrollDown() {
      window.scrollTo({
        behavior: "smooth",
        top: document.documentElement.clientHeight,
      });
    },
    scrollUp() {
      window.scrollTo({
        behavior: "smooth",
        top: (0, 0),
      });
    },

    // 查询博客 page
    async selectByPage() {
      const { data } = await findBlog(this.current, this.size);
      const { data: res } = await findTagList();
      const { data: as } = await tagTotal();
      const { data: cs } = await getTagList();
      const { data: ls } = await getLinkList();
      const{data:ws} = await catTotal();
      this.catTotal = ws.data.total
      this.List = ls.data.records;
      this.catagoryList = cs.data.records;
      this.tagList = res.data.records;
      this.tagTotall = as.data.total;
      this.blogList = data.data.records;
      this.total = data.data.total;
    },
    async handChange(catagoryId) {
      const { data: res } = await findByCa(catagoryId);
      this.blogList = res.data.records;
    },
  },
};
</script>
<style lang="less"  scoped>

.righttag {
  flex: auto;
  position: absolute;
  top: 200%;
  left: 68%;
  .tagcard {
    width: 350px;
    height: 100%;
    border-radius: 20px;
  }
}
.lei {
  position: absolute;
  top: 163%;
  left: 68%;
  .tagcard {
    width: 350px;
    height: 100%;
    border-radius: 20px;
  }
}
.banner {
  height: 100vh;
  font-size: 2.5em;
  // background: url("https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/10/e3208e5d26e7488ab9f7187769829959.jpg") 50% / cover no-repeat;
  background: url("@/assets/images/banner.jpg") 50% / cover no-repeat;
  background-attachment: fixed;
  text-align: center;
}
.right {
  position: absolute;
  top: 120%;
  left: 68%;
  .rightcard {
    width: 350px;
    border-radius: 20px;
  }
}
.link {
  position: absolute;
  top: 240%;
  left: 68%;
  .linkcard {
    width: 350px;
    border-radius: 20px;
  }
}
.m-home {
  .card {
    border-radius: 20px;
    width: 600px;
    border-top: none;
    margin: 10px 320px;
  }

  .box-card {
    border-radius: 20px;
    width: 600px;
    border-top: none;
    margin: 10px 320px;
  }
}

.main {
  position: absolute;
  left: 45%;
  top: 38%;
  font-size: 3.5rem;
  font-family: Teko, sans-serif;
  text-shadow: 0 1px 2px rgb(0 0 0 / 10%);
  text-transform: uppercase;
  color: #dad3d3d6;
  z-index: 999;
}
.nav {
  position: absolute;
  width: 406px;
  height: 105px;
  top: 65%;
  left: 36%;
  background: rgba(255, 255, 255, 0.164);
  backdrop-filter: saturate(200%) blur(20px);
  border-radius: 10px;
}
.el-upload__tip {
  width: 30px;
  height: 40px;
  margin: 0 0;
  padding: 0 0;
}

.icon {
  border-radius: 50%;
  margin: 3px 10px;
  width: 1.5em;
  height: 1.5em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}
.scroll-down {
  font-size: 1rem;
  color: white;
  position: absolute;
  left: 43%;
  top: 200%;
  cursor: pointer;
}
.tooBar {
  bottom: 20px;
  right: 0;
  position: fixed;
}
.icon:hover {
  box-shadow: 5px 5px 22px #949494, -5px -5px 22px #ffffff;
}
.youlian:hover {
  box-shadow: 5px 5px 22px #949494, -5px -5px 22px #ffffff;
}
</style>