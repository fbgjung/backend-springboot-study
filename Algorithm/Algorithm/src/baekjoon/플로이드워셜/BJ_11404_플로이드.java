package baekjoon.플로이드워셜;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BJ_11404_플로이드 {
	
	static final int INF = 10000000;
	static int N, M;
	static int[][] matrix;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine()); // 정점의 개수
		M = Integer.parseInt(br.readLine()); // 간선의 개수
		
		matrix = new int[N+1][N+1];
		
		// matrix를 모두 INF로 초기화
		for (int i = 1; i <= N; i++) {
			Arrays.fill(matrix[i], INF); // 충분히 큰 값으로 전체 초기화
		}

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if (i == j)
					matrix[i][j] = 0;
			}
		}
		
		// M개의 줄에 간선을 연결하는 두 정점 번호 입력
		for (int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int n1 = Integer.parseInt(st.nextToken());
			int n2 = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			matrix[n1][n2] = Math.min(matrix[n1][n2], c);
		}
		
		// 플로이드워셜
		for (int k = 1; k <= N; k++) { // 경유지
			for (int i = 1; i <= N; i++) { // 행
				for (int j = 1; j <= N; j++) { // 열
					matrix[i][j] = Math.min(matrix[i][j], matrix[i][k] + matrix[k][j]);
				}
			}
		}
		
		// 결과 출력
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				if(matrix[i][j] >= INF) 
					System.out.print(0 + " ");
				else
					System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}

	}

}
