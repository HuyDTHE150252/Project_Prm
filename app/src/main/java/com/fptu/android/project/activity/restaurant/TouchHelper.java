package com.fptu.android.project.activity.restaurant;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.adapter.MyRestaurantAdapter;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

//import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class TouchHelper extends ItemTouchHelper.SimpleCallback {

    private MyRestaurantAdapter adapter;
    Context context;

    public TouchHelper(Context context) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.context = context;
    }

    public TouchHelper(MyRestaurantAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.LEFT){

            adapter.updateData(position);
            adapter.notifyDataSetChanged();

        }else {
            new AlertDialog.Builder(viewHolder.itemView.getContext())
                    .setMessage("Are you sure?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int arg1) {
                            adapter.deleteData(position);
                            dialog.cancel();
                        };
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int arg1) {

                            adapter.getData();

                            dialog.cancel();
                        };
                    }).show();

        }}

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeRightBackgroundColor(Color.RED)
                .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                .addSwipeLeftBackgroundColor(R.color.purple_500)
                .addSwipeLeftActionIcon(R.drawable.ic_baseline_edit_24)
                .create()
                .decorate();


        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
