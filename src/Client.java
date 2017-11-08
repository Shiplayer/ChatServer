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
    private final LogList log;

    public Client(Socket addr, String name) throws FileNotFoundException {
        log = new LogList();
        socket = addr;
        this.name = name;
        id = count++;
        exchanger = new Exchanger<>();
        new Thread(new HandleEventsClientReceive()).start();
        new Thread(new HandleEventsClientSend()).start();
        log.log(addr.getInetAddress().toString() + " " + name, "info");
    }

    public class HandleEventsClientReceive implements Runnable{

        Message message;

        public HandleEventsClientReceive() {

        }

        @Override
        public void run() {
            try {
                String buf;
                BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    if(!socket.isConnected()){
                        socket.close();
                        log.log(socket.getInetAddress(), "close");
                    }
                    if((buf = bf.readLine()) != null) {
                        message = new Message(buf);
                        exchanger.exchange(message);
                        System.err.println("message is receiving");
                    }
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("exit");
                e.printStackTrace();
            }
        }
    }

    public class HandleEventsClientSend implements Runnable{
        Message message;

        public HandleEventsClientSend() {

        }

        @Override
        public void run() {
            try {
                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
                System.err.println("send: " + socket.getInetAddress().getHostName());
                while (true) {
                    System.out.println("wait");
                    message = exchanger.exchange(message);
                    System.out.println("msg");
                    if(!message.isEmpty()){
                        pw.print(message.getText() + "\n");
                        System.err.println("message is sending");
                        System.err.println("send: text = " + message.getText());
                        pw.flush();
                    }
                }
            } catch (IOException | InterruptedException e) {
                System.out.println("error");
                e.printStackTrace();
            }
        }
    }

}