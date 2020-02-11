package np.com.softwarica.bookshooping.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import np.com.softwarica.bookshooping.R;
import np.com.softwarica.bookshooping.adapters.CartAdapter;
import np.com.softwarica.bookshooping.models.Book;

public class CheckoutActivity extends AppCompatActivity {

    private ImageView imgBack;
    private RecyclerView recyclerView;
    private Button btnCheckout;
    private List<Book> bookList;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        imgBack = findViewById(R.id.imgBack);
        recyclerView = findViewById(R.id.recyclerView);
        btnCheckout = findViewById(R.id.btnCheckout);

        imgBack.setOnClickListener(v -> super.onBackPressed());

        bookList = new ArrayList<>();
        adapter = new CartAdapter(this, bookList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadCartItems();

        btnCheckout.setOnClickListener(v -> checkout());

    }

    private void checkout() {
        if (bookList.size() == 0) {
            Toast.makeText(this, "Cart is empty!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        int total = 0;
        for (Book book : bookList) {
            total = total + Integer.parseInt(book.getPrice());
        }
        Intent intent = new Intent(this, CheckoutDetailsActivity.class);
        intent.putExtra("total", "Rs." + total);
        startActivity(intent);
    }

    private void loadCartItems() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("carts").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dialog.dismiss();
                Book book = dataSnapshot.getValue(Book.class);
                book.setKey(dataSnapshot.getKey());
                bookList.add(book);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Book book = dataSnapshot.getValue(Book.class);
                for (Book b : bookList) {
                    if (b.getName().equals(book.getName())) {
                        bookList.remove(b);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
