package com.example.xyzdictionary.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.xyzdictionary.R;
import com.example.xyzdictionary.activity.DetailActivity;
import com.example.xyzdictionary.adapter.WordAdapter;
import com.example.xyzdictionary.interfaces.Observable;
import com.example.xyzdictionary.interfaces.Observer;
import com.example.xyzdictionary.model.Definition;
import com.example.xyzdictionary.model.Word;
import com.example.xyzdictionary.util.DBOpenHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ExploreFragment extends Fragment implements WordAdapter.WordClickListener, Observable {
    ArrayList<Word> listWord;
    ArrayList<Observer> observers = new ArrayList<>();
    RecyclerView rvWord;
    WordAdapter adapter;
    Button btnSearch;
    EditText etSearch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize();
        getWords("");
        settingSearch();
    }

    private void settingSearch() {
        btnSearch.setOnClickListener(x -> {
            String search = etSearch.getText().toString();
            getWords(search.trim());
        });
    }

    private void getWords(String search) {
        final String url = "https://myawesomedictionary.herokuapp.com/words?q=" + search;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonReq = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            try {
                listWord.clear();
                for(int i=0;i<response.length();i++){
                    JSONObject jsonObj = response.getJSONObject(i);
                    JSONArray array = jsonObj.getJSONArray("definitions");
                    ArrayList<Definition> definitions = new ArrayList<>();
                    for(int j=0;j<array.length();j++){
                        JSONObject def = array.getJSONObject(j);
                        definitions.add(new Definition(
                                0,
                                0,
                                def.getString("image_url"),
                                def.getString("type"),
                                def.getString("definition")
                        ));
                    }
                    Word word = new Word(
                            0,
                            jsonObj.getString("word"),
                            definitions
                    );
                    listWord.add(word);
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Log.d("Error", error.toString()));

        requestQueue.add(jsonReq);
    }

    private void initialize() {
        listWord = new ArrayList<>();
        rvWord = getView().findViewById(R.id.rv_words);
        adapter = new WordAdapter(listWord, this);
        rvWord.setLayoutManager(new LinearLayoutManager(getContext()));
        rvWord.setItemAnimator(new DefaultItemAnimator());
        rvWord.setAdapter(adapter);

        etSearch = getView().findViewById(R.id.et_search);
        btnSearch = getView().findViewById(R.id.btn_search);
    }

    @Override
    public void onWordClicked(int position) {
        Word word = listWord.get(position);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(DBOpenHelper.WORD, word);
        intent.putExtra(DBOpenHelper.IS_FAVORITE, false);
        startActivity(intent);
    }

    @Override
    public void onSaveClicked(int position) {
        Word word = listWord.get(position);
        if(Word.favoriteWordAlreadyExists(getContext(), word.getWord())){
            Toast.makeText(getContext(), "Already Exists in Favorites", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = (int) Word.insertFavoriteWord(getContext(), word);
        Definition.insertWordDefinitions(getContext(), id, word.getDefinitions());
        Toast.makeText(getContext(), "Favorite Added!", Toast.LENGTH_SHORT).show();
        notifyObservers();
    }

    @Override
    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers){
            observer.update();
        }
    }
}