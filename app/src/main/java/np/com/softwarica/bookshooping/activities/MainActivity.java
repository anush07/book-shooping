package np.com.softwarica.bookshooping.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import np.com.softwarica.bookshooping.R;
import np.com.softwarica.bookshooping.fragments.AboutUsFragment;
import np.com.softwarica.bookshooping.fragments.HomeFragment;
import np.com.softwarica.bookshooping.fragments.MyProfileFragment;
import np.com.softwarica.bookshooping.utils.ShakeDetector;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private TextView tvTitle, tvCartCount;
    private ImageView imgCart;
    private SensorManager manager;
    private Sensor sensor;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.tvTitle);
        tvCartCount = findViewById(R.id.tvCartCount);
        imgCart = findViewById(R.id.imgCart);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setItemIconTintList(null);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment());

        loadCartItem();

        imgCart.setOnClickListener(v -> startActivity(new Intent(this, CheckoutActivity.class)));

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(count -> logout());
    }

    private void logout() {
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        View v = LayoutInflater.from(this).inflate(R.layout.dialog_confirm, null, false);
        dialog.setView(v);
        v.findViewById(R.id.btnYes).setOnClickListener(vv -> {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        });
        v.findViewById(R.id.btnNo).setOnClickListener(vv -> dialog.dismiss());
        dialog.show();
    }

    private void loadCartItem() {
        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid() + "").child("carts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int total = (int) dataSnapshot.getChildrenCount();
                tvCartCount.setVisibility(View.VISIBLE);
                tvCartCount.setText(Integer.toString(total));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                tvTitle.setText("Home");
                loadFragment(new HomeFragment());
                break;
            case R.id.nav_profile:
                tvTitle.setText("My Profile");
                loadFragment(new MyProfileFragment());
                break;
            case R.id.nav_about:
                tvTitle.setText("About Us");
                loadFragment(new AboutUsFragment());
                break;
        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        manager.registerListener(mShakeDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        manager.unregisterListener(mShakeDetector);
    }
}
