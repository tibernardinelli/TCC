import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.bernardinelli.ann.impl.RNA;
import br.com.bernardinelli.ann.settings.DefaultRandom;
import br.com.bernardinelli.ann.settings.Settings;

//Teste Integrado
public class RNATest {


	@Before
	public void setUp() throws Exception {
		Settings.getInstance().setRandom(new FalseRandomizer(0.2, 0.1, 0.4, 0.7, -1.2, 1.2, 1.1, 0.1, 3.1, 1.17));
	}

	@Test
	public void exemploDaAulaTest() {
		RNA rna = new RNA(3, 2, 2);
		double[] inputs = new double[]{10.0,20.0,30.0};
		List<double[]> trainningData = new ArrayList<double[]>(1);
		trainningData.add(inputs);
		rna.exercise(trainningData);
		double[] resultado = rna.apresentacao(inputs);
		assertEquals(2, resultado.length);
		assertTrue(resultado[0] > resultado[1]);
	}
	
	public void tearDown() throws Exception{
		Settings.getInstance().setRandom(new DefaultRandom());
	}

}
