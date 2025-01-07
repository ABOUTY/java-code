package edu.uob;

public enum EntitySet {
    FURNITURE("furniture"),
    CHARACTER("characters"),
    ARTEFACT("artefacts"),
    PLAYERS("players");

    private final String entityName;

    EntitySet(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityName() {
        return entityName;
    }

    public static EntitySet getByEntityName(String entityName) {
        for (EntitySet entity : values()) {
            if (entity.getEntityName().equalsIgnoreCase(entityName)) {
                return entity;
            }
        }
        return null;
    }
}

