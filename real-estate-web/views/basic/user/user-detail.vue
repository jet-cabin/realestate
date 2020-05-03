<template>
  <div style="margin-top: 50px">
    <el-card class="form-container" shadow="never">
      <el-form
        :model="value"
        :rules="rules"
        ref="productInfoForm"
        label-width="120px"
        style="width: 600px"
        size="small"
      >
        <el-form-item label="用户名：" prop="name">
          <el-input v-model="value.username" readonly></el-input>
        </el-form-item>
        <el-form-item label="昵称：" prop>
          <el-input v-model="value.nickname" placeholder="请输入昵称"></el-input>
        </el-form-item>
        <el-form-item label="性别：" prop="genderr">
          <el-select v-model="value.gender" clearable>
            <el-option
              v-for="item in genderOpts"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="手机号：">
          <el-input v-model="value.phone" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="住址：">
          <el-input v-model="value.address" placeholder="请输入住址"></el-input>
        </el-form-item>
        <el-form-item label="备注：" style="height:250">
          <el-input v-model="value.note" style="height:200px" rows="10" type="textarea"></el-input>
        </el-form-item>
        <el-form-item style="text-align: center">
          <el-button type="primary" size="medium" @click="update()">保存</el-button>
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
        phone: [{ required: true, message: "请输入手机号", trigger: "blur" }]
      },
      value: {
        username: "",
        nickname: "",
        phone: "",
        note: "",
        gender: 1,
        address: null
      },
      genderOpts: [{ value: 1, label: "男" }, { value: 0, label: "女" }]
    };
  },
  created() {
    this.queryUser();
  },
  methods: {
    queryUser() {
      this.$store
        .dispatch("GetInfo")
        .then(data => {
          this.value = data.data;
        })
        .catch(() => {});
    },
    doReg() {
      let self = this;
      this.$store
        .dispatch("Register", this.value)
        .then(data => {
          this.loading = false;
          this.$message({
            type: "success",
            message: "保存成功",
            duration: 1500,
            onClose: me => {
              self.$router.back();
            }
          });
        })
        .catch(() => {
          this.loading = false;
        });
    },
    update() {
      // this.$store.dispatch("update", this.value).then(data => {
      this.$message({
        type: "success",
        message: "保存成功",
        duration: 1500
      }).catch(() => {});
      // });
    }
  }
};
</script>
