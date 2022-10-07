package com.Shirai_Kuroko.DLUTMobile.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Shirai_Kuroko.DLUTMobile.Common.CenterToast;
import com.Shirai_Kuroko.DLUTMobile.R;

public class CommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        TextView tv_cancel = findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(view -> finishWithNoResult());
        TextView tv_confirm = findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(view -> finishWithCommentResult());
        TextView comment_words_limit = findViewById(R.id.comment_words_limit);
        EditText comment_content_et = findViewById(R.id.comment_content_et);
        comment_content_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                comment_words_limit.setText(String.valueOf(140-editable.length()));
            }
        });
    }

    public void finishWithCommentResult()
    {
        EditText comment_content_et = findViewById(R.id.comment_content_et);
        RatingBar comment_ratingBar = findViewById(R.id.comment_ratingBar);
        if (comment_content_et.getText().length()>0)
        {
            Intent intent = new Intent();
            intent.putExtra("resultcode", 1);
            intent.putExtra("score", (int)comment_ratingBar.getRating());
            intent.putExtra("comment", comment_content_et.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
        else
        {
            CenterToast.makeText(this,"评论不可为空！", Toast.LENGTH_SHORT).show();
        }
    }

    public void finishWithNoResult()
    {
        Intent intent = new Intent();
        intent.putExtra("resultcode", 0);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finishWithNoResult();
    }
}