package co.za.carrental.factory;

import co.za.carrental.domain.AdminPortal;

public class AdminPortalFactory {

    // Static method to create a new AdminPortal object
    public static AdminPortal createPortal(String portalName, String description) {
        if (portalName == null || portalName.isBlank()) {
            // Validate so we don’t create nonsense data
            throw new IllegalArgumentException("Portal name required");
        }
        // Generate UUID manually or leave null (JPA will generate it)
        return new AdminPortal(null, portalName, description);
    }
}