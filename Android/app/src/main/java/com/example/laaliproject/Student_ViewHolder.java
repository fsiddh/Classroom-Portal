package com.example.laaliproject;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Student_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView uname, age;
    private ItemClickListener itemClickListener;

    //Setting student details in card
    public Student_ViewHolder(@NonNull View itemView) {
        super(itemView);
        uname=(TextView)itemView.findViewById(R.id.txtuaname);
        age=(TextView)itemView.findViewById(R.id.txtuage);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

    //On card click
    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}

