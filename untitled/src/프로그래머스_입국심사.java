//import java.io.*;
//import java.util.*;
//
//class Solution {
//    public long solution(int n, int[] times) {
//
//        Arrays.sort(times);
//
//        long left = 0;
//        long right = (long) times[times.length - 1] * n; // 모든 사람이 가장 느리게 심사받을 때
//        long answer = right;
//
//        while(left <= right) {
//
//            long mid = (left + right) / 2;
//
//            long sum = 0;
//            for(int i = 0; i < times.length; i++) {
//                sum += mid / times[i]; // 심사관당 맡을 수 있는 입국자 수
//            } // 80
//
//
//            if(sum < n) { // 모두 검사 불가
//                left = mid + 1;
//            } else { // 모두 검사 가능
//                right = mid - 1;
//                answer = Math.min(answer, mid);
//            }
//
//        }
//
//        return answer;
//    }
//}
//
//// 모든 사람이 심사를 받는데 걸리는 시간을 최소로 !!!
//// 입국심사 n, 심사관이 한 명 심사하는데 걸리는 시간 배열 times
//// 모든 사람이 심사 받는데 걸리는 시간 최솟값 return