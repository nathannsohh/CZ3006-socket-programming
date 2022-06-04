import java.net.*;
import java.io.*;

public class Rfc865TcpServer {

    static int PORT = 17;
    static String QUOTE = "An apple a day keeps the doctor away. - Albert Eintstein";

    public static void main(String[] args) {
        
        ServerSocket parentSocket = null;
        try{
            parentSocket = new ServerSocket(PORT);
            System.out.println("TCP Server listening on port: " + PORT);
        } catch (Exception e){}

        try {
            while(true){
                System.out.println("Waiting to establish connection with new client...");
                Socket childSocket = parentSocket.accept();
                System.out.println("Client connection request received.");

                System.out.println("Creating thread to handle client connection...");
                ClientHandler client = new ClientHandler(childSocket);
                Thread thread = new Thread(client);
                thread.start();
            }
        } catch (IOException e) {}
    }
} 

class ClientHandler implements Runnable {
    private Socket socket;

    ClientHandler(Socket socket){
        this.socket = socket;
        System.out.println("Established connection with client: " + socket.getInetAddress().getCanonicalHostName());
    }

    public void run() {
        try {
            byte[] buf = new byte[512];
            InputStream is = this.socket.getInputStream();
            System.out.println("Waiting for request...");
            is.read(buf);

            String requestContent = new String(buf);
            System.out.println("Received request: " + requestContent);

            String replyContent = Rfc865TcpServer.QUOTE;
            byte[] replyBuf = replyContent.getBytes("UTF-8");
            System.out.println("Reply content: " + replyContent);

            OutputStream os = this.socket.getOutputStream();
            System.out.println("Sending reply...");
            os.write(replyBuf);
            System.out.println("Reply sent");

        } catch (IOException e) {}
    }
}

