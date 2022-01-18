import java.util.*;

/*
  https://programmers.co.kr/learn/courses/30/lessons/92334
*/
class Solution {
    public int[] solution(String[] id_list, String[] report, int k) {
        int[] answer = new int[id_list.length];

        Map<String, Set<String>> reporters = new HashMap();


        for(int i = 0; i < report.length; i++) {
            String reporter, reported;
            reporter = report[i].split(" ")[0];
            reported = report[i].split(" ")[1];
            if(!reporters.containsKey(reported)) {
                reporters.put(reported, new HashSet<>());
            }
            reporters.get(reported).add(reporter);
        }

        for(String key : reporters.keySet()) {
            if(reporters.get(key).size() < k) continue;
            for(String reporter : reporters.get(key)) {
                answer[getIndex(reporter, id_list)]++;
            }
        }
        return answer;
    }
    
    public int getIndex(String id, String[] id_list) {
        for(int i = 0; i < id_list.length; i++) {
            if(id_list[i].equals(id)) return i;
        }
        return -1;
    }
}
