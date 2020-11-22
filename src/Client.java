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
    private static char[][] board;
    private static int row, col;


    public static void main(String[] args) {
        try {
            outstream = new DataOutputStream(fromClientSocket.getOutputStream());
            out = new PrintWriter(outstream, true);


            fromClientSocket = new Socket("localhost", 7788);
            instream = new DataInputStream(fromClientSocket.getInputStream());
            outstream = new DataOutputStream(fromClientSocket.getOutputStream());
            PrintWriter var1 = new PrintWriter(outstream, true);
            BufferedReader var2 = new BufferedReader(new InputStreamReader(instream));
            board = new char[3][3];

            for(int var3 = 0; var3 <= 2; ++var3) {
                for(int var4 = 0; var4 <= 2; ++var4) {
                    board[var3][var4] = ' ';
                }
            }

            row = -1;
            col = -1;


        }
        catch(IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        class clientInnerClass extends Thread {

            @Override
            public void run() {
                boolean continueGame = true;

                while(continueGame) {

                }
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    System.out.println(e);
                    e.printStackTrace();
                }
            }
        }
    }

}
