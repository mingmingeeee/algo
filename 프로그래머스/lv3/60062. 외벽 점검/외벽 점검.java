import java.util.*;

class Solution {
    static int answer = -1;
    static int m, size;
    static int[] num;
    static boolean[] visited;
    static int[] weak2;
    static boolean isFound = false;
    
    public int solution(int n, int[] weak, int[] dist) {
        
        m = dist.length;
        size = weak.length;

        weak2 = new int[2 * size - 1];    
        for(int i = 0; i < size; i++) {
            weak2[i] = weak[i];
            if(i < size - 1)
                weak2[size + i] = weak[i] + n;
        }
        
        
        for(int i = 1; i <= m; i++) {
            num = new int[i];
            visited = new boolean[m];
            perm(0, n, dist, i);
            if(isFound) break;
        }
        
        
        return answer;
    }

    
    static void perm(int cnt, int n, int[] dist, int end) {
        
        if(isFound)
            return;
        
        if(cnt == end) {
            if(check()) {
                isFound = true;
                answer = end;
            }
            return;
        }
        
        for(int i = 0; i < m; i++) {
            if(visited[i]) continue;
            visited[i] = true;
            num[cnt] = dist[i];
            perm(cnt + 1, n, dist, end);
            visited[i] = false;
        }
        
    }
    
    static boolean check() {
        
        for(int i = 0; i < size; i++) {
            
            int nidx = 0;
            int idx = i, next;
            while(idx < i + size && nidx < num.length) {
                next = idx + 1;
                while(next < i + size && weak2[idx] + num[nidx] >= weak2[next]) {
                    next++;
                }
                idx = next;
                nidx++;
            }
            if(idx == i + size)
                return true;
        }
        
        return false;
    }
}


// 레스토랑 구조: 완전히 동그란 모양
// 외벽 총 둘레 n미터
// 몇몇 지점 => 취약한 지점들 존재
// 정북 방향 지점: 0 취약 지점의 위치는 이 지점으로부터 시계 방향으로 떨어진 거리 나타냄
// 출발 지점부터 시계, 혹은 반시계 방향으로 외벽 따라서만 이동

// 외벽 길이: n, 취약 지점의 위치가 담긴 배열: weak, 각 친구가 1시간 동안 이동할 수 있는 거리가 담긴 배열: dist

// 취약 지점을 점검하기 위해 보내야 하는 친구 수의 최소값