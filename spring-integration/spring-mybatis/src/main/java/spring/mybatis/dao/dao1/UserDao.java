package spring.mybatis.dao.dao1;

import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

public interface UserDao {

    @Select("select * from user")
    List<Map<?,?>> findUser();

    List<Map<?,?>> findUserMapper();
}
