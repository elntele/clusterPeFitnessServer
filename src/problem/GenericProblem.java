package problem;

import java.io.Serializable;
import java.util.UUID;

import org.uma.jmetal.problem.Problem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.multiobjetivo.SearchForNetworkAndEvaluate;

public class GenericProblem implements Serializable {
	private Problem network;
	private UUID id;
	@JsonCreator
	public GenericProblem(@JsonProperty("network")Problem network) {
		super();
		this.network = network;
		this.id = UUID.randomUUID();
	}


	public Problem getNetwork() {
		return network;
	}

	public void setNetwork(SearchForNetworkAndEvaluate network) {
		this.network = network;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

}
