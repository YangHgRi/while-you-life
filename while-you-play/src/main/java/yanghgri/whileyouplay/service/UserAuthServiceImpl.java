package yanghgri.whileyouplay.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yanghgri.whileyouplay.entity.Role;
import yanghgri.whileyouplay.entity.User4Auth;
import yanghgri.whileyouplay.mapper.UserAuthMapper;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserAuthServiceImpl implements UserDetailsService {
    private final UserAuthMapper userAuthMapper;

    @Autowired
    public UserAuthServiceImpl(UserAuthMapper userAuthMapper) {
        this.userAuthMapper = userAuthMapper;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        if (log.isInfoEnabled()) {
            log.info("类: {}，方法: {}，用户：{} 请求登录，即将从数据库查询用户数据！", this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName(), username);
        }
        //先用用户名查询用户ID，然后用用户ID查询所属角色ID，最后用所属角色ID查询所属角色名
        Long userId = userAuthMapper.selectUserIdByUserName(username);

        if (log.isDebugEnabled()) {
            log.debug("用户ID：{}", userId);
        }

        Set<Long> roleIdSet = userAuthMapper.selectRoleIdSetByUserId(userId);

        if (log.isDebugEnabled()) {
            log.debug("角色ID集合：{}", roleIdSet);
        }

        Set<String> roleNameSet = userAuthMapper.selectRoleNameSetByRoleIdSet(roleIdSet);

        if (log.isDebugEnabled()) {
            log.debug("角色名集合：{}", roleNameSet);
        }

        //遍历用户所属角色名，并用此批量创建Role对象，最后存入集合
        Set<GrantedAuthority> authoritySet = new HashSet<>();
        roleNameSet.forEach(name -> authoritySet.add(new Role(name)));
        //返回UserDetails实现类对象
        return new User4Auth(username, userAuthMapper.selectPasswordByUserName(username), authoritySet);
    }
}