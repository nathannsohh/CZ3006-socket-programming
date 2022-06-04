import java.net.*;
import java.io.IOException;

public class Rfc865UdpClient {
    
    static final int PORT = 17;
    static String SERVER_NAME = "localhost";
    public static void main(String[] argv) {
        
        /* Open UDP Socket */
        DatagramSocket socket = null;
        try {
            InetAddress serverAddress = InetAddress.getLocalHost();
            socket = new DatagramSocket();
            socket.connect(serverAddress, PORT);
            System.out.println("UDP Client connected on port " + PORT + " to server: " + serverAddress.getCanonicalHostName());
        } catch (SocketException e) {} catch (UnknownHostException e) {}
        
        try {
            while(true){
                String content = "Nathan Soh, SS4, " + InetAddress.getLocalHost().getHostAddress();
                byte[] buf = content.getBytes("UTF-8");
                System.out.println("Content to send: " + content);

                DatagramPacket request = new DatagramPacket(buf, buf.length);
                System.out.println("Sending request...");
                socket.send(request);
                System.out.println("Request Sent to server");
                
                byte[] replyBuf = new byte[512];
                DatagramPacket reply = new DatagramPacket(replyBuf, replyBuf.length);
                System.out.println("Waiting for reply...");
                socket.receive(reply);

                String replyContent = new String(replyBuf);
                System.out.println("Received reply: " + replyContent);
            }
            } catch (IOException e) {}
            finally{
                socket.close();
            }
        }
       
}
