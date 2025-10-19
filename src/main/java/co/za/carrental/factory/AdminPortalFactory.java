package co.za.carrental.factory;

import co.za.carrental.domain.AdminPortal;

public class AdminPortalFactory {


    public static AdminPortal createPortal(String portalName, String description) {
        if (portalName == null || portalName.isBlank()) {

            throw new IllegalArgumentException("Portal name required");
        }

        return new AdminPortal(null, portalName, description);
    }
}