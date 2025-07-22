package cn.xl.examination.dao;

import cn.xl.examination.entity.FileAddress;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.relational.core.sql.In;

import javax.ws.rs.DELETE;
import java.util.List;

@Mapper
public interface FileAddressDao {
    @Insert("insert into file_address(file_name, content, file_url,objectKey) values (#{fileName},#{content},#{fileUrl},#{objectKey})")
    void save(FileAddress fileAddress);
    @Select("select * from file_address")
    List<FileAddress> getAll();
    @Delete("delete from file_address where file_url like concat('%',#{objectKey})")
    Integer delete(String objectKey);
}
