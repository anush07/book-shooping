package np.com.softwarica.bookshooping.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import np.com.softwarica.bookshooping.R;
import np.com.softwarica.bookshooping.activities.EditProfileActivity;
import np.com.softwarica.bookshooping.activities.LoginActivity;
import np.com.softwarica.bookshooping.models.User;

public class MyProfileFragment extends Fragment {


    private TextView tvFullName, tvEmail, tvPhoneNumber;
    private Button btnEditProfile, btnLogout;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        tvFullName = v.findViewById(R.id.tvFullName);
        tvEmail = v.findViewById(R.id.tvEmail);
        tvPhoneNumber = v.findViewById(R.id.tvPhoneNumber);
        btnEditProfile = v.findViewById(R.id.btnEditProfile);
        btnLogout = v.findViewById(R.id.btnLogout);

        loadProfile();

        btnEditProfile.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            intent.putExtra("user", user);
            getActivity().startActivity(intent);
        });

        btnLogout.setOnClickListener(view -> logout());

        return v;
    }

    private void logout() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_confirm, null, false);
        dialog.setView(v);
        v.findViewById(R.id.btnYes).setOnClickListener(vv -> {
            FirebaseAuth.getInstance().signOut();
            getActivity().finish();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });
        v.findViewById(R.id.btnNo).setOnClickListener(vv -> dialog.dismiss());
        dialog.show();
    }

    private void loadProfile() {
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                tvFullName.setText(user.getFullName());
                tvEmail.setText(user.getEmail());
                tvPhoneNumber.setText(user.getPhoneNumber());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
