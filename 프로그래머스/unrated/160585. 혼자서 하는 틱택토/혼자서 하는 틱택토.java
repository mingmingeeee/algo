import java.util.*;

class Solution {
    
    static int[] dx = {0, 1, 1};
    static int[] dy = {1, 0, 1};
    
    public int solution(String[] board) {
        
        boolean o = win(board, 'O');
        boolean x = win(board, 'X');
        if(o && x)
            return 0;
        
        int oCnt = 0;
        int xCnt = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(board[i].charAt(j) == 'O') {
                    oCnt++;      
                } else if (board[i].charAt(j) == 'X') {
                    xCnt++;
                }
            }
        }
        
        if(o && oCnt - xCnt != 1)
            return 0;
        if(x && xCnt - oCnt != 0)
            return 0;
        
        if(xCnt > oCnt || oCnt - xCnt > 1)
            return 0;
        
        return 1;
    }
    
    static boolean win(String[] board, char target) {
        
        // 가로
        for(int i = 0; i < 3; i++) {
            if(board[i].charAt(0) == target && board[i].charAt(0) == board[i].charAt(1) && board[i].charAt(1) == board[i].charAt(2))
                return true;
        }
        
        // 세로
        for(int i = 0; i < 3; i++) {
            if(board[0].charAt(i) == target && board[0].charAt(i) == board[1].charAt(i) && board[1].charAt(i) == board[2].charAt(i))
                return true;
        }
        
        // 대각선
        if(board[0].charAt(0) == target && board[0].charAt(0) == board[1].charAt(1) && board[1].charAt(1) == board[2].charAt(2))
            return true;
        
        if(board[0].charAt(2) == target && board[0].charAt(2) == board[1].charAt(1) && board[1].charAt(1) == board[2].charAt(0))
            return true;
        
        return false;
        
    }
    
}

// 가로, 세로, 대각선 => 3개가 같은 표시가 만들어지면 같은 표시 만든 사람이 승리
// 9칸 모두 차서 더 이상 표시 x => 무승부로 종료
// 혼자 선공, 후공 둘 다 밭음
// O, X 번갈아가면서 표시 진행
// 실수: O를 표시할 차례인데 X 표시하거나 반대로 X표시할 차례인데 O 표시하거나 
// 선공이나 후공 승리해서 게임 종료되었음에도 진행하거나

// 이 게임판이 규칙을 지켜서 틱텍토를 진행했을 때 나올 수 있는 게임 상황이라면 1
// 아니라면 0 return 