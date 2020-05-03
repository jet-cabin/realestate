<template>
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button style="float: right" @click="queryList()" type="primary" size="small">查询</el-button>
        <!-- <el-button
          style="float: right;margin-right: 15px"
          @click="handleResetSearch()"
          size="small"
        >重置</el-button>-->
      </div>

      <el-form
        :inline="true"
        :model="listQuery"
        size="small"
        label-width="140px"
        style="margin-top: 20px;"
      >
        <!-- <el-form-item label="过滤：">
          <el-select v-model="listQuery.my" placeholder="与我相关" clearable>
            <el-option
              v-for="item in myOpts"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>-->
        <el-form-item label="起始日期">
          <el-date-picker v-model="listQuery.beginTime" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
        <el-form-item label="结束日期">
          <el-date-picker v-model="listQuery.endTime" type="date" placeholder="选择日期"></el-date-picker>
        </el-form-item>
      </el-form>
    </el-card>

    <el-table
      :data="tableData"
      border
      show-summary
      :summary-method="getSummaries"
      style="width: 100%;margin-top:15px;"
    >
      <el-table-column prop="house.title" label="房屋"></el-table-column>
      <el-table-column prop="vendee.username" label="承租人"></el-table-column>
      <el-table-column prop="price" label="价格"></el-table-column>
      <el-table-column prop="createTime" label="日期"></el-table-column>
      <el-table-column label="操作" :width="100" align="center">
        <template slot-scope="scope">
          <span>
            <!-- <el-button size="mini" @click="handleShowLog(scope.$index, scope.row)">禁用</el-button> -->
            <el-button size="mini" @click="showDetails( scope.row)">修改</el-button>
          </span>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="修改租金" :visible.sync="showDlg" width="500px">
      <el-form
        ref="houseInfoForm"
        label-width="120px"
        style="width: 400px"
        size="small"
      >
        <el-form-item label="房屋:" prop="tilte">
          {{curTitle}}
        </el-form-item>
        <el-form-item label="租金:" prop="price">
          <el-input v-model="curPrice" type="number"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer" style="margin-right: 60px;">
        <el-button>保存</el-button>
        <el-button>取消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
const defaultQueryParams = {
  beginTime: new Date(Date.now() - 3600 * 24 * 1000),
  endTime: new Date(),
  pageNum: 1,
  pageSize: 0
};
export default {
  data() {
    return {
      curTitle: "",
      curPrice: 0,
      showDlg: false,
      listQuery: Object.assign({}, defaultQueryParams),
      tableData: [
        {
          house: { title: "aaaaa" },
          vendee: { username: "jet" },
          price: 333,
          createTime: "2019-10-26"
        },
        {
          house: { title: "bbbbb" },
          vendee: { username: "jet" },
          price: 123,
          createTime: "2019-10-26"
        }
      ]
    };
  },
  created() {
    this.queryList();
  },
  methods: {
    showDetails(rec) {
      this.curPrice = rec.price;
      this.curTitle = rec.house.title;
      this.showDlg = true;
    },
    queryList() {
      this.listQuery.beginTime.setHours(23);
      this.listQuery.beginTime.setMinutes(59);
      this.listQuery.beginTime.setSeconds(59);

      this.listQuery.endTime.setHours(23);
      this.listQuery.endTime.setMinutes(59);
      this.listQuery.endTime.setSeconds(59);
      this.$store
        .dispatch("queryRentList", this.listQuery)
        .then(data => {
          this.tableData = data.list;
        })
        .catch(e => {});
    },
    getSummaries(param) {
      const { columns, data } = param;
      const sums = [];
      columns.forEach((column, index) => {
        if (index === 0) {
          sums[index] = "总价";
          return;
        } else if (index == 1 || index == 3) {
          return;
        }
        const values = data.map(item => Number(item[column.property]));
        if (!values.every(value => isNaN(value))) {
          sums[index] = values.reduce((prev, curr) => {
            const value = Number(curr);
            if (!isNaN(value)) {
              return prev + curr;
            } else {
              return prev;
            }
          }, 0);
          sums[index] += " 元";
        } else {
          sums[index] = "N/A";
        }
      });

      return sums;
    }
  }
};
</script>
<style>
</style>