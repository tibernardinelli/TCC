import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import br.com.bernardinelli.ann.impl.RNA;
import br.com.bernardinelli.ann.impl.Trainable;
import br.com.bernardinelli.ann.input.DataFactory;
import br.com.bernardinelli.ann.input.Holdout;
import br.com.bernardinelli.ann.model.Data;
import br.com.bernardinelli.ann.model.Example;
import br.com.bernardinelli.ann.settings.Settings;
import br.com.bernardinelli.ann.test.Runner;
import br.com.bernardinelli.ann.util.ReaderUtil;


public class Main {

	public static void main(String[] args) throws IOException {
		Path path = Paths.get("data", "iris.data");
		List<Example> tuplas = ReaderUtil.readTupleFromFile(path);
		Data data = DataFactory.transform(tuplas);
		
		Settings.getInstance().setNormalizeWeight(true);
		RNA rna = new RNA(4, 2, 3);
		Trainable trainable = new Runner(rna, data);
		List<Example> tuples = data.getTuples();
		Collections.shuffle(tuples, new Random());
		new Holdout().run(tuples, trainable);
		
		
	
	}

}
