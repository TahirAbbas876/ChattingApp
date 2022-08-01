package com.example.netrootfb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.netrootfb.R;
import com.example.netrootfb.Adapters.FragmentsAdapter;
import com.example.netrootfb.GroupChatActivity;
import com.example.netrootfb.SettingsActivity;
import com.example.netrootfb.SignInActivity;
import com.example.netrootfb.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mauth;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mauth = FirebaseAuth.getInstance();

        binding.viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
               startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.logout:
                mauth.signOut();
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                break;
            case R.id.groupChat:
                mauth.signOut();
                startActivity(new Intent(getApplicationContext(), GroupChatActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}