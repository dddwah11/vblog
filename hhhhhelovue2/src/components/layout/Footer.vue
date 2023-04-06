<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
        <div class="footer-wrap">
      <div>&copy; 2022 - {{ new Date().getFullYear() }} By 小刘</div>
      <a href="https://beian.miit.gov.cn/" target="_blank">
        黔ICP备2022005053号
      </a>
    </div>

  </div>
</template>

<script>
export default {
  data () {
    return {
    };
  },

  components: {},

  computed: {},

  mounted() {},

  methods: {}
}

</script>
<style  scoped>

  .footer-wrap {
    line-height: 2;
    position: static;
    padding: 40px 20px;
    color: #eee;
    font-size: 14px;
    text-align: center;
   background: linear-gradient(140.54deg, #608D00 0%, #D30000 72.37%), linear-gradient(58.72deg, #0029FF 0%, #8FFF00 100%), radial-gradient(100% 164.72% at 100% 100%, #6100FF 0%, #00FF57 100%), radial-gradient(100% 148.07% at 0% 0%, #FFF500 0%, #51D500 100%);
    background-blend-mode: color-dodge, overlay, difference, normal;
    background-size: 400% 400%;
    animation: Gradient 10s ease infinite;
  }
  .footer-wrap a {
    color: #eee !important;
  }
  @keyframes Gradient {
    0% {
      background-position: 0 50%;
    }
    50% {
      background-position: 100% 50%;
    }
    100% {
      background-position: 0 50%;
    }
  }
</style>