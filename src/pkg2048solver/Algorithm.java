/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pkg2048solver;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author Cameron
 */
public class Algorithm {
    
    public static int getNextMove(String[][] grid) {
        int currEmpty = emptyBlocksInGrid(grid);
        int rightEmpty = emptyBlocksInGrid(gridForMove(grid, Main.RIGHT));
        int leftEmpty = emptyBlocksInGrid(gridForMove(grid, Main.LEFT));
        int upEmpty = emptyBlocksInGrid(gridForMove(grid, Main.UP));
        int downEmpty = emptyBlocksInGrid(gridForMove(grid, Main.DOWN));
        
        int bestScore = 0;
        Hashtable<Integer, Integer> bestMoves = new Hashtable<Integer, Integer>();
        bestMoves.put(new Integer(Main.DOWN), 10);
        bestMoves.put(new Integer(Main.LEFT), 10);
        bestMoves.put(new Integer(Main.RIGHT), 10);
        bestMoves.put(new Integer(Main.UP), 10);
        
        if ( currEmpty - rightEmpty > bestScore ) { bestMoves.put(Main.RIGHT, bestMoves.get(Main.RIGHT) + 1); bestScore = currEmpty - rightEmpty; }
        else if ( currEmpty - rightEmpty <= bestScore ) { bestMoves.put(Main.RIGHT, bestMoves.get(Main.RIGHT) - 1); }
        
        if ( currEmpty - leftEmpty > bestScore ) { bestMoves.put(Main.LEFT, bestMoves.get(Main.LEFT) + 1); bestScore = currEmpty - leftEmpty; }
        else if ( currEmpty - leftEmpty <= bestScore ) { bestMoves.put(Main.LEFT, bestMoves.get(Main.LEFT) - 1); }
        
        if ( currEmpty - upEmpty > bestScore ) { bestMoves.put(Main.UP, bestMoves.get(Main.UP) + 1); bestScore = currEmpty - upEmpty; }
        else if ( currEmpty - upEmpty <= bestScore ) { bestMoves.put(Main.UP, bestMoves.get(Main.UP) - 1); }
        
        if ( currEmpty - downEmpty > bestScore ) { bestMoves.put(Main.DOWN, bestMoves.get(Main.DOWN) + 1); bestScore = currEmpty - downEmpty; }
        else if ( currEmpty - downEmpty <= bestScore ) { bestMoves.put(Main.DOWN, bestMoves.get(Main.DOWN) - 1); }
        
        
        for (int x = 0; x < grid.length; x ++ ) {
            for (int y = 0; y < grid[x].length; y++ ) {
                if ( gridForMove(grid, Main.RIGHT)[x][y].equals("2") ) {
                    bestMoves.put(Main.RIGHT, bestMoves.get(Main.RIGHT) - 1);
                }
                if ( gridForMove(grid, Main.LEFT)[x][y].equals("2") ) {
                    bestMoves.put(Main.LEFT, bestMoves.get(Main.LEFT) - 1);
                }
                if ( gridForMove(grid, Main.DOWN)[x][y].equals("2") ) {
                    bestMoves.put(Main.DOWN, bestMoves.get(Main.DOWN) - 1);
                }
                if ( gridForMove(grid, Main.UP)[x][y].equals("2") ) {
                    bestMoves.put(Main.UP, bestMoves.get(Main.UP) - 1);
                }
            }
        }
        
        int bestMove = -1;
        bestScore = 0;
        for ( int i = 0; i < 4; i ++) {
            int move = (Integer)bestMoves.keySet().toArray()[i];
            if (bestMoves.get(move) > bestScore ) {
                bestMove = move;
                bestScore = bestMoves.get(move);
            }
        }
        
        
        
        return bestMove;
        
        
        
        
//        return Main.DOWN;
    }
    
    
    ///// Helpers
    
    private static ArrayList lockedBlocks;
    private static String[][] gridForMove(String[][] grid, int dir) {
        if ( lockedBlocks == null ) lockedBlocks = new ArrayList();
        
        if ( dir == Main.DOWN ) {
            for (int row = grid.length - 1; row >= 0; row -- ) {
                for (int column = grid[row].length - 1; column >= 0; column-- ) {
                    if ( grid[row][column].equals("") ) continue;
                    if ( row + 1 < grid.length ) {
                        if ( grid[row+1][column].equals("")) {
                            grid[row+1][column] = grid[row][column];
                            grid[row][column] = "";
                            return gridForMove(grid, dir);
                        } else if ( grid[row+1][column].equals(grid[row][column]) && ! Main.isBlockLocked(new int[]{row, column}, lockedBlocks) && ! Main.isBlockLocked(new int[]{row+1, column}, lockedBlocks) ) {
                            grid[row+1][column] = "" + Integer.parseInt(grid[row+1][column]) * 2;
                            grid[row][column] = "";
                            lockedBlocks.add(new int[]{row+1, column});
                            return gridForMove(grid, dir);
                        }
                    } 
                }
            }
            lockedBlocks.clear();

            return grid;
        } else if ( dir == Main.UP) {
            for (int row = 0; row < grid.length; row ++ ) {
                for (int column = 0; column < grid[row].length; column++ ) {
                    if ( grid[row][column].equals("") ) continue;
                    if ( row - 1 >= 0 ) {
                        if ( grid[row-1][column].equals("")) {
                            grid[row-1][column] = grid[row][column];
                            grid[row][column] = "";
                            return gridForMove(grid, dir);
                        } else if ( grid[row-1][column].equals(grid[row][column]) && ! Main.isBlockLocked(new int[]{row, column}, lockedBlocks) && ! Main.isBlockLocked(new int[]{row-1, column}, lockedBlocks) ) {
                            grid[row-1][column] = "" + Integer.parseInt(grid[row-1][column]) * 2;
                            grid[row][column] = "";
                            lockedBlocks.add(new int[]{row-1, column});
                            return gridForMove(grid, dir);
                        }
                    } 
                }
            }
            lockedBlocks.clear();
            return grid;
            
        } else if ( dir == Main.RIGHT ) {
            for (int row = 0; row < grid.length; row ++ ) {
                for ( int column = grid[row].length - 1; column >= 0; column --) {
                    if ( grid[row][column].equals("") ) continue;
                    if ( column + 1 < grid[row].length ) {
                        if ( grid[row][column+1].equals("")) {
                            grid[row][column+1] = grid[row][column];
                            grid[row][column] = "";
                            return gridForMove(grid, dir);
                        } else if ( grid[row][column+1].equals(grid[row][column]) && ! Main.isBlockLocked(new int[]{row, column}, lockedBlocks) && ! Main.isBlockLocked(new int[]{row, column+1}, lockedBlocks) ) {
                            grid[row][column+1] = "" + Integer.parseInt(grid[row][column+1]) * 2;
                            grid[row][column] = "";
                            lockedBlocks.add(new int[]{row, column+1});
                            return gridForMove(grid, dir);
                        } 
                    }
                }
            }
            lockedBlocks.clear();
            return grid;
        } else if ( dir == Main.LEFT ) {
            for (int row = 0; row < grid.length; row ++ ) {
                for ( int column = 0; column < grid[row].length; column ++) {
                    if ( grid[row][column].equals("") ) continue;
                    if ( column - 1 >= 0 ) {
                        if ( grid[row][column-1].equals("")) {
                            grid[row][column-1] = grid[row][column];
                            grid[row][column] = "";
                            return gridForMove(grid, dir);
                        } else if ( grid[row][column-1].equals(grid[row][column]) && ! Main.isBlockLocked(new int[]{row, column}, lockedBlocks) && ! Main.isBlockLocked(new int[]{row, column-1}, lockedBlocks) ) {
                            grid[row][column-1] = "" + Integer.parseInt(grid[row][column-1]) * 2;
                            grid[row][column] = "";
                            lockedBlocks.add(new int[]{row, column-1});
                            return gridForMove(grid, dir);
                        } 
                    }
                }
            }
            lockedBlocks.clear();
            return grid;
        }
        
        return grid;
    }
    
    private static int emptyBlocksInGrid(String[][] grid) {
        int e = 0;
        
        for ( int x = 0; x < grid.length; x++ ) {
            for ( int y = 0; y < grid[x].length; y++ ) {
                if (grid[x][y].equals("")) e += 1;
            }
        }
        
        return e;
    }
}
