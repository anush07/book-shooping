package np.com.softwarica.bookshooping.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import np.com.softwarica.bookshooping.R;
import np.com.softwarica.bookshooping.models.Book;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Holder> {

    private Context context;
    private List<Book> bookList;

    public CartAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Book book = bookList.get(position);
        holder.imgBook.setImageURI(Uri.parse(book.getImage()));
        holder.tvBookName.setText(book.getName());
        holder.tvPrice.setText("Rs." + book.getPrice());
        holder.imgDelete.setOnClickListener(v -> {
            FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("carts").child(book.getKey()).removeValue();
            Toast.makeText(context, "Item deleted successfully!!!", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private SimpleDraweeView imgBook;
        private TextView tvBookName, tvPrice;
        private ImageView imgDelete;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
}
