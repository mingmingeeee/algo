import java.util.*;

class Solution {
    static class Song {
        int play;
        String genre;
        public Song(int play, String genre) {
            this.play = play;
            this.genre = genre;
        }
    }
    public int[] solution(String[] genres, int[] plays) {
        int[] answer = {};
        
        Map<Integer, Song> map = new HashMap<>();
        Map<String, Integer> gCnt = new HashMap<>();
        for(int i = 0; i < genres.length; i++) {
            String genre = genres[i];
            
            if(gCnt.get(genre) == null) {
                gCnt.put(genre, plays[i]);
            } else {
                gCnt.put(genre, gCnt.get(genre) + plays[i]);
            }
            map.put(i, new Song(plays[i], genre));
        }
        
        List<Integer> numList = new ArrayList<>(map.keySet());       
        List<String> gList = new ArrayList<>(gCnt.keySet());
     
        Collections.sort(gList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return gCnt.get(o2) - gCnt.get(o1);
            }
        });
        Collections.sort(numList, new Comparator<Integer>(){
            @Override
            public int compare(Integer o1, Integer o2) {
                int a = gCnt.get(map.get(o1).genre);
                int b = gCnt.get(map.get(o2).genre);
                if(a == b) {
                    if(map.get(o1).play == map.get(o2).play) {
                        return o1 - o2;
                    }
                    return map.get(o2).play - map.get(o1).play;
                }
                return b - a;
            }
        });
        
        List<Integer> list = new ArrayList<>();
        for(String g : gList) {
            
            int cnt = 0;
            for(Integer n : numList) {
                if(map.get(n).genre.equals(g)) {
                    list.add(n);
                    cnt++;
                }
                if(cnt == 2)
                    break;
            }
            
        }
        
        answer = new int[list.size()];
        int idx = 0;
        for(Integer n : list) {
            answer[idx++] = n;
        }
        
        return answer;
    }
}

// 장르 별로 가장 많이 재생된 노래를 두 개씩 모아 베스트 앨범 출시
// 노래: 고유 번호로 구분

// 1. 속한 노래가 많이 재생된 장르 먼저 수록
// 2. 장르 내에서 많이 재생된 노래 먼저 수록
// 3. 장르 내에서 재생 횟수가 같은 노래 중에는 고유 번호가 낮은 노래 먼저 수록