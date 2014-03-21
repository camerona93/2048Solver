/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg2048solver;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Cameron
 */
public class Main {
    private static int alg1 = 0;
    private static MainFrame frame;
    private static String[][] grid;
    private static ArrayList lockedBlocks;
    public static final int LEFT = 37;
    public static final int UP = 38;
    public static final int RIGHT = 39;
    public static final int DOWN = 40;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        lockedBlocks = new ArrayList();
        
        frame = new MainFrame();
        frame.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {
                if ( e.getKeyCode() == UP )     moveUp();
                if ( e.getKeyCode() == DOWN)    moveDown();
                if ( e.getKeyCode() == RIGHT)   moveRight();
                if ( e.getKeyCode() == LEFT)    moveLeft();
            }
        });
//        beginNewGame();
        beginNewTestGame();
    }
    
    public static boolean tileSpaceAvailable() {
        for (int x = 0; x < grid.length; x ++) {
            for (int y = 0; y < grid[x].length; y ++) {
                if ( grid[x][y].equals("")) return true;
            }
        }
        return false;
    }
   
    public static void addRandomTile() {
        if ( ! tileSpaceAvailable() ) return;
        
        int value = Math.random() < 0.9 ? 2 : 4;
        
        Random rand = new Random();
        int rr1 = rand.nextInt(4);
        int rc1 = rand.nextInt(4);
        
        if ( ! grid[rr1][rc1].equals("") ) {
            addRandomTile();
            return;
        }
        
        grid[rr1][rc1] = "" + value;
        
        frame.updateDisplay(grid);
        
    }
    
    public static void beginNewTestGame() {
        grid = new String[4][4];
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                alg1 = Algorithm.getNextMove(grid);
                
                if ( alg1 == UP ) moveUp();
                if ( alg1 == RIGHT ) moveRight();
                if ( alg1 == DOWN ) moveDown();
                if ( alg1 == LEFT ) moveLeft();
            }
        }, 10, 10);
        
        for (int row = 0; row < grid.length; row ++ ) {
            for (int column = 0; column < grid[row].length; column++ ) {
                grid[row][column] = "";
            }
        }
        
        /** Testing start numbers **/
        grid[0][0] = "2";
//        grid[0][1] = "2";
//        grid[0][2] = "2";
//        grid[0][3] = "2";
        grid[1][0] = "2";
//        grid[1][1] = "2";
//        grid[1][2] = "2";
//        grid[1][3] = "2";
        grid[2][0] = "2";
//        grid[2][1] = "2";
//        grid[2][2] = "2";
//        grid[2][3] = "2";
        grid[3][0] = "2";
//        grid[3][1] = "2";
//        grid[3][2] = "2";
//        grid[3][3] = "2";
        
        addRandomTile();
        addRandomTile();
        
        frame.updateDisplay(grid);
    }
    
    public static void beginNewGame() {
        grid = new String[4][4];
        for (int row = 0; row < grid.length; row ++ ) {
            for (int column = 0; column < grid[row].length; column++ ) {
                grid[row][column] = "";
            }
        }
        /* Random first two tiles placed here */
        addRandomTile();
        addRandomTile();
        
        frame.updateDisplay(grid);
        
    }
    
    private static boolean isBlockLocked(int[] coords) {
        for (int i = 0; i < lockedBlocks.size(); i++ ) {
            if ( ((int[])lockedBlocks.get(i))[0] == coords[0] && ((int[])lockedBlocks.get(i))[1] == coords[1] ) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
    
    public static boolean isBlockLocked(int[] coords, ArrayList<int[]> arr ) {
        for (int i = 0; i < arr.size(); i++ ) {
            if ( ((int[])arr.get(i))[0] == coords[0] && ((int[])arr.get(i))[1] == coords[1] ) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
    
    public static void moveDown() {
        for (int row = grid.length - 1; row >= 0; row -- ) {
            for (int column = grid[row].length - 1; column >= 0; column-- ) {
                if ( grid[row][column].equals("") ) continue;
                if ( row + 1 < grid.length ) {
                    if ( grid[row+1][column].equals("")) {
                        grid[row+1][column] = grid[row][column];
                        grid[row][column] = "";
                        moveDown();
                        return;
                    } else if ( grid[row+1][column].equals(grid[row][column]) && ! isBlockLocked(new int[]{row, column}) && ! isBlockLocked(new int[]{row+1, column}) ) {
                        grid[row+1][column] = "" + Integer.parseInt(grid[row+1][column]) * 2;
                        grid[row][column] = "";
                        lockedBlocks.add(new int[]{row+1, column});
                        moveDown();
                        return;
                    }
                } 
            }
        }
        addRandomTile();
        lockedBlocks.clear();
        frame.updateDisplay(grid);
    }
    
    public static void moveUp() {
        System.out.println("Hit moveUp()");
        for (int row = 0; row < grid.length; row ++ ) {
            for (int column = 0; column < grid[row].length; column++ ) {
                if ( grid[row][column].equals("") ) continue;
                if ( row - 1 >= 0 ) {
                    if ( grid[row-1][column].equals("")) {
                        grid[row-1][column] = grid[row][column];
                        grid[row][column] = "";
                        moveUp();
                        return;
                    } else if ( grid[row-1][column].equals(grid[row][column]) && ! isBlockLocked(new int[]{row, column}) && ! isBlockLocked(new int[]{row-1, column}) ) {
                        grid[row-1][column] = "" + Integer.parseInt(grid[row-1][column]) * 2;
                        grid[row][column] = "";
                        lockedBlocks.add(new int[]{row-1, column});
                        moveUp();
                        return;
                    }
                } 
            }
        }
        addRandomTile();
        lockedBlocks.clear();
        frame.updateDisplay(grid);
    }
    
    public static void moveRight() {
        System.out.println("Hit moveRight()");
        for (int row = 0; row < grid.length; row ++ ) {
            for ( int column = grid[row].length - 1; column >= 0; column --) {
                if ( grid[row][column].equals("") ) continue;
                if ( column + 1 < grid[row].length ) {
                    if ( grid[row][column+1].equals("")) {
                        grid[row][column+1] = grid[row][column];
                        grid[row][column] = "";
                        moveRight();
                        return;
                    } else if ( grid[row][column+1].equals(grid[row][column]) && ! isBlockLocked(new int[]{row, column}) && ! isBlockLocked(new int[]{row, column+1}) ) {
                        grid[row][column+1] = "" + Integer.parseInt(grid[row][column+1]) * 2;
                        grid[row][column] = "";
                        lockedBlocks.add(new int[]{row, column+1});
                        moveRight();
                        return;
                    } 
                }
            }
        }
        addRandomTile();
        lockedBlocks.clear();
        frame.updateDisplay(grid);
    }
    
    public static void moveLeft() {
        System.out.println("Hit moveLeft()");
        for (int row = 0; row < grid.length; row ++ ) {
            for ( int column = 0; column < grid[row].length; column ++) {
                if ( grid[row][column].equals("") ) continue;
                if ( column - 1 >= 0 ) {
                    if ( grid[row][column-1].equals("")) {
                        grid[row][column-1] = grid[row][column];
                        grid[row][column] = "";
                        moveLeft();
                        return;
                    } else if ( grid[row][column-1].equals(grid[row][column]) && ! isBlockLocked(new int[]{row, column}) && ! isBlockLocked(new int[]{row, column-1}) ) {
                        grid[row][column-1] = "" + Integer.parseInt(grid[row][column-1]) * 2;
                        grid[row][column] = "";
                        lockedBlocks.add(new int[]{row, column-1});
                        moveLeft();
                        return;
                    } 
                }
            }
        }
        addRandomTile();
        lockedBlocks.clear();
        frame.updateDisplay(grid);
    }
    
   
    
}
