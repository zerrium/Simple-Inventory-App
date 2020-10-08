package com.zerrium.uts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button add = (Button) findViewById(R.id.buttonAdd);
        add.setOnClickListener(this);
        Button list = (Button) findViewById(R.id.buttonList);
        list.setOnClickListener(this);
        Button logout = (Button) findViewById(R.id.buttonLogout);
        logout.setOnClickListener(this);
        Button help = (Button) findViewById(R.id.buttonHelp);
        help.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.buttonAdd:
                final Context context = view.getContext();

                AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.DialogTheme);
                dialog.setTitle("Add new item");

                final ScrollView sc = new ScrollView(context);
                sc.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);

                final LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final TextInputEditText item_id = new TextInputEditText(context);
                item_id.setHint("Item ID");
                item_id.setInputType(InputType.TYPE_CLASS_NUMBER);
                item_id.setTextColor(Color.WHITE);
                item_id.setHintTextColor(ColorStateList.valueOf(Color.argb(127, 255, 255, 255)));
                final TextInputLayout idl = new TextInputLayout(context);
                idl.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                idl.setPadding(20, 20, 20, 20);
                idl.addView(item_id);
                layout.addView(idl);

                final TextInputEditText item_name = new TextInputEditText(context);
                item_name.setHint("Item name");
                item_name.setSingleLine();
                item_name.setTextColor(Color.WHITE);
                item_name.setHintTextColor(ColorStateList.valueOf(Color.argb(127, 255, 255, 255)));
                final TextInputLayout namel = new TextInputLayout(context);
                namel.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                namel.setPadding(20, 20, 20, 20);
                namel.addView(item_name);
                layout.addView(namel);

                final TextInputEditText item_qty = new TextInputEditText(context);
                item_qty.setHint("Item quantities");
                item_qty.setInputType(InputType.TYPE_CLASS_NUMBER);
                item_qty.setTextColor(Color.WHITE);
                item_qty.setHintTextColor(ColorStateList.valueOf(Color.argb(127, 255, 255, 255)));
                final TextInputLayout qtyl = new TextInputLayout(context);
                qtyl.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                qtyl.setPadding(20, 20, 20, 20);
                qtyl.addView(item_qty);
                layout.addView(qtyl);

                final TextInputEditText item_desc = new TextInputEditText(context);
                item_desc.setHint("Item description (optional)");
                item_desc.setSingleLine();
                item_desc.setTextColor(Color.WHITE);
                item_desc.setHintTextColor(ColorStateList.valueOf(Color.argb(127, 255, 255, 255)));
                final TextInputLayout descl = new TextInputLayout(context);
                descl.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                descl.setPadding(20, 20, 20, 20);
                descl.addView(item_desc);
                layout.addView(descl);

                sc.addView(layout);
                dialog.setView(sc);
                dialog.setNegativeButton("Cancel", null);
                dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String id = Objects.requireNonNull(item_id.getText()).toString();
                        final String name = Objects.requireNonNull(item_name.getText()).toString();
                        final String qty = Objects.requireNonNull(item_qty.getText()).toString();
                        final String desc = Objects.requireNonNull(item_desc.getText()).toString();

                        if (id.isEmpty()) {
                            Snackbar.make(view, "Please enter item ID!", Snackbar.LENGTH_LONG).show();
                        } else if (name.isEmpty()) {
                            Snackbar.make(view, "Please enter item name!", Snackbar.LENGTH_LONG).show();
                        } else if (qty.isEmpty()) {
                            Snackbar.make(view, "Please enter item QTY!", Snackbar.LENGTH_LONG).show();
                        } else {
                            SqliteHelper helper = new SqliteHelper(context);
                            try {
                                helper.addItem(new Item(Integer.parseInt(id), name, Integer.parseInt(qty), desc));
                                Snackbar.make(view, "Added new item to database", Snackbar.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Snackbar.make(view, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                dialog.setCancelable(false);
                dialog.create().show();
                break;

            case R.id.buttonList:
                startActivity(new Intent(HomeActivity.this, ListActivity.class));
                break;

            case R.id.buttonLogout:
                finish();
                break;

            case R.id.buttonHelp:
                startActivity(new Intent(HomeActivity.this, HelpActivity.class));
                break;
        }
    }
}