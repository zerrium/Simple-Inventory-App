package com.zerrium.uts;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<Item> dataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected TextView textView1, textView2, textView3, textView4;
        public ViewHolder(View v) {
            super(v);
            textView1 = (TextView) v.findViewById(R.id.textViewRow1);
            textView2 = (TextView) v.findViewById(R.id.textViewRow2);
            textView3 = (TextView) v.findViewById(R.id.textViewRow3);
            textView4 = (TextView) v.findViewById(R.id.textViewRow4);
        }
    }

    public ListAdapter(ArrayList<Item> dataset){
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Item it = dataset.get(position);
        holder.textView1.setText(String.valueOf(it.getId()));
        holder.textView2.setText(it.getName());
        holder.textView3.setText(String.valueOf(it.getQty()));
        holder.textView4.setText(it.getDesc().isEmpty() ? "No description" : "Desc : " + it.getDesc());
        View.OnClickListener vocl = new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final Context context = view.getContext();

                AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.DialogTheme);
                dialog.setTitle("Edit item");
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final TextInputEditText item_id = new TextInputEditText(context);
                item_id.setHint("Item ID");
                item_id.setInputType(InputType.TYPE_CLASS_NUMBER);
                item_id.setTextColor(Color.WHITE);
                item_id.setText(String.valueOf(it.getId()));
                item_id.setEnabled(false);
                final TextInputLayout idl = new TextInputLayout(context);
                idl.setPadding(20, 20, 20, 20);
                idl.addView(item_id);
                layout.addView(idl);

                final TextInputEditText item_name = new TextInputEditText(context);
                item_name.setHint("Item name");
                item_name.setSingleLine();
                item_name.setTextColor(Color.WHITE);
                item_name.setText(it.getName());
                final TextInputLayout namel = new TextInputLayout(context);
                namel.setPadding(20, 20, 20, 20);
                namel.addView(item_name);
                layout.addView(namel);

                final TextInputEditText item_qty = new TextInputEditText(context);
                item_qty.setHint("Item quantities");
                item_qty.setInputType(InputType.TYPE_CLASS_NUMBER);
                item_qty.setTextColor(Color.WHITE);
                item_qty.setText(String.valueOf(it.getQty()));
                final TextInputLayout qtyl = new TextInputLayout(context);
                qtyl.setPadding(20, 20, 20, 20);
                qtyl.addView(item_qty);
                layout.addView(qtyl);

                final TextInputEditText item_desc = new TextInputEditText(context);
                item_desc.setHint("Item description (optional)");
                item_desc.setSingleLine();
                item_desc.setTextColor(Color.WHITE);
                item_desc.setText(it.getDesc());
                final TextInputLayout descl = new TextInputLayout(context);
                descl.setPadding(20, 20, 20, 20);
                descl.addView(item_desc);
                layout.addView(descl);

                dialog.setView(layout);
                dialog.setNegativeButton("Cancel", null);
                dialog.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            ListActivity.deleteList(it);
                            Snackbar.make(view, "Deleted item from database", Snackbar.LENGTH_SHORT).show();
                        } catch (SQLiteException e) {
                            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                        } catch (Exception e){
                            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
                dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String id = item_id.getText().toString();
                        final String name = item_name.getText().toString();
                        final String qty = item_qty.getText().toString();
                        final String desc = item_desc.getText().toString();

                        if (name.isEmpty()) {
                            Snackbar.make(view, "Please enter item name!", Snackbar.LENGTH_LONG).show();
                        } else if (qty.isEmpty()) {
                            Snackbar.make(view, "Please enter item QTY!", Snackbar.LENGTH_LONG).show();
                        } else {
                            Item ite = new Item(Integer.parseInt(id), name, Integer.parseInt(qty), desc);
                            try {
                                ListActivity.updateList(ite, it);
                                Snackbar.make(view, "Updated item to database", Snackbar.LENGTH_SHORT).show();
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
        };

        holder.textView1.setOnClickListener(vocl);
        holder.textView2.setOnClickListener(vocl);
        holder.textView3.setOnClickListener(vocl);
        holder.textView4.setOnClickListener(vocl);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
