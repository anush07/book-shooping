package np.com.softwarica.bookshooping;

import org.junit.Assert;
import org.junit.Test;

import np.com.softwarica.bookshooping.models.Book;

public class BookModelTest {

    @Test
    public void Test(){
        Book book = new Book("book","5","image","500","description");
        Assert.assertNotNull(book);
        Assert.assertEquals("book",book.getName());
        Assert.assertEquals("5",book.getRating());
        Assert.assertEquals("image",book.getImage());
        Assert.assertEquals("500",book.getPrice());
        Assert.assertEquals("description",book.getDescription());
    }
}
