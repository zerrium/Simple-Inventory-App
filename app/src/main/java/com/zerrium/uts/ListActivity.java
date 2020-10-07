package com.zerrium.uts;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SqliteHelper sqlhelper;
    private Item[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sqlhelper = new SqliteHelper(getApplicationContext());
        ArrayList<Item> a = sqlhelper.getItem();
        items = new Item[a.size()];
        for(int i=0; i<items.length; i++){
            items[i] = a.get(i);
            Log.d("Items", items[i].getId() + " - " + items[i].getName());
        }
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(items);
        recyclerView.setAdapter(mAdapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
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
            }
        });
    }
}