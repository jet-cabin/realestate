<template>
  <div class="app-container">
    <el-card class="filter-container" shadow="never">
      <div>
        <i class="el-icon-search"></i>
        <span>筛选搜索</span>
        <el-button style="float: right" @click="handleSearchList()" type="primary" size="small">查询</el-button>
        <el-button
          style="float: right;margin-right: 15px"
          @click="handleResetSearch()"
          size="small"
        >重置</el-button>
      </div>
      <div class="ct-q-form" :style="{'height':(complexSearch==false ? '35px' : '200px')}">
        <el-form :inline="true" :model="listQuery" size="small" label-width="140px">
          <el-form-item label="过滤：">
            <el-select v-model="listQuery.my" placeholder="与我相关" clearable>
              <el-option
                v-for="item in myOpts"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="是否新房：">
            <el-checkbox v-model="listQuery.newHouse" type="checkbox" class="cb"></el-checkbox>
          </el-form-item>
          <el-form-item label="审核状态：" :style="{'visibility':(complexSearch?'visible':'hidden')}">
            <el-select style="width: 203px" v-model="listQuery.audit">
              <el-option
                v-for="item in auditOpts"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="户型：" :style="{'visibility':(complexSearch?'visible':'hidden')}">
            <el-select v-model="listQuery.layout" placeholder="户型" clearable>
              <el-option
                v-for="item in layoutOpts"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="标题：" :style="{'visibility':(complexSearch?'visible':'hidden')}">
            <el-input style="width: 203px" v-model="listQuery.username" placeholder="输入标题关键字"></el-input>
          </el-form-item>
          <el-form-item label="价格区间：" :style="{'visibility':(complexSearch?'visible':'hidden')}">
            <el-input
              style="width: 85px"
              min="0"
              type="number"
              v-model="listQuery.priceMin"
              placeholder
            ></el-input>&nbsp;至
            <el-input
              style="width: 85px"
              min="0"
              type="number"
              v-model="listQuery.priceMax"
              placeholder
            ></el-input>
          </el-form-item>
          <el-form-item label="面积区间：" :style="{'visibility':(complexSearch?'visible':'hidden')}">
            <el-input
              style="width: 85px"
              min="0"
              type="number"
              v-model="listQuery.areaMin"
              placeholder
            ></el-input>&nbsp;至
            <el-input
              style="width: 95px"
              min="0"
              type="number"
              v-model="listQuery.areaMax"
              placeholder
            ></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="act-toggle" @click="toggleSearch()">{{toggleCaption}}</div>
    </el-card>
    <el-card class="operate-container" shadow="never">
      <i class="el-icon-tickets"></i>
      <span>数据列表</span>
      <el-button class="btn-add" @click="handleAddHouse()" size="mini">马上发布</el-button>
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
        <el-table-column label="标题" width="250" align="left">
          <template slot-scope="scope">
            <a href="#" @click="showDetail(scope.row)">{{scope.row.title}}</a>
          </template>
        </el-table-column>
        <el-table-column label="户型" width="180" align="center">
          <template slot-scope="scope">{{layoutOpts[scope.row.layout].label}}</template>
        </el-table-column>
        <el-table-column label="位置" align="center">
          <template slot-scope="scope">
            <p>{{scope.row.address}}</p>
          </template>
        </el-table-column>
        <el-table-column label="价格" width="120" align="center">
          <template slot-scope="scope">
            <p>{{scope.row.price}}</p>
          </template>
        </el-table-column>
        <el-table-column label="发布时间" width="160" align="center">
          <template slot-scope="scope">
            <p>{{scope.row.publishTime}}</p>
          </template>
        </el-table-column>
        <el-table-column label="是否新房" width="120" align="center">
          <template slot-scope="scope">
            <p>{{scope.row.newHouse?'是':'否'}}</p>
          </template>
        </el-table-column>
        <el-table-column
          :style="{'display':(ad?'':'none')}"
          label="审核状态"
          width="120"
          align="center"
        >
          <template slot-scope="scope">
            <p>{{scope.row.audit?'已审':'未审'}}</p>
          </template>
        </el-table-column>
        <el-table-column label="已发布" width="120" align="center">
          <template slot-scope="scope">
            <p>{{scope.row.publish?'是':'否'}}</p>
          </template>
        </el-table-column>

        <el-table-column label="操作" :width="ww" align="center">
          <template slot-scope="scope">
            <span>
              <!-- <el-button size="mini" @click="handleShowLog(scope.$index, scope.row)">禁用</el-button> -->
              <el-button size="mini" @click="showDetails( scope.row.id)">查看</el-button>
              <el-button
                size="mini"
                :style="{'display':(ad && scope.row.audit==false?'':'none')}"
                @click="audit(scope.row)"
              >审核</el-button>

              <el-button
                size="mini"
                :style="{'display':(scope.row.vendorId==user.id && scope.row.publish==false ?'':'none')}"
                @click="publish(scope.row)"
              >发布</el-button>
              <el-button
                :style="{'display':(scope.row.vendorId==user.id && scope.row.status==0 ?'':'none')}"
                size="mini"
                type="danger"
                @click="remove( scope.row.id)"
              >删除</el-button>
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

    <el-dialog :title="title" :visible.sync="dialogVisible" width="700px">
      <el-form
        :model="house"
        ref="houseInfoForm"
        label-width="120px"
        style="width: 600px"
        size="small"
      >
        <el-form-item label="标题：" prop="username">
          <el-input v-model="house.title"></el-input>
        </el-form-item>
        <el-form-item label="位置：">
          <el-input v-model="house.address"></el-input>
        </el-form-item>
        <el-form-item label="是否新房：">
          <el-checkbox v-model="house.newHouse" class="cb" type="checkbox"></el-checkbox>
        </el-form-item>
        <el-form-item label="户型：" prop="nickname">
          <el-select v-model="house.layout" placeholder="户型" clearable>
            <el-option
              v-for="item in layoutOpts"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="面积：">
          <el-input v-model="house.area" type="number" min="0"></el-input>
        </el-form-item>
        <el-form-item label="价格：">
          <el-input v-model="house.price" type="number" min="0"></el-input>
        </el-form-item>
        <el-form-item label="联系电话：">
          <el-input v-model="house.contactPhone"></el-input>
        </el-form-item>
        <el-form-item label="备注：">
          <el-input v-model="house.note" type="textarea"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer" style="margin-right: 60px;">
        <el-button @click="addHouse()">保存</el-button>
        <el-button @click="publishHouse()">保存并发布</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { formatDate } from "@/utils/date";
const defaultListQuery = {
  my: null,
  layout: null,
  title: null,
  priceMin: null,
  priceMax: null,
  areaMin: null,
  areaMax: null,
  audit: null,
  newHouse: null,
  pageNum: 1,
  pageSize: 5,
  includeUnAudit: false
};

const houseModel = {
  title: "",
  layout: 1,
  price: "",
  area: "",
  address: "",
  note: "",
  publish: 0,
  status: 0,
  vendorId: 4,
  audit: 1,
  newHouse: null,
  publishTime: null,
  contactPhone: "15801689640"
};
export default {
  data() {
    return {
      toggleCaption: "高级搜索",
      complexSearch: false,
      house: Object.assign({}, houseModel),
      title: "",
      list: [],
      listQuery: Object.assign({}, defaultListQuery),
      dialogVisible: false,
      curRecord: {},
      listLoading: false,
      total: 0,
      user: null,
      layoutOpts: [
        { value: 0, label: "一居室" },
        { value: 1, label: "两居室" },
        { value: 2, label: "三居室" },
        { value: 3, label: "四居室" },
        { value: 4, label: "复式" },
        { value: 5, label: "别墅" }
      ],
      myOpts: [
        { value: 1, label: "我发布的" },
        { value: 2, label: "我承租的" }
      ],
      auditOpts: [{ value: 1, label: "未审核" }, { value: 2, label: "已审核" }],
      pp: false,
      aa: false
      // layoutMap: {
      //   ONE: 0,
      //   TWO: 1,
      //   THREE: 2,
      //   FOUR: 3,
      //   DUPLEX: 4,
      //   VILLA: 5
      // }
      //           userInfo: {
      //     username: "",
      //     nickname: "",
      //     password: "",
      //     phone: "",
      //     ackpwd: ""
      //   }
    };
  },
  computed: {
    // showPublish() {
    //   return this.pp && this.vendorId == this.user.id;
    // },
    ad() {
      return this.role.name == "staff" && this.aa;
    },
    ww() {
      return this.pp ? "250" : "150";
    }
  },
  created() {
    this.user = this.$store.getters.curUser;
    this.role = this.$store.getters.roles[0];
    this.queryhouses();
  },
  methods: {
    toggleSearch() {
      this.complexSearch = !this.complexSearch;
      this.toggleCaption = !this.complexSearch ? "高级搜索" : "收起";
    },
    queryhouses() {
      if (this.role.name == "staff") {
        this.listQuery.includeUnAudit = true;
      }
      this.$store
        .dispatch("houseList", this.listQuery)
        .then(resp => {
          this.list = resp.data.list;
          // this.list[0].publish=false;
          let me = this;
          this.list.forEach(h => {
            if (h.publish == false) {
              me.pp = true;
              // return;
            }
            if (h.audit == false) {
              me.aa = true;
            }
          });
          this.total = resp.data.total;
          this.loading = false;
        })
        .catch(e => {});
    },
    handleSearchList() {
      let userId = this.user.id;
      if (this.listQuery.my == 1) {
        this.listQuery.vendorId = userId;
        this.listQuery.vendeeId =null
      } else if (this.listQuery.my == 2) {
        this.listQuery.vendeeId = userId;
         this.listQuery.vendorId = null
      }
      this.queryhouses();
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleSizeChange(val) {
      this.listQuery.pageNum = 1;
      this.listQuery.pageSize = val;
      this.queryhouses();
    },
    handleCurrentChange(val) {
      this.listQuery.pageNum = val;
      this.queryhouses();
    },
    handleResetSearch() {
      this.listQuery = Object.assign({}, defaultListQuery);
      this.complexSearch = false;
    },
    handleAddHouse() {
      this.dialogVisible = true;
      this.title = "新建房屋信息";
    },
    publishHouse() {
      this.house.publish = true;
      this.house.publishTime = this.getDate();
      this.addHouse();
    },
    getDate() {
      return formatDate(new Date(), "yyyy-MM-dd hh:mm:ss");
    },
    publish(rec) {
      this.$store
        .dispatch("publish", rec)
        .then(() => {
          rec.publish = true;
          rec.publishTime = this.getDate();
        })
        .catch(() => {});
    },
    addHouse() {
      this.$store
        .dispatch("create", this.house)
        .then(() => {
          this.$message({
            type: "success",
            message: "保存成功",
            duration: 1500
          });
          this.queryhouses();
          this.dialogVisible = false;
          this.house = houseModel;
        })
        .catch(() => {});
    },
    showDetail(rec) {
      this.curRecord = rec || {};
      this.dialogVisible = true;
    },
    remove(id) {
      let me = this;
      this.$confirm("确定要结束吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$store
            .dispatch("remove", id)
            .then(data => {
              me.queryhouses();
            })
            .catch(() => {});
        })
        .catch(() => {});
    },
    audit(rec) {
      rec.contactPhone = this.user.phone;
      this.$store
        .dispatch("audit", rec)
        .then(() => {
          rec.audit = true;
        })
        .catch(() => {});
    },
    showDetails(id) {
      this.$router.push({ name: "house-detail1", params: { id: id } });
    }
  }
};
</script>

<style>
.el-table__row:hover {
  cursor: pointer;
}
.el-card__body {
  position: relative;
}
.el-card {
  overflow: visible;
  margin-bottom: 22px;
}
.act-toggle {
  width: 80px;
  height: 20px;

  margin: 0 auto;
  position: absolute;
  left: 0;
  right: 0;
  text-align: center;
  border-top: none;
  border-bottom-left-radius: 4px;
  border-bottom-right-radius: 4px;
  font-size: 14px;
  line-height: 20px;
  color: #888383;
  border-color: #c6e2ff;
  background-color: #ecf5ff;
  bottom: -20px;
}

.act-toggle:hover {
  color: #fff;
  background-color: #64acd6;
  user-select: none;
  cursor: pointer;
  box-shadow: 0px 2px 5px #0cf767;
}

.ct-q-form {
  height: 35px;
  width: 800px;
  margin-top: 15px;
  overflow: hidden;
  transition: height 1s cubic-bezier(0.18, 0.89, 0.32, 1.28) 0s;
}
.cb,
.cb input {
  -webkit-appearance: none;
}
</style>
