import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.bernardinelli.ann.input.DataFactory;
import br.com.bernardinelli.ann.model.Data;
import br.com.bernardinelli.ann.model.Tuple;

public class DataFactoryTest {

	private List<Tuple> tuplas;
	private List<Tuple> tuplaUnica;
	private List<Tuple> testNormalizacao;

	@Before
	public void setUp() throws Exception {
		tuplas = new ArrayList<Tuple>();
		tuplas.add(new Tuple("A", 1d, 1d));
		tuplas.add(new Tuple("B", 2d, 3d));

		tuplaUnica = new ArrayList<Tuple>();
		tuplaUnica.add(new Tuple("B", 2d, 3d, 5d));

		testNormalizacao = new ArrayList<Tuple>();
		testNormalizacao.add(new Tuple("B", 2d));
		testNormalizacao.add(new Tuple("B", 3d));
		testNormalizacao.add(new Tuple("B", 4d));
	}

	@Test
	public void objetoDataConsistenteTest() {
		Data data = DataFactory.transform(tuplas);
		String[] possibleResults = data.getPossibleResults();
		assertArrayEquals(new String[] { "A", "B" }, possibleResults);
		List<Tuple> tuples = data.getTuples();
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
		
		Tuple tuple = data.getTuples().get(0);
		assertEquals(-1.22d, tuple.getValue(0), 0.01);
		
		tuple = data.getTuples().get(1);
		assertEquals(0.00d, tuple.getValue(0), 0.01);
		
		tuple = data.getTuples().get(2);
		assertEquals(1.22d, tuple.getValue(0), 0.01);
		
	}

}
