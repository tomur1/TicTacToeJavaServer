package TicTacToe;

public class Judge {

    int turn;

    Judge() {
        turn = 0;
    }


    //if is not end return 0 if cross won return 1 circle won return 2 tie return 3
    int isGameEnd(Board board) {
        //first check for cross
        for (int i = 0; i < board.SIZE; i++) {
            for (int j = 0; j < board.SIZE; j++) {
                if (checkForLine(board, i, j, 1)) {
                    //Game Over Cross won
                    //std::cout << "Game Over\nCross Won\n";
                    board.printBoard();
                    return 1;
                }
            }
        }
        //now check for circle
        for (int i = 0; i < board.SIZE; i++) {
            for (int j = 0; j < board.SIZE; j++) {
                if (checkForLine(board, i, j, -1)) {
                    //Game Over Circle won
                    //std::cout << "Game Over\nCircle Won\n";
                    board.printBoard();
                    return 2;
                }
            }
        }
        int counter = 9;
        for (int i = 0; i < board.SIZE; i++) {
            for (int j = 0; j < board.SIZE; j++) {
                if (board.cellState[i][j] != 0) {
                    counter--;
                }
            }
        }
        if (counter == 0) {
            //std::cout << "Game Over\nDraw\n";
            board.printBoard();
            return 3;
        }
        return 0;
    }
    //returns true if cell is empty and inside the board
    boolean isMoveValid(Board board, Coordinates coords, int symbol) {
        if(turn % 2 == 0){
            if(symbol == -1){
                return false;
            }
        }else{
            if(symbol == 1){
                return false;
            }
        }
        if (board.cellState[coords.x][coords.y] == 0 && coords.x < 3 && coords.x >= 0 && coords.y < 3 && coords.y >= 0) {
            return true;
        }
        return false;
    }

    //checks for line of cross or circle
    boolean checkForLine(Board board, int x, int y, int value) {
        //first checks line to the right

        int counter = 0;
        if (board.cellState[x][y] != value) {

        } else {

            for (int i = 0; i < board.SIZE; i++) {
                if (y + i >= board.SIZE) {

                } else {
                    if (board.cellState[x][y + i] == value) {
                        counter++;
                    }
                }

            }
            if (counter == board.SIZE) {
                return true;
            }
            //checks line to the left
            counter = 0;
            for (int i = 0; i < board.SIZE; i++) {
                if (y - i < 0) {

                } else {
                    if (board.cellState[x][y - i] == value) {
                        counter++;
                    }
                }

            }
            if (counter == board.SIZE) {
                return true;
            }
            //checks line up
            counter = 0;
            for (int i = 0; i < board.SIZE; i++) {
                if (x - i < 0) {

                }else
                {
                    if (board.cellState[x - i][y] == value) {
                        counter++;
                    }
                }

            }
            if (counter == board.SIZE) {
                return true;
            }
            //checks line down
            counter = 0;
            for (int i = 0; i < board.SIZE; i++) {
                if (x + i >= board.SIZE) {

                }else
                {
                    if (board.cellState[x + i][y] == value) {
                        counter++;
                    }
                }
            }
            if (counter == board.SIZE) {
                return true;
            }
            //checks line right up
            counter = 0;
            for (int i = 0; i < board.SIZE; i++) {
                if (x - i < 0 || y + i >= board.SIZE) {

                }else{
                    if (board.cellState[x - i][y + i] == value) {
                        counter++;
                    }
                }

            }
            if (counter == board.SIZE) {
                return true;
            }
            //checks line right down
            counter = 0;
            for (int i = 0; i < board.SIZE; i++) {
                if (x + i >= board.SIZE || y + i >= board.SIZE) {

                }else{
                    if (board.cellState[x + i][y + i] == value) {
                        counter++;
                    }
                }

            }
            if (counter == board.SIZE) {
                return true;
            }
            //checks line left up
            counter = 0;
            for (int i = 0; i < board.SIZE; i++) {
                if (x - i < 0 || y - i < 0) {

                }else{
                    if (board.cellState[x - i][y - i] == value) {
                        counter++;
                    }
                }

            }
            if (counter == board.SIZE) {
                return true;
            }
            //checks line left down
            counter = 0;
            for (int i = 0; i < board.SIZE; i++) {
                if (x + i >= board.SIZE || y - i < 0) {

                }else{
                    if (board.cellState[x + i][y - i] == value) {
                        counter++;
                    }
                }

            }
            if (counter == board.SIZE) {
                return true;
            }
        }
        return false;
    }
}
