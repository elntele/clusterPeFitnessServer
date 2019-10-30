//package server;
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.SocketException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import problem.GenericProblem;
//
//
//public class ServerOneByOneUDP {
//		
//
//	public static void main(String args[]){ 
//    	DatagramSocket aSocket = null;
//    	
//    	 ObjectMapper mapper = new ObjectMapper();
//         
//         
////         try {
//// 			temp = mapper.writeValueAsString(p);
//// 		} catch (JsonProcessingException e1) {
//// 			// TODO Auto-generated catch block
//// 			e1.printStackTrace();
//// 		}
//    	
//		try{
//	    	aSocket = new DatagramSocket(1234);
//					// create socket at agreed port
//	    	byte[] buffer = new byte[1000];
// 			while(true){
// 				// aqui o servidor se prepara para receber a msg
// 				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
//  				aSocket.receive(request); // aqui ele recebe
//  				int id=0;
//  				byte[] answer=null;
//  				String requestJson= new String(request.getData());
//  				GenericProblem  receivedGp= mapper.readValue(requestJson, GenericProblem.class);
//  				byte[] req = new byte[1000];
//  				// req=request.getData();
//  				Map<UUID, GenericProblem> gpList = new HashMap<UUID, GenericProblem>();
//  				if (gpList.containsKey(receivedGp.getId())) {
//  					GenericProblem p=gpList.get(id);
//  				p.getProblem().evaluate(null/* aqui vai entrar a solution*/);
//  					
//  					DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), 
//  		    				request.getAddress(), request.getPort());
//  		  				
//  		    			aSocket.send(reply);
//  				}else {
//  					gpList.put(receivedGp.getId(),receivedGp);
//  					String SucessNewInstanceProblem ="new stance criated";
//  					answer =SucessNewInstanceProblem.getBytes();
//  					DatagramPacket reply = new DatagramPacket(answer, answer.length, 
//  	  	    				request.getAddress(), request.getPort());
//  	    			aSocket.send(reply);
//  					
//  				}
//    		
//    		}
//		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
//		}catch (IOException e) {System.out.println("IO: " + e.getMessage());
//		}finally {if(aSocket != null) aSocket.close();}
//    }
//
//}
