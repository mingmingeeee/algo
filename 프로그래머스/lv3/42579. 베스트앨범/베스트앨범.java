import java.util.*;

class Solution {
    class Song {
        String genre;
        int playCnt;
        
        public Song(int playCnt, String genre) {
            this.playCnt = playCnt;
            this.genre = genre;
        }
    }
    public int[] solution(String[] genres, int[] plays) {
        
        Map<Integer, Song> map = new HashMap<>();
        Map<String, Integer> genreCnt = new HashMap<>();
        int n = genres.length;
        for(int i = 0; i < n; i++) {
            String genre = genres[i];
            Song song = new Song(plays[i], genre);
            int cnt = plays[i];
            if(genreCnt.get(genre) != null) {
                cnt += genreCnt.get(genre);
            }
            map.put(i, song);
            genreCnt.put(genre, cnt);
        }
        
        List<Integer> keySet = new ArrayList<>(map.keySet());
        List<String> stringKey = new ArrayList<>(genreCnt.keySet());
        Collections.sort(stringKey, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return genreCnt.get(o2) - genreCnt.get(o1);
            }
        });
        Collections.sort(keySet, new Comparator<Integer>() {
           @Override
            public int compare(Integer o1, Integer o2) {
                int a = genreCnt.get(map.get(o2).genre);
                int b = genreCnt.get(map.get(o1).genre);
                if(a == b) {
                    if(map.get(o2).playCnt == map.get(o1).playCnt) {
                        return o1 - o2;
                    }
                    return map.get(o2).playCnt - map.get(o1).playCnt;
                }
                return a - b;
            }
        });
        

        List<Integer> list = new ArrayList<>();
        for(String key : stringKey) {
            
            int cnt = 0;
            for(Integer k : keySet) {
                if(map.get(k).genre.equals(key)) {
                    if(cnt == 2) break;
                    list.add(k);
                    cnt++;
                }
            }
            
        }
       
    
        int[] answer = new int[list.size()];
        int i = 0;
        for(Integer k : list) {
            answer[i++] = k;
        }  
       
        return answer;
    }
}

// 노래 장르 genres, 노래별 재생 횟수 plays
// 베스트 앨범에 들어갈 노래의 고유 번호 순서대로 return

// 1. 속한 노래가 가장 많이 재생된 장르 
// 2. 장르 내에서 많이 재생된 노래 
// 3. 고유 번호가 낮은 노래