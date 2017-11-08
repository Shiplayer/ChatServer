import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamReader extends InputStreamReader {
    private InputStream inputStream;
//    private

    public StreamReader(InputStream in) {
        super(in);
        inputStream = in;
    }

//    public void readAll()
}
