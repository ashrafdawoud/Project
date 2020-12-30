package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    ArrayList<String> fname=new ArrayList<>();
    ArrayList<String>bname=new ArrayList<>();
    ArrayList<String>itemid=new ArrayList<>();
    CallbackInterface callbackInterface;
    Context context;
    public Adapter(ArrayList<String> fname, ArrayList<String> bname, ArrayList<String> itemid,CallbackInterface callbackInterface,Context context) {
        this.fname = fname;
        this.bname = bname;
        this.itemid = itemid;
        this. callbackInterface=callbackInterface;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fname.setText(fname.get(position));
        holder.lname.setText(bname.get(position));
        holder.delete.setOnClickListener(v->{
            callbackInterface.onclick(itemid.get(position));
        });
        holder.edit.setOnClickListener(v->{
            Intent intent=new Intent(context,EditActivity.class);
            intent.putExtra("id",itemid.get(position));
            intent.putExtra("fname",fname.get(position));
            intent.putExtra("lname",bname.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return fname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView fname,lname,delete,edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fname=itemView.findViewById(R.id.fname);
            lname=itemView.findViewById(R.id.lname);
            delete=itemView.findViewById(R.id.delete);
            edit=itemView.findViewById(R.id.edit);
        }
    }
    interface CallbackInterface{
        void onclick(String id);
    }
}
