package np.com.softwrica.bookshooping.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import np.com.softwarica.bookshooping.R;
import np.com.softwarica.bookshooping.adapters.BookAdapter;
import np.com.softwarica.bookshooping.models.Book;

public class HomeFragment extends Fragment {

    private SwipeRefreshLayout reload;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private List<Book> bookList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        reload = view.findViewById(R.id.reload);
        recyclerView = view.findViewById(R.id.recyclerView);

        bookList = new ArrayList<>();
        adapter = new BookAdapter(getActivity(), bookList);

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(adapter);

        reload.post(this::getBookList);
        reload.setOnRefreshListener(this::getBookList);

        return view;
    }

    private void getBookList() {
        reload.setRefreshing(true);
        FirebaseDatabase.getInstance().getReference().child("books").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                bookList.add(dataSnapshot.getValue(Book.class));
                adapter.notifyDataSetChanged();
                reload.setRefreshing(false);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

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
