package com.example.owner.quizquest;

import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.HashMap;

public class Quiz {

    private HashMap<String, Answer> questions;

    //private ArrayList<String> questions;
    //private ArrayList<String> answers;

    public Quiz(){

    }

    public Quiz(ArrayList<String> questions, ArrayList<Answer> answers){
        for(String question : questions){
            this.questions.put(question, answers.get(questions.indexOf(question)));
        }
    }

    public Answer getAnswer(String question){
        Answer answer = null;
        if(questions.containsKey(question)){
            answer = questions.get(question);
        }
        return answer;
    }

    public String getCorrectAnswer(String question){
        String correctAnswer = null;
        if(questions.containsKey(question)){
            Answer answer = questions.get(question);
            correctAnswer = answer.getCorrectAnswer();
        }
        return correctAnswer;
    }

    public int getNumQuestions(){
        return this.questions.size();
    }

    public boolean removeQuestion(String question){
        if(questions.containsKey(question)){
            questions.remove(question);
            return true;
        }
        else
            return false;
    }

    public boolean changeCorrectAnswer(String question, String answer){
        if(questions.containsKey(question)){
            Answer mAnswer = questions.get(question);
            mAnswer.changeCorrectAnswer(answer);
            return true;
        }else{
            return false;
        }
    }

    public void addNewQuestion(String question, ArrayList<String> answers, String correctAnswer){
        Answer answer = new Answer(answers);
        answer.addAnswer(correctAnswer);
        questions.put(question, answer);
    }


    public boolean isCorrect(String question, String answer){
        if(questions.containsKey(question)){
            Answer mAnswer = questions.get(question);
            return mAnswer.isCorrectAnswer(answer);
        }else{
            return false;
        }
    }


}
