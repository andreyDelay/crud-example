package com.andrey.application.util;

public abstract class Builder {
    public int getIdFromString(String data) {
        String stringId = data.replaceAll("[^0-9]", "");
        if (stringId.length() == 0) {
            return 0;
        }
        return Integer.parseInt(stringId);
    }

}
