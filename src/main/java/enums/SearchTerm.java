package enums;

public enum SearchTerm {
    PHARMACY("בתי מרקחת"),
    DOCTOR("רופא"),
    CLINIC("מרפאה");

    private final String value;

    SearchTerm(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
