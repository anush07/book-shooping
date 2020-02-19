package np.com.softwrica.bookshooping.activities;

import android.app.Notification;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import np.com.softwarica.bookshooping.R;
import np.com.softwarica.bookshooping.models.History;
import np.com.softwarica.bookshooping.models.User;
import np.com.softwarica.bookshooping.utils.CreateChannel;

public class CheckoutDetailsActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etAddress;
    private TextView tvTotalAmount;
    private Button btnCheckout;
    private int id = 1;
    private CreateChannel createChannel;
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_details);

        findViewById(R.id.imgBack).setOnClickListener(v->super.onBackPressed());

        etName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        btnCheckout = findViewById(R.id.btnCheckout);

        tvTotalAmount.setText(getIntent().getStringExtra("total"));

        loadProfile();

        btnCheckout.setOnClickListener(v -> checkout());

        createChannel = new CreateChannel(this);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        createChannel.createChannel();
    }

    private void loadProfile() {
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                etName.setText(user.getFullName());
                etEmail.setText(user.getEmail());
                etPhone.setText(user.getPhoneNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkout() {
        if (etAddress.getText().toString().equals("")) {
            etAddress.setError("Please enter address.");
            etAddress.requestFocus();
            return;
        }
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("history").push();
        String key = reference.getKey();
        History history = new History(key, tvTotalAmount.getText().toString(), String.valueOf(System.currentTimeMillis()), etAddress.getText().toString());
        reference.setValue(history);
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("carts").removeValue();
        super.onBackPressed();
        Toast.makeText(this, "Your order has been placed successfully!!!", Toast.LENGTH_SHORT).show();
    }



    private void showNotification() {
        Notification notification = new NotificationCompat.Builder(this, CreateChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Item Checked Out")
                .setContentText("You order has been placed successfully!!!")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(id, notification);
        id++;
    }
}
