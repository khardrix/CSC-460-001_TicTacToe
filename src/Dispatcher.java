// IMPORTS of any needed tools or plug-ins
import java.io.IOException;
import java.util.*;
import java.net.*;


public class Dispatcher {

    // CLASS VARIABLE(s) declaration(s)
    public static ServerSocket port;         // "GLOBAL STATIC VARIABLE .... " public or private?


    public static void main(String[] args) {
        try {
            Socket connectToServer = new Socket("172.58.139.199", 7788);
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }
}
