package np.com.softwrica.bookshooping.models;

import java.io.Serializable;

public class Book implements Serializable {
    private String name;
    private String rating;
    private String image;
    private String price;
    private String description;
    private String key;


    public Book() {
    }

    public Book(String name, String rating, String image, String price, String description) {
        this.name = name;
        this.rating = rating;
        this.image = image;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
