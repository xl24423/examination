package cn.xl.examination.service;

import cn.xl.examination.entity.FileAddress;

import java.util.List;

public interface FileAddressService {
    public void save(FileAddress fileAddress);

    List<FileAddress> getAll();

    List<FileAddress> getAll(int pageNum, int pageSize);

    Integer deleteAddress(String objectKey);
}
