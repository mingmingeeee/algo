class Solution {

    public int solution(int[][] board, int[][] skill) {
        int answer = 0;
        int n = board.length;
        int m = board[0].length;
        int[][] sum = new int[n + 1][m + 1];
        
        for(int i = 0; i < skill.length; i++) {
            int type = skill[i][0];
            int r1 = skill[i][1], c1 = skill[i][2];
            int r2 = skill[i][3], c2 = skill[i][4];
            int degree = skill[i][5];
            if(type == 1) { // 공격
                sum[r1][c1] -= degree;
                sum[r2 + 1][c1] += degree;
                sum[r1][c2 + 1] += degree;
                sum[r2 + 1][c2 + 1] -= degree;
            } else { // 회복
                sum[r1][c1] += degree;
                sum[r2 + 1][c1] -= degree;
                sum[r1][c2 + 1] -= degree;
                sum[r2 + 1][c2 + 1] += degree;
            }
        }
        
        for(int i = 0; i < m + 1; i++) {
            for(int j = 0; j < n; j++) {
                sum[j + 1][i] += sum[j][i];
            }
        }
        
        for(int i = 0; i < n + 1; i++) {
            for(int j = 0; j < m; j++) {
                sum[i][j + 1] += sum[i][j];
            }
        }
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(board[i][j] + sum[i][j] > 0) answer++;
            }
        }
        
        return answer;
    }
}

// skill: type, r1, c1, r2, c2, degree
// [1, 0, 0, 3, 4, 4]
// type: 1
// (r1, c1) : (0, 0)
// (r2, c2) : (3, 4)
// degree: 4    