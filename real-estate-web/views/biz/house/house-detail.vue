<template>
  <div class="app-container">
    <el-card style="margin-top:15px">
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
        <el-form-item label="面积(m2))：">
          <el-input v-model="house.area" type="number" min="0"></el-input>
        </el-form-item>
        <el-form-item label="价格(万)：">
          <el-input v-model="house.price" type="number" min="0"></el-input>
        </el-form-item>
        <el-form-item label="房屋状态：">{{house.status==0?'未租':'已租'}}</el-form-item>
        <el-form-item label="发布时间：">{{house.publishTime}}</el-form-item>
        <el-form-item
          label="房主："
        >{{house.id?(house.vendor.nickname||'房主昵称为空'):(this.user.nickname||this.user.username)}}</el-form-item>
        <el-form-item label="电话(中介))：">{{house.contactPhone}}</el-form-item>
        <el-form-item label="备注：">
          <el-input v-model="house.note" type="textarea" rows="10"></el-input>
        </el-form-item>
        <el-form-item>
          <span class="footer" style="margin-right: 60px;">
            <el-button
              :style="{'display':(house.id && house.status==0?'':'none')}"
              @click="rent()"
            >租用</el-button>
            <el-button :style="{'display':(house.status==0?'':'none')}" @click="addHouse()">保存</el-button>
            <el-button
              :style="{'display':(house.id && house.publish==0?'':'none')}"
              @click="publishHouse()"
            >保存并发布</el-button>
          </span>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card style="margin-top:15px" :style="{'display':(house.id==null?'none':'block')}">
      <el-form ref="houseInfoForm1" label-width="120px" style="width: 600px" size="small">
        <el-form-item label="评分" class="ct-free" :v-model="scoreAgg">
          <div class="cmp-score">
            <div
              class="ct-per"
              :style="{'height':scoreAgg[1]}"
              style="background: rgb(238, 238, 238);"
            >
              <span class="ct-per-v">{{scoreAgg[1]}}</span>
              <span>1星</span>
            </div>
            <div class="ct-per" :style="{'height':scoreAgg[2]}" style="background: #FFEB3B;">
              <span class="ct-per-v">{{scoreAgg[2]}}</span>
              <span>2星</span>
            </div>
            <div
              class="ct-per"
              style="background-color: rgb(12, 19, 234);"
              :style="{'height':scoreAgg[3],'color':(scoreAgg[3]==0?'#000':'rgb(222, 215, 215)')}"
            >
              <span class="ct-per-v">{{scoreAgg[3]}}</span>
              <span>3星</span>
            </div>
            <div
              class="ct-per"
              :style="{'height':scoreAgg[4]}"
              style="background-color: rgb(251, 88, 88);"
            >
              <span class="ct-per-v">{{scoreAgg[4]}}</span>
              <span>4星</span>
            </div>
            <div
              class="ct-per"
              :style="{'height':scoreAgg[5]}"
              style="background-color: rgb(255, 0, 0);"
            >
              <span class="ct-per-v">{{scoreAgg[5]}}</span>
              <span>5星</span>
            </div>
          </div>
        </el-form-item>
        <el-form-item>
          <el-radio-group v-model="userScore">
            <el-radio @change="doScore(1)" name="score" value="1" :label="1">1星</el-radio>
            <el-radio @change="doScore(2)" name="score" value="2" :label="2">2星</el-radio>
            <el-radio @change="doScore(3)" name="score" value="3" :label="3">3星</el-radio>
            <el-radio @change="doScore(4)" name="score" value="4" :label="4">4星</el-radio>
            <el-radio @change="doScore(5)" name="score" value="5" :label="5">5星</el-radio>
          </el-radio-group>
          <!-- <el-button @click="doScore()" style="
    margin-left: 20px;
    margin-top: 10px;
          ">确定评分</el-button>-->
        </el-form-item>
      </el-form>
    </el-card>

    <el-card style="margin-top:15px" :style="{'display':(house.id==null?'none':'block')}">
      <el-form ref="houseInfoForm2" label-width="120px" style="width: 100%" size="small">
        <el-form-item label="留言" class="ct-free"></el-form-item>
        <ul class="list-msg">
          <li v-for="msg in messages" :key="msg.id">
            <div class="ct-content">{{msg.content}}</div>
            <div class="ct-info">
              <span>用户：{{msg.userName}}</span>
              <span>日期：{{msg.createTime}}</span>
            </div>
          </li>

<li>
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

</li>
          <li style="height: 180px;padding-left:0px">
            <el-input style="height:180px;" v-model="msgContent" type="textarea" placeholder="文明留言"></el-input>
          </li>
          <li style="height:30px;line-height:30px;padding-left:5px;margin: 10px 0px;">
            <el-button style="line-height: 1px;" @click="postMsg()">发表</el-button>
          </li>
        </ul>
      </el-form>
    </el-card>
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
  pageSize: 5
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
    listQuery:{
    pageNum:1,
    pageSize:5,total:20},total:20,
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
      // layoutMap: {
      //   ONE: 0,
      //   TWO: 1,
      //   THREE: 2,
      //   FOUR: 3,
      //   DUPLEX: 4,
      //   VILLA: 5
      // },
      house: {
        id: null,
        title: "博雅西苑",
        layout: 3,
        price: "990",
        area: "135",
        address: "北京市海淀区厢黄旗西路1号",
        note: "wonderful",
        publish: 1,
        status: 0,
        vendorId: 4,
        audit: 1,
        newHouse: true,
        contactPhone: "15801689640"
      },
      scoreAgg: {
        1: "5%",
        2: "10%",
        3: "5%",
        4: "30%",
        5: "5%"
      },
      userScore: 0,
      messages: [
        {
          id: 1,
          content: "环境优美环境优美环境优美环境优美环境优美环境优美",
          userName: "jet",
          createTime: "2019-10-24"
        },
        {
          id: 2,
          content: "交通便利交通便利交通便利交通便利交通便利交通便利",
          userName: "jet",
          createTime: "2019-10-24"
        },
        {
          id: 3,
          content: "家具齐全家具齐全家具齐全家具齐全家具齐全家具齐全家具齐全",
          userName: "jet",
          createTime: "2019-10-24"
        },
        {
          id: 4,
          content: "装修豪华装修豪华装修豪华装修豪华装修豪华装修豪华装修豪华",
          userName: "jet",
          createTime: "2019-10-24"
        }
      ],
      msgContent: null,
      user: null
    };
  },
  created() {
    this.user = this.$store.getters.curUser;
    this.loadData();
  },
  watch: {
    $route(to, from) {
      // debugger;
      if (from.path.indexOf(to.path) != -1) {
        console.log(to);
        this.user = this.$store.getters.curUser;
        this.loadData();
      }
    }
  },
  methods: {
    loadData() {
      let id = this.$route.params.id || this.$route.query.id;
      if (id) {
        this.queryHouse(id);
        this.queryMessages(id);
        this.queryScoresAggregation(id);
      } else {
        this.clearData();
      }
    },
    clearData() {
      this.house = Object.assign({}, houseModel);
      this.messages = [];
      this.agg = {};
      this.userScore = 0;
    },
    queryMessages(id) {
      let opts = { houseId: id, pageSize: 0, disabled: 0 };
      this.$store.dispatch("queryMessages", opts).then(info => {
        let data = info.list;
        if (data && data.length > 0) {
          data.forEach(element => {
            element.createTime = this.fmt(element.createTime);
          });
          this.messages = data;
        }
      });
    },
    queryScoresAggregation(id) {
      this.$store
        .dispatch("aggScore", { userId: this.user.id, houseId: id })
        .then(data => {
          this.userScore = data.curUserScore;
          if (data.agg) {
            data.agg[5] = data.agg[5] > 0 ? data.agg[5] * 100 + "%" : 0;
            data.agg[1] = data.agg[1] > 0 ? data.agg[1] * 100 + "%" : 0;
            data.agg[2] = data.agg[2] > 0 ? data.agg[2] * 100 + "%" : 0;
            data.agg[3] = data.agg[3] > 0 ? data.agg[3] * 100 + "%" : 0;
            data.agg[4] = data.agg[4] > 0 ? data.agg[4] * 100 + "%" : 0;

            this.scoreAgg = data.agg;
          }
        });
    },
    queryHouse(id) {
      this.$store.dispatch("house", id).then(data => {
        this.house = data;
      });
    },
    publishHouse() {
      this.house.publish = 1;
      this.addHouse();
    },
    addHouse() {
      if (!this.house.id) {
        this.house.vendorId = this.user.id;
      }

      this.$store
        .dispatch(!this.house.id ? "create" : "update", this.house)
        .then(data => {
          // this.queryhouses();
          // self.dialogVisible = false;
          // self.house = houseModel;
          this.$message({
            type: "success",
            message: "保存成功",
            duration: 1500
          });

          if (data) {
            this.house = data;
            console.log(this.house);
          }
        })
        .catch(() => {});
    },
    postMsg() {
      let msg = {
        content: this.msgContent,
        userId: this.user.id,
        houseId: this.house.id
      };
      this.$store
        .dispatch("addMsg", msg)
        .then(data => {
          this.msgContent = null;
          data.createTime = this.fmt(data.createTime);
          this.messages.splice(0, 0, data);
        })
        .catch(() => {});
    },
    fmt(d) {
      return formatDate(new Date(d), "yyyy-MM-dd hh:mm:ss");
    },
    doScore(val) {
      let score = {
        houseId: this.house.id,
        userId: this.user.id,
        grade: val
      };
      this.$store
        .dispatch("score", score)
        .then(data => {
          //todo hide radiogroup
          this.queryScoresAggregation(this.house.id);
        })
        .catch(() => {});
    },
    rent() {
      // let r = {
      //   houseId: this.house.id,
      //   vendeeId: this.user.id,
      //   price: this.house.price
      // };
      this.house.vendeeId = this.user.id;
      this.$store.dispatch("rent", this.house).then(data => {
        this.$message({
          type: "success",
          message: "已租用",
          duration: 1500
        });

        this.house.status = 1;
      });
    }
  }
};
</script>
<style>
.ct-per {
  width: 50px;
  border: none;
  bottom: 0px;
  margin-left: 5px;
  margin-right: 5px;
  height: 20px;
  border-bottom: none;
  text-align: center;
  line-height: 20px;
  font-size: 12px;
  box-shadow: 2px 2px 2px #716a6a;
  border-top-left-radius: 4px;
  border-top-right-radius: 4px;
}

.ct-per span {
  position: absolute;
  bottom: 0;
  margin: 0 auto;
  width: 30px;
  display: block;
  margin-left: 10px;
  float: none;
  clear: both;
}

span.ct-per-v {
  position: absolute;
  top: 0px;
  display: block;
  height: 20px;
  margin: 0 auto;
  margin-left: 10px;
}

ul.list-msg {
  padding-inline-start: 0px;
  border: 1px solid #eee;
  border-radius: 4px;
  width: 100%;
  font-size: 14px;
  list-style: none;
}

.list-msg li {
  border-bottom: 1px solid #eee;
  height: 80px;
  padding-left: 10px;
  display: flex;
  flex-direction: row;
}
.ct-content {
  flex: 1;
  line-height: 50px;
}

.ct-info {
  width: 270px;
  position: relative;
  color: #aaa;
}

ct-info span:first-child {
  text-align: center;
}

.ct-info span {
  display: inline-block;
  position: absolute;
  bottom: 0;
  white-space: nowrap;
}
.ct-info span:first-child {
  text-align: center;
  width: 60px;
}
.ct-info span:nth-child(2) {
  width: 150px;
  margin-left: 90px;
}
.el-textarea textarea {
  height: 100%;
}
.ct-free .el-form-item__label {
  font-weight: bold;
  text-align: left;
}

.cmp-score {
  width: 500px;
  height: 250px;
  border-bottom: 1px solid;
  border-left: 1px solid;
  position: relative;
  display: flex;
  flex-flow: row;
  justify-content: space-around;
  align-items: flex-end;
}
.el-radio {
  margin-left: 20px;
}
</style>
