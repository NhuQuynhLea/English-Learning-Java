package com.example.englishlearning.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.ItemHomelistBinding;
import com.example.englishlearning.databinding.ItemRevisionlistBinding;
import com.example.englishlearning.model.Module;
import com.example.englishlearning.mylist.SelectListener;

import java.util.List;
import java.util.Objects;
import java.util.Random;


public class RevisionAdapter extends RecyclerView.Adapter<RevisionAdapter.MyViewHolder> {

    List<Module> revisionModules ;
    Context context;
    private SelectListener listener;

    public RevisionAdapter(List<Module> listModules, Context context, SelectListener listener) {
        this.revisionModules = listModules;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RevisionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRevisionlistBinding binding = ItemRevisionlistBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new RevisionAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RevisionAdapter.MyViewHolder holder, int position) {
        Module module = revisionModules.get(position);
        holder.title.setText(module.getTitle());

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
        if(!Objects.equals(module.getImage(), "")){
            Uri imgUri= Uri.parse(module.getImage());
            holder.imageView.setImageURI(imgUri);
        }else {
            int[] images = new int[3];
            images[0] = R.drawable.bg1;
            images[1] = R.drawable.bg2;
            images[2] = R.drawable.bg3;

            int idx = new Random().nextInt(images.length);
            holder.imageView.setImageResource(images[idx]);
        }


        holder.progressBar.setProgress(module.getScore());
        holder.progressBar.setMax(100);
    }

    @Override
    public int getItemCount() {
        return revisionModules.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setModules(List<Module> modules){
        this.revisionModules = modules;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title;

        CardView cardView;
        ImageView imageView;
        ImageView menu;
        ProgressBar progressBar;
        public MyViewHolder(@NonNull ItemRevisionlistBinding itemView) {
            super(itemView.getRoot());
            title = itemView.txtTitle;

            cardView = itemView.cardView;
            imageView = itemView.imgModule;
            menu = itemView.imgMenu;
            progressBar = itemView.progressBar;
        }
    }


}

