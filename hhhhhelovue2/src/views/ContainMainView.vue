<template>
  <div  >
    <!--面包屑-->
    <el-breadcrumb
      separator="/"
      style="padding-left: 10px; padding-bottom: 10px; font-size: 12px"
    >
      <el-breadcrumb-item :to="{ path: '/main' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>主页</el-breadcrumb-item>
    </el-breadcrumb>
    <hr />
  </div>
</template>

<script>
import BIRDS from 'vanta/src/vanta.waves'
// Make sure window.THREE is defined, e.g. by including three.min.js in the document head using a <script> tag

export default {
 mounted() {
  this.vantaEffect = BIRDS({
   el: this.$refs.vantaRef
  })
 },
 beforeDestroy() {
  if (this.vantaEffect) {
   this.vantaEffect.destroy()
  }
 }
}
</script>
<style lang="less" scoped>
hr {
  margin-top: 20px;
  margin-bottom: 20px;
  border: 0;
  border-top: 1px solid rgba(55, 45, 45, 0.278);
}
</style>