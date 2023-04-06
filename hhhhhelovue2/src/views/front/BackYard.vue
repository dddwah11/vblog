<!-- eslint-disable vue/multi-word-component-names -->
<template>
    <div>
      <div class="home">
        <!-- banner -->
        <div class="banner">

          <div class="main animated zoomIn">关于我</div>
        </div>
        <div class="m-home">
        <div>
          <div class="content animated fadeInLeft">
                <img style="width:300px;height:300px;margin:20px;border-radius:50%" src="@/assets/images/src.jpeg" />
                <div style="margin:50px;
    font: 20px/1.5 Tahoma,Helvetica,Arial,'宋体';color:black
                ">
                <p>本人在校大学生一枚~</p>
                <p>对技术有着求知的欲望，敢于尝试新东西🎇</p>
                  <p>热爱技术，热爱思考，，求知的路上愈走愈远...</p>
                  <p>本站记录了我学习的过程📕，还有趣事🌆</p>
                </div>
              <h1>联系我</h1>
              <div style="width: 100%;height:330px;">
              <img style="float:center;width:260px;height:320px;margin:0 95px;border-radius: 10px;object-fit: over;background-size: 100%;" src="@/assets/images/qq.jpg" />
              <!-- <img style="float:left;width:260px;height:330px;margin:0 40px;border-radius: 10px;object-fit: over;" src="http://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/03/98dedb437aee4fde886be63b9ac0506f.png" /> -->
            </div>
            &nbsp;
            <div >
            </div>
          </div>
        </div>
      </div>
      &nbsp;

      

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
  
  export default {
    data() {
      return {
      };
    },
    components: {},
    computed: {},
  
    mounted() {
      this.selectByPage();
    },
  
    methods: {
      async selectByPage() {

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
    background: url("@/assets/images/zyk.jpg") center center /cover ;
    // background: url("https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/03/799279e4282b4a01a83b47b413c248f2.jpg") center center /cover ;
    background-attachment: fixed;
    text-align: center;
    // animation: header-effect 1s!important;
  }
  
  .main {
    position: absolute;
    top: 10%;
    left: 45%;
    font-size: 2.5rem;
    font-family: Teko, sans-serif;
    text-shadow: 0 1px 2px rgba(222, 79, 79, 0.1);
    text-transform: uppercase;
    color: rgb(223, 218, 218);
    z-index: 999
  }
  .m-home {
    margin: 20px auto;
    text-align: center;
    
  }

  .content {
    margin: 0 auto;
    border-radius:20px;
    width: 60%;
    background: rgba(255, 255, 255, 0.164);
    backdrop-filter: saturate(200%) blur(20px);
  }
  .card{
    border: 1em;
  }
  </style>