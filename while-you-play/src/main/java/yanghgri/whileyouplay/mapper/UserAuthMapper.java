package yanghgri.whileyouplay.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Mapper
@Repository
public interface UserAuthMapper {
    String selectPasswordByUserName(@Param("username") String username);

    Long selectUserIdByUserName(@Param("username") String username);

    Set<Long> selectRoleIdListByUserId(@Param("userId") Long userId);

    Set<String> selectRoleNameListByRoleIdList(@Param("roleIdList") Set<Long> roleIdList);
}