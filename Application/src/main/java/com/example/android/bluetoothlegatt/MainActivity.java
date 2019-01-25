package com.example.android.bluetoothlegatt;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserViewModel mUserViewModel;

    public static final int NEW_USER_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final UserListAdapter adapter = new UserListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        mUserViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> users) {
                // Update the cached copy of the users in the adapter.
                adapter.setUsers(users);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        User myUser = adapter.getUserAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myUser.getUser(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        mUserViewModel.deleteUser(myUser);
                    }
                });

        helper.attachToRecyclerView(recyclerView);

        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Clearing the data...",
                    Toast.LENGTH_SHORT).show();

            // Delete the existing data
            mUserViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void BLE(View view) {
        Intent intent = new Intent(this, DeviceScanActivity.class);
        startActivity(intent);
    }

    public void NewUser(View view) {
        Intent intent = new Intent(this, NewUserActivity.class);
        startActivityForResult(intent, NEW_USER_ACTIVITY_REQUEST_CODE);
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_USER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            User user = new User(data.getStringExtra(NewUserActivity.EXTRA_REPLY));
            mUserViewModel.insert(user);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    }
