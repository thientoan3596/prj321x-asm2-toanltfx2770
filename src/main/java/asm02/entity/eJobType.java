package asm02.entity;

public enum eJobType {
    FULL_TIME,
    PART_TIME,
    FREELANCE;
    @Override
    public String toString() {
        switch (this) {
            case FULL_TIME: return "Full-time";
            case PART_TIME: return "Part-time";
            case FREELANCE: return "Freelance";
            default: return name();
        }
    }
}
