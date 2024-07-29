package baekjoon.플로이드워셜;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_1956_운동 {

	static final int INF = 10000000;
	static int V, E;
	static int[][] matrix;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		// matrix 초기화
		matrix = new int[V+1][V+1];
		
		for (int i = 1; i <= V; i++) {
			for (int j = 1; j <= V; j++) {
				if(i != j) matrix[i][j] = INF;
			}
		}
		
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int n1 = Integer.parseInt(st.nextToken());
			int n2 = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			matrix[n1][n2] = c;
		}
		
		// 플로이드워셜
		for (int k = 1; k <= V; k++) {	// 경유지
			for (int i = 1; i <= V; i++) { // 행
				for (int j = 1; j <= V; j++) { // 열
					matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
				}
			}
		}
		
		// 최소 사이클 도로 길이의 합 출력 (운동 경로 찾는 것이 불가능할 경우 -1 출력)
		int distance = INF;
		for (int i = 1; i <= V; i++) {
			for (int j = 1; j <= V; j++) {
				if(i != j && matrix[i][j] < INF && matrix[j][i] < INF) {
					distance = Math.min(distance, matrix[i][j] + matrix[j][i]);
				}
			}
		}
		
		if(distance >= INF)
			System.out.println(-1);
		else
			System.out.println(distance);
		
	}

}
