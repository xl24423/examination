package cn.xl.examination.vo;

import cn.xl.examination.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AnswerVO {
    Integer qid;
    String user_answer;

    public AnswerVO(Integer qid, String user_answer) {
        this.qid = qid;
        this.user_answer = user_answer;
    }

    public AnswerVO() {
    }

}
