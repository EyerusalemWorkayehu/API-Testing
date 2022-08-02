package com.iwork.teaching;

public enum Gender {
    Male("Male"),
    Female("Female");

    private String strVal;

    Gender(String strVal) {
        this.strVal = strVal;
    }

    public static Gender fromString(String gender) {
        return (gender.compareTo("Female") == 0) ? Female : Male;
    }

    @Override
    public String toString() {
        return this.strVal;
    }
}
