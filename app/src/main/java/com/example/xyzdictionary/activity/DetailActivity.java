package com.example.xyzdictionary.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xyzdictionary.R;
import com.example.xyzdictionary.adapter.DefinitionAdapter;
import com.example.xyzdictionary.model.Definition;
import com.example.xyzdictionary.model.Word;
import com.example.xyzdictionary.util.DBOpenHelper;

public class DetailActivity extends AppCompatActivity {
    boolean isFavorite;
    TextView tvWord;
    RecyclerView recyclerView;
    DefinitionAdapter adapter;
    Word word;
    Button btnSave, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getDataFromIntent();
        initialize();
        setting();
        settingButton();
    }

    private void settingButton() {
        btnDelete.setOnClickListener(x -> {
            if(!isFavorite) return;
            Definition.deleteDefinitions(this, String.valueOf(word.getId()));
            int count = Word.deleteFavoriteWord(this, String.valueOf(word.getId()));

            if(count > 0){
                Toast.makeText(this, "Favorite Deleted!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        btnSave.setOnClickListener(x -> {
            if(isFavorite) return;
            if(Word.favoriteWordAlreadyExists(this, word.getWord())){
                Toast.makeText(this, "Already Exists in Favorites", Toast.LENGTH_SHORT).show();
                return;
            }

            int id = (int) Word.insertFavoriteWord(this, word);
            Definition.insertWordDefinitions(this, id, word.getDefinitions());
            Toast.makeText(this, "Favorite Added!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    private void setting() {
        tvWord.setText(word.getWord());
        if (isFavorite) {
            btnSave.setVisibility(View.GONE);
        } else {
            btnDelete.setVisibility(View.GONE);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        adapter = new DefinitionAdapter(word.getDefinitions());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void initialize() {
        tvWord = findViewById(R.id.tv_word_name);
        recyclerView = findViewById(R.id.rv_definitions);
        btnSave = findViewById(R.id.btn_detail_save);
        btnDelete = findViewById(R.id.btn_detail_delete);
    }

    private void getDataFromIntent() {
        word = (Word) getIntent().getSerializableExtra(DBOpenHelper.WORD);
        isFavorite = getIntent().getBooleanExtra(DBOpenHelper.IS_FAVORITE, true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}