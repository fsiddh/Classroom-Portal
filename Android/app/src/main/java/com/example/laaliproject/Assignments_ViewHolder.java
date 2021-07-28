package com.example.laaliproject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Assignments_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView aname, status;
    private ItemClickListener itemClickListener;
    public Assignments_ViewHolder(@NonNull View itemView) {
        super(itemView);
        aname=(TextView)itemView.findViewById(R.id.assdetails);
        status=(TextView)itemView.findViewById(R.id.assdue);
        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }
    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
