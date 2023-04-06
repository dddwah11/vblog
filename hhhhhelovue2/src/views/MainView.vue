<template>
  <el-container class="main_container">
    <!--头部布局-->
    <el-header>
      <!--logo和项目名称-->
      <div class="left_box">
        <span>🎊博客后台</span>
      </div>
      <div class="whether">
        <div id="he-plugin-simple">hi</div>
      </div>
      <!--用户登录展示的头像-->
      <div class="right_box">
        <el-dropdown @command="handleCommand">
          <img src="@/assets/images/src.jpeg" />
          <!--下拉菜单-->
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-house" command="a"
              >系统首页</el-dropdown-item
            >
            <el-dropdown-item icon="el-icon-switch-button" command="b"
              >退出登录</el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-header>
    <el-container>
      <!--侧边栏-->
      <el-aside width="200px" :menu="menus">
        <el-menu
          background-color="#EEEEEE"
          :router="true"
         :default-openeds="null" 
          class="el-menu-vertical-demo"
        >
        <!--:default-openeds="opens" -->
          <div v-for="item in menus" :key="item.id">
            <div v-if="item.path">
              <el-menu-item :index="item.path">
                <template slot="title">
                  <i :class="item.icon"></i>
                  <span slot="title" style="color: normal">{{
                    item.name
                  }}</span>
                </template>
              </el-menu-item>
            </div>
            <div v-else>
              <el-submenu :index="item.id + ''">
                <template slot="title">
                  <i :class="item.icon"></i>
                  <span slot="title" style="color: normal">{{
                    item.name
                  }}</span>
                </template>
                <div v-for="subItem in item.children" :key="subItem.id">
                  <el-menu-item :index="subItem.path">
                    <i :class="subItem.icon"></i>
                    <span slot="title" style="color: normal">{{
                      subItem.name
                    }}</span>
                  </el-menu-item>
                </div>
              </el-submenu>
            </div>
          </div>
        </el-menu>
      </el-aside>
      <el-main class="mainClass">
        <!--路由视图-->
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import store from "@/store";
import { logout } from '@/api/user/user';
export default {
  name: "MainView",
  mounted() {
    window.WIDGET = {
      CONFIG: {
        modules: "01234",
        background: "1",
        tmpColor: "FFFFFF",
        tmpSize: "16",
        cityColor: "FFFFFF",
        citySize: "16",
        aqiColor: "FFFFFF",
        aqiSize: "16",
        weatherIconSize: "24",
        alertIconSize: "18",
        padding: "10px 10px 10px 10px",
        shadow: "1",
        language: "auto",
        borderRadius: "10",
        fixed: "false",
        vertical: "top",
        horizontal: "right",
        city: "CN101260101",
        key: "66a855861d724908b73de62ceadfe971",
      },
    };
    let script = document.createElement("script");
    script.type = "text/javascript";
    script.src =
      "https://widget.qweather.net/simple/static/js/he-simple-common.js?v=2.0";
    document.getElementsByTagName("head")[0].appendChild(script);
  },
  data() {
    return {
      customerRole: localStorage.getItem("user")
        ? JSON.parse(localStorage.getItem("user"))
        : {},
      menus: sessionStorage.getItem("menus")
        ? JSON.parse(sessionStorage.getItem("menus"))
        : [],
      opens: sessionStorage.getItem("menus")
        ? JSON.parse(sessionStorage.getItem("menus")).map((v) => v.id + "")
        : [],
    };
  },
  methods: {
   async handleCommand(command) {
      if (command == "a") {
        this.$router.push("/contain");
      }
      if (command == "b") {
        const {data:res} = await logout()
        console.log(res);
        store.commit("logout");
        this.$notify({
          title: '操作',
          message: '登出成功',
          offset: 100
        });
      }
    },
  },
};
</script>

<style lang="less" >
.whether {
  position: relative;
  z-index: 9999;
  left: 20%;
}
.mainClass {
  background-image: url("@/assets/images/jg.png");
  background-size: cover;
}

/*设置整个容器的高度*/
.main_container {
  height: 750px;
}

/*头部布局*/
.el-header {
  background-color: #222;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  color: #ffffff;
  align-items: center;
  font-size: 20px;

  /*左边logo和标题*/

  .left_box {
    display: flex;
    align-items: center;

    /*logo*/

    img {
      width: 60px;
      height: 60px;
      margin: 0px 0px 10px 15px;
    }

    /*标题*/

    span {
      margin-left: 15px;
    }
  }

  /*右边的登录头像*/

  .right_box {
    .el-dropdown > img {
      width: 55px;
      height: 55px;
      border-radius: 50%;
      margin: 10px 15px 5px 0px;
      background-size: contain;
      cursor: pointer;
    }
  }
}

/*侧边栏*/
.el-aside {
  background-color: #eeeeee;

  .el-menu-vertical-demo {
    border-right: none;
    width: 180px;
  }
}

/*下拉菜单的样式*/
.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
</style>
