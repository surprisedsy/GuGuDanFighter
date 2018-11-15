package com.example.gugudanfighter.test;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kicks on 2017-07-06.
 */

public class RandomTest {

    public static void main( String[] args ) {
        System.out.println( "Hello World" );

        new Timer().schedule( new GamePlayTimerTask(), 1000, 1000);
    }

    private static class GamePlayTimerTask extends TimerTask {

        @Override
        public void run() {
            System.out.println( "hi" );
        }
    }
}
