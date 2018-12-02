package com.example.owner.quizquest;

import java.util.ArrayList;

public class Answer {

    private ArrayList<String> answers;
    private int correctAnswerIndex;

    public Answer(ArrayList<String> answers, int correctAnswerIndex){
        this.answers = answers;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public Answer(ArrayList<String> answers){
        this.answers = answers;
    }

    public Answer(){

    }



    public String getCorrectAnswer(){
        if(!answers.isEmpty()){
            return answers.get(correctAnswerIndex);
        }else {
            return null;
        }
    }

    public boolean changeCorrectAnswer(String answer){
        if(!answers.isEmpty()){
            answers.set(correctAnswerIndex, answer);
            return true;
        }else{
            return false;
        }
    }

    public boolean changeAnswer(String previousAnswer, String answer){
        if(!answers.isEmpty()){
            if(answers.contains(previousAnswer)){
                answers.set(answers.indexOf(previousAnswer), answer);
                return true;
            }
        }
        return false;
    }

    public boolean isCorrectAnswer(String answer){
        if(!answers.isEmpty()){
            return answer.equalsIgnoreCase(answers.get(correctAnswerIndex));
        }else
            return false;

    }

    public ArrayList<String> getAnswers(){
        return this.answers;
    }

    public void addAnswer(String answer){
        answers.add(answer);
    }

    public void addCorrectAnswer(String answer){
        if(answers.contains(answer)){
            correctAnswerIndex = answers.indexOf(answer);
        }else{
            answers.add(answer);
            correctAnswerIndex = answers.indexOf(answer);
        }

    }
}
