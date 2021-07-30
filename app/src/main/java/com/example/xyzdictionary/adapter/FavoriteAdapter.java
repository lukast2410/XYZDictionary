package com.example.xyzdictionary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xyzdictionary.R;
import com.example.xyzdictionary.model.Word;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MyViewHolder> {
    private ArrayList<Word> favorites;
    private FavoriteClickListener listener;

    public FavoriteAdapter(ArrayList<Word> favorites, FavoriteClickListener listener){
        this.favorites = favorites;
        this.listener = listener;
    }

    public void setFavorites(ArrayList<Word> favorites) {
        this.favorites = favorites;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private FavoriteClickListener listener;
        private TextView tvWord;
        private Button btnDelete;

        public MyViewHolder(final View view, FavoriteClickListener listener){
            super(view);
            this.listener = listener;
            tvWord = view.findViewById(R.id.tv_favorite);
            btnDelete = view.findViewById(R.id.btn_delete);
            btnDelete.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v == btnDelete){
                listener.onDeleteClicked(getAdapterPosition());
            }else{
                listener.onFavoriteClicked(getAdapterPosition());
            }
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item, parent, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvWord.setText(favorites.get(position).getWord());
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public interface FavoriteClickListener{
        void onFavoriteClicked(int position);
        void onDeleteClicked(int position);
    }
}
