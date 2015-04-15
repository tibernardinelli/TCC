import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import br.com.bernardinelli.ann.model.Tuple;
import br.com.bernardinelli.ann.util.ReaderUtil;

public class ReaderUtilTest {

	private Path badFormatedFile;
	private Path castExceptionFile;
	private Path differentTuples;
	private Path wellFormatedFile;

	@Before
	public void setUp() throws Exception {
		badFormatedFile = Paths.get("data", "badFormatedFile");
		castExceptionFile = Paths.get("data", "castExceptionFile.data");
		differentTuples = Paths.get("data", "differentTuples.data");
		wellFormatedFile = Paths.get("data", "wellFormatedFile.data");
	}

	@Test(expected = IOException.class)
	public void inexistentFileTest() throws IOException {
		Path path = Paths.get("arquivo_não_existente");
		ReaderUtil.readTupleFromFile(path);
	}

	@Test
	public void wellFormatedFileTest() throws IOException {
		Collection<Tuple> tuples = ReaderUtil.readTupleFromFile(wellFormatedFile);
		assertEquals(2, tuples.size());
	}
	
	@Test(expected = NumberFormatException.class)
	public void castExceptionFileTest() throws IOException {
		ReaderUtil.readTupleFromFile(castExceptionFile);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void differentTuplesFileTest() throws IOException {
		ReaderUtil.readTupleFromFile(differentTuples);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void badFormatedFileTest() throws IOException {
		ReaderUtil.readTupleFromFile(badFormatedFile);
	}

}
