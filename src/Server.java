import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Server {

    ArrayList<OutputStream> outs = new ArrayList<>();

    void start(){
        System.out.println("Starting the server...");
        try (var listener = new ServerSocket(59898)) {
            var pool = Executors.newFixedThreadPool(2);
            Thread MMaker = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true){
                        if(outs.size() == 2){
                            System.out.println("Starting game");
                            tellEveryone("start");
                        }
                    }
                }
            });
            while (true) {
                Socket client = listener.accept();
                outs.add(client.getOutputStream());
                pool.execute(new ClientHandling(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    synchronized void tellEveryone(String message){
        for (OutputStream out :
                outs) {
            PrintWriter pw = new PrintWriter(out);
            pw.write(message);
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
                var out = new PrintWriter(socket.getOutputStream(), true);
                while (in.hasNextLine()) {
                   tellEveryone(in.nextLine());
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
    }
}
