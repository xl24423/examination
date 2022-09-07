package cn.xl.examination.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Comparator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableDataVO implements Comparable<TableDataVO> {
    @NotBlank(message = "选项内容不能为空")
    private String content;     // 选项内容
    @NotBlank(message = "选项不能为空")
    private String option;      // 选项
    private Boolean type;       // 是否为答案

    @Override
    public int compareTo(TableDataVO o) {
        return (int)(this.getOption().charAt(0)) - (int)(o.getOption().charAt(0)) ;
    }
}
