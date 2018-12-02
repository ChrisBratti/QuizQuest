package com.example.owner.quizquest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {
    private int currentQuesiton;
    private int numQuestions;
    private Quiz quiz;

    TextView questionNumber;
    TextView timeRemaining;
    TextView questionText;
    RadioGroup answerChoices;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionNumber = findViewById(R.id.tvQuestionNum);
        timeRemaining = findViewById(R.id.tvTimeRemaining);
        questionText = findViewById(R.id.tvQuestionText);
        answerChoices = findViewById(R.id.rgAnswerChoices);

        Intent intent = getIntent();
        quiz = (Quiz) intent.getSerializableExtra("quiz");
        numQuestions = quiz.getNumQuestions();

        displayQuestion();

    }

    private void displayQuestion(){
        if(currentQuesiton >= numQuestions){
            return;
        }
        String question = quiz.getNextQuestion();
        questionText.setText(question);
        Answer choices = quiz.getAnswerChoices(question);

        for(String s : choices.getAnswers()){
            RadioButton rb = new RadioButton(getApplicationContext());
            rb.setText(s);
            answerChoices.addView(rb);
        }

    }
}
