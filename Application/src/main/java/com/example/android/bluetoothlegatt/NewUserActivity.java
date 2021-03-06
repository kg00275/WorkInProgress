package com.example.android.bluetoothlegatt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewUserActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY =
            "com.example.android.bluetoothlegatt.REPLY";

    private EditText mEditUserView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        mEditUserView = findViewById(R.id.edit_user);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditUserView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String user = mEditUserView.getText().toString();
                    replyIntent.putExtra(EXTRA_REPLY, user);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}