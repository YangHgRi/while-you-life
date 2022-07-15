package yanghgri.whileyouplay.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import yanghgri.common.entity.BaseDO;

@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseDO<Long> implements GrantedAuthority {
    private final String role;

    @Override
    public String getAuthority() {
        return role;
    }
}