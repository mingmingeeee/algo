import java.util.*;

class Solution {
    
    private static int[] nums;
    private static int answer;
    
    public int solution(int[] numbers, int target) {
        
        
        dfs(numbers, 0, 0, target);
    
        
        return answer;
    }
    
    private static void dfs(int[] numbers, int ans, int cnt, int target) {
        
        if(cnt == numbers.length) {
            if(ans == target)
                answer++;
            return;
        }
        
        dfs(numbers, ans + numbers[cnt], cnt + 1, target);
        dfs(numbers, ans + numbers[cnt] * -1, cnt + 1, target);
        
        
    }
    
}