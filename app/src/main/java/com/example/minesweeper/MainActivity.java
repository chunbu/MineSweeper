package com.example.minesweeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    boolean flag;
    boolean mine;
    int neighMines;
    static int a;
    static int flags;
    static int blocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToggleButton togglebutton = findViewById(R.id.toggleButton);

        BlockButton[][] buttons = new BlockButton[9][9];
        for (int i = 0; i < 9; i++) {
            TableLayout table;
            table = (TableLayout) findViewById(R.id.tableLayout);
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);
            for (int j = 0; j < 9; j++) {
                buttons[i][j] = new BlockButton(this, i, j);
                TableRow.LayoutParams layoutParams =
                        new TableRow.LayoutParams(
                                TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                1.0f);
                buttons[i][j].setLayoutParams(layoutParams);
                tableRow.addView(buttons[i][j]);

                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((BlockButton) view).toggleFlag2();
                    }
                });


                togglebutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        for (int i = 0; i < 9; i++) {
                            for (int j = 0; j < 9; j++) {
                                if (isChecked) {
                                    buttons[i][j].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ((BlockButton) view).toggleFlag();
                                        }
                                    });
                                } else {
                                    buttons[i][j].setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            ((BlockButton) view).toggleFlag2();
                                        }
                                    });
                                }
                            }
                        }
                    }
                });
            }
        }
    }
}