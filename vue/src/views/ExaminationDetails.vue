<template>
  <div>
    <div class="app-container" style="text-align: center">
      <span><el-page-header @back="goBack" content="成绩详情">
      </el-page-header></span>
      <h2 class="text-center">{{ paperData.examName }}</h2>
      <p class="text-center" style="color: #666">

       <span v-if="paperData.startdate != null">考试时间: {{
           paperData.startdate | parseTime("{y}-{m}-{d} {h}:{i}")
         }}</span>
        <span v-else></span>
      </p>

      <el-row :gutter="24" style="margin-top: 50px">

        <el-col :span="8" class="text-center">
          考生姓名：{{ paperData.username }}
        </el-col>

        <el-col :span="8" class="text-center">
          考试用时：{{ paperData.minute }}分钟
        </el-col>

        <el-col :span="8" class="text-center">
          考试得分：{{ paperData.count }}
        </el-col>

      </el-row>
    </div>
    <!-- 答题区域 -->
    <div class="answer-container">
      <!-- 答题卡 -->
      <div class="answer-sheet-box">
        <!-- 标题 -->
        <div class="answer-sheet-title">答题卡</div>
        <el-tag type="info" style="background-color: #909399;color: white" >未作答</el-tag>
        <el-tag type="success" style="background-color: #67C23A;color: white">已作答</el-tag>
        <el-tag type="warning" style="background-color: #E6A23C; color: white">当前题目</el-tag>
        <div v-for="(item, index) in questionInfoList" :key="index">
          <div class="answer-sheet-title">{{ item.text }}</div>
          <div>
            <el-button
                @click="jump(index,idx)"
                style="margin: 5px 5px; max-width:10px; height: 20px; text-align: center; line-height: 5px ; border: none; "
                v-for="(ele, idx) in item.questionlist"
                :key="idx"
                :type="getTagType(ele.type)"
            >{{ idx + 1 }}
            </el-button>
          </div>
        </div>
      </div>
      <div class="answer-area-box">
        <div>{{ currentQuestion.index + 1 }}.{{ currentQuestion.question }}
          <span style="font-weight:bold; color: blue; font-size: small">(分值: {{ currentQuestion.score }}) </span></div>

        <img
            @click="previewFlag = true"
            class="thumbnail"
            v-if="currentQuestion.url"
            :src="currentQuestion.url"
            alt=""
            srcset=""
        />
        <div class="answer-operation-box">
          <div
              class="answer-item"
              :class="item.current ? 'answer-item-current' : ''"
              v-for="(item, index) in currentQuestion.option"
              :key="index"

          >
            <span v-if="currentQuestionType!==1">{{ item.value }} :    </span>{{ item.label }}
          </div>
        </div>
        <div class="analysis">
          <h5>正确答案: {{currentQuestion.solution}} </h5>
          <h5>答案解析: {{currentQuestion.analysis}} </h5>
        </div>
      </div>
      <!-- 图片预览弹框 -->
      <el-dialog
          custom-class="preview-dialog"
          title="问题预览"
          :visible.sync="previewFlag"
          width="50%"
      >
        <div class="img-box">
          <img
              v-if="currentQuestion.url"
              :src="currentQuestion.url"
              alt=""
              srcset=""
              style="width: auto; height: 400px;"
          />
        </div>
      </el-dialog>
    </div>

    <div style="margin-left: 29%; margin-top: 20px">
      <el-button
          v-if="showLastBtn"
          @click="lastQuestion"
          type="primary"
      >上一题
      </el-button
      >
      <el-button
          v-if="showNextBtn"
          @click="nextQuestion(nextText)"
          type="warning"
      >{{ nextText }}
      </el-button
      >
    </div>
  </div>
</template>

<script>
import qs from 'qs'
import {formateTimeStamp} from '@/utils/tools.js'

export default {
  data() {
    return {
      time:0,   // 考试时间
      starTime: '', // 考试开始时间
      previewFlag: false, //预览弹框
      dialogVisible: false,
      questionInfoList: [
        {
          text: '选择题',
          id: 1,
          questionlist: []
        },
        {
          text: '判断题',
          id: 2,
          questionlist: [],
        },
        {
          text: '多选题',
          id: 3,
          questionlist: [], //多选
        }
      ],

      times: {},// 过滤的时间
      countDown: '',// 倒计时
      indexnumber: 0, // 当前题目序号
      showLastBtn: false,// 展示上一题
      showNextBtn: true, // 展示下一题
      nextText: '下一题', // 按钮文字
      currentQuestion: {},  // 当前问题
      currentItemIndex: 0, // 当前行数
      currentElIndex: 0, // 当前个数
      currentQuestionType: 1, // 当前问题类型 1:单选 2判断 3多选
      answerList: [],
      answerVO:{},
      paperData: {},
    }
  },
  created(){
    this.stopF5Refresh();
    this.init();
  },
  mounted() {
    let id = this.$route.query.bankId
    this.starTime = new Date().getTime()
    this.request.get("/question/examRecode?id=" + id).then(res => {
      if (res.code === 200) {
        this.total = res.data.length;
        let q = res.data;
        q.forEach((item, index) => {
          if (item.type === "1") {
            this.questionInfoList[0].questionlist.push(item)
          } else if (item.type === "2") {
            this.questionInfoList[2].questionlist.push(item)
          } else if (item.type === "3") {
            this.questionInfoList[1].questionlist.push(item)
          }
        })
        // 默认状态
        this.currentQuestionType = 0
        this.questionInfoList[0].questionlist[0].type = 'current'
        // 默认问题(右侧)
        this.currentQuestion = this.questionInfoList[0].questionlist[0]
        this.questionInfoList.forEach((item, index) => {
          item.questionlist.forEach((el, idx) => {
            if (el.value !== "null") {
              el.type = 'answer'
            } else {
              el.type = ''
            }
            if (index === 0 && idx ===0){
              el.type = 'current'
            }
          })
        })
        this.currentQuestion.index = 0
        this.currentQuestion.cId = 1  // 默认选择题
      } else {
        this.$message.error(res.msg)
      }
    })

  },
  methods: {
    init(){
      let username = this.$route.query.username;
      let bankId = this.$route.query.bankId;
      this.request.get("/exam/oneDetail",{
        params:{
          username: username,
          bankId: bankId
        }
      }).then(res=>{
        if (res.code===200){
          this.paperData = res.data;
          this.consumeTime = this.paperData.enddate - this.paperData.startdate
        }else{
          this.$message.error(res.msg)
        }
      })
    },
    goBack() {
      this.$router.push({path:"/ExaminationRecord"});
    },
    stopF5Refresh() {
      document.onkeydown = function (e) {
        var evt = window.event || e;
        var code = evt.keyCode || evt.which;
        //屏蔽F1---F11
        if (code > 111 && code < 123) {
          if (evt.preventDefault) {
            evt.preventDefault();
          } else {
            evt.keyCode = 0;
            evt.returnValue = false;
          }
        }
      };
      //禁止鼠标右键菜单
      document.oncontextmenu = function () {
        return false;
      };
      //阻止后退的所有动作，包括 键盘、鼠标手势等产生的后退动作。
      history.pushState(null, null, window.location.href);
      window.addEventListener("popstate", function () {
        history.pushState(null, null, window.location.href);
      });
    },
    jump(item, index) {
      if (item !== 0 || index !== 0) {
        this.showLastBtn = true;
      }else {
        this.showLastBtn = false;
      }
      let q = this.questionInfoList
      this.currentQuestionType = q[item].id
      let long = this.questionInfoList.length
      if (item === long - 1 &&
          index === q[long - 1].questionlist.length - 1) {
        this.showNextBtn = false;
      } else {
        this.showNextBtn = true;
      }


      this.questionInfoList.forEach((item, index) => {
        item.questionlist.forEach((el, idx) => {
          if (el.type === 'current') {
            if (el.value !== "null") {
              el.type = 'answer'
            } else {
              el.type = ''
            }
            this.$forceUpdate()
          }
        })
      })

      this.currentQuestionType = item
      this.questionInfoList[item].questionlist[index].type = "current"
      this.currentQuestion = this.questionInfoList[item].questionlist[index];
      this.currentQuestion.index = index;
      this.currentItemIndex = item;
      this.currentElIndex = index;

    },
    // 上一题
    lastQuestion() {
      this.showNextBtn = true;
      let currentItemIndex
      let currentElIndex
      // 循环让当前题目已回答
        this.questionInfoList.forEach((item, index) => {
          item.questionlist.forEach((el, idx) => {
            if (el.type === 'current') {
              if (el.value !== "null") {
                el.type = 'answer'
              } else {
                el.type = ''
              }
              this.$forceUpdate()
              currentItemIndex = index
              currentElIndex = idx
            }
          })
        })
      if (currentElIndex === 0) {
        if (currentItemIndex !== 0) {
          this.questionInfoList[currentItemIndex - 1].questionlist[this.questionInfoList[currentItemIndex - 1].questionlist.length - 1].type = 'current'
        }
        this.$forceUpdate()
      } else {
        this.questionInfoList[currentItemIndex].questionlist[currentElIndex - 1].type = 'current'

      }
      this.questionInfoList.forEach((item, index) => {
        item.questionlist.forEach((el, idx) => {
          if (el.type === 'current') {
            currentItemIndex = index
            currentElIndex = idx
          }
        })
      })
      // 第一项隐藏
      if (currentItemIndex === 0 && currentElIndex === 0) {
        this.showLastBtn = false
      }
      // 获取当前问题
      this.currentQuestion = this.questionInfoList[currentItemIndex].questionlist[currentElIndex]
      this.currentQuestion.index = currentElIndex
      this.currentQuestion.cId = this.questionInfoList[currentItemIndex].id
      this.currentItemIndex = currentItemIndex
      this.currentElIndex = currentElIndex
      this.currentQuestionType = this.questionInfoList[currentItemIndex].id - 1
      this.$forceUpdate()
    },
    // 下一题
    nextQuestion(type) {
      if (type === '下一题') {
        // 解开上一题
        this.showLastBtn = true
        // 找到当前选中
        let currentItemIndex
        let currentElIndex
        // 循环让当前题目已回答
        this.questionInfoList.forEach((item, index) => {
          item.questionlist.forEach((el, idx) => {

            if (el.type === 'current') {
              if (el.value !== "null") {
                el.type = 'answer'
              } else {
                el.type = ''
              }
              this.$forceUpdate()
              currentItemIndex = index
              currentElIndex = idx
            }
          })
        })
        // 已经是一个类别中的最后一题
        if (this.questionInfoList[currentItemIndex].questionlist.length - 1 === currentElIndex) {
          // 跳到下一个类别
          if (this.questionInfoList.length - 1 !== currentItemIndex) {
            this.questionInfoList[currentItemIndex + 1].questionlist[0].type = 'current'
            this.$forceUpdate()
          }

        } else {
          this.questionInfoList[currentItemIndex].questionlist[currentElIndex + 1].type = 'current'
          this.$forceUpdate()
        }
        // 当前题目索引
        this.questionInfoList.forEach((item, index) => {
          item.questionlist.forEach((el, idx) => {
            if (el.type === 'current') {
              currentItemIndex = index
              currentElIndex = idx
            }
          })
        })
        // 最后一项改变文字
        if (this.questionInfoList[this.questionInfoList.length - 1].questionlist[this.questionInfoList[this.questionInfoList.length - 1].questionlist.length - 1].type === 'current') {
          this.showNextBtn = false
        } else {
          this.showNextBtn = true
        }
        // 获取当前问题
        this.currentQuestion = this.questionInfoList[currentItemIndex].questionlist[currentElIndex]
        this.currentQuestion.index = currentElIndex
        this.currentQuestion.cId = this.questionInfoList[currentItemIndex].id
        this.currentItemIndex = currentItemIndex
        this.currentElIndex = currentElIndex
        this.currentQuestionType = this.questionInfoList[currentItemIndex].id - 1

      } else {
        this.questionInfoList.forEach((item) => {
          item.questionlist.forEach((q) => {
            if (q.value.length === 0) {
              this.dialogVisible = true;
            }
          })
        })
        if (this.dialogVisible === false) {
          this.postExam();
        }
      }
    },
// 获取答题状态
    getTagType(val) {
      if (val === 'current') {
        return 'warning'
      } else if (val === 'answer') {
        return 'success'
      } else {
        return 'info'
      }
    }
    ,

  },
  beforeDestroyed() {

  }
}
;
</script>
<style scoped>
.el-tag {
  margin-right: 5px;
}

.answer-container {
  display: flex;
  height: 500px;
  width: 100%;
  margin-top: 20px;
  position: relative;
}

.mock {
  position: absolute;
  top: 0;
  left: 0;
  background: #000;
  opacity: 0.8;
  color: #fff;
  width: 100%;
  line-height: 15;
  text-align: center;
  font-size: 35px;
  height: 100%;
  z-index: 999;
}

.answer-container .answer-sheet-box {
  width: 300px;
  height: 100%;
  padding: 15px;
  border: 1px solid #eee;
}

/* 缩略图 */
.thumbnail {
  margin-top: 10px;
  cursor: pointer;
  max-height: 150px;
  max-width: 100%;
}

/* 右侧 */

.answer-container .answer-area-box {
  width: calc(100% - 300px);
  height: 100%;
  overflow: auto;
  padding: 15px;
  border: 1px solid #eee;
}

.answer-item {
  background: #fff;
  border: #409ffd 1px solid;
  color: #409ffd;
  height: 35px;
  line-height: 35px;
  margin: 10px 0;
  cursor: pointer;
  font-size: 12px;
  padding-left: 12px;
  border-radius: 5px;
}

.answer-item:hover {
  background: #409ffd;
  color: #fff;
}

.answer-item-current {
  background: #409ffd;
  color: #fff;
}

/* 公共样式 */
.answer-sheet-title {
  margin: 5px 0;
  background: #eee;
  height: 35px;
  text-align: center;
  line-height: 35px;
}

.preview-dialog .img-box {
  max-height: 400px;
  overflow: hidden;
}
/deep/.el-button span {
  display: flex !important;
  justify-content: center !important;
  align-items: center !important;
}
</style>