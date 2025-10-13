package co.za.carrental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity // Marks this as a DB entity
public class AdminPortal {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.UUID) // Auto-generate unique IDs
    private String portalId; // Unique ID for each portal

    private String portalName;   // Name of the portal section
    private String description;  // What this portal section does

    protected AdminPortal() {} // Default constructor for JPA

    public AdminPortal(String portalId, String portalName, String description) {
        this.portalId = portalId;
        this.portalName = portalName;
        this.description = description;
    }

    // Getters
    public String getPortalId() { return portalId; }
    public String getPortalName() { return portalName; }
    public String getDescription() { return description; }
}