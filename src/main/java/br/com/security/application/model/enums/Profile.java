package br.com.security.application.model.enums;

/**
 * @author Elvis Fernandes on 21/10/19
 */
public enum  Profile {

    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private Integer id;
    private String description;

    private Profile(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static Profile toEnum(Integer id) {

        if(id == null) return null;

        for (Profile profile : Profile.values()) {
            if(id.equals(profile.getId())) {
                return profile;
            }
        }

        throw new IllegalArgumentException("Id invalid: " + id);
    }
}
