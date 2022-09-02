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
}
