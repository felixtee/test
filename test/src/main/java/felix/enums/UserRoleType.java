package felix.enums;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Felix Tee Yong Thye
 */
public enum UserRoleType {

    // @formatter:off
    ROLE_ADMIN("ROLE_ADMIN", true, "ROLE ADMIN"),
    ROLE_API("ROLE_API", true, "ROLE API");
    // @formatter:on

    private final String role;
    private final boolean status;
    private final String description;

    private UserRoleType(String role, boolean status, String description) {
        this.role = role;
        this.status = status;
        this.description = description;
    }

    public String getRole() {
        return role;
    }

    public boolean isStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    private static final Map<String, UserRoleType> DICT_OF_ROLE;

    static {
        DICT_OF_ROLE = new HashMap<>();
        for (UserRoleType em : UserRoleType.values()) {
            String uc = em.role.toUpperCase();
            if (em.role.equals(uc) == false) {
                throw new IllegalArgumentException(UserRoleType.class
                        + " enum=[" + em
                        + "] role=[" + em.role
                        + "] must be uppercase only");
            }
            DICT_OF_ROLE.put(em.role, em);
        }
        if (DICT_OF_ROLE.size() != UserRoleType.values().length) {
            throw new IllegalArgumentException(UserRoleType.class
                    + " (DICT_OF_ROLE) is not unique expected=["
                    + UserRoleType.values().length
                    + "] actual=[" + DICT_OF_ROLE.size()
                    + ']');
        }
    }

    public static UserRoleType fromRole(String role) {
        return DICT_OF_ROLE.get(role);
    }

    public static UserRoleType fromRoleIgnoreCase(String role) {
        if (role == null) {
            return null;
        }
        return DICT_OF_ROLE.get(role.toUpperCase());
    }

}
