package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.IntegerSolution;
import org.uma.jmetal.solution.impl.DefaultIntegerSolution;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.cns.model.GmlData;
import br.multiobjetivo.SearchForNetworkAndEvaluate;
import cbic15.Kmeans;
import cbic15.Pattern;
import problem.GenericProblem;

public class serverEvaluateGroupTCP {

	public static void main(String args[]) {

		try {
			int serverPort = 7896; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			Map<UUID, GenericProblem> gpMap = new HashMap<UUID, GenericProblem>();

			while (true) {
				Socket clientSocket = listenSocket.accept();
				Connection c = new Connection(clientSocket, gpMap);
			}
		} catch (IOException e) {
			System.out.println("Listen socket:" + e.getMessage());
		}
	}
}

class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	Map<UUID, GenericProblem> gpMap;
	String msg;
	boolean createProblemStance = true;

	public Connection(Socket aClientSocket, Map<UUID, GenericProblem> gpMap) {
		this.gpMap = gpMap;
		try {
			clientSocket = aClientSocket;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			int length = in.readInt(); // read length of incoming message
			if (length > 0) {
				byte[] message = new byte[length];
				in.readFully(message, 0, message.length); // read the message
				String s = new String(message, StandardCharsets.US_ASCII);
				msg = s;
			}

			this.start();
		} catch (IOException e) {
			System.out.println("Connection:" + e.getMessage());
		}
	}

	public void run() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String requestJson = msg;
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		System.out.println(requestJson);
		List<String> l1 = null;

		try {
			l1 = mapper.readValue(requestJson, new TypeReference<List<String>>() {
			});
		} catch (JsonParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// the l1 list's head contains the order to execute operation
		if (l1.get(0).equals("createproblem")) {

			try { // an echo server
				Kmeans kmeans = mapper.readValue(l1.get(1), Kmeans.class);
				GmlData gml = mapper.readValue(l1.get(2), GmlData.class);
				List<Pattern>[] clustters = mapper.readValue(l1.get(3), new TypeReference<List<Pattern>[]>() {
				});
				// mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);//novo
				String prop1 = mapper.readValue(l1.get(4), String.class);

				// System.out.println(prop1);
				prop1.trim();
				prop1 = prop1.replaceAll("\\s+", "");
				prop1 = prop1.replace("{", "");
				prop1 = prop1.replace("}", "");

				String[] prop2 = prop1.split(",");
				Properties prop = new Properties();
				for (int i = 0; i < prop2.length; i++) {
					String[] bufer = prop2[i].split("=");
					prop.setProperty(bufer[0], bufer[1]);
				}

				Problem<IntegerSolution> problem;
				problem = new SearchForNetworkAndEvaluate(kmeans, gml, clustters, prop);
				GenericProblem receivedGp = new GenericProblem(problem);
				gpMap.put(receivedGp.getId(), receivedGp);
				String SucessNewInstanceProblem = receivedGp.getId().toString();
				this.createProblemStance = false;
				out.writeUTF(SucessNewInstanceProblem);

			} catch (EOFException e) {
				System.out.println("EOF:" + e.getMessage());
			} catch (IOException e) {
				System.out.println("readline:" + e.getMessage());
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e) {
					/* close failed */}
			}

		}

		if (l1.get(0).equals("EvaluateSolution")) {

			try { // an echo server
				List<String> l = mapper.readValue(requestJson, new TypeReference<List<String>>() {
				});
				List<DefaultIntegerSolution> population = mapper.readValue(l.get(2),
						new TypeReference<List<DefaultIntegerSolution>>() {
						});
				System.out.println("recebi para avaliar " + population.size());
				UUID id = UUID.fromString(l.get(1));
				GenericProblem p = gpMap.get(id);
				for (DefaultIntegerSolution s : population) {
					p.getNetwork().evaluate((IntegerSolution) s);
				}
				String backtoclient = mapper.writeValueAsString(population);
				byte[] b = backtoclient.getBytes(StandardCharsets.UTF_8);
				out.writeInt(b.length); // write length of the message
				out.write(b);
			} catch (EOFException e) {
				System.out.println("EOF:" + e.getMessage());
			} catch (IOException e) {
				System.out.println("readline:" + e.getMessage());
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e) {
					/* close failed */}
			}

		}

		if (l1.get(0).equals("AreYouOnline")) {
			try { // an echo server
				String backtoclient = "true";
				byte[] b = backtoclient.getBytes(StandardCharsets.UTF_8);
				out.writeInt(b.length); // write length of the message
				out.write(b);
			} catch (EOFException e) {
				System.out.println("EOF:" + e.getMessage());
			} catch (IOException e) {
				System.out.println("readline:" + e.getMessage());
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e) {
					/* close failed */}
			}

		}
		
		if (l1.get(0).equals("DoYouHaveThisIdProblem")) {
			try { // an echo server
				if(this.gpMap.containsKey(UUID.fromString(l1.get(1)))){
					String backtoclient = "true";
					byte[] b = backtoclient.getBytes(StandardCharsets.UTF_8);
					out.writeInt(b.length); // write length of the message
					out.write(b);
				}else {
					String backtoclient = "false";
					byte[] b = backtoclient.getBytes(StandardCharsets.UTF_8);
					out.writeInt(b.length); // write length of the message
					out.write(b);
					
				}
				
				
				
			} catch (EOFException e) {
				System.out.println("EOF:" + e.getMessage());
			} catch (IOException e) {
				System.out.println("readline:" + e.getMessage());
			} finally {
				try {
					clientSocket.close();
				} catch (IOException e) {
					/* close failed */}
			}

		}
	}

}
