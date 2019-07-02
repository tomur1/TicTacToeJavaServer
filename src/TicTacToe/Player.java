package TicTacToe;

public class Player {

    String name;
    int value;

    Player()
    {
        name = "Cross";
        value = 1;
    }

    void setName(String string) {
        name=string;
    }

    void setValue(int x) {
        value=x;
    }

    Coordinates askForMove(Board board) {
        Coordinates coords = new Coordinates();
        System.out.println("Where do you want to put Your ");
        board.printBoard();
        return coords;
    }
}
