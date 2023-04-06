
<template>
  <div id="app"> 
    <TopNavBar v-show="$route.path==='/'|| $route.path==='/detail' || $route.path==='/catagories' || $route.path==='/tags'|| $route.path==='/archive'|| $route.path==='/chat'|| $route.path==='/linkview'|| $route.path==='/photo'|| $route.path==='/backyard'|| $route.path==='/login'|| $route.path==='/source'"></TopNavBar>
    <router-view/>
    <Music></Music>
  </div>
</template>

<script>
import TopNavBar from './components/layout/TopNavBar.vue';
import Music from './components/layout/Music.vue';
export default {
    name: "App",
    // eslint-disable-next-line no-dupe-keys
    components: { TopNavBar, Music }
}
</script>

<style>
.router-link-active {
    text-decoration: none;
    color: white;
  }
  a{
    text-decoration: none;
    color: white;
  }
/* 整个滚动条 */
::-webkit-scrollbar {
    width: 10px;
    height: 10px;
}

/* 滚动条上的滚动滑块 */
::-webkit-scrollbar-thumb {
    background-color: #44d7b6;
    /* 关键代码 */
    background-image: -webkit-linear-gradient(45deg,
            rgba(255, 255, 255, 0.4) 25%,
            transparent 25%,
            transparent 50%,
            rgba(255, 255, 255, 0.4) 50%,
            rgba(255, 255, 255, 0.4) 75%,
            transparent 75%,
            transparent);
    border-radius: 32px;
}

/* 滚动条轨道 */
::-webkit-scrollbar-track {
    background-color: #f4f4f4;
    border-radius: 32px;
}


</style>
