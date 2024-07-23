package baekjoon.dfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_2606_바이러스 {

	static boolean[][] matrix;
	static boolean[] visit;
	static int N, M, cnt;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // 정점의 개수
		st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken()); // 간선의 개수
		
		matrix = new boolean[N+1][N+1];
		
		// M개의 줄에 간선을 연결하는 두 정점 번호 입력
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int n1 = Integer.parseInt(st.nextToken());
			int n2 = Integer.parseInt(st.nextToken());
			
			matrix[n1][n2] = true;
			matrix[n2][n1] = true;
		}

		
		// DFS 결과 출력
		visit = new boolean[N + 1];
		dfs(1);
		System.out.println(cnt-1); // 시작 정점 제외
	}
	
	static void dfs(int n) { // n : 시작 정점
		visit[n] = true;
		
		cnt++;
		
		for (int i = 1; i <= N; i++) {
			if( ! matrix[n][i] || visit[i] ) continue; // 간선이 없거나 방문했으면 넘어간다.
			dfs(i);
		}
		
	}

}
