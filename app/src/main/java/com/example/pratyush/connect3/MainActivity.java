package com.example.pratyush.connect3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //0 = yellow  1 = white
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};
    boolean isGameActive = true;

    public void dropIn(View view) {

        ImageView counter = (ImageView) view;
        int boxClicked = Integer.parseInt(counter.getTag().toString());
        //System.out.println(boxClicked.getTag());

        if ( gameState[boxClicked] == 2 && isGameActive ) {

            gameState[boxClicked] = activePlayer;
            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.nought);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1000f).rotationBy(360).setDuration(1000);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] != 2
                        && gameState[winningPosition[0]] == gameState[winningPosition[1]]
                        && gameState[winningPosition[1]] == gameState[winningPosition[2]]) {

                    String winnerPlayer = "Yellow";
                    if (gameState[winningPosition[0]] == 1) {
                        winnerPlayer = "White";
                    }
                    gameResult(winnerPlayer + " has won");
                    break;
                }
                else{
                    boolean isGameOver = true;
                    for( int counterState : gameState ){
                        if( counterState == 2 ){
                            isGameOver = false;
                            break;
                        }

                    }
                    if( isGameOver ){
                        gameResult("It's a draw!!!");
                    }
                }
            }


        }
    }

    public void gameResult( String message ){
        isGameActive = false;
        TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
        winnerMessage.setText( message );
        winningLayoutAction(View.VISIBLE);
    }

    public void winningLayoutAction( int visible ){
        LinearLayout winningLayout = (LinearLayout) findViewById(R.id.winnerLayout);
        winningLayout.setVisibility(visible);
    }

    public void playAgain(View view){
        winningLayoutAction(View.INVISIBLE);

        isGameActive = true;
        activePlayer = 0;

        for( int i=0 ; i < gameState.length ; i++ ){
            gameState[i] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for( int i = 0 ; i < gridLayout.getChildCount() ; i++ ){
            ((ImageView)gridLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
