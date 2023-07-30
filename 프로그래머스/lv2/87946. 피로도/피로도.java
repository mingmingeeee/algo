class Solution {
    
    private static int[] nums;
    private static boolean[] visited;
    private static int n;
    private static int answer;
    
    public int solution(int k, int[][] dungeons) {

        n = dungeons.length;
        nums = new int[n];
        visited = new boolean[n];
        
        dfs(0, dungeons, k);
        
        return answer;
    }
    
    private static void dfs(int cnt, int[][] dungeons, int k) {
        if(cnt == n) {
            // 검사
            int cur = k;
            int ans = 0;
            for(int i = 0; i < n; i++) {
                int idx = nums[i];
                if(cur < dungeons[idx][0]) break;
                cur -= dungeons[idx][1];
                ans++;
            }
            answer = Math.max(answer, ans);
            return;
        }
        
        for(int i = 0; i < n; i++) {
            if(visited[i]) continue;
            visited[i] = true;
            nums[cnt] = i;
            dfs(cnt + 1, dungeons, k);
            visited[i] = false;
        }
    }
    
}

// 최소 필요 피로도
// 최소한 피로도
// 소모 피로도
// 던전 탐험후 소모되는 피로도 
