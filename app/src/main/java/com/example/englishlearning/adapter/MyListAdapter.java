package com.example.englishlearning.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishlearning.databinding.ItemMylistBinding;
import com.example.englishlearning.model.Module;

import java.util.ArrayList;
import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder> {

     List<Module> listModules ;
    Context context;

    public MyListAdapter(List<Module> listModules, Context context) {
        this.listModules = listModules;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMylistBinding binding = ItemMylistBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Module module = listModules.get(position);
        holder.title.setText(module.getTitle());
        holder.description.setText(module.getDescription());
    }

    @Override
    public int getItemCount() {
        return listModules.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setModules(List<Module> modules){
        this.listModules = modules;
        Toast.makeText(context,modules.size(),Toast.LENGTH_SHORT).show();
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        public MyViewHolder(@NonNull ItemMylistBinding itemView) {
            super(itemView.getRoot());
            title = itemView.txtTitle;
            description = itemView.txtDescription;

        }
    }
}
