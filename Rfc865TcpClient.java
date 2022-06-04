import java.io.*;
import java.net.*;

public class Rfc865TcpClient {
    
    static int PORT = 17;
    static String SERVER_NAME = "localhost";

    public static void main(String[] args) {
        Socket socket = null;
        try{
            InetAddress serverAddress = InetAddress.getByName(SERVER_NAME);
            socket = new Socket(serverAddress, PORT);
            System.out.println("TCP Client connected on port " + PORT + " to server: " + serverAddress.getCanonicalHostName());
        } catch (Exception e) {}

        try {
            String content = "Nathan Soh, SS4, " + InetAddress.getLocalHost().getHostAddress();
            byte buf[] = content.getBytes("UTF-8");
            System.out.println("Content to send: " + content);

            OutputStream os = socket.getOutputStream();
            System.out.println("Writing request to output stream...");
            os.write(buf);
            System.out.println("Request sent to server");

            byte[] replyBuf = new byte[512];
            InputStream is = socket.getInputStream();
            System.out.println("Reading reply from input stream...");
            is.read(replyBuf);

            String replyContent = new String(replyBuf);
            System.out.println("Received reply: " + replyContent);

        } catch (IOException e) {}
    }
}
