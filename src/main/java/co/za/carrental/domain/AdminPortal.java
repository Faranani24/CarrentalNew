package co.za.carrental.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class AdminPortal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String portalId;

    private String portalName;
    private String description;

    protected AdminPortal() {}

    public AdminPortal(String portalId, String portalName, String description) {
        this.portalId = portalId;
        this.portalName = portalName;
        this.description = description;
    }


    public String getPortalId() { return portalId; }
    public String getPortalName() { return portalName; }
    public String getDescription() { return description; }
}