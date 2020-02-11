package np.com.softwarica.bookshooping.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import np.com.softwarica.bookshooping.R;
import np.com.softwarica.bookshooping.activities.DetailActivity;
import np.com.softwarica.bookshooping.models.Book;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.Holder> {

    private Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Book book = bookList.get(position);
        holder.imgBook.setImageURI(Uri.parse(book.getImage()));
        holder.tvBookName.setText(book.getName());
        holder.ratingBar.setRating(Float.parseFloat(book.getRating()));
        holder.tvPrice.setText("Rs." + book.getPrice());
        holder.itemView.setOnClickListener(v->{
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("book", book);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        private ImageView imgBook;
        private RatingBar ratingBar;
        private TextView tvBookName, tvPrice;

        public Holder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
