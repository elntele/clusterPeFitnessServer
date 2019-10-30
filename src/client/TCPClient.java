//package client;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.EOFException;
//import java.io.IOException;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.util.UUID;
//
//import org.uma.jmetal.problem.Problem;
//import org.uma.jmetal.solution.IntegerSolution;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import br.multiobjetivo.SearchForNetworkAndEvaluate;
//import problem.GenericProblem;
//
//public class TCPClient {
//	
//	public static void main (String args[]) {
//		// arguments supply message and hostname
//		Socket s = null;
//		
//		Problem<IntegerSolution> problem;
//		problem =  (Problem<IntegerSolution>) new SearchForNetworkAndEvaluate();
//		GenericProblem p = new GenericProblem();
//        ObjectMapper mapper = new ObjectMapper();
//        p.setId(UUID.randomUUID());
//        String textOut=null;
//        try {
//        	textOut = mapper.writeValueAsString(p);
//		} catch (JsonProcessingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		String adress= "localhost";
//		try{
//			int serverPort = 7896;
//			s = new Socket(adress, serverPort);    
//			DataInputStream in = new DataInputStream( s.getInputStream());
//			DataOutputStream out =new DataOutputStream( s.getOutputStream());
//			out.writeUTF(textOut);      	// UTF is a string encoding see Sn. 4.4
//			String data = in.readUTF();	    // read a line of data from the stream
//			System.out.println("Received: "+ data) ; 
//		}catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
//		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
//		}catch (IOException e){System.out.println("readline:"+e.getMessage());
//		}finally {if(s!=null) try {s.close();}catch (IOException e){System.out.println("close:"+e.getMessage());}}
//     }
//
//}
