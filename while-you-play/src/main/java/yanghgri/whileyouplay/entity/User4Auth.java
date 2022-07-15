package yanghgri.whileyouplay.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * 专用于身份认证的用户对象
 *
 * @author YangHgRi
 * @date 2022/07/15
 */
@Data
public class User4Auth implements UserDetails {
    //用户名
    private final String username;
    //明文密码
    private final String password;
    //角色
    private Role role;
    //账号未过期
    private boolean accountNonExpired = true;
    //账号未锁定
    private boolean accountNonLocked = true;
    //凭证未过期
    private boolean credentialsNonExpired = true;
    //账号启用中
    private boolean enabled = true;
    //用户角色列表
    private final Set<GrantedAuthority> authoritySet = new HashSet<>();

    /**
     * 获取用户角色列表
     *
     * @return {@link Collection}<{@link ?} {@link extends} {@link GrantedAuthority}>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritySet;
    }

    /**
     * 获取用户名
     *
     * @return {@link String}
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 获取密码
     *
     * @return {@link String}
     */
    @Override
    public String getPassword() {
        return password;
    }


    /**
     * 确认账户未过期
     *
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * 确认账户未锁定
     *
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * 确认凭证未过期
     *
     * @return boolean
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * 确认账号启用中
     *
     * @return boolean
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}