<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div class="main">

    <div style="z-index: 9; position: absolute; left: 45%; top: 32%">
      <div style="color:tomato; margin-left: 50px" class="animated zoomIn"><h1>留言板</h1></div>
      <div style="width: 150px; float: left; border: 1px">
        <el-input
        class="animated zoomIn"
          v-model="messageList.content"
          placeholder="说点什么吧"
        ></el-input>
      </div>
      <div @click="addList" style="float: left; border: 1px">
        <button
          style="
            background-color: #3e7ab1;
            border-radius: 20px;
            color: white;
            width: 60px;
            margin: 5px;
            height: 30px;
            border-color: aliceblue;
          "
          class="animated bounceInLeft"
        >
          发送
        </button>
      </div>
    </div>
    <vue-baberrage
      class="baberrage-box"
      :isShow="barrageIsShow"
      :barrageList="barrageList"
      :loop="barrageLoop"
    >
    </vue-baberrage>
  </div>
</template>

<script>
import { findChatAll, reg } from "@/api/chat/chat";

export default {
  data() {
    return {
      messageList: {
        content: "",
      },
      barrageIsShow: true,
      barrageLoop: true,

      dataList: [],
      barrageList: [],
    };
  },

  components: {},

  computed: {},

  mounted() {
    this.selectByPage();
  },

  methods: {
    async selectByPage() {
      const { data: res } = await findChatAll();
      this.dataList = res.data.records;
      this.dataList.forEach((v) => {
        this.barrageList.push({
          id: v.id,
          avatar:'https://s1.ax1x.com/2022/11/17/zmwSN6.png',
          msg: v.content,
          time: Math.floor(Math.random() * 10 + 6),
        });
      });
    },
    async addList() {
      if (this.messageList.content.trim() === "") {
        this.$message.info("留言不能为空");
        return false;
      }
      const { data: res } = await reg(this.messageList);
      if (res.code === 20000) {
        // 弹出提示信息
        this.$message.success("新增留言成功");
        this.messageList = {};
        this.selectByPage();
      } else {
        // 执行失败
        this.$message.error("留言失败");
      }
    },
  },
};
</script>
<style lang="less"  scoped>
.main {
  position: absolute;
  // background: url("https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/03/2ad7744993b74e8e921ec4edcdcc5c87.jpeg") center center/ cover no-repeat;
  background: url("@/assets/images/lt.jpeg") center center/ cover no-repeat;
  animation: header-effect 1s!important;
  width: 100%;
  height: 100%;
}
.baberrage-box{
    margin-top: 50px;
    width: 200px;
    height: 590px;
}
</style>