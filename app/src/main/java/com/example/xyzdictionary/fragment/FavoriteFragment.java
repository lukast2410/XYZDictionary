package com.example.xyzdictionary.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.xyzdictionary.R;
import com.example.xyzdictionary.activity.DetailActivity;
import com.example.xyzdictionary.adapter.FavoriteAdapter;
import com.example.xyzdictionary.interfaces.Observable;
import com.example.xyzdictionary.interfaces.Observer;
import com.example.xyzdictionary.model.Definition;
import com.example.xyzdictionary.model.Word;
import com.example.xyzdictionary.util.DBOpenHelper;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements FavoriteAdapter.FavoriteClickListener, Observer {
    ArrayList<Word> favorites;
    RecyclerView rvFavorites;
    FavoriteAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeFavorites();

    }

    private void initializeFavorites() {
        favorites = Word.getMyFavoriteWords(getContext());
        adapter = new FavoriteAdapter(favorites, this);
        rvFavorites = getView().findViewById(R.id.rv_favorites);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavorites.setItemAnimator(new DefaultItemAnimator());
        rvFavorites.setAdapter(adapter);
    }

    @Override
    public void onFavoriteClicked(int position) {
        Word word = favorites.get(position);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DBOpenHelper.WORD, word);
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(int position) {
        Word word = favorites.get(position);
        Definition.deleteDefinitions(getContext(), String.valueOf(word.getId()));
        int count = Word.deleteFavoriteWord(getContext(), String.valueOf(word.getId()));

        if(count > 0){
            Toast.makeText(getContext(), "Favorite Deleted!", Toast.LENGTH_SHORT).show();
            favorites.remove(position);
            adapter.notifyItemRemoved(position);
        }
    }

    @Override
    public void update() {
        favorites = Word.getMyFavoriteWords(getContext());
        adapter.setFavorites(favorites);
        adapter.notifyDataSetChanged();
    }
}