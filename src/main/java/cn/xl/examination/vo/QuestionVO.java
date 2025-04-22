package cn.xl.examination.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QuestionVO {
    private Integer id;
    private String type;
    private String question;
    private String value;
    private OptionVO[] option;
    private String url;
    private Float score;
    private String analysis;
    private String solution;

    public QuestionVO(Integer id, String type, String question, String value, OptionVO[] option, String url) {
        this.id = id;
        this.type = type;
        this.question = question;
        this.value = value;
        this.option = option;
        this.url = url;
    }
}
