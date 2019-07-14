package TicTacToe;

import java.util.concurrent.atomic.AtomicReference;

public class Game implements Runnable {
    //used in get location method to look if there is new data available to make a move
    AtomicReference<String> in;
    //used to notify players
    AtomicReference<String> out;

    int lastCallerId = -999;
    int lastCallerSymbol = -999;
    public Game(AtomicReference<String> in, AtomicReference<String> out){
        this.in = in;
        this.out = out;
    }
    @Override
    public void run() {
        in.set("");
        out.set("");
        main();
    }

    int main() {
        Board board = new Board();
        Judge sempai = new Judge();
        board.fillWithZeros();
        while (sempai.isGameEnd(board) == 0) {
                boolean validMove;
                do {
                    Coordinates cords = getPosition();
                    validMove = sempai.isMoveValid(board, cords, lastCallerSymbol);
                    if (validMove) {
                        if (sempai.turn % 2 == 0){
                            board.placeTicTacToe(board, lastCallerSymbol, cords);
                            out.set(""+ 0 + ",PLACE," + cords.x + "," + cords.y + "," + (lastCallerSymbol == 1 ? 1 : 2));
                            System.out.println("valid move");
                            //notify the players that the move is valid
                        }else{
                            board.placeTicTacToe(board, lastCallerSymbol, cords);
                            out.set(""+ 0 + ",PLACE," + cords.x + "," + cords.y + "," + (lastCallerSymbol == 1 ? 1 : 2));
                            System.out.println("valid move");
                            //notify the players that the move is valid
                        }
                    } else {
                        System.out.println("Move is not valid");
                        out.set( lastCallerId+",INVALID");
                    }
                } while (!validMove);
            sempai.turn++;
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(sempai.isGameEnd(board) == 1){
            //cross
            out.set(0 + ",END," + 1);
        }else if(sempai.isGameEnd(board) == 2){
            //circle
            out.set(0 + ",END," + 2);
        }else if(sempai.isGameEnd(board) == 3){
            //tie
            out.set(0 + ",END," + 3);
        }
        return 0;
    }

    Coordinates getPosition(){
        while(in.get().isEmpty()) {
            //Jeśli input jest pusty poczekaj 10 ms i spróbuj ponownie
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Coordinates cords = new Coordinates();
        String message = in.get();
        in.set("");
        char[] array = message.toCharArray();

        int callerId = array[0] - '0';
        lastCallerId = callerId;
        cords.x = array[1] - '0';
        cords.y = array[2] - '0';
        int symbol;
        if (array[3] == '1'){
            symbol = 1;
        }else{
            symbol = -1;
        }
        lastCallerSymbol = symbol;
        int reset = array[4] - '0';
        return cords;
    }
}
