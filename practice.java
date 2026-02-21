import java.io.*;
import java.util.*;
import java.util.regex.*;

public class practice {
   public static void main(String[] args) throws IOException {
      
        BufferedReader br = new BufferedReader(new FileReader("data.json"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        String content = sb.toString();

        Pattern patternK = Pattern.compile("\"k\"\\s*:\\s*(\\d+)");
        Matcher matcherK = patternK.matcher(content);
        int k = 0;
        if (matcherK.find()) {
            k = Integer.parseInt(matcherK.group(1));
        }

        Pattern patternPoint = Pattern.compile("\"(\\d+)\"\\s*:\\s*\\{\\s*\"base\"\\s*:\\s*\"(\\d+)\",\\s*\"value\"\\s*:\\s*\"(\\d+)\"\\s*\\}");
        Matcher matcherPoint = patternPoint.matcher(content);

        List<Integer> xList = new ArrayList<>();
        List<Integer> yList = new ArrayList<>();
        int count = 0;

        while (matcherPoint.find() && count < k) {
            int x = Integer.parseInt(matcherPoint.group(1));
            int base = Integer.parseInt(matcherPoint.group(2));
            int y = Integer.parseInt(matcherPoint.group(3), base); 
            xList.add(x);
            yList.add(y);
            count++;
        }

  
        double c = 0;
        for (int i = 0; i < k; i++) {
            double term = yList.get(i);
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    term *= (0 - xList.get(j)) / (double)(xList.get(i) - xList.get(j));
                }
            }
            c += term;
        }

        System.out.println("Constant term (c): " + c);
    }
}