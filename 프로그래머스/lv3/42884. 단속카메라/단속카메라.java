import java.util.*;

class Solution {
    public int solution(int[][] routes) {
        
        // i번째 차량이 고속도로에 진입한 지점
        // i번째 차량이 고속도로에서 나간 지점 
        Arrays.sort(routes, new Comparator<int[]>() {
           @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[1] == o2[1]) {
                    return o1[0] - o2[0];
                }
                return o1[1] - o2[1];
            }
        });
        
        int count = 0;
        int prev = Integer.MIN_VALUE;
        for(int i = 0; i < routes.length; i++) {
            if(prev < routes[i][0]) {
                prev = routes[i][1];
                count++;
            }
        }
        
        //                                -5      -3
        //                   -14          -5
        //     -18              -13
        // -20            -15
        
        return count;
    }
}

// 고속도로 이동하는 모든 차량이 고속도로 이용하면서 단속용 카메라 만나도록 카메라 설치
// routes가 매개변수로 주어질 때, 모든 차량이 한 번은 단속용 카메라를 만나도록 하려면 최소 몇대?