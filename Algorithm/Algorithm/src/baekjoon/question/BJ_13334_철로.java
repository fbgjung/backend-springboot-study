package baekjoon.question;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BJ_13334_철로 {
	
	static int N;  // 사람 수
	static long[][] arr;
	static PriorityQueue<Long> q = new PriorityQueue<>();

	public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        N = Integer.parseInt(br.readLine());
        arr = new long[N][2];
        
        // 입력 처리 : 작은 값이 arr[i][0], 큰 값이 arr[i][1]
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            arr[i][0] = Math.min(s, e);
            arr[i][1] = Math.max(s, e);
        }

        long l = Long.parseLong(br.readLine());  // 철로의 길이 d
        Arrays.sort(arr, (o1, o2) -> {  // end 오름차순 정렬, end가 같으면 start 오름 차순 정렬
            if (o1[1] == o2[1]) { return Long.compare(o1[0], o2[0]); }
            return Long.compare(o1[1], o2[1]);
        });

        int max = 0;
        for (int i = 0; i < N; i++) {
            long s = arr[i][0];
            long e = arr[i][1];
            if (e - s > l) { continue; }  // 집과 사무실의 거리가 d보다 먼 사람
            q.add(s);					  // d거리보다 짧은 사람 저장 
            while (!q.isEmpty() && q.peek() < e - l) { // end - d보다 start 지점이 작으면 (철로에 포함되지 않으면)
                q.poll();	// 제거
            }
            max = Math.max(max, q.size());
        }
        System.out.println(max);
    }
	
}

// 참고 : https://m.blog.naver.com/adamdoha/222282297962
//문제를 해결한 방법
//
//1. 우선 데이터의 위치가 무작위로 들어오므로, s<e의 형식을 갖추도록 min, max를 이용하여 데이터를 정렬하여 입력 받습니다.
//2. e가 작은 것을 먼저 탐색하도록 데이터를 전체 정렬합니다.
//3. e-s > L 인 것을 쳐내줍시다.
//4. pq.peek() < e-L 보다 작다면, 철로의 범위를 벗어나므로 pop을 해줍니다. 더 이상 조건을 만족하지 않을 때까지 반복합니다. 
//5. s를 PriorityQueue에 add 합니다.
//6. 남아있는 pq.size()의 최댓값을 구합니다.