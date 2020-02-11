package np.com.softwarica.bookshooping.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import np.com.softwarica.bookshooping.R;
import np.com.softwarica.bookshooping.models.Book;

public class DetailActivity extends AppCompatActivity {

    private SimpleDraweeView imgCover, imgBook;
    private TextView tvName, tvPrice, tvDesc, tvTitle;
    private RatingBar ratingBar;
    private Book book;
    private Button btnAddCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        book = (Book) getIntent().getSerializableExtra("book");

        findViewById(R.id.imgBack).setOnClickListener(v -> super.onBackPressed());

        imgCover = findViewById(R.id.imgCover);
        tvTitle = findViewById(R.id.tvTitle);
        tvName = findViewById(R.id.tvBookName);
        tvPrice = findViewById(R.id.tvPrice);
        tvDesc = findViewById(R.id.tvDescription);
        ratingBar = findViewById(R.id.ratingBar);
        imgBook = findViewById(R.id.imgBook);
        btnAddCart = findViewById(R.id.btnAdd);

        tvTitle.setText(book.getName());
        tvName.setText(book.getName());
        tvPrice.setText("Rs." + book.getPrice());
        tvDesc.setText(book.getDescription());
        ratingBar.setRating(Float.parseFloat(book.getRating()));
        imgBook.setImageURI(book.getImage());
        imgCover.setImageURI(book.getImage());

        btnAddCart.setOnClickListener(v -> addToCart());
    }

    private void addToCart() {
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("carts").push().setValue(book);
        super.onBackPressed();
        Toast.makeText(this, "Book added to cart successfully!!!", Toast.LENGTH_SHORT).show();
    }
}
