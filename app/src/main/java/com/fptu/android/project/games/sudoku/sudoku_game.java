package com.fptu.android.project.games.sudoku;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class sudoku_game extends Fragment {
    private RecyclerView sudokuLayout;
    private SudokuAdapter adapter;

    private ProgressBar progressBar;

    private TextView currentPoint;
    private TextView level;
    private TextView loadingPercent;

    private Button newBoard;
    private Button eraseBoard;
    private Button hint;

    FirebaseFirestore db;

    public sudoku_game() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void bindingView(View view) {
        sudokuLayout = view.findViewById(R.id.sudoku_board);

        progressBar = view.findViewById(R.id.sudoku_loadingBoard_progressbar);


        loadingPercent = view.findViewById(R.id.sudoku_loadingBoard_percentage);

        currentPoint = view.findViewById(R.id.sudoku_current_point);
        level = view.findViewById(R.id.sudoku_level);

        newBoard = view.findViewById(R.id.sudoku_new_board);
        eraseBoard = view.findViewById(R.id.sudoku_erase_board);
        hint = view.findViewById(R.id.sudoku_hint);


    }

    private void bindingAction() {
        newBoard.setOnClickListener(this::getNewBoard);
        eraseBoard.setOnClickListener(this::doErase);
        hint.setOnClickListener(this::getHint);
    }

    private void getHint(View view) {
    }

    private void doErase(View view) {
    }

    private void getNewBoard(View view) {
        new SudokuBoard().execute(level.getText().toString().replace("Level: ", "").toLowerCase());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (view != null) {
            bindingView(view);
            bindingAction();
            bindingData(view);
        }
    }

    private void bindingData(View view) {
        new SudokuBoard().execute("easy");
        adapter = new SudokuAdapter();
        sudokuLayout.setAdapter(adapter);
        sudokuLayout.setLayoutManager(new GridLayoutManager(view.getContext(), SudokuBoard.GRID_SIZE));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sudoku_game, container, false);
    }

    public interface IMySudokuApi {
        @GET("/board")
        Call<Object> generateSudokuBoard(@Query("difficulty") String level);
    }

    class SudokuBoard extends AsyncTask<String, Integer, ArrayList<Integer>> {
        public static final int GRID_SIZE = 3;
        private ArrayList<Integer> originBoard = null;
        private ArrayList<Integer> solvedBoard = null;
        private static final String BASE_URL = "https://sugoku.herokuapp.com/";
        private int[][] tempData =
                {
                        {8, 0, 0, 0, 3, 0, 0, 0, 0},
                        {0, 3, 5, 0, 0, 0, 9, 1, 8},
                        {2, 0, 7, 0, 9, 0, 3, 6, 0},
                        {0, 0, 0, 7, 5, 1, 8, 0, 0},
                        {0, 5, 0, 9, 0, 8, 0, 7, 1},
                        {1, 0, 0, 2, 0, 3, 0, 0, 5},
                        {0, 0, 0, 4, 0, 0, 0, 0, 6},
                        {6, 0, 0, 3, 8, 0, 5, 4, 0},
                        {0, 8, 4, 0, 1, 6, 2, 0, 0}
                };

        public SudokuBoard() {
        }

        @Override
        protected ArrayList<Integer> doInBackground(String... strings) {
            return generateGameData(strings[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sudokuLayout.setVisibility(View.GONE);
            progressBar.setProgress(0);
            loadingPercent.setText(0 + "/100");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int value = values[0];
            progressBar.setProgress(value);
            loadingPercent.setText(value + "/100");
        }

        @Override
        protected void onPostExecute(ArrayList<Integer> integers) {
            super.onPostExecute(integers);
            sudokuLayout.setVisibility(View.VISIBLE);
            adapter.setOriginBoard(integers);
            adapter.setSolvedBoard(solvedBoard);
            System.out.println("data size: " + integers.size());
            for (int i = 0; i < integers.size(); i++) {
                System.out.println("Position: " + i + ", value: " + integers.get(i));
            }
        }

        private ArrayList<Integer> generateGameData(String difficulty) {
            level.setText("Level: " + difficulty.toUpperCase());

            originBoard = new ArrayList<>();
            solvedBoard = new ArrayList<>();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            IMySudokuApi mySudokuApi = retrofit.create(IMySudokuApi.class);
            System.out.println("Running");
            mySudokuApi.generateSudokuBoard(difficulty).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    LinkedTreeMap<String, ArrayList<ArrayList<Double>>> data = (LinkedTreeMap<String, ArrayList<ArrayList<Double>>>) response.body();
                    System.out.println("fetched data: " + data.toString());

                    ArrayList<ArrayList<Double>> boardRes = ((LinkedTreeMap<String, ArrayList<ArrayList<Double>>>) response.body()).get("board");
                    int count = 0;
                    for (int i = 0; i < boardRes.size(); i++) {
                        for (int j = 0; j < boardRes.get(i).size(); j++) {
                            count++;
                            publishProgress((count * 100 / 81));
                            // System.out.println("Position: " + i + ", " + j + "; value: " + board.get(i).get(j).intValue());
                            originBoard.add(boardRes.get(i).get(j).intValue());
                        }
                    }
                    if (solveBoard(boardRes)) {
                        for (int i = 0; i < boardRes.size(); i++) {
                            for (int j = 0; j < boardRes.get(i).size(); j++) {
                                solvedBoard.add(boardRes.get(i).get(j).intValue());
                            }
                        }
                    }

                    System.out.println("Sovled: " + boardRes.toString());
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    generateGameData(difficulty);
                }
            });
            return originBoard;
        }

        private boolean isNumberInRow(ArrayList<ArrayList<Double>> board, int number, int row) {
            for (int i = 0; i < GRID_SIZE; i++) {
                if (board.get(row).get(i) == number) {
                    return true;
                }
            }
            return false;
        }

        private boolean isNumberInColumn(ArrayList<ArrayList<Double>> board, int number, int column) {
            for (int i = 0; i < GRID_SIZE; i++) {
                if (board.get(i).get(column) == number) {

                    return true;
                }
            }
            return false;
        }

        private boolean isNumberInBox(ArrayList<ArrayList<Double>> board, int number, int row, int column) {
            int localBoxRow = row - row % 3;
            int localBoxColumn = column - column % 3;

            for (int i = localBoxRow; i < localBoxRow + 3; i++) {
                for (int j = localBoxColumn; j < localBoxColumn + 3; j++) {
                    if (board.get(i).get(j) == number) {

                        return true;
                    }
                }
            }
            return false;
        }

        private boolean isValidPlacement(ArrayList<ArrayList<Double>> board, int number, int row, int column) {
            return !isNumberInRow(board, number, row) &&
                    !isNumberInColumn(board, number, column) &&
                    !isNumberInBox(board, number, row, column);
        }

        public boolean solveBoard(ArrayList<ArrayList<Double>> board) {
            if (board != null) {
                for (int row = 0; row < GRID_SIZE; row++) {
                    for (int column = 0; column < GRID_SIZE; column++) {
                        if (board.get(row).get(column) == 0) {
                            for (int numberToTry = 1; numberToTry <= GRID_SIZE; numberToTry++) {
                                if (isValidPlacement(board, numberToTry, row, column)) {
                                    board.get(row).set(column, (double) numberToTry);
                                    if (solveBoard(board)) {
                                        return true;
                                    } else {
                                        board.get(row).set(column, 0.0);
                                    }
                                }
                            }
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        public boolean gameState() {
            return this.originBoard.equals(solvedBoard);
        }

        public ArrayList<Integer> getSolvedBoard() {
            return solvedBoard;
        }
    }

    class SudokuAdapter extends RecyclerView.Adapter<SudokuAdapter.SudokuViewHolder> {

        private ArrayList<Integer> originBoard;
        private ArrayList<Integer> solvedBoard;
        private Context context;

        public SudokuAdapter() {
        }

        @NonNull
        @Override
        public SudokuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            return new SudokuViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sudoku_entrypoint, parent, false));
        }


        @Override
        public void onBindViewHolder(@NonNull SudokuViewHolder holder, @SuppressLint("RecyclerView") int position) {
            if (originBoard != null) {
                int currentValue = originBoard.get(position);
                holder.entry.setText(currentValue + "");
                holder.entry.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (originBoard.equals(solvedBoard)) {
                            Toast.makeText(context, "Win!", Toast.LENGTH_SHORT).show();
                        }
                        originBoard.set(position, currentValue == 9 ? 0 : currentValue + 1);
                        notifyDataSetChanged();
                    }
                });
            }
        }

        public void setOriginBoard(ArrayList<Integer> originBoard) {
            this.originBoard = originBoard;
            notifyDataSetChanged();
        }

        public void setSolvedBoard(ArrayList<Integer> solvedBoard) {
            this.solvedBoard = solvedBoard;
        }

        @Override
        public int getItemCount() {
            return originBoard.size();
        }

        public class SudokuViewHolder extends RecyclerView.ViewHolder {
            private TextView entry;

            public SudokuViewHolder(@NonNull View itemView) {
                super(itemView);
                entry = itemView.findViewById(R.id.sudoku_entrypoint);
            }
        }
    }
}



