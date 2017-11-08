import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TestConnect {

    public static void main(String[] args) throws IOException {
        new TestConnect().run();
    }

    private void run() throws IOException {
        final Socket socket = new Socket(InetAddress.getByName("localhost"), 44579);
        //Socket socket = new Socket(InetAddress.getByName("localhost"), 4405);
        BufferedReader bfCMD = new BufferedReader(new InputStreamReader(System.in));
        String cmd;
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        new Thread(() -> {
            try {
                String line;
                SocketChannel socketChannel = socket.getChannel();
                ByteBuffer[] byteBuffers = new ByteBuffer[2048];

                while(true) {
                    while (socketChannel.read(byteBuffers) != -1) {

                        System.out.println(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        while(!(cmd = bfCMD.readLine()).equals("exit")) {
            pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println(cmd);
            pw.flush();

            //osw.write(cmd, 0, cmd.length());
            //osw.flush();
            pw.close();
            System.out.println("next cmd");
        }
        socket.close();
    }
}
