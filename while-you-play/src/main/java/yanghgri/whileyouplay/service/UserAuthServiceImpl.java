package yanghgri.whileyouplay.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yanghgri.common.enums.StatusCode;
import yanghgri.common.exception.GenericException;
import yanghgri.whileyouplay.entity.User4Auth;
import yanghgri.whileyouplay.mapper.UserAuthMapper;

@Service
public class UserAuthServiceImpl implements UserDetailsService {
    private final UserAuthMapper userAuthMapper;

    @Autowired
    public UserAuthServiceImpl(UserAuthMapper userAuthMapper) {
        this.userAuthMapper = userAuthMapper;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        String password = userAuthMapper.selectPasswordByUserName(username);
        if (StringUtils.isEmpty(password)) {
            try {
                throw new GenericException(StatusCode.NOT_EXIST_USER);
            } catch (GenericException e) {
                throw new RuntimeException(e);
            }
        }
        return new User4Auth(username, password);
    }
}