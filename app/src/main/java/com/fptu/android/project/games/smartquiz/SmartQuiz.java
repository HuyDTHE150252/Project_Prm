package com.fptu.android.project.games.smartquiz;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.fptu.android.project.R;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class SmartQuiz extends Fragment {

    private CardView quizCard;

    private TextView question;
    private TextView point;
    private TextView trialView;

    private ArrayList<Button> answers;
    private Quiz quiz;

    public SmartQuiz() {

    }


    private void bindingView(View view) {

        quizCard = view.findViewById(R.id.quiz_card);

        question = view.findViewById(R.id.quizGame_question);
        point = view.findViewById(R.id.quiz_point);
        trialView = view.findViewById(R.id.quiz_trial);
        answers = new ArrayList<>();
        answers.add(view.findViewById(R.id.quizGame_answer_1));
        answers.add(view.findViewById(R.id.quizGame_answer_2));
        answers.add(view.findViewById(R.id.quizGame_answer_3));
        answers.add(view.findViewById(R.id.quizGame_answer_4));

    }

    private static int currentQuizRight = 0;
    private static int trialCount = 5;

    @SuppressLint("ResourceAsColor")
    private void bindingAction(View view) {
        quizCard.setVisibility(View.INVISIBLE);
        renderQuestion(view);
        for (int i = 0; i < answers.size(); i++) {
            Button currentButton = answers.get(i);
            currentButton.setBackgroundColor(R.color.md_deep_orange_400);
            currentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (trialCount == 0) {
                        Toast.makeText(view.getContext(), "Good bye", Toast.LENGTH_SHORT).show();
                        getActivity().getFragmentManager().popBackStack();
                    } else {
                        if (currentQuizRight >= 0 && currentQuizRight < 10) {
                            if (currentButton.getText().equals(quiz.getCorrect_answer())) {
                                currentQuizRight++;
                                renderQuestion(view);
                            } else {
                                currentButton.setVisibility(View.INVISIBLE);
                                trialCount--;
                                if (trialCount == 0) {
                                    Toast.makeText(view.getContext(), "You are out of trial", Toast.LENGTH_SHORT).show();
                                }
                            }
                            trialView.setText("Trial: " + trialCount + "/5");
                            point.setText("Point: " + currentQuizRight + "/10");
                        } else {
                            getActivity().getFragmentManager().popBackStack();
                            Toast.makeText(view.getContext(), "Good job!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    private static Retrofit retrofit;
    private static IMyQuizApi myQuizApi;

    private void renderQuestion(View view) {

        for (int i = 0; i < answers.size(); i++) {
            Button currentButton = answers.get(i);
            currentButton.setVisibility(View.VISIBLE);
        }
        final String BASE_URL = "https://opentdb.com/";
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            myQuizApi = retrofit.create(IMyQuizApi.class);
        }

        myQuizApi.getQuizzes("1", "9", "easy", "multiple")
                .enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        LinkedTreeMap<Object, Object> data = (LinkedTreeMap<Object, Object>) response.body();
                        Gson gson = new Gson();
                        String json = gson.toJson(data.get("results"));
                        ArrayList<Quiz> output = new Gson().fromJson(json, new TypeToken<ArrayList<Quiz>>() {
                        }.getType());
                        //update quiz
                        quiz = output.get(0);
                        question.setText(quiz.getQuestion());

                        ArrayList<String> allAnswer = quiz.getIncorrect_answers();
                        allAnswer.add(quiz.getCorrect_answer());

                        Collections.shuffle(allAnswer);
                        for (int i = 0; i < answers.size(); i++) {
                            Button currentButton = answers.get(i);
                            currentButton.setText(allAnswer.get(i));
                        }
                        quizCard.setVisibility(View.VISIBLE);
                        System.out.println("Current quiz: " + quiz);
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        System.out.println("Fail and do not know why: " + t.getMessage());
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_smart_quizz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            bindingView(view);
            bindingAction(view);
        }
    }
}

interface IMyQuizApi {
    @GET("api.php")
    Call<Object> getQuizzes(@Query("amount") String amount, @Query("category") String category, @Query("difficulty") String difficulty, @Query("type") String type);
}

class Quiz {
    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correct_answer;
    private ArrayList<String> incorrect_answers;


    public Quiz() {

    }

    public Quiz(String category, String type, String difficulty, String question, String correct_answer, ArrayList<String> incorrect_answers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correct_answer = correct_answer;
        this.incorrect_answers = incorrect_answers;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public ArrayList<String> getIncorrect_answers() {
        return incorrect_answers;
    }

    public void setIncorrect_answers(ArrayList<String> incorrect_answers) {
        this.incorrect_answers = incorrect_answers;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", question='" + question + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", incorrect_answers=" + incorrect_answers +
                '}';
    }
}