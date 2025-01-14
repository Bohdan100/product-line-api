package corp.product.data;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{
    ADMIN,
    OPERATOR,
    SUPERIOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
