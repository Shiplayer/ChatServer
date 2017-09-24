import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton on 22.09.2017.
 */
public class ClientConnections {
    private ArrayList<Client> clients;

    public ClientConnections(){
        clients = new ArrayList<>();
    }

    public boolean addClient(Client client){
        return clients.add(client);
    }
}
