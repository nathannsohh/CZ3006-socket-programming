import java.io.IOException;
import java.net.*;

public class Rfc865UdpServer {

    static final int PORT = 17;
    static final String QUOTE = "An apple a day keeps the doctor away. -Albert Einstein";

    public static void main(String[] args) {

        DatagramSocket socket = null;
        try{
            socket = new DatagramSocket(PORT);
            System.out.println("UDP Server listening on port " + PORT);
        } catch (SocketException e) {}
        
        try{
            while(true){
            byte[] buf = new byte[512];
            DatagramPacket request = new DatagramPacket(buf, buf.length);
            socket.receive(request);
            
            String requestContent = new String(buf);
            System.out.println("Received request: " + requestContent);

            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();
            System.out.println("From client: " + clientAddress.getCanonicalHostName());
            
            String replyContent = QUOTE;
            byte[] replyBuf = replyContent.getBytes("UTF-8");
            System.out.println("Reply content: " + replyContent);

            DatagramPacket reply = new DatagramPacket(replyBuf, replyBuf.length, clientAddress, clientPort);
            System.out.println("Sending reply...");
            socket.send(reply);
            System.out.println("Reply sent");
            }
        } catch (IOException e) {}
        finally {
            socket.close();
        }
    }
}