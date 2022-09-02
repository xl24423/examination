<template>
  <div>
    <el-card style="font-size: 18px">
      <span>距离考试结束还有：</span>
      <span style="color: red; font-weight: 550">{{ countDown }}</span>
    </el-card>
    <!-- 答题区域 -->
    <div class="answer-container">
      <!-- 考试结束蒙版 -->
      <div v-if="countDown === '考试已经结束'" class="mock">
        <el-button type="info" round>考试已经结束</el-button>
      </div>
      <!-- 答题卡 -->
      <div class="answer-sheet-box">
        <!-- 标题 -->
        <div class="answer-sheet-title">答题卡</div>
        <el-tag type="info">未作答</el-tag>
        <el-tag type="success">已作答</el-tag>
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
        <div>{{ currentQuestion.index + 1 }}.{{ currentQuestion.question }}</div>

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
              @click="answerClick(item, index)"
              v-for="(item, index) in currentQuestion.option"
              :key="index"
          >
            <span v-if="currentQuestionType!==1">{{ item.value }} :    </span>{{ item.label }}
          </div>

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
    <div>
      <el-dialog
          title="提示"
          :visible.sync="dialogVisible"
          width="30%"
          center

      >
        <span>你还有未完成的题目,确定要提交吗?</span>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="postExam">确 定</el-button>
  </span>
      </el-dialog>
    </div>
    <div style="margin-left: 29%; margin-top: 20px">
      <el-button
          :disabled="countDown === '考试已经结束'"
          v-if="showLastBtn"
          @click="lastQuestion"
          type="primary"
      >上一题
      </el-button
      >
      <el-button
          :disabled="countDown === '考试已经结束'"
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
      starTime: '',
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
      nextText: '下一题', // 按钮文字
      currentQuestion: {},  // 当前问题
      currentItemIndex: 0, // 当前行数
      currentElIndex: 0, // 当前个数
      currentQuestionType: 1, // 当前问题类型 1:单选 2判断 3多选
      answerList: [],
      answerVO:{},
    }
  },
  mounted() {
    this.starTime = new Date().getTime()
    this.$nextTick(() => {
      this.mistiming();
    })
    let id = location.pathname.split("/")[2]
    this.request.get("/question/exam?id=" + id).then(res => {
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
        this.currentQuestion.index = 0
        this.currentQuestion.cId = 1  // 默认选择题
      } else {
        this.$message.error(res.msg)
      }
    })

  },
  methods: {
    jump(item, index) {
      if (item !== 0 || index !== 0) {
        this.showLastBtn = true;
      }
      let q = this.questionInfoList
      this.currentQuestionType = q[item].id
      let long = this.questionInfoList.length
      if (item === long - 1 &&
          index === q[long - 1].questionlist.length - 1) {
        this.nextText = '提交试卷';
      } else {
        this.nextText = '下一题'
      }


      this.questionInfoList.forEach((item, index) => {
        item.questionlist.forEach((el, idx) => {
          if (el.type === 'current') {
            if (el.value.length > 0) {
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
    // 答案点击事件
    answerClick(val, index) {
      // 多选
      if (this.currentQuestionType === 2) {
        this.questionInfoList[this.currentItemIndex].questionlist[this.currentElIndex].option[index].current = !this.questionInfoList[this.currentItemIndex].questionlist[this.currentElIndex].option[index].current // 选中事件

        const arr = this.questionInfoList[this.currentItemIndex].questionlist[this.currentElIndex].option.filter(item => {
          if (item.current) {
            return item
          }
        })

        this.questionInfoList[this.currentItemIndex].questionlist[this.currentElIndex].value = arr
      } else {
        this.questionInfoList[this.currentItemIndex].questionlist[this.currentElIndex].value = val.value
        this.questionInfoList[this.currentItemIndex].questionlist[this.currentElIndex].option.forEach(item => {
          item.current = false
        })
        this.questionInfoList[this.currentItemIndex].questionlist[this.currentElIndex].option[index].current = true // 选中事件
      }
      this.$forceUpdate()

    },
    // 上一题
    lastQuestion() {
      this.nextText = '下一题'
      let currentItemIndex
      let currentElIndex
      // 循环让当前题目已回答
      this.questionInfoList.forEach((item, index) => {
        item.questionlist.forEach((el, idx) => {
          if (el.type === 'current') {
            if (el.value.length > 0) {
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
              if (el.value.length > 0) {
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
          this.nextText = '提交试卷'
        } else {
          this.nextText = '下一题'
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
    postExam() {
      this.answerList = []
      this.questionInfoList.forEach((item, index) => {
        if (index === 0 || index === 1) {
          item.questionlist.forEach((q) => {
            let answer = ''
            q.option.forEach((o) => {
              if (o.current === true) {
                answer = o.value;
              }
            })
            let answerEdit = {}
            answerEdit.qid = q.id
            answerEdit.user_answer = answer;
            this.answerList.push(answerEdit);
          })
        } else {
          item.questionlist.forEach((q) => {
            let answer = ''
            q.option.forEach((o) => {
              if (o.current === true) {
                answer += o.value + ",";
              }
            })
            answer = answer.substring(0, answer.length - 1);
            let answerEdit = {}
            answerEdit.qid = q.id
            answerEdit.user_answer = answer;
            this.answerList.push(answerEdit);
          })
        }
      })
      this.answerVO.answerList = this.answerList;
      this.answerVO.starTime = this.starTime
      this.answerVO.bankId = location.pathname.split("/")[2]
      this.request.post("/exam", JSON.stringify(this.answerVO), {headers: {'Content-Type': 'application/json;charset=UTF-8'}}).
      then(res => {
        if (res.code===200){
          this.$message.success(res.msg)
          this.$router.push("/ExaminationRecord")
        }
      }).catch(e=>{
        this.$message.error(e.response.data.msg);
        this.$router.push("/onlineexamination")
      })
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
// 考试倒计时
    mistiming() {
      var timeStamp = 500 //秒
      this.times = formateTimeStamp(timeStamp);
      const str = `${this.times.hour}时${this.times.min}分${this.times.seconds}秒`
      this.countDown = str;
      var TimeDown = setInterval(() => {
        if (timeStamp > 0) {
          timeStamp--;
          const newTime = formateTimeStamp(timeStamp);
          const str = `${newTime.hour}时${newTime.min}分${newTime.seconds}秒`
          this.countDown = str;
        } else {
          this.countDown = '考试已经结束'
          clearInterval(TimeDown);
        }
      }, 1000)
    }
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
</style>