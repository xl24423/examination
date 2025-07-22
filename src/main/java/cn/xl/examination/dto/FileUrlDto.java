package cn.xl.examination.dto;

import lombok.Data;

@Data
public class FileUrlDto {
    private String fileUrl;
    private String fileName;
    private String content;
    private String objectKey;
}
