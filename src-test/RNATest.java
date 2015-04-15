import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.bernardinelli.ann.impl.RNA;
import br.com.bernardinelli.ann.impl.Trainable;
import br.com.bernardinelli.ann.input.DataFactory;
import br.com.bernardinelli.ann.input.Holdout;
import br.com.bernardinelli.ann.input.ModelValidationTechnique;
import br.com.bernardinelli.ann.model.Data;
import br.com.bernardinelli.ann.model.Tuple;
import br.com.bernardinelli.ann.test.Runner;
import br.com.bernardinelli.ann.util.ReaderUtil;

//Teste Integrado
public class RNATest {

	private Data data;

	@Before
	public void setUp() throws Exception {
		Path path = Paths.get("data", "iris.data");
		List<Tuple> tuples = ReaderUtil.readTupleFromFile(path);
		data = DataFactory.transform(tuples);

	}

	@Test
	public void holdoutTest() {
		RNA rna = new RNA(4, 2, 3);
		Trainable runner = new Runner(rna, data);
		
		ModelValidationTechnique holdout = new Holdout();
		holdout.run(data.getTuples(), runner);
		for (Tuple tupla: data.getTuples()){
			assertTrue(runner.test(tupla));
		}
	}

}
