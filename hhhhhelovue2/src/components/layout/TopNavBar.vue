<template>
  <div>
    <div class="main">
      <div class="menu-item">
        <router-link to="/"><i class="el-icon-s-home" />首页</router-link>
      </div>
      <div
        @click="dialogFormVisible = true"
        style="color: white; cursor: pointer"
        class="menu-item"
      >
        <i class="el-icon-search" />搜索
      </div>
      <div class="menu-item">
        <!--下拉菜单-->
        <el-dropdown @command="handleCommand">
          <div
            class="menu-item"
            style="color: white; font-size: 16px; margin: 5px"
          >
            <i class="el-icon-collection" />分类
          </div>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-s-order" command="a"
              >类别</el-dropdown-item
            >
            <el-dropdown-item icon="el-icon-s-management" command="b"
              >标签</el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown>
      </div>
      <div class="menu-item">
        <router-link to="/archive"
          ><i class="el-icon-s-finance" />归档</router-link
        >
      </div>
      <div class="menu-item">
        <router-link to="/linkview"
          ><i class="el-icon-connection" />友链</router-link
        >
      </div>
      <div class="menu-item">
        <router-link to="/photo"
          ><i class="el-icon-picture" />照片墙</router-link
        >
      </div>
      <div class="menu-item">
        <router-link to="/chat"
          ><i class="el-icon-chat-line-round" />留言板</router-link
        >
      </div>
      <div class="menu-item">
        <router-link to="/source"
          ><i class="el-icon-guide" />资源库</router-link
        >
      </div>
      <div class="menu-item">
        <router-link to="/backyard"
          ><i class="el-icon-cold-drink" />关于我</router-link
        >
      </div>
      <div class="menu-item">
        <router-link to="/login"
          ><i class="el-icon-s-custom" />后台</router-link
        >
      </div>
    </div>
    <el-dialog
      title="搜索"
      :visible.sync="dialogFormVisible"
      width="40%"
      center
    >
      <el-input
        placeholder="请输入文章标题"
        clearable
        :style="{ width: '550px' }"
        v-model="article.tittle"
      >
      </el-input>
      <div
        v-for="item in dataList"
        :key="item.id"
        style="margin: 10px auto; max-height: 200px; overflow-y: auto"
        v-loading="fullscreenLoading"

      >
        <div style="cursor: pointer" @click="searchTo(item.id)">
          <h1>
            {{ item.tittle }}
            <div style="float: right">
              <img
                :src="item.thumbnail"
                style="width: 100px; border-radius: 10px"
              />
            </div>
          </h1>
          <i class="el-icon-time" />
          {{ item.createdTime }}
          <el-divider> </el-divider>
        </div>
      </div>
      <div v-show="this.fullscreenLoading" v-loading="fullscreenLoading" style="text-align:center"><h1> 加载中。。。</h1></div>
        <div v-show="this.dataList.length">
        <!-- 分页 -->
        <el-pagination
        style="padding-top: 15px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="this.current"
        :page-sizes="[5, 10, 20, 30]"
        :page-size="this.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
        </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button  type="primary" @click="search" >确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import { findBlogList } from "@/api/blog/article";
export default {
  data() {
    return {
      drawer: false,
      fullscreenLoading:false,
      dialogFormVisible: false,
      article: {
        tittle: "",
      },
      current:1,
      size:5,
      total:0,
      dataList: [],
    };
  },

  components: {},

  computed: {},
 
  mounted() {},

  methods: {
    //当每页条数改变的时候
    handleSizeChange(val) {
      //将val赋值给size
      this.size = val;
      //重新去后台查询数据
      this.searchall2();
    },
    //当前页码改变的时候
    handleCurrentChange(val) {
      this.current = val;
      this.searchall2();
    },
    handleCommand(command) {
      if (command == "a") {
        this.$router.push("/tags");
      }
      if (command == "b") {
        this.$router.push("/catagories");
      }
    },
     search() {
      if (this.article.tittle.trim() === "") {
        this.$message.info("文章名不能为空");
        return false;
      } else {
        this.fullscreenLoading = true
        
        setTimeout(() => {
          this.searchall()
        }, 1000);
      }
    },
   async searchall(){
    this.fullscreenLoading = false
    const { data: res } = await  findBlogList(
      this.current,
      this.size,
          this.article
        );
        this.dataList = res.data.records;
        this.total = res.data.total
        if (!this.dataList.length) {
          this.$notify({
          title: '😲',
          message: '没找到',
          offset: 100
        });
        } 
   },
   async searchall2(){
    this.fullscreenLoading = false
    const { data: res } = await  findBlogList(
      this.current,
      this.size,
          this.article
        );
        this.dataList = res.data.records;
        this.total = res.data.total
   },
    searchTo(id) {
      this.dialogFormVisible = false;
      this.article = {};
      this.dataList = [];
      this.$router.push("/detail?id=" + id);
      console.log('ids==>'+id);
      // location.reload()
      this.$router.push({
        path: '/detail',
        query: {id:id}
      })
    },
  },
};
</script>
<style lang="less"  scoped>
.main {
  font-size: 16px;
  top: 0;
  float: right;
  position: fixed; /* 绝对定位，fixed是相对于浏览器窗口定位。 */
  /*position: relative;*/
  top: 15px; /* 距离窗口顶部距离 */
  right: 22px;
  height: 10px; /* 高度 */
  z-index: 9999; /* 层叠顺序，数值越大就越高。页面滚动的时候就不会被其他内容所遮挡。 */
}

.menu-item {
  margin: 12px;
  display: inline;
 
}

</style>