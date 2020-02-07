package np.com.softwrica.bookshooping.models;

public class History {
    private String key;
    private String price;
    private String timestamp;
    private String address;

    public History() {
    }

    public History(String key, String price, String timestamp, String address) {
        this.key = key;
        this.price = price;
        this.timestamp = timestamp;
        this.address = address;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
