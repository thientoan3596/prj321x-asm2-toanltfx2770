package asm02.entity;

public enum eApplicationStatus {
    PENDING,
    APPROVED,
    REJECTED;
    @Override
    public String toString() {
        switch (this) {
            case PENDING: return "Pending";
            case APPROVED: return "Approved";
            case REJECTED: return "Rejected";
            default: return name();
        }
    }
}
