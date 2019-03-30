package cn.com.cmbcc.techstar;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserInfoMapper {

    @Select("select * from techstar_user where ID <#{end} and ID>=#{beg}")
    List<UserInfo> getListByParam(@Param("beg")long beg, @Param("end")long end);

    void updateBatch(List<UserInfo> list);

    @Select("select ID from techstar_user order by ID desc limit 1")
    long getMaxID();
}
