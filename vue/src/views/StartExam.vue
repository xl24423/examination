<template>
  <div>
    <el-card style="font-size: 18px">
      <span>距离考试结束还有：</span>
      <span style="color: red; font-weight: 550"
        >{{ minute }}分钟{{ seconds }}秒</span
      >
    </el-card>

    <div
      style="display: flex; justify-content: space-between; margin-top: 20px"
    >
      <el-card style="width: 27%; text-align: center">
        <div>
          <p
            style="
              background-color: #eee;
              height: 30px;
              line-height: 30px;
              font-size: 15px;
            "
          >
            答题卡
          </p>
          <div style="margin: 10px 0; text-align: left">
            <el-tag type="info">未作答</el-tag>
            <el-tag style="margin-left: 10px" type="success">已作答</el-tag>
          </div>
        </div>

        <div v-for="(item, index) in datas" :key="index">
          <p
            style="
              background-color: #eee;
              height: 30px;
              line-height: 30px;
              font-size: 15px;
            "
          >
            {{ item.type }}
          </p>
          <div style="margin: 10px 0; text-align: left">
            <el-tag
              v-for="(item1, index1) in item.size"
              :key="index1"
              :type="
                item.flag && item.indexnumber == item1 ? 'warning' : 'info'
              "
              >{{ item1 }}</el-tag
            >
          </div>
        </div>
      </el-card>
      <el-card style="width: 72%">
        <p>{{ indexnumber+1 }}.{{ test.content }}</p>
                <img width="200px" height="200px" v-if="test.image" :src="test.image" alt=""/>
        <el-radio-group v-model="Answer">
          <el-radio-button
          text-color="rgb(255, 255, 255)"
          fill="rgb(255, 255, 255)"
          style="margin:5px 1px"
            :label="item.abc"
            v-for="item in test.answerList"
            :key="item.abc"
            ><p style="width: 900px;line-height: 30px;text-align: left;font-size: 18px;">{{ item.abc }}.{{ item.content }}</p>
            <div v-if="item.image!=''&&item.image.length!=0"><img  width="200px" height="200px" :src="item.image" alt=""></div>
            </el-radio-button
          >
        </el-radio-group>
        <label for=""></label>
      </el-card>
    </div>
    <div style="margin-left: 29%;margin-top: 20px;">
        <el-button v-if="indexnumber>0" type="primary">上一题</el-button>
    <el-button @click="next" type="warning">{{ message }}</el-button>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      Answer: "",
      minute: 29,
      seconds: 60,
      indexnumber: 0,
      test: {},
      datas: [
        {
          type: "选择题",
          size: 5,
          flag: true,
          indexnumber: 1,
        },
        {
          type: "多选题",
          size: 5,
          flag: false,
          indexnumber: 1,
        },
        {
          type: "判断题",
          size: 5,
          flag: false,
          indexnumber: 1,
        },
      ],
      // 选择题
      datas1: [
        {
          actualScore: 10,
          answer: "",
          answerList: [
            {
              abc: "A",
              answerId: "1286860480886951937",
              checked: false,
              content: "13",
              id: "021a7f4a-8485-4eb6-a2c3-3b747ecae6ca",
              image: "https://img-blog.csdnimg.cn/878f4366dde949eea56a1722294fe248.png#pic_center",
              paperId: "1556166949681176578",
              quId: "1286860480865980417",
              sort: 0,
            },
            {
              abc: "C",
              answerId: "1286860480878563329",
              checked: false,
              content: "5",
              id: "55a9d144-3ecd-4188-8d73-98578f80f612",
              image:
                "https://img-blog.csdnimg.cn/878f4366dde949eea56a1722294fe248.png#pic_center",
              paperId: "1556166949681176578",
              quId: "1286860480865980417",
              sort: 2,
            },
          ],
          answered: false,
          content: "图中阴影部分占面积百分之几？",
          id: "1556166949693759489",
          image: "https://img-blog.csdnimg.cn/878f4366dde949eea56a1722294fe248.png#pic_center",
          isRight: false,
          paperId: "1556166949681176578",
          quId: "1286859710305226754",
          quType: 1,
          score: 10,
          sort: 0,
        },
        {
          actualScore: 10,
          answer: "",
          answerList: [
            {
              abc: "A",
              answerId: "1286860480886951937",
              checked: false,
              content: "13",
              id: "021a7f4a-8485-4eb6-a2c3-3b747ecae6ca",
              image: "",
              paperId: "1556166949681176578",
              quId: "1286860480865980417",
              sort: 0,
            },
            {
              abc: "C",
              answerId: "1286860480878563329",
              checked: false,
              content: "5",
              id: "55a9d144-3ecd-4188-8d73-98578f80f612",
              image:
                "http://localhost:8101/upload/file/2021/10/21/1451088637159571458.png",
              paperId: "1556166949681176578",
              quId: "1286860480865980417",
              sort: 2,
            },
          ],
          answered: false,
          content: "图中阴影部分占面积百分之几？",
          id: "1556166949693759489",
          image: "",
          isRight: false,
          paperId: "1556166949681176578",
          quId: "1286859710305226754",
          quType: 1,
          score: 10,
          sort: 0,
        },
        {
          actualScore: 10,
          answer: "",
          answerList: [
            {
              abc: "A",
              answerId: "1286860480886951937",
              checked: false,
              content: "13",
              id: "021a7f4a-8485-4eb6-a2c3-3b747ecae6ca",
              image: "",
              paperId: "1556166949681176578",
              quId: "1286860480865980417",
              sort: 0,
            },
            {
              abc: "C",
              answerId: "1286860480878563329",
              checked: false,
              content: "5",
              id: "55a9d144-3ecd-4188-8d73-98578f80f612",
              image:
                "http://localhost:8101/upload/file/2021/10/21/1451088637159571458.png",
              paperId: "1556166949681176578",
              quId: "1286860480865980417",
              sort: 2,
            },
          ],
          answered: false,
          content: "图中阴影部分占面积百分之几？",
          id: "1556166949693759489",
          image: "",
          isRight: false,
          paperId: "1556166949681176578",
          quId: "1286859710305226754",
          quType: 1,
          score: 10,
          sort: 0,
        },
        {
          actualScore: 10,
          answer: "",
          answerList: [
            {
              abc: "A",
              answerId: "1286860480886951937",
              checked: false,
              content: "13",
              id: "021a7f4a-8485-4eb6-a2c3-3b747ecae6ca",
              image: "",
              paperId: "1556166949681176578",
              quId: "1286860480865980417",
              sort: 0,
            },
            {
              abc: "C",
              answerId: "1286860480878563329",
              checked: false,
              content: "5",
              id: "55a9d144-3ecd-4188-8d73-98578f80f612",
              image:
                "http://localhost:8101/upload/file/2021/10/21/1451088637159571458.png",
              paperId: "1556166949681176578",
              quId: "1286860480865980417",
              sort: 2,
            },
          ],
          answered: false,
          content: "图中阴影部分占面积百分之几？",
          id: "1556166949693759489",
          image: "",
          isRight: false,
          paperId: "1556166949681176578",
          quId: "1286859710305226754",
          quType: 1,
          score: 10,
          sort: 0,
        },
        {
          actualScore: 10,
          answer: "",
          answerList: [
            {
              abc: "A",
              answerId: "1286860480886951937",
              checked: false,
              content: "13",
              id: "021a7f4a-8485-4eb6-a2c3-3b747ecae6ca",
              image: "",
              paperId: "1556166949681176578",
              quId: "1286860480865980417",
              sort: 0,
            },
            {
              abc: "C",
              answerId: "1286860480878563329",
              checked: false,
              content: "5",
              id: "55a9d144-3ecd-4188-8d73-98578f80f612",
              image:
                "http://localhost:8101/upload/file/2021/10/21/1451088637159571458.png",
              paperId: "1556166949681176578",
              quId: "1286860480865980417",
              sort: 2,
            },
          ],
          answered: false,
          content: "图中阴影部分占面积百分之几？",
          id: "1556166949693759489",
          image: "",
          isRight: false,
          paperId: "1556166949681176578",
          quId: "1286859710305226754",
          quType: 1,
          score: 10,
          sort: 0,
        },
      ],
      message: "下一题",
    };
  },
  created() {
    this.f1();
    if (this.indexnumber === 0) {
      this.test = this.datas1[this.indexnumber];
    //   console.log();
    }
  },
  methods: {
    success() {
      console.log("提交");
    },
    next() {
      if (this.message == "提交答案") {
        return this.success();
      }
      let datas = this.datas;
      console.log(datas);
      var data1, data;
      var x = null;
      var u = null;
      for (let i = 0; i < datas.length; i++) {
        u = i;
        if (datas[i].flag) {
            this.indexnumber++;
          datas[i].indexnumber++;
          if (i == 0) {
            this.test = this.datas1[datas[i].indexnumber]?this.datas1[datas[i].indexnumber]:{};
          }
          if (datas[i].indexnumber > datas[i].size && i + 1 < datas.length) {
            let y = i + 1;
            console.log(datas[y]);
            var { size, type } = datas[y];
            data = {
              size: size,
              type: type,
              flag: true,
              indexnumber: 1,
            };
            var { size, type } = datas[i];
            console.log("data[i]", datas[i]);
            data1 = {
              size: size,
              type: type,
              flag: false,
              indexnumber: 1,
            };
            x = i;
            // datas[(y)].flag = true;
            // datas[(y)].indexnumber = 1;
          }

          break;
        }
      }
      if (x != null) {
        console.log("data", data);
        console.log("data1", data1);
        this.$set(this.datas, x + 1, data);
        this.$set(this.datas, x, data1);
        let datas = this.datas;

        console.log(this.datas);
      }
      if (u + 1 >= datas.length && datas[u].indexnumber >= datas[u].size) {
        console.log("执行");
        this.message = "提交答案";
      }

      // this.datas = datas
      console.log();
    },
    f1() {
      var time;
      if (sessionStorage.getItem("countDown")) {
        time = sessionStorage.getItem("countDown");
        var time1 = new Date().getTime();
        this.minute = 29 - Math.floor((time1 - time) / 1000 / 60); //分
        this.seconds = 60 - Math.floor(((time1 - time) / 1000) % 60); //秒
      } else {
        time = new Date().getTime();
        sessionStorage.setItem("countDown", time);
      }
      var timer = setInterval(() => {
        // console.log(this.minute + " " + this.seconds);
        if (this.seconds == "00" && this.minute > 0) {
          this.seconds = 59;
          this.minute--;
        } else {
          this.seconds--;
        }
        if (this.seconds >= 0 && this.seconds < 10) {
          this.seconds = "0" + Number(this.seconds);
        }
        if (this.minute >= 0 && this.minute < 10) {
          this.minute = "0" + Number(this.minute);
        }
        if (this.minute == 0 && this.seconds == 0) {
          clearInterval(timer);
        }
      }, 1000);
    },
  },
  destroyed() {
    sessionStorage.clear();
  },
};
</script>