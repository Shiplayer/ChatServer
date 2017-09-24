import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Anton on 22.09.2017.
 */
public class myWebServer {
    public static void main(String[] args) throws IOException {
        new myWebServer().run();
    }

    private void run() throws IOException {
        ServerSocket ss = new ServerSocket(4405);
        ClientConnections clients = new ClientConnections();
        while(true){
            Socket socket = ss.accept();
            System.err.println(socket.getInetAddress().getHostName());
            clients.addClient(new Client(socket, socket.getInetAddress().getHostName()));
        }
    }
}
