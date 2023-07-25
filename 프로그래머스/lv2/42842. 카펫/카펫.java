class Solution {
    public int[] solution(int brown, int yellow) {
        int[] answer = new int[2];
        
        int n = brown + yellow;
        
        for(int i = 3; i < n; i++) {
            int j = n / i;
            
            if(n % i != 0)
                continue;
            if(j < 3)
                continue;
            
            int garo = Math.max(i, j);
            int sero = Math.min(i, j);
            int center = (garo - 2) * (sero - 2);
            
            if(center == yellow) {
                answer[0] = garo;
                answer[1] = sero;
                break;
            }
        }
        
        return answer;
    }
}

// 