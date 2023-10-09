import java.util.*;

class Solution {
    public int solution(String[][] book_time) {
        int answer = 0;
        
        int[] arr = new int[9999];
        
        int[][] times = new int[book_time.length][2];
        
        for(int i = 0; i < book_time.length; i++) {
            times[i] = convertTime(book_time[i]);
        }
        
        for(int i = 0; i < times.length; i++) {
            for(int j = times[i][0]; j <= times[i][1]; j++) {
                arr[j]++;
            }
        }
        
        for(int i = 0; i < arr.length; i++) {
            answer = Math.max(answer, arr[i]);
        }
        
        
        return answer;
    }
    static int[] convertTime(String[] books) {
        int start = Integer.parseInt(books[0].substring(0, 2)) * 60 + Integer.parseInt(books[0].substring(3, 5));
        int end = Integer.parseInt(books[1].substring(0, 2)) * 60 + Integer.parseInt(books[1].substring(3, 5)) + 9;
        
        return new int[] {start, end};
    }
}

// 한 번 사용한 객실은 퇴실 시간 기준 10분 청소 후 다음 손님 사용 가능
// book_time이 매개변수 => 코니에게 필요한 최소 객실 수 return
