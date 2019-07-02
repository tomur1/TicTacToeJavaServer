package TicTacToe;

import java.security.Signature;

public class Board {

    static final int SIZE=3;
    int[][] cellState = new int[SIZE][SIZE];

    void fillWithZeros()
    {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                cellState[i][j]=0;
            }
        }
    }

    void printBoard() {
        for (int i = 0; i < SIZE; ++i) {
            for (int j = 0; j < SIZE; ++j) {
                //std::cout << cellState[i][j] << " ";
            }
        }
    }
    void placeTicTacToe(Board board, int value, Coordinates coords) {
        cellState[coords.x][coords.y]=value;
    }

}
