public class 프로그래머스_디펜스게임 {

    private static int answer;
    public int solution(int n, int k, int[] enemy) {

        return binarySearch(n, k, enemy);
    }

    private static int binarySearch(int n, int k, int[] enemy) {
        int left = 0;
        int right = enemy.length;

        while(left < right) {
            int mid = (left + right) / 2;

            // mid까지 막을 수 있는지 확인
            if(isDefence(n, k, mid, enemy)) { // 있다면 left 값 변경
                left = mid + 1;
            } else { // 없다면 right 값 줄이기
                right = mid;
            }
        }

        return left;
    }

    // 정렬 후 막을 수 있나 확인
    // 정렬 이유: "mid"까지 막을 수 있나만 보기 위해서
    // 순서가 바뀌어도 상관 없음
    // 작은 것들 앞으로 보내고 n - cur 함
    // n > cur 이라면 막을 수 없으므로
    // "무적권" 스킬 사용 되면 true, 아니면 false
    private static boolean isDefence(int n, int k, int mid, int[] enemy) {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < mid + 1; i++) {
            list.add(enemy[i]);
        }

        Collections.sort(list);

        for(int i = 0; i < list.size(); i++) {
            Integer cur = list.get(i);

            if(cur <= n) {
                n -= cur;
                continue;
            }

            return k >= list.size() - i;
        }

        return true;
    }


}
