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
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


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

                AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.DialogTheme);
                dialog.setTitle("Add new item");
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final TextInputEditText item_id = new TextInputEditText(context);
                item_id.setHint("Item ID");
                item_id.setInputType(InputType.TYPE_CLASS_NUMBER);
                final TextInputLayout idl = new TextInputLayout(context);
                idl.setPadding(20, 20, 20, 20);
                idl.addView(item_id);
                layout.addView(idl);

                final TextInputEditText item_name = new TextInputEditText(context);
                item_name.setHint("Item name");
                item_name.setSingleLine();
                final TextInputLayout namel = new TextInputLayout(context);
                namel.setPadding(20, 20, 20, 20);
                namel.addView(item_name);
                layout.addView(namel);

                final TextInputEditText item_qty = new TextInputEditText(context);
                item_qty.setHint("Item quantities");
                item_qty.setInputType(InputType.TYPE_CLASS_NUMBER);
                final TextInputLayout qtyl = new TextInputLayout(context);
                qtyl.setPadding(20, 20, 20, 20);
                qtyl.addView(item_qty);
                layout.addView(qtyl);

                final TextInputEditText item_desc = new TextInputEditText(context);
                item_desc.setHint("Item description (optional)");
                item_desc.setSingleLine();
                final TextInputLayout descl = new TextInputLayout(context);
                descl.setPadding(20, 20, 20, 20);
                descl.addView(item_desc);
                layout.addView(descl);

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