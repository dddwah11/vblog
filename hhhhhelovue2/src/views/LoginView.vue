<template>
  <!-- 表单 -->
  <div class="main">
  <div class="login_box">
    <span>登录</span>
    <el-form
      ref="userForm"
      :model="user"
      :rules="loginrules"
      label-width="0px"
      class="login_form"
    >
      <el-form-item prop="username">
        <el-input
          v-model="user.username"
          prefix-icon="el-icon-user-solid"
          placeholder="请输入账号"
          class="username"
        ></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          v-model="user.password"
          prefix-icon="el-icon-lock"
          placeholder="请输入密码"
          show-password
          class="password"
        ></el-input>
      </el-form-item>
      <div class="login_btn">
        <el-form-item>
          <Vcode :show="isShow" @success="loginEvent()" @close="close" />
          <el-button type="primary" @click="submit()">登录</el-button>
          <el-button @click="resetForm()">重置</el-button>
        </el-form-item>
      </div>
    </el-form>
  </div>
  </div>
</template>

<script>
import { login } from "@/api/user/user";
import Vcode from "vue-puzzle-vcode"
import { setRoutes } from '@/router';
export default {
  components:{
    Vcode
  },
  data() {
    return {
      isShow: false,
      user: {
        username: "",
        password: "",
      },
      loginrules: {
        username: [
          { required: true, message: "请输入账号", trigger: "blur" },
          {
            min: 1,
            max: 16,
            message: "长度在 3 到 16 个字符",
            trigger: "blur",
          },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          {
            min: 1,
            max: 100,
            message: "长度在 3 到 100  个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    submit(){
      this.$refs["userForm"].validate( (valid) => {
        if(valid){
          this.isShow=true
        }
      })
    },
    close(){
      this.isShow=false;
    },
    resetForm() {
      this.$refs["userForm"].resetFields();
    },
   async loginEvent() {
          this.isShow=false
          const { data } = await login(this.user);
          if (data.success === true) {
            // 登录成功
            sessionStorage.setItem("menus",JSON.stringify(data.data.menus))
            sessionStorage.setItem("user",JSON.stringify(data.data.user))
              setRoutes();
            this.$router.push("/contain");
            this.$notify({
          title: '操作',
          message: '登录成功',
          offset: 100
        });
          }
    },
  },
};
</script>
<style lang="less">
.login_box {
  text-align: center;
  position: absolute;
  top: 10%;
  left: 40%;
}

</style>