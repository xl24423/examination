package cn.xl.examination.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OptionVO {
    String label;
    String value;

    public OptionVO(String label, String value) {
        this.label = label;
        this.value = value;
    }

    Boolean current;
}
