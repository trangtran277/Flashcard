package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashCards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashCards = flashcardDatabase.getAllCards();

        if(allFlashCards != null && allFlashCards.size() > 0)
        {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashCards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashCards.get(0).getAnswer());
        }

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.flashcard_answer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.addButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCardDisplayedIndex++;

                if(currentCardDisplayedIndex > allFlashCards.size() - 1)
                {
                    currentCardDisplayedIndex = 0;
                }

                ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashCards.get(currentCardDisplayedIndex).getQuestion());
                ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashCards.get(currentCardDisplayedIndex).getAnswer());
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data != null && requestCode == 100)
        {
            String ques = data.getExtras().getString("questString");
            String ans = data.getExtras().getString("answerString");
            ((TextView)findViewById(R.id.flashcard_question)).setText(ques);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(ans);
            flashcardDatabase.insertCard(new Flashcard(ques, ans));
            allFlashCards = flashcardDatabase.getAllCards();
        }
    }
}
