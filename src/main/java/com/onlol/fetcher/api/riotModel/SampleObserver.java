package com.onlol.fetcher.api.riotModel;

public class SampleObserver {
    private String encryptionKey;

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    @Override
    public String toString() {
        return "SampleObserver{" +
                "encryptionKey='" + encryptionKey + '\'' +
                '}';
    }
}
