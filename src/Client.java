import java.io.*;
import java.net.Socket;
import java.util.concurrent.Exchanger;

/**
 * Created by Anton on 22.09.2017.
 */
public class Client{
    private final Socket socket;
    private final String name;
    private String messageSend;
    private String messageRecive;
    private final int id;
    private static int count = 0;
    private final Exchanger<Message> exchanger;

    public Client(Socket addr, String name){
        socket = addr;
        this.name = name;
        id = count++;
        exchanger = new Exchanger<>();
        new Thread(new HandleEventsClientReceive(socket)).start();
        new Thread(new HandleEventsClientSend(socket)).start();
    }

    public class HandleEventsClientReceive implements Runnable{
        private Socket socket;
        Message message;

        public HandleEventsClientReceive(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    if(bf.ready()) {
                        message = new Message(bf.readLine());
                        exchanger.exchange(message);
                        System.err.println("message is receiving");
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class HandleEventsClientSend implements Runnable{
        private Socket socket;
        Message message;

        public HandleEventsClientSend(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                System.err.println("send: " + socket.getInetAddress().getHostName());
                while (true) {
                    message = exchanger.exchange(message);
                    if(!message.isEmpty()){
                        pw.println(message.getText());
                        System.err.println("message is sending");
                        System.err.println("send: text = " + message.getText());
                        pw.flush();
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}