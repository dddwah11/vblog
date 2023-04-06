<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <div>
    <div class="home">
      <!-- banner -->
      <div class="banner">
        <div class="main animated zoomIn">文章详情</div>
      </div>
      <!-- content -->
      <div class="m-home animated fadeInLeft">
        <el-card class="viewcard">
          <i class="el-icon-time" />
          {{ detailList.createdTime }}
          &nbsp; &nbsp; 标签：
          <el-tag type="success" v-if="detailList.categoryName">
            {{ detailList.categoryName }}
          </el-tag>
          &nbsp; &nbsp;
          <i class="el-icon-view" />
          {{ detailList.viewCounts==null?'0':detailList.viewCounts }}
        </el-card>
        <!-- 博客详情 -->
        <div>
          <el-image
            style="
              width: 60%;
              height: 500px;
              margin: 10px auto;
              border-radius: 20px;
            "
            :src="detailList.thumbnail"
            fit="fill"
          ></el-image>
          <div class="content">
            <el-card >
              <h1>{{ detailList.tittle }}</h1>
              <mavon-editor 
                :value="detailList.content"
                :subfield="false"
                :defaultOpen="'preview'"
                :toolbarsFlag="false"
                :editable="false"
                :scrollStyle="true"
                :ishljs="true"
                :navigation= "true"
                @navigationToggle	="onAddUrl"
                previewBackground="#fffcfcd8"
                style="max-height:10000px;"
              />
            </el-card>
          </div>
        </div>
        <div
          style="
            border-radius: 10px;
            color: #2c662d;
            background: #a3c293;
            padding: 10px 10px;
            margin: 0 auto;
            width: 900px;
            height: 280px;
          "
        >
          <li>作者：小刘</li>
          <li>qq：1937589397</li>
          <li>创建时间：{{ detailList.createdTime }}</li>
          <li>
            版权声明：自由转载-非商用-非衍生-保持署名（创意共享3.0许可证）
          </li>
          <li>公众号转载：请注明微信二维码</li>
          <!-- <img src="https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/03/98dedb437aee4fde886be63b9ac0506f.png" style="width:150px;height:160px;margin:10px;border-radius: 20px;" /> -->
          <img src="@/assets/images/wx.png" style="width:150px;height:160px;margin:10px;border-radius: 20px;" />
        </div>
        <!-- /评论 -->
        <div
          style="
            border-bottom: 1px solid orangered;
            padding: 10px 0;
            font-size: 30px;
          "
        >
          评论
        </div>

        <el-form :model="comment" style="color:red" :rules="commentRules" ref="commentForm">
          <!-- 姓名 -->
          <el-form-item
            prop="createBy"
            style="padding: 10px 10px; width: 130px; display: inline-block"
          >
            <el-input
              prefix-icon="el-icon-user"
              v-model="comment.createBy"
              placeholder="请输入昵称"
              class="users"
            ></el-input>
          </el-form-item>
          <!-- 邮箱 -->
          <el-form-item
            style="padding: 10px 20px; width: 180px; display: inline-block"
            prop="email"
          >
            <el-input
              prefix-icon="el-icon-message"
              v-model="comment.email"
              placeholder="有效的邮箱"
              class="email"
            ></el-input>
          </el-form-item>
          <!-- 加入了邮箱回复功能，请填入有效的邮箱📪 -->
          <!-- 评论按钮 -->
          <el-form-item
            style="padding: 10px 20px; width: 130px; display: inline-block"
          >
            <el-button type="primary" @click="handComment">评论</el-button>
          </el-form-item>
          <!-- 评论内容 -->
          <el-form-item style="padding: 0px 10px; width: 50%" prop="content">
            <el-input
              style="left: 49%"
              placeholder="一起来聊天吧(友善留言哦~)👾👽..."
              v-model="comment.content"
              type="textarea"
            ></el-input>
          </el-form-item>
        </el-form>

        <div v-for="item in comments" :key="item.id">
          <!-- 评论列表 -->
          <div style="width: 50%; height: 100%; margin: 10px auto">
            <div class="orgin">
              <div class="item">
                <div class="photo">
                  <!-- 头像 -->
                  <svg aria-hidden="true">
                    <use xlink:href="#icon-xuegao"></use>
                  </svg>
                </div>
                <div class="text">
                  <div class="title">
                    <i class="el-icon-user" />
                    <div style="color: black; font-size: 14px">
                      {{ item.createBy }}
                    </div>
                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                    &nbsp; &nbsp;
                    <i class="el-icon-time"></i>
                    <div style="color: bisque">{{ item.createdTime }}</div>
                  </div>

                  <div class="content">
                    {{ item.content }}
                    <div style="float: right">
                      <el-button type="text" style="color:azure" @click="replyComment(item.id)"
                        >回复</el-button
                      >
                      <el-button
                        type="text"
                        style="color: red"
                        @click="del(item.id)"
                        v-if="user.role === 'admin'"
                        >删除</el-button
                      >
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- 二级评论 -->
          <div
            v-if="item.children.length"
            style="width: 50%; height: 100%; margin: 0px auto"
          >
            <div
              class="orgin-2"
              v-for="subItem in item.children"
              :key="subItem.id"
            >
              <div class="item">
                <div class="photo">
                  <!-- 头像 -->
                  <svg aria-hidden="true">
                    <use xlink:href="#icon-xuegao"></use>
                  </svg>
                </div>
                <div class="text">
                  <div class="title">
                    回复
                    <div style="color:blue">{{ subItem.puser }}</div>
                    ：
                    <i class="el-icon-user" />
                    <div style="color: black; font-size: 14px">
                      {{ subItem.createBy }}
                    </div>
                    &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <i class="el-icon-time" ></i>
                    <div style="color:bisque">{{ subItem.createdTime }}</div>
                  </div>

                  <div class="content">
                    {{ subItem.content }}
                    <div style="float: right">
                      <el-button
                        type="text"
                        style="color:azure"
                        @click="reply2Comment(item.id, subItem.id)"
                        >回复</el-button
                      >
                      <el-button
                        type="text"
                        style="color: red"
                        @click="del(subItem.id)"
                        v-if="user.role === 'admin'"
                        >删除</el-button
                      >
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 回复弹窗 -->
    <el-dialog
      style="width: 80%; margin: 0 auto"
      title="回复"
      :visible.sync="dialogFormVisible"
    >
      <el-form :model="commentTemp" ref="replytForm" :rules="replyRules">
        <el-form-item prop="createByReply" label="昵称">
          <el-input
            v-model="commentTemp.createByReply"
            placeholder="请输入昵称"
          ></el-input>
        </el-form-item>
        <el-form-item prop="emailReply" label="邮箱">
          <el-input
            v-model="commentTemp.emailReply"
            placeholder="请输入有效的邮箱"
            class="email"
          ></el-input>
        </el-form-item>
        <el-form-item prop="contentReply" label="回复内容">
          <el-input
            type="textarea"
            v-model="commentTemp.contentReply"
            placeholder="请输入..."
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handelConfirm">确 定</el-button>
      </div>
    </el-dialog>

    <Footer />
        <!-- 上滑工具 -->
    <div class="tooBar">
      <div style="margin-right:60px">
        <el-button type="primary" @click="scrollUp"><i class="el-icon-arrow-up"></i></el-button>
      </div>
    </div>


  </div>
</template>

<script>
import Footer from "@/components/layout/Footer.vue";
import { searchId } from "@/api/blog/article";
import { saveComment, findComment, delComment } from "@/api/comment/comment";
export default {
  data() {
    return {
      url: "",
      dialogFormVisible: false,
      user: sessionStorage.getItem("user")
        ? JSON.parse(sessionStorage.getItem("user"))
        : {},
      detailList: [],
      comments: [],
      commentTemp: {},
      comment: {
        createBy: undefined,
        email: undefined,
        blogId: undefined,
        content: undefined,
      },
      id: this.$route.query.id,
      replyRules: {
        createByReply: [
          { required: true, message: "请输入昵称", trigger: "blur" },
          {
            min: 1,
            max: 16,
            message: "长度在 3 到 16 个字符",
            trigger: "blur",
          },
        ],
        emailReply: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            min: 4,
            max: 30,
            message: "长度在 4 到 30 个字符",
            trigger: "blur",
          },
        ],
        contentReply: [
          {
            required: true,
            message: "请输入评论（长度在 1 到 20 个字符）",
            trigger: "blur",
          },
          {
            min: 1,
            max: 20,
            message: "长度在 1 到 20 个字符",
            trigger: "blur",
          },
        ],
      },
      commentRules: {
        createBy: [
          { required: true, message: "请输入昵称", trigger: "blur" },
          {
            min: 1,
            max: 16,
            message: "长度在 3 到 16 个字符",
            trigger: "blur",
          },
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: "blur" },
          {
            min: 6,
            max: 30,
            message: "长度在 6 到 30 个字符",
            trigger: "blur",
          },
        ],
        content: [
          {
            required: true,
            message: "请输入评论（长度在 1 到 20 个字符）",
            trigger: "blur",
          },
          {
            min: 1,
            max: 20,
            message: "长度在 1 到 20 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  components: {
    Footer,
  },
  computed: {},
  watch:{
    $route(){
    if(this.$router.history.current.name=='Detail'){
      this.$router.go()
    }
  },
  },
  mounted() {
    this.selectByPage();
  },

  methods: {
    async selectByPage() {
      // 博客详情
      const { data } = await searchId(this.id);
      this.detailList = data.data.records;
      //   评论
      const { data: res } = await findComment(this.id);
      this.comments = res.data.records;
    },
    handComment() {
      this.$refs["commentForm"].validate((valid) => {
        if (valid) {
          this.load();
        } else return false;
      });
    },
    async load() {
      this.comment.blogId = this.id;
      await saveComment(this.comment);
      this.comment = {};
      await findComment(this.id);
      this.selectByPage();
      this.$message.success("成功");
    },
    async del(commentId) {
      const { data } = await delComment(commentId);
      this.$message.success(data.message);
      this.selectByPage();
    },
    replyComment(pid) {
      (this.dialogFormVisible = true),
        (this.comment = { pid: pid, originId: pid });
    },
    reply2Comment(oId, pid) {
      (this.dialogFormVisible = true),
        (this.comment = { pid: pid, originId: oId });
    },
    handelConfirm() {
      this.$refs["replytForm"].validate((valid) => {
        if (valid) {
          if (this.commentTemp.emailReply) {
            this.comment.email = this.commentTemp.emailReply;
          }
          if (this.commentTemp.contentReply) {
            this.comment.content = this.commentTemp.contentReply;
          }
          if (this.commentTemp.createByReply) {
            this.comment.createBy = this.commentTemp.createByReply;
          }
          this.load();
          this.comment = {};
          this.selectByPage;
          this.dialogFormVisible = false;
        } else return false;
      });
    },
     scrollUp() {
      window.scrollTo({
        behavior: "smooth",
        top: (0,0),
      });
    },
    onAddUrl(){
      this.$nextTick(function () {
        let _aList = document.querySelectorAll(".v-note-navigation-content a");
        for (let i = 0; i < _aList.length; i++) {
          let _aParent = _aList[i].parentNode;
          let _a = _aParent.firstChild;
          if (!_a.id) continue; // 把不属于导航中的a标签去掉，否则会报错
          let _text = _aParent.lastChild;
          let text = _text.textContent;
          _a.href = "#" + _a.id;
          _a.innerText = text;
          _aParent.removeChild(_text);
          // _a.style.color = "red";
        }
      });
    },  

    
  },
};
</script>
<style lang="less"  scoped>
.orgin-2 {
  margin-left: 40px;
}
.banner {
  height: 50vh;
  font-size: 2.5em;
  background-color: #49b1f5;
  // background: url("https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/03/653c01916a9341d19943de27fc4b5587.jpg");
  background: url("@/assets/images/detail.jpg");
  background-size: cover;
  background-attachment: fixed;
  text-align: center;
}

.main {
  position: absolute;
  top: 10%;
  left: 45%;
  font-size: 2.5rem;
  font-family: Teko, sans-serif;
  text-shadow: 0 1px 2px rgb(0 0 0 / 10%);
  text-transform: uppercase;
  color: rgb(223, 218, 218);
  z-index: 999;
}
.m-home {
  margin: 0 auto;
  text-align: center;
}
.content {
  margin: 0 auto;
  width: 80%;
  height: 100%;
}
.viewcard {
  margin: 10px auto;
  width: 60%;
  border-radius: 30px;
  color: grey;
}

.item {
  width: 600px;
  display: flex;
  margin-bottom: 20px;
}
.photo > svg {
  height: 60px;
  width: 60px;
  background-color: rgb(96, 126, 199);
  border-radius: 50%;
  margin-right: 20px;
}
.text {
  flex: 1;
  padding: 20px;
  background-color: #8c8c8c;
  
  border-radius: 15px;
}
.title {
  display: flex;
  justify-content: space between;
  margin-bottom: 10px;
}
.content {
  text-indent: 2em;
}
.tooBar{
    bottom: 20px;
    right: 0;
  position: fixed;
}

</style>