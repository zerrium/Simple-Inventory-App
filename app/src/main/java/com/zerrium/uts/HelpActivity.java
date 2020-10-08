package com.zerrium.uts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class HelpActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonGithub, buttonHotline, buttonEmail, buttonReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonGithub = (Button) findViewById(R.id.buttonGithub);
        buttonHotline = (Button) findViewById(R.id.buttonHotline);
        buttonEmail = (Button) findViewById(R.id.buttonEmail);
        buttonReport = (Button) findViewById(R.id.buttonReport);
    }

    @SuppressLint("ShowToast")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonGithub:
                Intent h = new Intent(Intent.ACTION_VIEW);
                h.setData(Uri.parse("https://github.com/zerrium/Simple-Inventory-App"));
                startActivity(h);
                break;

            case R.id.buttonHotline:
                String no = "081423532675";
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.fromParts("tel", no, null));
                startActivity(i);
                break;

            case R.id.buttonEmail:
                Intent j = new Intent(Intent.ACTION_SEND);
                j.setType("text/plain");
                j.putExtra(Intent.EXTRA_EMAIL, new String[]{"test@mail.com"});
                j.putExtra(Intent.EXTRA_SUBJECT, "Inventory App - Help");
                j.putExtra(Intent.EXTRA_TEXT, "Hello there, I have problem...");

                try{
                    startActivity(Intent.createChooser(j, "Choose..."));
                } catch (Exception e) {
                    Toast.makeText(HelpActivity.this, e.getMessage(), Toast.LENGTH_LONG);
                }
                break;

            case R.id.buttonReport:
                Intent k = new Intent(Intent.ACTION_VIEW);
                k.setData(Uri.parse("https://github.com/zerrium/Simple-Inventory-App/issues"));
                startActivity(k);
                break;
        }
    }
}