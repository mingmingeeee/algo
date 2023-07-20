import java.io.*;
import java.util.*;

class Solution {
    static int[] dx = {
        3, 0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3
    };
    static int[] dy = {
        1, 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 2
    };
    public String solution(int[] numbers, String hand) {
        String answer = "";
        
        // 1, 4, 7: 왼손
        
        // 3, 6, 9: 오른손
        
        // 2, 5, 8, 0: 키패드 위치에서 더 가까운 쪽
        // 길이가 같으면 손잡이에 다라 다름
        
        int type = 1;
        if(hand.equals("right"))
            type = 0;
    
        // left: type = 1
        // right: type = 0
        int curR = 11;
        int curL = 10;
        for(int i = 0; i < numbers.length; i++) {
            switch(numbers[i]) {
                case 1: case 4: case 7:
                    curL = numbers[i];
                    answer += "L";
                    break;
                    
                case 3: case 6:  case 9:
                    curR = numbers[i];
                    answer += "R";
                    break;
                    
                case 2: case 5: case 8: case 0:
                    int n = numbers[i];
                    int dest1 = Math.abs(dx[n] - dx[curR]) + Math.abs(dy[n] - dy[curR]);
                    double dest2 = Math.abs(dx[n] - dx[curL]) + Math.abs(dy[n] - dy[curL]);
                    
                    if(dest1 < dest2) {
                        curR = n;
                        answer += "R";
                    } else if(dest1 > dest2) {
                        curL = n;
                        answer += "L";
                    } else {
                        if(type == 0) {
                            curR = n;
                            answer += "R";
                        }
                        else {
                            curL = n;
                            answer += "L";
                     }
                    }
                    break;
                    
            }
        }
        
        return answer;
    }
}

// 맨 처음 왼손: *
// 오른손 엄지손가락: #