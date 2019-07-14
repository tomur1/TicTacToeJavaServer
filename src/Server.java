import TicTacToe.Game;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class Server {

    //ids start from 1 because 0 is reserved for communiaction for everyone
    int IDs = 1;

    //output streams to unity game clients
    AtomicReference<ArrayList<OutputStream>> outs = new AtomicReference<>();

    //String used to feed data into the game
    AtomicReference<String> gameInput = new AtomicReference<>();

    AtomicReference<String> gameOutput = new AtomicReference<>();

    void start(){
        System.out.println("Starting the server...");
        outs.set(new ArrayList<>());
        try (var listener = new ServerSocket(59898)) {
            var pool = Executors.newFixedThreadPool(20);
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (outs.get().size() == 2) {
                            System.out.println("Starting game");
                            tellEveryone(0+",NEWGAME");
                            pool.execute(new Game(gameInput, gameOutput));
                            //listener for information from game to players
                            pool.execute(new Runnable() {
                                @Override
                                public void run() {
                                    while(true){
                                        if (gameOutput.get().isEmpty()){
                                            try {
                                                Thread.sleep(10);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }else{
                                            tellEveryone(gameOutput.get());
                                            gameOutput.set("");
                                        }

                                    }
                                }
                            });
                            return;
                        }
                    }
                }
            });

            while (true) {
                Socket client = listener.accept();
                outs.get().add(client.getOutputStream());
                pool.execute(new ClientHandling(client));
                //this line assigns id
                tellEveryone((IDs++) + "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

   void tellEveryone(String message){
        for (OutputStream out :
                outs.get()) {
            PrintWriter pw = new PrintWriter(out);
            pw.write(message +"\n");
            pw.flush();
            System.out.println(message);
        }
    }

    private class ClientHandling implements Runnable {
        private Socket socket;

        ClientHandling(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Connected: " + socket);
            try {
                var in = new Scanner(socket.getInputStream());
                while (in.hasNextLine()) {
                   performMove(in.nextLine());
                }
            } catch (Exception e) {
                System.out.println("Error:" + socket);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Closed: " + socket);
            }
        }

        public void performMove(String move){
            System.out.println("client sent: " + move);
            gameInput.set(move);
        }
    }
}
