package br.com.bernardinelli.ann.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.bernardinelli.ann.model.Example;

public abstract class ReaderUtil {

	public static List<Example> readTupleFromFile(Path path)
			throws IOException {
		Stream<String> in = Files.lines(path);
		try {
			return in
					.map(line -> {
						if (line == null || line.equals(""))
							return null;
						String[] innerSplitedLine = line.split(",");
						int args = innerSplitedLine.length - 1;
						if (args >= 2)
							throw new IllegalArgumentException();
						double[] params = new double[args];
						for (int i = 0; i < args; i++) {
							params[i] = Double.parseDouble(innerSplitedLine[i]);
						}
						return Example.parseTuple(
								innerSplitedLine[innerSplitedLine.length - 1],
								params);

					}).collect(Collectors.toList());

		} finally {
			in.close();
		}
	}
}
