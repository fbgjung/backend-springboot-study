package baekjoon.question;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class BJ_4358_생태학 {
	
	static HashMap<String, Integer> trees = new HashMap<>();
	static int totalCnt = 0;
	static String treeType;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while( true ) {
			treeType = br.readLine();
			
			if( treeType == null || treeType.length() == 0 ) break;
			
			trees.put(treeType, trees.getOrDefault(treeType, 0) + 1);
			totalCnt++;
			
		}
		
		Object[] keys = trees.keySet().toArray();
		Arrays.sort(keys);
		for (Object key : keys) {
			 String tree = (String) key;
	         int treeSize = trees.get(tree);
			System.out.println( tree + " " + String.format("%.4f", (double) ((trees.get(key) * 100.0) / totalCnt)) );
		}
		
	}
	
}
