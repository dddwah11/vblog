<template>
  <div v-loading="loading" :style="'height:'+height">
    <iframe :src="src" style="width: 100%;height: 100%" scrolling="auto" />
    </div>
</template>

<script>
export default {
  data () {
    return {
        src: "http://www.lhzzz-61.top/api/druid/login.html",
        height: document.documentElement.clientHeight - 94.5 + "px;",
      loading: true

    };
  },
  mounted() {
    setTimeout(() => {
      this.loading = false;
    }, 230);
    const that = this;
    window.onresize = function temp() {
      that.height = document.documentElement.clientHeight - 94.5 + "px;"
  }
  }
}
</script>
<style  scoped>
</style>