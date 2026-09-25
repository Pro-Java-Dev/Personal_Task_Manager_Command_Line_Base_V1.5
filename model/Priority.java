package PERSONAL_TASK_MANAGER.model;

public enum Priority {
    LOW("LOW"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH");

    private String displayName;

    private Priority(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
