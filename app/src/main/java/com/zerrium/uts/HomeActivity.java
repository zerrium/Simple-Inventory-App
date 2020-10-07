package com.zerrium.uts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button add, list, logout, help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        add = (Button) findViewById(R.id.buttonAdd);
        add.setOnClickListener(this);
        list = (Button) findViewById(R.id.buttonList);
        list.setOnClickListener(this);
        logout = (Button) findViewById(R.id.buttonLogout);
        logout.setOnClickListener(this);
        help = (Button) findViewById(R.id.buttonHelp);
        help.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonAdd:
                //startActivity(HomeActivity.this, AddActivity.class);
                break;

            case R.id.buttonList:
                //startActivity(HomeActivity.this, ListActivity.class);
                break;

            case R.id.buttonLogout:
                finish();
                break;

            case R.id.buttonHelp:
                //startActivity(HomeActivity.this, HelpActivity.class);
                break;
        }
    }
}