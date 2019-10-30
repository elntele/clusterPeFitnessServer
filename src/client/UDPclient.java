//package client;
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//import java.net.SocketException;
//import java.util.UUID;
//
//import org.uma.jmetal.problem.Problem;
//import org.uma.jmetal.solution.IntegerSolution;
//
//import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import br.multiobjetivo.SearchForNetworkAndEvaluate;
//import problem.GenericProblem;
//public class UDPclient {
//	public static void main(String[] args) {
//		// args give message contents and destination hostname
//		DatagramSocket aSocket = null;
//		Problem<IntegerSolution> problem;
//		problem =  (Problem<IntegerSolution>) new SearchForNetworkAndEvaluate();
//		String temp=null;
//		GenericProblem p = new GenericProblem();
//        ObjectMapper mapper = new ObjectMapper();
//        p.setId(UUID.randomUUID());
//        
//        try {
//			temp = mapper.writeValueAsString(p);
//		} catch (JsonProcessingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//
//		try {
////		String msg = "aula SD";
////			
//		aSocket = new DatagramSocket();
//		byte[] m = temp.getBytes();
//		InetAddress aHost = InetAddress.getByName("localhost");
//		int serverPort = 1234;
//		DatagramPacket request = new DatagramPacket(m, temp.length(), aHost, serverPort);
//		aSocket.send(request);// aqui ele envia 
//		// aqui ele se prepara pra receber a o resposta
//		byte[] buffer = new byte[1000];
//		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
//		aSocket.receive(reply);// aqui ele recebe
//		System.out.println("Reply: " + new String(reply.getData()));
//		} catch (SocketException e) {
//		System.out.println("Socket: " + e.getMessage());
//		} catch (IOException e) {
//		System.out.println("IO: " + e.getMessage());
//		} finally {
//		if (aSocket != null)
//		aSocket.close();
//		}
//		}
//		// TODO Auto-generated method stub
//
//}
