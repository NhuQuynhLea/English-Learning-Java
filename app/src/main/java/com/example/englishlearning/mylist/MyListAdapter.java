package com.example.englishlearning.mylist;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.englishlearning.R;
import com.example.englishlearning.databinding.ItemMylistBinding;
import com.example.englishlearning.flashcard.FlashCardFragment;
import com.example.englishlearning.model.Module;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder> {

     List<Module> listModules ;
    Context context;
    private SelectListener listener;

    public MyListAdapter(List<Module> listModules, Context context, SelectListener listener) {
        this.listModules = listModules;
        this.context = context;
        this.listener = listener;
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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(module,view);
            }
        });
        if(!Objects.equals(module.getImage(), "")){
            Uri imgUri= Uri.parse(module.getImage());
            holder.imageView.setImageURI(imgUri);
        }else{
            int[] images = new int[4];
            images[0] = R.drawable.bg1;
            images[1] = R.drawable.bg2;
            images[2] = R.drawable.bg3;
            images[3] = R.drawable.bg4;
            int idx = new Random().nextInt(images.length);
            holder.imageView.setImageResource(images[idx]);
        }

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
        holder.progressBar.setProgress(module.getScore());
        holder.progressBar.setMax(100);

    }

    @Override
    public int getItemCount() {
        return listModules.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setModules(List<Module> modules){
        this.listModules = modules;
       // Toast.makeText(context,modules.size(),Toast.LENGTH_SHORT).show(); Loi o day
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        CardView cardView;
        ImageView imageView;
        ImageView menu;
        ProgressBar progressBar;
        public MyViewHolder(@NonNull ItemMylistBinding itemView) {
            super(itemView.getRoot());
            title = itemView.txtTitle;
            cardView = itemView.cardView;
            imageView = itemView.imgModule;
            menu = itemView.imgMenu;
            progressBar = itemView.progressBar;
        }
    }


}
