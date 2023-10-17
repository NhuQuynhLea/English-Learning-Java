package com.example.englishlearning.mylist;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishlearning.R;
import com.example.englishlearning.databinding.ItemMylistBinding;
import com.example.englishlearning.flashcard.FlashCardFragment;
import com.example.englishlearning.model.Module;

import java.util.List;

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
        holder.description.setText(module.getDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(module,view);
//                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_flashCardFragment);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("module",module); // Put anything what you want
//
//                FlashCardFragment flashCardFragment = new FlashCardFragment();
//                flashCardFragment.setArguments(bundle);
            }
        });

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
        TextView description;
        CardView cardView;
        public MyViewHolder(@NonNull ItemMylistBinding itemView) {
            super(itemView.getRoot());
            title = itemView.txtTitle;
            description = itemView.txtDescription;
            cardView = itemView.cardView;
        }
    }


}
