package com.example.minesweeper;

import android.content.Context;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Random;

public class BlockButton extends androidx.appcompat.widget.AppCompatButton {
    boolean flag;
    boolean mine;
    int neighMines;
    static int a;
    static int flags;
    static int blocks;

    public BlockButton(Context context, int x, int y){
        super(context);
        int xposition = x;
        int yposition = y;
        mine = false;
        flag = false;
        neighMines = 0;
        a = 10;
        flags = 0;
        blocks = 81;
    }

    public boolean mines(){
        return mine;
    }

    public boolean flag(){
        return flag;
    }

    public void toggleFlag(){
        if(flag){
            flag = false;
            flags--;
            setText(" ");
        }
        else {
            flag = true;
            flags++;
            setText("+");
        }
    }

    public void toggleFlag2(){
        if(flag){
            flag = false;
            flags--;
            setText(" ");
        }
        else {
            flag = true;
            flags++;
            setText("1");
        }
    }



   /* public void getMinePosition(){
        Random random = new Random();
        for(int i=0 ; i < 10 ; ++i){
            int ranNum = random.nextInt(81);
            if(!mines.containsKey(ranNum)){
                mines.put(ranNum, -1);
            }else{
                --i;
            }
        }
    }*/

    // 블록 클릭 시 숫자 or 지뢰
    public boolean breakBlock(){
        if(mine){
            //지뢰 모양 표현
            setText("펑");
            setClickable(false);
            return true;
        }
        else{
            // 숫자 표시
            setText("1");
            blocks--;
            return false;
        }
    }

}