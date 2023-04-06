<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <div class="home">
      <!-- banner -->
      <div class="banner">
        <div class="main animated zoomIn">照片墙</div>
      </div>
      <viewer :images="imageList">
        <div
        class="img animated zoomIn"
          style="
            position: relative;
            display: inline-block;
            float: left;
            margin: 2px 3px;
          "
          v-for="item in imageList"
          :key="item.id"
        >
          <img
            style="
              width: 502px;
              height: 377px;
              cursor: pointer;
              border-radius: 5px;
              background-size: 100%;
              object-fit: cover;
            "
            :src="item.url"
          />
          <div style="position: absolute; top: 230px; left: 10px">
            <p style="color: white; font-size: 23px">{{ item.name }}</p>
            <p style="color: white; font-size: 15px">{{item.region}}</p>
            <p style="color: white; font-size: 15px">{{item.description}}</p>
          </div>
        </div>       
      </viewer>
    </div>
  </div>
</template>

<script>
import { findList } from "@/api/photo";
export default {
  data() {
    return {
      imageList: [],
    };
  },
  components: {},
  computed: {},

  mounted() {
    this.selectByPage();
  },

  methods: {
    async selectByPage() {
      const { data: res } = await findList();
      this.imageList = res.data.records;
    },
  },
};
</script>
<style lang="less"  scoped>
.banner {
  height: 50vh;
  font-size: 2.5em;
  background-color: #49b1f5;
  // background: url("https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/03/319fcc4360ee4c039ad6ba6eefee6f7b.jpg") ;
  background: url("@/assets/images/zpq.jpg") ;
  background-size: cover;
  background-attachment: fixed;
  text-align: center;
}

.main {
  position: absolute;
  top: 20%;
  left: 46%;
  font-size: 2.5rem;
  font-family: Teko, sans-serif;
  text-shadow: 0 1px 2px rgb(0 0 0 / 10%);
  text-transform: uppercase;
  color: rgb(223, 218, 218);
  z-index: 999;
}
img:hover{
  box-shadow:  5px 5px 10px #868686,
             -5px -5px 10px #ffffff;
}
</style>