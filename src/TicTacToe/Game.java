package TicTacToe;

public class Game implements Runnable {
    @Override
    public void run() {

    }

    int main() {
        Board board = new Board();
        Player playerCross = new Player();
        Player playerCircle = new Player();
        Judge sempai = new Judge();
        board.fillWithZeros();
        playerCircle.setName("Circle");
        playerCircle.setValue(-1);
        while (!sempai.isGameEnd(board)) {
            if (sempai.turn % 2 == 0) {
                boolean validMove;
                do {
                    Coordinates cords = playerCross.askForMove(board);
                    validMove = sempai.isMoveValid(board, cords);
                    if (validMove) {
                        board.placeTicTacToe(board, playerCross.value, cords);
                    } else {
                        //cout << "Move is not valid" << endl;
                    }
                } while (!validMove);
            }
            if (sempai.turn % 2 == 1) {
                boolean validMove;
                do {
                    Coordinates cords = playerCircle.askForMove(board);
                    validMove = sempai.isMoveValid(board, cords);
                    if (validMove) {
                        board.placeTicTacToe(board, playerCircle.value, cords);
                    } else {
                        //cout << "Move is not valid" << endl;
                    }
                } while (!validMove);
            }
            sempai.turn++;
        }

        return 0;
    }
}
