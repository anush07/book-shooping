package np.com.softwrica.bookshooping.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import np.com.softwarica.bookshooping.R;
import np.com.softwarica.bookshooping.models.User;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etFullName, etPhoneNumber;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        etFullName = findViewById(R.id.etFullName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnUpdate = findViewById(R.id.btnUpdate);

        findViewById(R.id.imgBack).setOnClickListener(v -> super.onBackPressed());

        btnUpdate.setOnClickListener(v -> updateProfile());

        User user = (User) getIntent().getSerializableExtra("user");
        etFullName.setText(user.getFullName());
        etPhoneNumber.setText(user.getPhoneNumber());
    }

    private void updateProfile() {
        if (etFullName.getText().toString().equals("")) {
            etFullName.setError("Full name is required!!");
            etFullName.requestFocus();
            return;
        } else if (etPhoneNumber.getText().toString().equals("")) {
            etPhoneNumber.setError("Phone number is required!!!");
            etPhoneNumber.requestFocus();
            return;
        }
        String uid = FirebaseAuth.getInstance().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("fullName").setValue(etFullName.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("phoneNumber").setValue(etPhoneNumber.getText().toString());
        super.onBackPressed();
        Toast.makeText(this, "Profile Updated successfully!!!", Toast.LENGTH_SHORT).show();
    }
}
