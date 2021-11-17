package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static TextView textView;
    boolean mines[][];
    static BlockButton[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);

        mines = createMines(9);
        buttons = new BlockButton[9][9];

        BlockButton.mineBlocks = mines;
        BlockButton.buttons = this.buttons;
        BlockButton.brokenBlocks = new boolean[9][9];
        BlockButton.mines = 10;
        BlockButton.blocks = 81;

        // 9*9 블록 생성
        TableRow.LayoutParams layoutParams =
                new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f);

        for (int i = 0; i < 9; i++) {
            TableRow tableRow = new TableRow(this);
            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new BlockButton(this, i, j);
                buttons[i][j].setLayoutParams(layoutParams);
                if (mines[i][j]) {
                    buttons[i][j].mine = true;
                }
                tableRow.addView(buttons[i][j]);
            }
            table.addView(tableRow);
        }

        //재시작 버튼
        Button restartButton;
        restartButton = findViewById(R.id.restart);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart(view);
            }
        });

        //우상단 마인개수
        textView = (TextView) findViewById(R.id.mine);
        textView.setText("Mines : " + BlockButton.mines);

        //토글 버튼
        ToggleButton toggleButton;
        toggleButton = (ToggleButton) findViewById(R.id.togglebutton);
        //toggle 버튼으로 숫자/깃발 변환 -> boolean으로
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                if (on) { //on
                    BlockButton.toggleflag = true;
                } else { //off
                    BlockButton.toggleflag = false;
                }
            }
        });
    }

    //게임 재시작 메소드
    public void restart(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    //깃발 뽑을 때 우상단 마인 남은 개수
    public static void putOffFlags() {
        textView.setText("Mines : " + Integer.toString(BlockButton.mines));
    }

    //깃발 꼿을 때 우상단 마인 남은 개수
    public static void putFlags() {
        textView.setText("Mines : " + Integer.toString(BlockButton.mines));
    }

    //지뢰 랜덤하게 생성
    public boolean[][] createMines(int m) {
        boolean[][] mines = new boolean[m][m];
        String[] numbers = new String[m + 1];

        Random random = new Random();

        for (int i = 0; i < m + 1; i++) {
            int x = random.nextInt(m); // row
            int y = random.nextInt(m); // column
            if (Arrays.asList(numbers).contains(String.valueOf((x) + (y)))) { //중복이면 건너뛴다
                i--;
                continue;
            } else {
                numbers[i] = String.valueOf((x) + (y));
                mines[x][y] = true;
            }
        }
        return mines;
    }
}