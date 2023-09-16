import java.io.*;
import java.util.*;

// dfs로 했는데 시간 초과 그도 그럴 것이 길이들이...
// 이분 탐색 => 특정 범위 내의 탐색 log(N)의 시간 

class Solution {
    private static int answer;
    public int solution(int n, int k, int[] enemy) {
        
        return binarySearch(n, k, enemy);
    }
    
    private static int binarySearch(int n, int k, int[] enemy) {
        int left = 0;
        int right = enemy.length;
        
        while(left < right) {
            int mid = (left + right) / 2;
            
            // mid까지 막을 수 있는지 확인
            if(isDefence(n, k, mid, enemy)) { // 있다면 left 값 변경
                left = mid + 1;
            } else { // 없다면 right 값 줄이기 
                right = mid;
            }
        }
        
        return left;
    }
    
    private static boolean isDefence(int n, int k, int mid, int[] enemy) {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < mid + 1; i++) {
            list.add(enemy[i]);
        }
        
        Collections.sort(list);
        
        for(int i = 0; i < list.size(); i++) {
            Integer cur = list.get(i);
            
            if(cur <= n) {
                n -= cur;
                continue;
            }
            
            return k >= list.size() - i;
        }
        
        return true;
    }
    
    
}

// 무적권: 최대한 적군의 수가 많을 때 사용 
// 적군의 수가 적은 라운드는 병사로 막고
// 적군 수가 많으면 무적권으로 막음 => 병사들로 막을 수 없을 때 
