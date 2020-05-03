<template>
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选</span>
      </div>
      <el-form :inline="true" :model="listQuery" size="small" label-width="140px" style="margin-top:25px;">
        <el-form-item label="房屋列表：" placeholder="请选择房屋">
          <el-select
            v-model="listQuery.houseId"
            placeholder="请选择"
            clearable
            @change="selectHouse"
            @clear="clear"
          >
            <el-option disabled="disabled" class="li-house" :key="-1" :value="-1" style="width:300px;">
              <span>标题</span>
              <span>地址</span>
            </el-option>
            <el-option
              v-for="item in houses"
              :key="item.id"
              :label="item.title"
              :value="item.id"
              class="li-house"
            >
              <span>{{ item.title }}</span>
              <span>{{ item.address }}</span>
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
    </el-card>
    <div class="table-container">
      <el-table ref="productTable" :data="list" style="width: 100%" v-loading="listLoading" border>
        <!-- <el-table-column type="selection" width="30" align="center"></el-table-column> -->
        <el-table-column label="内容" align="left">
          <template slot-scope="scope">
            <a href="#">{{scope.row.content}}</a>
          </template>
        </el-table-column>
        <el-table-column label="用户" width="120" align="center">
          <template slot-scope="scope">{{scope.row.username}}</template>
        </el-table-column>
        <el-table-column label="发表时间" width="160" align="center">
          <template slot-scope="scope">
            <p>{{scope.row.createTime}}</p>
          </template>
        </el-table-column>
        <!-- <el-table-column label="房屋标题" width="150" align="center">
          <template slot-scope="scope">
            <p>{{scope.row.houseTitle}}</p>
          </template>
        </el-table-column>-->
        <el-table-column label="禁用状态" width="100" align="center">
          <template slot-scope="scope">
            <p>{{scope.row.disabled?'是':'否'}}</p>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="80" align="center">
          <template slot-scope="scope">
            <span>
              <el-button
                size="mini"
                @click="disableMsg(scope.row)"
              >{{scope.row.disabled? "启用" : "禁用"}}</el-button>
            </span>
          </template>
        </el-table-column>
      </el-table>
      <div class="pagination-container">
        <el-pagination
          background
          layout="total, sizes,prev, pager, next,jumper"
          :page-size="listQuery.pageSize"
          :page-sizes="[5,10,15]"
          :current-page.sync="listQuery.pageNum"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { formatDate } from "@/utils/date";

const defaltListQuery = {
  houseId: null,
  pageNum: 1,
  pageSize: 10
};

export default {
  data() {
    return {
      listLoading: false,
      houses: [],
      list: [],
      listQuery: defaltListQuery,
      // btnDisableCaption: "禁用",
      total: 0
    };
  },
  created() {
    this.queryHouses();
  },
  methods: {
    clear() {
      this.list = [];
      this.listQuery = Object.assign({}, defaltListQuery);
    },
    selectHouse() {
      this.loadList();
    },
    queryHouses() {
      let opts = {
        pageSize: 0,
        pageNum: 0
      };
      this.$store
        .dispatch("houseList", opts)
        .then(resp => {
          this.houses = resp.data.list;
          if (this.houses && this.houses.length > 0) {
            this.listQuery.houseId = this.houses[0].id;
          }
        })
        .catch(e => {});
    },
    loadList() {
      let opts = this.listQuery;

      if (!opts.houseId) return;
      this.listLoading = true;
      opts.ts = new Date().getTime();
      this.$store
        .dispatch("queryMessages", opts)
        .then(data => {
          data.list.forEach(m => {
            m.createTime = formatDate(
              new Date(m.createTime),
              "yyyy-MM-dd hh:mm:ss"
            );
            m.houseTitle = "";
          });
          this.list = data.list;
          this.total = data.total;
          this.listLoading = false;
        })
        .catch(() => {
          this.listLoading = false;
        });
    },
    disableMsg(rec) {
      rec.disabled = !rec.disabled;
      // this.btnDisableCaption = rec.disabled ? "启用" : "禁用";
      let opts = {
        id: rec.id,
        disabled: rec.disabled
      };
      this.$store.dispatch("disableMsg", opts).then(data => {
        this.list = data.list;
        this.total = resp.data.total;
        this.loading = false;
      });
    },
    handleSizeChange(val) {
      this.listQuery.pageNum = 1;
      this.listQuery.pageSize = val;
      this.loadList();
    },
    handleCurrentChange(val) {
      this.listQuery.pageNum = val;
      this.loadList();
    }
  }
};
</script>
<style>
.el-table td {
  padding: 0px;
}

.li-house {
  border-bottom: 1px solid #eee;
}

.li-house span {
  float: left;
  padding-left: 10px;
  width: 50%;
}
</style>