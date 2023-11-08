package com.example.englishlearning.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.ItemHomelistBinding;
import com.example.englishlearning.databinding.ItemMylistBinding;
import com.example.englishlearning.model.Module;
import com.example.englishlearning.mylist.SelectListener;

import java.util.List;
import java.util.Random;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.MyViewHolder> {

    List<Module> recommendModules ;
    Context context;
    private SelectListener listener;

    public RecommendAdapter(List<Module> listModules, Context context, SelectListener listener) {
        this.recommendModules = listModules;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecommendAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHomelistBinding binding = ItemHomelistBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new RecommendAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendAdapter.MyViewHolder holder, int position) {
        Module module = recommendModules.get(position);
        holder.title.setText(module.getTitle());
        holder.description.setText(module.getDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(module,view);
            }
        });

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.menu);
                popupMenu.inflate(R.menu.popup_menu_module);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getItemId() == R.id.delete){
                            listener.onDelete(module);
                        }
                        else if(menuItem.getItemId() == R.id.edit){
                            listener.onEdit(module,view);
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        int[] images = new int[3];
        images[0] = R.drawable.bg1;
        images[1] = R.drawable.bg2;
        images[2] = R.drawable.bg3;

        int idx = new Random().nextInt(images.length);
        holder.imageView.setImageResource(images[idx]);
    }

    @Override
    public int getItemCount() {
        return recommendModules.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setModules(List<Module> modules){
        this.recommendModules = modules;
        // Toast.makeText(context,modules.size(),Toast.LENGTH_SHORT).show(); Loi o day
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        CardView cardView;
        ImageView imageView;
        ImageView menu;
        public MyViewHolder(@NonNull ItemHomelistBinding itemView) {
            super(itemView.getRoot());
            title = itemView.txtTitle;
            description = itemView.txtDescription;
            cardView = itemView.cardView;
            imageView = itemView.imgModule;
            menu = itemView.imgMenu;
        }
    }


}

