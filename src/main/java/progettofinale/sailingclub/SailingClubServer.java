package progettofinale.sailingclub;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * The class {@code SailingClubServer} defines a server that waits for a message,
 * generates a thread that manage the communication with the client and then
 * sends an answer.
 *
 */
public class SailingClubServer {

    /**
     * Class fields.
     *
     * COREPOOL
     * MAXPOOL
     * IDLETIME
     * SPORT
     * socket
     * pool
     */

    private static final int COREPOOL = 10;
    private static final int MAXPOOL = 100;
    private static final long IDLETIME = 5000;
    private static final int SPORT = 4444;

    private final ServerSocket socket;


    /**
     * Class constructor.
     *
     * @throws IOException if the creation of the server socket fails.
     **/

    public SailingClubServer() throws IOException {
        this.socket = new ServerSocket(SPORT);
    }


    /**
     *
     * This method is used to run the server code.
     *
     **/
    private void run() {

        System.out.println("SERVER IS RUNNING ON 4444");

        ThreadPoolExecutor pool = new ThreadPoolExecutor(COREPOOL, MAXPOOL, IDLETIME, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());


        while (true) {

            try {
                Socket socketClient = this.socket.accept();

                pool.execute(new ServerThread(socketClient));
            }
            catch (Exception e) {
                break;
            }
        }

        pool.shutdown();
    }

    /**
     *
     * Starts the execution of the server.
     *
     * @param args the method does not require arguments.
     *
     * @throws IOException if the execution fails.
     **/
    public static void main(final String[] args) throws IOException {
        new SailingClubServer().run();
    }
}

