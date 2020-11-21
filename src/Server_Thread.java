/*********************************************************************************************************************
 *********************************************************************************************************************
 *****                                       Program #2: Tic-Tac-Toe                                             *****
 *****___________________________________________________________________________________________________________*****
 *****                                     Developed by: Ryan Huffman                                            *****
 *****                                             CSC-460-001                                                   *****
 *****                                         Professor Gary Newell                                             *****
 *********************************************************************************************************************
 ********************************************************************************************************************/


// IMPORTS of needed tools and plug-ins
import java.io.*;
import java.net.*;
import java.util.Random;



public class Server_Thread extends Thread {

    private Socket toclientsocket;          // the Socket used to communicate with the client
    private DataInputStream instream;       // stores the input stream of the socket
    private DataOutputStream outstream;     // stores the output stream of the socket
    private PrintWriter out;                // the PrintWriter will allow us to use print() and println() methods
    private BufferedReader in;              // the BufferedReader will allow us to use readLine() method
    private Random gen;                     // used to select random moves
    private char [][] board;                // I implemented my game board as a matrix of char to make printing easier as well as comparisons.
    private int row, col;                   // Obviously, to hold the current row and/or column values for moves
    private char playerMark, serverMark;    // Used to mark an 'O' (for the player) and a 'X' (for the Server)


    public Server_Thread(Socket socket) {
        try {
            toclientsocket = socket;       // Assign the socket into the toclientsocket variable for future communication usage.
            gen = new Random();            // Instantiate the random number generator gen
            instream = new DataInputStream(toclientsocket.getInputStream());     // Obtain the DataInputStream from the socket
            outstream = new DataOutputStream(toclientsocket.getOutputStream());  // Similarly, obtain the DataOutputStream. /** DID I DO THIS THE CORRECT WAY? **/
            out = new PrintWriter(outstream, true);     // Use the DataOutputStream object to instantiate the PrintWriter object. Be sure to use the PrintWriter constructor that takes two arguments. The first is the DataOutputStream object and the second is the value true which turns on autoflush.
            in = new BufferedReader(new InputStreamReader(instream));     // Use the DataInputStream object to instantiate the BufferedReader

            // Next we need to instantiate and initialize the char matrix for the board.
                // Create a 3x3 char array for the board. Walk through the board matrix and assign each cell a blank “ “
            for(int i = 0; i < 3; i++) {
                for(int j = 0; j < board[i].length; j++) {
                    board[i][j] = ' ';
                }
            }

            // Might as well initialize the row and col variables to something. I used -1.
            row = -1;
            col = -1;
        }
        catch(IOException e) {
            System.out.println(e);
        }
    }


    @Override
    public void run() {
        // An int counter to keep track of the number of moves made in the game initialized to 0
        int counter = 0;
        // A String variable response to hold messages sent from the client initialized to null string “”
        String response = "";
        // A Boolean variable gameover initialized to false
        boolean gameOver = false;
        // A Boolean variable turn initialized to the result of the flip() method
        boolean turn = flip();

        // If turn is true, then it is the player making the first move. As the assignment indicates, we should
            // send a message “NONE” to the client via the PrintWriter object that I named, out.
            // We can do this with out.println(“NONE”);
            // BE CAREFUL not to add any additional characters or fail to use UPPERCASE characters
        if(turn) {
            out.println("NONE");
        }

        while(!gameOver) {
            // player's move
            if (turn) {
                try {
                    /* INSTRUCTION ADDED BY RYAN HUFFMAN */ // Add prompt for player to enter their move
                    out.println("Enter your move (row and column [between 0 - 2], separated by a space)  : ");

                    // read player's move using BufferedReader (see readLine() method)
                    response = in.readLine();

                    // entering a "spacer line" for readability
                    out.println();
                }
                catch(IOException e) {
                    System.out.println(e);
                }
                // now break up string into words
                    // this breaks up string on any whitespace
                String [] data = response.split("\\s+");

                // set the row equal to data[1] parsed as an integer
                row = Integer.parseInt(data[1]);
                // do the same for col  but use data[2]
                col = Integer.parseInt(data[2]);

                /* put an ‘O’ into selected cell of the matrix */

                /* call the printboard() method defined later in this document */
                printBoard();

                // add two "spacer lines" to help with readability between player and server moves
                out.println("\n");

                // increment the counter
                counter++;

                // now we check to see if the game is over – either a win for player or a tie
                if (checkWin() || counter == 9) {
                    // set gameover to true as the game is now finished either win or tie
                    gameOver = true;

                    if (checkWin()) {
                        // send message MOVE -1 -1 WIN to the client via PrintWriter object
                        out.println("MOVE -1 -1 WIN");     /** NOT SURE IF THIS IS CORRECT **/
                    }
                    else {
                        // send message MOVE -1 -1 TIE to the client
                        out.println("MOVE -1 -1 TIE");     /** NOT SURE IF THIS IS CORRECT **/
                    }
                }
            }
            // end player's move
                // this is the computer/server’s move code
            else {
                // see below for this method’s description
                makeMove();

                // increment the move counter
                counter++;

                /** set the board[row][col] to an ‘X’ **/

                //

                // see below for method’s description
                printBoard();

                // did computer win or tie???
                if (checkWin() || counter == 9) {
                    // set gameover to true
                    gameOver = true;

                    if (checkWin()) {
                        /** send a LOSS message to the client but make sure you send the correct row and col values **/
                    }
                    else {
                        /** send a TIE message and again make sure you use correct row and col values **/
                    }
                }
                // move does not end the game
                else {
                    /** send the client a MOVE message with correct row and col values **/
                }
            // end server/computer's move code
            }
            // next turn needs to be signaled
            turn = !turn;
        // end the game's while loop
        }
    // end of the run() method
    }


    // continually generate random random empty spaces on the board and store the row and col numbers
    public void makeMove() {
        boolean valid_R_And_C = false;

        while(!valid_R_And_C) {
            row = gen.nextInt(3);
            col = gen.nextInt(3);

            valid_R_And_C = isEmpty(row, col);
        }
    }


    // print out the game board in a way that looks OK. Be sure to include enough whitespace so that it is easy and
        // clear to understand where one board print ends and another begins.
    public void printBoard(){
        // int variable to help store the row that is currently being printed
        int x = 0;

        // for loop to go through the three rows of the board
        for(int i = 0; i < 3; i++) {
            out.print(' ' + ' ');
            if (board[x][0] == playerMark) {
                out.print(playerMark);
            } else if (board[x][0] == serverMark) {
                out.print(serverMark);
            } else {
                out.print(' ');
            }
            out.print(' ' + ' ');
            out.print('|');

            out.print(' ' + ' ');
            if (board[x][1] == playerMark) {
                out.print(playerMark);
            } else if (board[x][1] == serverMark) {
                out.print(serverMark);
            } else {
                out.print(' ');
            }
            out.print(' ' + ' ');
            out.print('|');

            out.print(' ' + ' ');
            if (board[x][2] == playerMark) {
                out.print(playerMark);
            } else if (board[x][2] == serverMark) {
                out.print(serverMark);
            } else {
                out.print(' ');
            }
            out.print(' ' + ' ');

            // end of line, to start next line
            out.println();

            if(x == 0 || x == 1) {
                // line of equal signs to help divide up board for easier visibility
                for (int k = 0; k < 15; k++) {
                    out.print('=');
                }
            }

            // end of line, to start next line
            out.println();

            // spacer line
            out.println();

            // increment the int variable "x"
            x++;
        }

    }


    // boolean method to return whether anyone won the game or not
    public boolean checkWin() {
        // boolean variable to keep track of if someone won
        boolean wonGame = false;

        if(!wonGame) {
            // check for a row-win (horizontal win)
            for (int x = 0; x <= 2; x++) {
                if (board[x][0] == board[x][1] && board[x][1] == board[x][2] && board[x][0] != ' ') {
                    return true;
                }
            }

            // check for col-win (vertical win)
            for (int v = 0; v <= 2; v++) {
                if (board[0][v] == board[1][v] && board[1][v] == board[2][v] && board[0][v] != ' ') {
                    return true;
                }
            }

            // check for vertical win
            if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ') {
                return true;
            } else if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ') {
                return true;
            }
        }
        // return the local boolean variable that kept track of if anyone won the the game or not if no one won
        return wonGame;
    }


    // boolean method to randomly decide whose turn it is
    //***** METHOD ADDED BY RYAN HUFFMAN *****//
    private boolean flip() {
        return gen.nextInt(101) % 2 == 0;
    }


    // boolean method to check if a board spot is empty
    //***** METHOD ADDED BY RYAN HUFFMAN *****//
    private boolean isEmpty(int row, int col) {
        return board[row][col] == ' ';
    }
}
