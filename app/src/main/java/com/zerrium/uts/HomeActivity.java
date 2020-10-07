package com.zerrium.uts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.concurrent.atomic.AtomicBoolean;

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
    public void onClick(final View view) {
        switch (view.getId()){
            case R.id.buttonAdd:
                final Context context = view.getContext();

                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Add new item");
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText item_id = new EditText(context);
                item_id.setHint("Item ID");
                item_id.setInputType(InputType.TYPE_CLASS_NUMBER);
                layout.addView(item_id);

                final EditText item_name = new EditText(context);
                item_name.setHint("Item name");
                item_name.setSingleLine();
                layout.addView(item_name);

                final EditText item_qty = new EditText(context);
                item_qty.setHint("Item quantities");
                item_qty.setInputType(InputType.TYPE_CLASS_NUMBER);
                layout.addView(item_qty);

                final EditText item_desc = new EditText(context);
                item_desc.setHint("Item description (optional)");
                item_desc.setSingleLine();
                layout.addView(item_desc);

                dialog.setView(layout);
                dialog.setNegativeButton("Cancel", null);
                dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String id = item_id.getText().toString();
                        final String name = item_name.getText().toString();
                        final String qty = item_qty.getText().toString();
                        final String desc = item_desc.getText().toString();

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
                            } catch (SQLiteException e) {
                                Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                            } catch (Exception e){
                                Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
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
                //startActivity(new Intent(HomeActivity.this, HelpActivity.class));
                break;
        }
    }
}