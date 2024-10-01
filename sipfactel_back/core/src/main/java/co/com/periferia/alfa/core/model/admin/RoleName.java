package co.com.periferia.alfa.core.model.admin;

public enum RoleName {
	GUEST("Invitado"),
	SUPER_ADMIN("Super administrador"),
	ADMIN("Administrador");
	
	private String displayName;

	RoleName(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() { return displayName; }

    // Optionally and/or additionally, toString.
    @Override public String toString() { return displayName; }
}
