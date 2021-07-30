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

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.MyViewHolder> {
    private ArrayList<Word> words;
    private WordClickListener listener;

    public WordAdapter(ArrayList<Word> words, WordClickListener listener){
        this.words = words;
        this.listener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private WordClickListener listener;
        private TextView tvWord;
        private Button btnSave;

        public MyViewHolder(final View view, WordClickListener listener){
            super(view);
            this.listener = listener;
            tvWord = view.findViewById(R.id.tv_word);
            btnSave = view.findViewById(R.id.btn_save);
            btnSave.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v == btnSave){
                listener.onSaveClicked(getAdapterPosition());
            }else{
                listener.onWordClicked(getAdapterPosition());
            }
        }
    }

    @NonNull
    @Override
    public WordAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_item, parent, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull WordAdapter.MyViewHolder holder, int position) {
        holder.tvWord.setText(words.get(position).getWord());
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    public interface WordClickListener{
        void onWordClicked(int position);
        void onSaveClicked(int position);
    }
}
