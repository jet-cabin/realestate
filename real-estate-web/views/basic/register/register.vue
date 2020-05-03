<template>
  <div class="app-container" style="margin-top: 50px">
    <el-card shadow="never" style="
    width: 800px;
    margin: 0 auto;
">
      <el-form :model="value" :rules="rules"  ref="productInfoForm" label-width="120px" style="width: 600px" size="small">
        <el-form-item label="用户名：" prop="name">
          <el-input v-model="value.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="昵称：" prop="subTitle">
          <el-input v-model="value.nickname" placeholder="请输入昵称"></el-input>
        </el-form-item>
        <el-form-item label="密码：">
          <el-input v-model="value.password" :type="pwdType" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码：">
          <el-input v-model="value.ackpwd" :type="pwdType" placeholder="请再次输入密码"></el-input>
        </el-form-item>
        <el-form-item label="手机号：">
          <el-input v-model="value.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item style="text-align: center">
          <el-button type="primary" size="medium" @click="doReg()">注册</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script>
export default {
  props: {
    // value: Object
  },
  data() {
    return {
      pwdType: "password",
      rules: {
        username: [
          { required: true, message: "请输入用户名", trigger: "blur" }
        ],
        nickname: [{ required: true, message: "请输入昵称", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        password: [
          { required: true, message: "请再次输入密码", trigger: "blur" }
        ],
        phone: [{ required: true, message: "请输入手机号", trigger: "blur" }]
      },
      value: {
        username: "",
        nickname: "",
        password: "",
        phone: "",
        ackpwd: ""
      }
    };
  },
  methods: {
    doReg() {
      let self = this;
      this.$store
        .dispatch("Register", this.value)
        .then(data => {
          this.loading = false;
          this.$message({
            type: "success",
            message: "注册成功",
            duration: 1500,
            onClose: me => {
              self.$router.back();
            }
          });
        })
        .catch(() => {
          this.loading = false;
        });
    }
  }
};
</script>
