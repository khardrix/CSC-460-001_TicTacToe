// IMPORTS of needed tools and plug-ins


import java.io.*;
import java.net.Socket;

public class Client {

    // CLASS VARIABLE(s) declaration(s)
    private static Socket fromClientSocket;        // the Socket used to communicate with the Server
    private static DataInputStream instream;       // stores the input stream of the socket
    private static DataOutputStream outstream;     // stores the output stream of the socket
    private static PrintWriter out;                // the PrintWriter will allow us to use print() and println() methods
    private static BufferedReader in;              // the BufferedReader will allow us to use readLine() method


    public static void main(String[] args) {
        try {
            outstream = new DataOutputStream(fromClientSocket.getOutputStream());
            out = new PrintWriter(outstream, true);
        }
        catch(IOException e) {

        }
    }

}
