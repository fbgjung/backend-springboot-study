# DFS 알고리즘

### DFS(Depth-First Search) 정의
DFS(Depth-First Search)는 그래프 탐색 알고리즘 중 하나로, 시작 노드에서 출발하여 한 방향으로 가능한 멀리까지 탐색을 진행한 후, 더 이상 갈 곳이 없으면 직전에 방문했던 노드로 돌아와 다른 방향을 탐색하는 방식입니다. 이 과정은 더 이상 방문할 노드가 없을 때까지 반복됩니다. 

DFS는 스택 자료구조를 사용하며, 재귀적으로 구현할 수도 있습니다.

### DFS 알고리즘의 작성 방법

1. 그래프 초기화:

    - 그래프를 인접 리스트나 인접 행렬로 초기화합니다. 인접 리스트는 각 노드에 연결된 다른 노드들을 저장한 리스트입니다.

2. 방문 여부 초기화:

    - 노드의 방문 여부를 저장할 배열을 초기화합니다. 배열의 크기는 노드의 수와 동일하게 설정합니다.

3. DFS 함수 작성:

    - DFS 함수는 현재 노드를 방문한 것으로 표시하고, 해당 노드와 연결된 모든 인접 노드를 재귀적으로 방문합니다.
    - 이미 방문한 노드는 다시 방문하지 않습니다.

4. DFS 실행:

    - 시작 노드를 설정하고, DFS 함수를 호출하여 탐색을 시작합니다.


### DFS의 간단한 코드 - 인접 행렬 예시
```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class DFS {
	static boolean[][] matrix;
    static boolean[] visit;
	static int N, M, V;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // 정점의 개수
		M = Integer.parseInt(st.nextToken()); // 간선의 개수
		V = Integer.parseInt(st.nextToken()); // 시작 정점
		
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
		visit = new boolean[N+1];
		dfs(V);
		System.out.println();
	}
	
	// stack 대신 동일한 효과 더 편한 재귀 호출을 이용한다.
	static void dfs(int n) { // n : 시작 정점
		visit[n] = true;
		
		// 필요한 처리 수행
		System.out.print(n + " ");
		
		// 계속 방문을 이어 간다.
		for (int i = 1; i <= N; i++) {
			if( ! matrix[n][i] || visit[i] ) continue; // 간선이 없거나 방문했으면 넘어간다.
			dfs(i);
		}
		
	}

}
```