package cn.xl.examination.controller;

import cn.xl.examination.common.lang.Result;
import cn.xl.examination.entity.FileAddress;
import cn.xl.examination.service.FileAddressService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/fileAddress")
public class FileAddressController {
    @Resource
    FileAddressService fileAddressService;
    @GetMapping("/getAll")
    public Result getAllOssResource(int pageNum, int pageSize){
        List<FileAddress> list = fileAddressService.getAll(pageNum,pageSize);
        System.out.println(list);
        return Result.succ(200,"",list);
    }
}
