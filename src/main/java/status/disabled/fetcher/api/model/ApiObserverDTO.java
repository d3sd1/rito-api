package status.disabled.fetcher.api.model;

/*
/lol/spectator/v4/featured-games
 */
public class ApiObserverDTO {
    private String encryptionKey = ""; // Key used to decrypt the spectator grid game data for playback

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
