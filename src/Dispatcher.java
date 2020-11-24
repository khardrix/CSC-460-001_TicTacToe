// IMPORTS of any needed tools or plug-ins
import java.io.IOException;
import java.util.*;
import java.net.*;


public class Dispatcher {

    // CLASS VARIABLE(s) declaration(s)
    public static ServerSocket port;


    public static void main(String[] args) {
        try {
            Server_Thread serverThread;
            port = new ServerSocket(7788);

            Socket  connectToServer;  // just do this!



            while(true) {
                connectToServer = port.accept();

                serverThread = new Server_Thread(connectToServer);
                serverThread.start();
            }
        }
        catch(IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
