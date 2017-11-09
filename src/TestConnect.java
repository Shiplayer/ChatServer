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
        Socket socket = new Socket("localhost", 44579);
        new Thread(() -> {
            try(BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
                String line;
                while((line = bf.readLine()) != null){
                    System.out.println("From server: " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        try( BufferedReader bfCMD = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true)){
            String line;
            while((line = bfCMD.readLine()) != null){
                pw.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        socket.close();

    }

    private void run_prev() throws IOException {
        final Socket socket = new Socket(InetAddress.getByName("localhost"), 44579);
        //Socket socket = new Socket(InetAddress.getByName("localhost"), 4405);
        BufferedReader bfCMD = new BufferedReader(new InputStreamReader(System.in));
        String cmd;
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        //OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
        /*new Thread(() -> {
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
        }).start();*/
        String line = null;
/*        do{
            if(line == null){
                line = bfCMD.readLine();
                pw.println(line);
            } else {
                System.out.println(line);
                line = bfCMD.readLine();
                //pw.println()
            }
        }
        while(!(cmd = bfCMD.readLine()).equals("exit")) {
            pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println(cmd);
            pw.flush();

            //osw.write(cmd, 0, cmd.length());
            //osw.flush();
            System.out.println("next cmd");
        }*/
        socket.close();
    }
}
