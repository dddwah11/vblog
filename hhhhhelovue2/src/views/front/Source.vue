<!-- eslint-disable vue/multi-word-component-names -->
<template>
    <div>
      <div class="home">
        <!-- banner -->
        <div class="banner">

          <div class="main animated zoomIn">📕资源库</div>
        </div>
        <div class="m-home">
        <div v-for="item in typeData" :key="item.id"
        >
        <!-- 标题 -->
            <div  class="conetentTitle animated fadeInLeft">
                <h1>{{item.name}}</h1>
            </div>
            
            <!-- 主体 -->
            <div   class="content animated fadeInLeft">

          <!-- 个体 -->
          <div v-for="array in sourceData" :key="array.id" v-show="array.type===item.name"   class="img animated zoomIn">
        <a :href="array.url" target="_blank">
          <img 
          style="width: 120px;
          height: 120px;
          cursor: pointer;
          border-radius: 5px;
          margin: 8px 10px;
          background-size: 100%;
          object-fit: cover;
          "
          :src="array.pic"
          />
          </a>
          <p style="color: white; font-size: 20px;margin: 0 0;">{{array.name}}</p>
          <p style="color: white; font-size: 15px;margin: 0 0;">{{array.descrption}}</p>
          <a :href="array.url" target="_blank"  ><p class="pbq" style="height: 30px;border-radius: 7px; font-size: 15px;margin: 0px 0;">{{array.url}}</p></a>
        </div>
</div>
            &nbsp;
            <div >
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
  import {findSourceAll} from   '@/api/source/request'
  import {findAll} from   '@/api/type/request'
  export default {
    data() {
      return {
        sourceData:[],
        typeData:[]
      };
    },
    components: {},
    computed: {},
  
    mounted() {
      this.selectByPage();
    },
  
    methods: {
      async selectByPage() {
        const {data} = await findSourceAll()
        const {data:res} = await findAll()
        this.sourceData = data.data.records
        this.typeData = res.data.records
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
    // background: url("https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/03/799279e4282b4a01a83b47b413c248f2.jpg") center center /cover ;
    background: url("@/assets/images/zyk.jpg") center center /cover ;
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
  .conetentTitle{
    margin: 0 auto;
    border-radius:20px;
    width: 15%;
    background: rgba(255, 255, 255, 0.164);
    backdrop-filter: saturate(200%) blur(20px);
  }
  .card{
    border: 1em;
  }
.img{
    overflow: hidden;
    flex-grow: 1;
    border: none;
    height: 230px;
    width: 200px;
    border: 3px solid rgba(193, 122, 51, 0.1);
    backdrop-filter: saturate(200%) blur(20px);
    border-radius: 10px;
    background: 0 0;
    margin: 10px 10px;
    box-shadow: none;
    font-size: 1em;
    position: relative;
    display: inline-block;
    margin: 10px 10px;
}
.img:hover{
    box-shadow:  5px 5px 10px #534d4dc7,
             0px 0px 10px #4e4c4cb2;
}
.pbq:hover{
  box-shadow:  5px 5px 10px #534d4dc7,
             0px 0px 10px #4e4c4cb2;
}
  </style>