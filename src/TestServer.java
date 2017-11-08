import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class TestServer {
    public static void main(String[] args) throws IOException {
        new TestServer().run();
    }

    private void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(4405);
        while(true){
            Socket socket = serverSocket.accept();
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            Reader reader = new Reader(socket.getInputStream());
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            char[] buffer = new char[2048];
            int charsRead = 0;
            while(true) {
                int ch;

                while ((line = reader.readLine()) != null) {
                    stringBuilder = stringBuilder.append(line);
                    System.out.println(line);
                }
                System.out.println("next");

                osw.write(stringBuilder.toString(), 0, stringBuilder.toString().length());
                osw.write(-1);
                osw.flush();
            }

        }

    }

    class Reader extends InputStreamReader{
        private final InputStream in;
        private byte[] buffer;
        private final int len = 2048;
        private final StringBuilder stringBuilder = new StringBuilder();
        private String[] lines;
        private int currentLine = 0;
        StringTokenizer stringTokenizer;

        public Reader(InputStream in) {
            super(in);
            this.in = in;
            buffer = new byte[len];
        }

        private int readAll() throws IOException {
            int total = 0;
            int count;
            currentLine = 0;
            while((count = in.read(buffer)) != -1){
                total += count;
                stringBuilder.append(new String(buffer));
            }
            System.out.println("total read: " + total);
            lines = stringBuilder.toString().split("\n");
            stringTokenizer = new StringTokenizer(lines[currentLine]);
            return lines.length;
        }

        public String readLine() throws IOException {
            if(lines == null){
                readAll();
                return readLine();
            }
            if(currentLine < lines.length){
                if(currentLine < lines.length - 1){
                    stringTokenizer = new StringTokenizer(lines[currentLine]);
                }
                return lines[currentLine++];
            }
            return stringBuilder.toString();
        }

        public String readString(){
            if(stringTokenizer.hasMoreTokens()){
                return stringTokenizer.nextToken();
            }
            return null;
        }

        public int readInt(){

            return Integer.parseInt(readString());
        }

    }
}
