package com.example.xyzdictionary.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xyzdictionary.R;
import com.example.xyzdictionary.model.Definition;
import com.example.xyzdictionary.model.Word;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionAdapter.MyViewHolder> {
    private ArrayList<Definition> definitions;

    public DefinitionAdapter(ArrayList<Definition> definitions){
        this.definitions = definitions;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvType, tvDefinition;
        private ImageView ivNoImage, ivImage;

        public MyViewHolder(final View view){
            super(view);
            tvType = view.findViewById(R.id.tv_definition);
            tvDefinition = view.findViewById(R.id.tv_def_detail);
            ivImage = view.findViewById(R.id.iv_image);
            ivNoImage = view.findViewById(R.id.iv_no_image);
        }
    }

    @NonNull
    @Override
    public DefinitionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.definition_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String type = "Type: " + definitions.get(position).getType();
        holder.tvType.setText(type);
        holder.tvDefinition.setText(definitions.get(position).getDefinition());
        if(definitions.get(position).getImageUrl() == null || definitions.get(position).getImageUrl().equals("null")){
            holder.ivImage.setVisibility(View.GONE);
        }else{
            holder.ivNoImage.setVisibility(View.GONE);
            Picasso.get().load(definitions.get(position).getImageUrl()).into(holder.ivImage);
        }
    }

    @Override
    public int getItemCount() {
        return definitions.size();
    }
}
