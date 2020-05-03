<template>
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button style="float: right" @click="handleSearchList()" type="primary" size="small">查询结果</el-button>
        <el-button
          style="float: right;margin-right: 15px"
          @click="handleResetSearch()"
          size="small"
        >重置</el-button>
      </div>
      <div style="margin-top: 15px">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
          <el-form-item label="用户名：">
            <el-input style="width: 203px" v-model="listQuery.username" placeholder="用户名"></el-input>
          </el-form-item>
          <el-form-item label="手机号：">
            <el-input style="width: 203px" v-model="listQuery.phone" placeholder="手机号"></el-input>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
      <el-button class="btn-add" @click="handleAddProduct()" size="mini">添加</el-button>
    </el-card>
    <div class="table-container">
      <el-table
        ref="productTable"
        :data="list"
        style="width: 100%"
        v-loading="listLoading"
        @row-dblclick="showDetail"
        border
      >
        <!-- <el-table-column type="selection" width="30" align="center"></el-table-column> -->
        <el-table-column label="用户名" width="120" align="center">
          <template slot-scope="scope">
            <a href="#" @click="showDetail(scope.row)">{{scope.row.username}}</a>
          </template>
        </el-table-column>
        <el-table-column label="昵称" width="120" align="center">
          <template slot-scope="scope">{{scope.row.nickname}}</template>
        </el-table-column>
        <el-table-column label="手机号" width="120" align="center">
          <template slot-scope="scope">
            <p>{{scope.row.phone}}</p>
          </template>
        </el-table-column>
        <el-table-column label="邮箱" width="120" align="center">
          <template slot-scope="scope">
            <p>{{scope.row.email}}</p>
          </template>
        </el-table-column>

        <el-table-column label="备注" align="center">
          <template slot-scope="scope">{{scope.row.note}}</template>
        </el-table-column>

        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <span>
              <el-button size="mini" @click="handleShowLog(scope.$index, scope.row)">禁用</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </span>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="pagination-container">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        layout="total, sizes,prev, pager, next,jumper"
        :page-size="listQuery.pageSize"
        :page-sizes="[5,10,15]"
        :current-page.sync="listQuery.pageNum"
        :total="total"
      ></el-pagination>
    </div>

    <el-dialog title="用户信息" :visible.sync="dialogVisible" width="700px">
      <el-form
        :model="curRecord"
        ref="productInfoForm"
        label-width="120px"
        style="width: 600px"
        size="small"
      >
        <el-form-item label="用户名：" prop="username">
          <el-input v-model="curRecord.username" readonly></el-input>
        </el-form-item>
        <el-form-item label="昵称：" prop="nickname">
          <el-input v-model="curRecord.nickname" readonly></el-input>
        </el-form-item>
        <!-- <el-form-item label="商品品牌：" prop="brandId">
        <el-select
          v-model="curRecord.brandId"
          @change="handleBrandChange"
          placeholder="请选择品牌">
          <el-option
            v-for="item in brandOptions"
            :key="item.curRecord"
            :label="item.label"
            :curRecord="item.curRecord">
          </el-option>
        </el-select>
        </el-form-item>-->
        <!-- <el-form-item label="商品介绍：">
        <el-input
          :autoSize="true"
          v-model="curRecord.description"
          type="textarea"
          placeholder="请输入内容"></el-input>
        </el-form-item>-->
        <!-- <el-form-item label="密码：">
        <el-input v-model="curRecord.password" readonly :type="pwdType" placeholder="请输入密码"></el-input>
      </el-form-item>
        -->
        <el-form-item label="手机号：">
          <el-input v-model="curRecord.phone" readonly></el-input>
        </el-form-item>
        <el-form-item label="备注：">
          <el-input v-model="curRecord.note" type="textarea" readonly></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">关闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
const defaultListQuery = {
  username: null,
  phone: null,
  roleId: 3,
  pageNum: 1,
  pageSize: 5
};
export default {
  data() {
    return {
      list: [],
      listQuery: Object.assign({}, defaultListQuery),
      dialogVisible: false,
      curRecord: {},
      listLoading: false,
      total: 0
      //           userInfo: {
      //     username: "",
      //     nickname: "",
      //     password: "",
      //     phone: "",
      //     ackpwd: ""
      //   }
    };
  },
  created() {
    this.queryUsers();
  },
  methods: {
    queryUsers() {
      let role = this.$store.getters.roles[0];

      if (role.name == "admin") {
        this.listQuery.roleId = 2;
      } else if (role.name == "staff") {
        this.listQuery.roleId = 3;
      } else {
        this.$router.push({ path: "/404" });
      }

      this.$store
        .dispatch("List", this.listQuery)
        .then(data => {
          this.list = data;
          this.loading = false;
        })
        .catch(e => {});
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleSizeChange(val) {
      this.listQuery.pageNum = 1;
      this.listQuery.pageSize = val;
      this.queryUsers();
    },
    handleCurrentChange(val) {
      this.listQuery.pageNum = val;
      this.queryUsers();
    },
    handleResetSearch() {
      this.listQuery = Object.assign({}, defaultListQuery);
    },
    showDetail(rec) {
      this.curRecord = rec || {};
      this.dialogVisible = true;
    }
  }
};
</script>

<style>
.el-table__row:hover {
  cursor: pointer;
}
</style>
