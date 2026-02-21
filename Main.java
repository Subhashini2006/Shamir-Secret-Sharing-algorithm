import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.*;

public class Main {

    static class Fraction {
        BigInteger num;
        BigInteger den;

        Fraction(BigInteger num, BigInteger den) {
            this.num = num;
            this.den = den;
        }

        Fraction multiply(Fraction f) {
            return new Fraction(this.num.multiply(f.num), this.den.multiply(f.den));
        }

        Fraction add(Fraction f) {
            BigInteger newNum = this.num.multiply(f.den).add(f.num.multiply(this.den));
            BigInteger newDen = this.den.multiply(f.den);
            return new Fraction(newNum, newDen);
        }

    
        BigInteger toBigInteger() {
            return this.num.divide(this.den);
        }
    }

    public static void main(String[] args) throws IOException {
       
        BufferedReader br = new BufferedReader(new FileReader("data2.json"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line);
        br.close();
        String content = sb.toString();

        Pattern patternK = Pattern.compile("\"k\"\\s*:\\s*(\\d+)");
        Matcher matcherK = patternK.matcher(content);
        int k = 0;
        if (matcherK.find()) k = Integer.parseInt(matcherK.group(1));

        Pattern patternPoint = Pattern.compile("\"(\\d+)\"\\s*:\\s*\\{\\s*\"base\"\\s*:\\s*\"(\\d+)\",\\s*\"value\"\\s*:\\s*\"([0-9a-fA-F]+)\"\\s*\\}");
        Matcher matcherPoint = patternPoint.matcher(content);

        List<BigInteger> xList = new ArrayList<>();
        List<BigInteger> yList = new ArrayList<>();
        int count = 0;

        while (matcherPoint.find() && count < k) {
            BigInteger x = new BigInteger(matcherPoint.group(1));
            int base = Integer.parseInt(matcherPoint.group(2));
            BigInteger y = new BigInteger(matcherPoint.group(3), base);
            xList.add(x);
            yList.add(y);
            count++;
        }

  
        Fraction c = new Fraction(BigInteger.ZERO, BigInteger.ONE);

        for (int i = 0; i < k; i++) {
            Fraction term = new Fraction(yList.get(i), BigInteger.ONE);

            for (int j = 0; j < k; j++) {
                if (i != j) {
                    BigInteger numerator = xList.get(j).negate(); // -x_j
                    BigInteger denominator = xList.get(i).subtract(xList.get(j)); // x_i - x_j
                    term = term.multiply(new Fraction(numerator, denominator));
                }
            }

            c = c.add(term);
        }

        System.out.println("Exact constant term (c): " + c.toBigInteger());
    }
}