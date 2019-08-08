package com.interview;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

class Result {

	/**
	 * Complete the 'decode' function below.
	 *
	 * The function is expected to return a STRING. The function accepts following
	 * parameters: 1. STRING_ARRAY codes 2. STRING encoded
	 * 
	 * 
7
a	100100
b	100101
c	110001
d	100000
[newline]	111111
p	111110
q	000001
111110000001100100111111100101110001111110
	 */

	public static String decode(List<String> codes, String encoded) {
		StringBuilder decoded = new StringBuilder();
		Map<String, String> encodedMap = new HashMap<>();
		
		for(String str : codes) {
			String[] split = str.split("\t");
			String value = "";
			String myStr = (String) split[0];
			if(myStr.equals("[newline]")) {
				value = "\n";
			} else {
				value = myStr;
			}
			encodedMap.put(split[1], value);
		}
		
		StringBuilder partial = new StringBuilder();
		for (int i = 0; i < encoded.length(); i++) {
			partial.append(encoded.charAt(i));
			if (encodedMap.containsKey(partial.toString())) {
				decoded.append(encodedMap.get(partial.toString()));
				partial = new StringBuilder();
			}
		}
		return decoded.toString();
	}
}

public class N2HuffmanDecoder {
	
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

		int codesCount = Integer.parseInt(bufferedReader.readLine().trim());

		List<String> codes = IntStream.range(0, codesCount).mapToObj(i -> {
			try {
				return bufferedReader.readLine();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}).collect(toList());

		String encoded = bufferedReader.readLine();

		String result = Result.decode(codes, encoded);

		System.out.println(result);

		bufferedReader.close();
	}
}
