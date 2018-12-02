package com.example.owner.quizquest;

import android.widget.PopupWindow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Quiz implements Serializable {
    private ArrayList<String> questionsText;
    private HashMap<String, Answer> questions;
    private ArrayList<String> questionsAnswered;

    //private ArrayList<String> questions;
    //private ArrayList<String> answers;

    public Quiz(){

    }

    public Quiz(ArrayList<String> questions, ArrayList<Answer> answers){
        for(String question : questions){
            this.questions.put(question, answers.get(questions.indexOf(question)));
        }
    }

    public Answer getAnswerChoices(String question){
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

    public boolean addAnswerChoice(String question, String answer){
        if(questions.containsKey(question)){
            Answer mAnswer = questions.get(question);
            mAnswer.addAnswer(answer);
            questions.put(question, mAnswer);
            return true;
        }else
            return false;
    }

    public String getNextQuestion(){
        boolean found = false;
        String question = null;
        while(!found){
            int rand = (int)(Math.random() * questionsText.size());
            question = questionsText.get(rand);
            if(!questionsAnswered.contains(question)){
                found = true;
            }
        }

        return question;
    }


}
