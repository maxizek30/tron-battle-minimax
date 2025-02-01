import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

// Write an action using System.out.println()
// To debug: System.err.println("Debug messages...");
class Player {

    private static final int[][] DIRECTIONS = {
        {0, -1},  
        {1, 0},    
        {0, 1},    
        {-1, 0}   
    };
    private static final String[] DIRECTION_STRING = {
        "UP", "RIGHT", "DOWN", "LEFT"
    };

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width = 30;
        int height = 20;
        //lets make a 2d array representing the game '0' for an empty cell and '1' for a wall or a player
        int [][] board = new int[height][width];
        
        // game loop
        while (true) {
            int N = in.nextInt(); // total number of players (2 to 4).
            int P = in.nextInt(); // your player number (0 to 3)
            System.err.println("I am player: " + P);
            int x = -1;
            int y = -1;
            for (int i = 0; i < N; i++) {
                System.err.println("Player " + i);
                int X0 = in.nextInt(); // starting X coordinate of lightcycle (or -1)
                int Y0 = in.nextInt(); // starting Y coordinate of lightcycle (or -1)
                System.err.println("X0: " + X0 + ", Y0: " + Y0);
                int X1 = in.nextInt(); // starting X coordinate of lightcycle (can be the same as X0 if you play before this player)
                int Y1 = in.nextInt(); // starting Y coordinate of lightcycle (can be the same as Y0 if you play before this player)
                System.err.println("X1: " + X1 + ", Y1: " + Y1);
                //add a wall to the 2d array for x1 and y1
                board[Y1][X1] = 1;
                if(i == P) {
                    x = X1;
                    y = Y1;
                }
            }

            // //check around the board and see where we can move
            // if(y - 1 >= 0 && board[y - 1][x] == 0) {
            //     //up
            //     System.out.println("UP");
            // } else if (y + 1 < height &&  board[y + 1][x] == 0) {
            //     //down
            //     System.out.println("DOWN");
            // } else if (x - 1 >= 0 && board[y][x - 1] == 0) {
            //     //left
            //     System.out.println("LEFT");
            // } else if (x + 1 < width && board[y][x + 1] == 0) {
            //     //right
            //     System.out.println("RIGHT");
            // } else {
            //     System.out.println("LEFT"); // A single line with UP, DOWN, LEFT or RIGHT
            // }


            int bestScore = Integer.MIN_VALUE;
            String bestMove = "LEFT";

            for (int d = 0; d < DIRECTIONS.length; d++) {
                int dx = DIRECTIONS[d][0];
                int dy = DIRECTIONS[d][1];
                
                int nx = x + dx;
                int ny = y + dy;

                if (nx >= 0 && nx < width && ny >= 0 && ny < height && board[ny][nx] == 0) {
                    

                    //use helper method to determine which way is better
                    board[ny][nx] = 1;
                    int score = MiniMax(board, 4, false, nx, ny);
                    board[ny][nx] = 0;
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = DIRECTION_STRING[d];
                    }
                }
            }

            System.out.println(bestMove);
            
        }
    }
    private static int Helper(int [][] board, int startX, int startY) {
        int height = board.length;
        int width = board[0].length;

        boolean[][] visited = new boolean[height][width];
        int count = 0;
        Queue<int[]> queue = new LinkedList<>();

        visited[startY][startX] = true;
        queue.add(new int[]{startX, startY});

        while(!queue.isEmpty()) {
            int[] current = queue.poll();
            int cx = current[0];
            int cy = current[1];
            count++;

             // Explore nextdoor cells
            int[][] moves = {{1,0},{-1,0},{0,1},{0,-1}};
            for (int[] m : moves) {
                int nx = cx + m[0];
                int ny = cy + m[1];
                
                // Check if boundries and if empty
                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    if (!visited[ny][nx] && board[ny][nx] == 0) {
                        visited[ny][nx] = true;
                        queue.add(new int[]{nx, ny});
                    }
                }
            }
        }
        return count;
    }
    //we are gonna use flood fill as our heuristic
    private static int MiniMax(int[][] board, int depth, boolean isMaximizing, int x, int y) {
        int height = board.length;
        int width = board[0].length;

        if(depth == 0) {
            return Helper(board, x, y);
        }
        if(isMaximizing) {
            int maxEva = Integer.MIN_VALUE;
            //for each legit direction we are gonna minimax
            int[][] moves = {{1,0},{-1,0},{0,1},{0,-1}};
            for (int[] m : moves) {
                int nx = x + m[0];
                int ny = y + m[1];
                
                // Check if boundries and if empty
                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    if (board[ny][nx] == 0) {
                        board[ny][nx] = 1;
                        int score = MiniMax(board, depth - 1, !isMaximizing, nx, ny);
                        board[ny][nx] = 0;
                        maxEva = Math.max(score, maxEva);
                    }
                }
            }
            return maxEva;
        } else {
            int minEva = Integer.MAX_VALUE;
            int[][] moves = {{1,0},{-1,0},{0,1},{0,-1}};
            for (int[] m : moves) {
                int nx = x + m[0];
                int ny = y + m[1];
                
                // Check if boundries and if empty
                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    if (board[ny][nx] == 0) {
                        board[ny][nx] = 1;
                        int score = MiniMax(board, depth - 1, !isMaximizing, nx, ny);
                        board[ny][nx] = 0;
                        minEva = Math.min(score, minEva);
                    }
                }
            }
            return minEva;
        }
        }
    }