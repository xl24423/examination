package cn.xl.examination.service.impl;

import cn.xl.examination.constBag.OssConst;
import cn.xl.examination.dao.FileAddressDao;
import cn.xl.examination.entity.FileAddress;
import cn.xl.examination.service.FileAddressService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileAddressServiceImpl implements FileAddressService {
    @Resource
    FileAddressDao fileAddressDao;
    @Override
    @Transactional
    public void save(FileAddress fileAddress) {
        fileAddressDao.save(fileAddress);
    }

    @Override
    public List<FileAddress> getAll() {
        return fileAddressDao.getAll();
    }

    @Override
    public List<FileAddress> getAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return fileAddressDao.getAll();
    }

    @Override
    @Transactional
    public Integer deleteAddress(String objectKey) {
        int i = fileAddressDao.delete(objectKey);
        OSS ossClient = new OSSClient(OssConst.endPoint, OssConst.accessKeyId, OssConst.secret);
        ossClient.deleteObject(OssConst.bucketName, objectKey); // objectKey从数据库获取
        return i;
    }
}
