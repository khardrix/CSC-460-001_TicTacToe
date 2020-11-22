// IMPORTS of any needed tools or plug-ins
import java.io.IOException;
import java.util.*;
import java.net.*;


public class Dispatcher {

    // CLASS VARIABLE(s) declaration(s)
    public static ServerSocket port;         // "GLOBAL STATIC VARIABLE .... " public or private?


    public static void main(String[] args) {
        try {
            Server_Thread serverThread;
            port = new ServerSocket(7788);
            Socket connectToServer = new Socket("localhost", 7788);




            while(true) {
                // Now, inside an infinite loop ( while (true) ) you need to have the dispatcher block and
                    // listen for a connection on the ServerSocket variable port. Do this by calling port.accept()
                connectToServer = port.accept();

                serverThread = new Server_Thread(connectToServer);
                if( connectToServer.isConnected()) {
                    System.out.println("SUCCESSFUL CONNECTION!");
                } else {
                    System.out.println("CONNECTION NOT SUCCESSFUL!");
                }
                serverThread.start();
            }
        }
        catch(IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }


    }
}
