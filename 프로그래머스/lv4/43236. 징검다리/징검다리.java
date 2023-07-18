import java.io.*;
import java.util.*;

class Solution {
    public int solution(int distance, int[] rocks, int n) {
        int answer = 0;
        
        Arrays.sort(rocks);
        
        int left = 0;
        int right = distance;
        while(left <= right) {
            
            int mid = (left + right) / 2;
            int prev = 0;
            int removeCnt = 0;
            
            for(int i = 0; i < rocks.length; i++) {
                if(rocks[i] - prev < mid) {
                    removeCnt++;
                } else {
                    prev = rocks[i];
                }
            }
            
            if(distance - prev < mid) {
                removeCnt++;
            }
            if(removeCnt <= n) { // 높으면 간격 높이기 
                answer = mid;
                left = mid + 1;
            }
            else // removeCnt가 더 크다면 n이하로 줄여야 하므로 간격 줄이기
                right = mid - 1;
            
            
        }
        
        return answer;
    }
}
// 출발지점부터 distance만큼 떨어진 곳에 도착지점
// 바위들 놓여있음 => 바위 중 몇 개 제거
