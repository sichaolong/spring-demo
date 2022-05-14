package henu.soft.xiaosi.mapper;

import henu.soft.xiaosi.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

    User findLoginUserByName(String name);
}
