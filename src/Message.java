/**
 * Created by Anton on 22.09.2017.
 */
public class Message {
    private String text;

    public Message(String txt){
        text = txt;
    }

    public boolean isEmpty(){
        return text.isEmpty();
    }

    public String getText(){
        return text;
    }
}
