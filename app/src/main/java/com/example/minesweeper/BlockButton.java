package com.example.minesweeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

public class BlockButton extends AppCompatButton {
    int x;
    int y;
    boolean mine = false; // mine or not
    boolean flag = false; // flag or not
    int neighborMines = 0; // 주변 8개의 지뢰
    static int mines; //남은 지뢰
    static int blocks; //남은 블럭
    static boolean toggleflag = false;
    static boolean[][] mineBlocks; //지뢰인 블럭들 모음
    static boolean[][] brokenBlocks; //이미 열린 블럭들 모음
    static BlockButton[][] buttons; //블럭들 모음


    public BlockButton(Context context, int x, int y) {
        super(context);
        this.x = x;
        this.y = y;
        neighborMines = getneighMines(this.x, this.y);

        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toggleflag on
                if (toggleflag == true) {
                    //깃발을 꼿을 때
                    if (flag == false) {
                        flag = true;
                        setText("🚩");
                        setTextSize(15);
                        setTextColor(Color.RED);
                        setTypeface(Typeface.SERIF, Typeface.BOLD);
                        mines -= 1;
                        MainActivity.putOffFlags(); // 깃발 꼿아서 우상단 남은 마인 수 증가
                        return;
                    }
                    // 깃발을 뽑을 때
                    else if (flag = true) {
                        flag = false;
                        setText(null);
                        mines += 1;
                        MainActivity.putFlags(); // 깃발 뽑아서 우상단 남은 마인 수 감소
                        return;
                    }
                }
                //toggleflag off
                else if ((toggleflag == false)) {
                    if (flag == true) { //toggleflag off인 상태에서 깃발이 이미 꼿혀있을 때
                        return;
                    }
                    //지뢰인 경우
                    else if (open()) {
                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 9; j++) {
                                if (buttons[i][j].mine == true) {
                                    buttons[i][j].open();
                                }
                                buttons[i][j].setEnabled(false);
                            }
                        }

                        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                .setTitle("Game Over")
                                .setMessage("게임을 다시하려면 재시작을 눌러주세요.")
                                .setPositiveButton("확인", null)
                                .create();
                        alertDialog.show();
                    } else { //지뢰가 아닌 경우
                        return;
                    }
                }
            }
        });
    }

    private boolean open() {
        if (flag) //플래그상태면 리턴
            return false;
        this.setEnabled(false); //클릭 안되게 바꾼다
        brokenBlocks[this.x][this.y] = true;
        blocks -= 1; //남은 블록수 -1

        // 지뢰있는 곳 눌렀을 때
        if (mine) {
            setText("💣");
            setTextSize(15);
            setTextColor(Color.BLACK);
            setTypeface(Typeface.SERIF, Typeface.BOLD);
            setBackgroundColor(Color.RED);
            return true;
        }
        // 지뢰가 아닌 곳 눌렀을 때
        else {
            if (blocks <= 10) { //승리한 경우
                AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                        .setTitle("Clear")
                        .setMessage("게임을 다시하려면 재시작을 눌러주세요")
                        .setPositiveButton("확인", null)
                        .create();
                alertDialog.show();
            }
            if (neighborMines == 0) { //주변 8칸에 지뢰 없음
                setBackgroundColor(Color.WHITE);
                int xLen = this.x + 2;
                int yLen = this.y + 2;
                try {
                    for (int i = this.x - 1; i < xLen; i++) {
                        if (i < 0 || i >= buttons.length)
                            continue;
                        for (int j = this.y - 1; j < yLen; j++) {
                            if (j < 0 || j >= buttons.length || brokenBlocks[i][j] || (i == this.x && j == this.y))
                                continue;
                            buttons[i][j].open();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            } else { // 주변 8칸에 지뢰 있는 경우(1 ~ 8)
                if (neighborMines == 1) {
                    setTextColor(Color.BLUE);
                    setTypeface(Typeface.SERIF, Typeface.BOLD);
                    setText(Integer.toString(neighborMines));
                    setBackgroundColor(Color.WHITE);
                } else if (neighborMines == 2) {
                    setTextColor(Color.GREEN);
                    setTypeface(Typeface.SERIF, Typeface.BOLD);
                    setText(Integer.toString(neighborMines));
                    setBackgroundColor(Color.WHITE);
                } else if (neighborMines == 3) {
                    setTextColor(Color.RED);
                    setTypeface(Typeface.SERIF, Typeface.BOLD);
                    setText(Integer.toString(neighborMines));
                    setBackgroundColor(Color.WHITE);
                } else if (neighborMines == 4) {
                    setTextColor(Color.YELLOW);
                    setTypeface(Typeface.SERIF, Typeface.BOLD);
                    setText(Integer.toString(neighborMines));
                    setBackgroundColor(Color.WHITE);
                } else if (neighborMines == 5) {
                    setTextColor(Color.DKGRAY);
                    setTypeface(Typeface.SERIF, Typeface.BOLD);
                    setText(Integer.toString(neighborMines));
                    setBackgroundColor(Color.WHITE);
                } else if (neighborMines == 6) {
                    setTextColor(Color.BLACK);
                    setTypeface(Typeface.SERIF, Typeface.BOLD);
                    setText(Integer.toString(neighborMines));
                    setBackgroundColor(Color.WHITE);
                } else if (neighborMines == 7) {
                    setTextColor(Color.BLACK);
                    setTypeface(Typeface.SERIF, Typeface.BOLD);
                    setText(Integer.toString(neighborMines));
                    setBackgroundColor(Color.WHITE);
                } else if (neighborMines == 8) {
                    setTextColor(Color.BLACK);
                    setTypeface(Typeface.SERIF, Typeface.BOLD);
                    setText(Integer.toString(neighborMines));
                    setBackgroundColor(Color.WHITE);
                }
            }
            return false;
        }
    }

    private int getneighMines(int x, int y) {
        int count = 0;
        int xLen = x + 2;
        int yLen = y + 2;

        try {
            for (int i = x - 1; i < xLen; i++) {
                if (i < 0 || i >= mineBlocks.length)
                    continue;
                for (int j = y - 1; j < yLen; j++) {
                    if (j < 0 || j >= mineBlocks.length || (i == x && j == y))
                        continue;
                    else if (mineBlocks[i][j])
                        count += 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return count;
    }
}