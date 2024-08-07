import java.util.*;

class Solution {
    public String[] solution(String[][] plans) {
        int n = plans.length;
        
        String[] ans = new String[n];
        List<int[]> ps = new ArrayList<>(); // startTime, index, remaining
        Stack<int[]> s = new Stack<>(); // index, remaining
        for (int i = 0; i < n; ++i) {
            int st = convertMinutes(plans[i][1]);
            int remaining = Integer.parseInt(plans[i][2]);
            ps.add(new int[]{st, i, remaining});
        }
        ps.sort((a, b) -> Integer.compare(a[0], b[0]));
        int surplus = 0;
        int idx = 0;
        for (int i = 0; i < n; ++i) {
            if (i != n - 1) {
                int timeGap = ps.get(i + 1)[0] - ps.get(i)[0];
                int newRemaining = ps.get(i)[2] - timeGap;
                if (newRemaining <= 0) {
                    ans[idx++] = plans[ps.get(i)[1]][0];
                    surplus += -newRemaining;
                    newRemaining = 0;
                }
                else {
                    s.push(new int[]{ps.get(i)[1], newRemaining});
                }
                while (!s.isEmpty() && surplus > 0) {
                    if (s.peek()[1] <= surplus) {
                        surplus -= s.peek()[1];
                        ans[idx++] = plans[s.peek()[0]][0];
                        s.pop();
                    }
                    else {
                        var cur = s.pop();
                        s.push(new int[]{cur[0], cur[1] - surplus});
                        surplus = 0;
                    }
                }
                if (s.isEmpty()) surplus = 0;
            }
            else {
                ans[idx++] = plans[ps.get(i)[1]][0];
            }
        }
        while (!s.isEmpty()) {
            var cur = s.pop();
            ans[idx++] = plans[cur[0]][0];
        }
        
        return ans;
    }
    
    int convertMinutes(String time) {
        String[] times = time.split(":");
        int hour = Integer.parseInt(times[0]);
        int minutes = Integer.parseInt(times[1]);
        return hour * 60 + minutes;
    }
}
