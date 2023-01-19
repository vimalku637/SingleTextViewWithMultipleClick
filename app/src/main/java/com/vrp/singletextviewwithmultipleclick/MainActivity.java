package com.vrp.singletextviewwithmultipleclick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.core.util.Pair;

public class MainActivity extends AppCompatActivity {
    TextView tvTermsAndConditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTermsAndConditions = findViewById(R.id.tvTermsAndConditions);

        tvTermsAndConditions.setText("I agree to the Terms of Conditions & Privacy Policy.");
        List<Pair<String, View.OnClickListener>> links = new ArrayList<>();

        String stringToHyperLink1 = "Terms of Conditions";
        String stringToHyperlink2 = "Privacy Policy";

        links.add(new Pair<>(stringToHyperLink1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://143.110.191.144:3005/terms-and-conditions";
                openWebViewUrls(url, "1");
            }
        }));
        links.add(new Pair<>(stringToHyperlink2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://143.110.191.144:3005/magic-loan-privacy-policy";
                openWebViewUrls(url, "2");
            }
        }));

        makeLinks(tvTermsAndConditions, links);
    }

    public static void makeLinks(TextView textView, List<Pair<String, View.OnClickListener>> links) {
        SpannableString spannableString = new SpannableString(textView.getText().toString());
        int startIndexState = -1;

        for (Pair<String, View.OnClickListener> link : links) {
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(@NonNull View view) {
                    view.invalidate();
                    assert link.second != null;
                    link.second.onClick(view);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    // use this to change the link color
                    ds.setColor(Color.BLUE);
                    // toggle below value to enable/disable
                    // the underline shown below the clickable text
                    ds.setUnderlineText(true);
                    // use this to make text BOLD
                    ds.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                }
            };
            assert link.first != null;
            int startIndexOfLink = textView.getText().toString().indexOf(link.first, startIndexState + 1);
            spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

    private void openWebViewUrls(String url, String type) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}