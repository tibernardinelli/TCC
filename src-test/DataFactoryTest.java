import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.bernardinelli.ann.input.DataFactory;
import br.com.bernardinelli.ann.model.Data;
import br.com.bernardinelli.ann.model.Example;

public class DataFactoryTest {

	private List<Example> tuplas;
	private List<Example> tuplaUnica;
	private List<Example> testNormalizacao;

	@Before
	public void setUp() throws Exception {
		tuplas = new ArrayList<Example>();
		tuplas.add(new Example("A", 1d, 1d));
		tuplas.add(new Example("B", 2d, 3d));

		tuplaUnica = new ArrayList<Example>();
		tuplaUnica.add(new Example("B", 2d, 3d, 5d));

		testNormalizacao = new ArrayList<Example>();
		testNormalizacao.add(new Example("B", 2d));
		testNormalizacao.add(new Example("B", 3d));
		testNormalizacao.add(new Example("B", 4d));
	}

	@Test
	public void objetoDataConsistenteTest() {
		Data data = DataFactory.transform(tuplas);
		String[] possibleResults = data.getPossibleResults();
		assertArrayEquals(new String[] { "A", "B" }, possibleResults);
		List<Example> tuples = data.getTuples();
		assertEquals(tuplas.size(), tuples.size());

		for (int i = 0; i < tuplas.size(); i++) {
			assertNotEquals(tuplas.get(0), tuples.get(0));
		}
	}

	@Test
	public void objetoDeveTerSidoClonadoTest() {
		Data data = DataFactory.transform(tuplaUnica);
		assertEquals(false, tuplaUnica.get(0) == data.getTuples().get(0));
	}

	@Test
	public void objetoUnicoNaoDeveTerValoresAlteradosTest() {
		Data data = DataFactory.transform(tuplaUnica);
		assertEquals(true, tuplaUnica.get(0).equals(data.getTuples().get(0)));
	}
	
	@Test 
	public void valoresNormalizadosTest(){
		Data data = DataFactory.transform(testNormalizacao);
		
		Example tuple = data.getTuples().get(0);
		assertEquals(-1.22d, tuple.getValue(0), 0.01);
		
		tuple = data.getTuples().get(1);
		assertEquals(0.00d, tuple.getValue(0), 0.01);
		
		tuple = data.getTuples().get(2);
		assertEquals(1.22d, tuple.getValue(0), 0.01);
		
	}

}
