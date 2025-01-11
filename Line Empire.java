import java.util.*;
import java.io.*;

import static java.lang.Math.*;

public class Main {

    private static final int mod = (int) 1e9 + 7; //mod
    private static final int iif = 998244353; // int infinity!

    public static void main(String[] args) {
        try {
            FastReader in = new FastReader();
            FastWriter out = new FastWriter();

            Comparator<Integer> com = Comparator.comparingInt(i -> i%2);

            int testcases = 1;

            testcases = in.nextInt();

//<------------------------ Main Code starts Here-------------------------------------------->

            while(testcases-- > 0) {

                int siz = in.nextInt();

                int a = in.nextInt();

                int b = in.nextInt();

                Integer[] nums = in.nextIntArray(0,siz);

                // what are the states that are changing?
                // idx and currCapital are the states that are changing!
                // let's memoize them!
                // the at most value of the currentCapital is the last value in the array as the array is already sorted!
                // but due to high values of array it gives MLE

                // greedy Sol!

                /*
                    Considering every index as capital, and then we can obtain a formula in-order to calculate the cost.
                    That is required to conquer all kingdoms as assuming the current capital as permanent capital!
                    So, we can say that this can be done using a greedy approach!
                 */

                /*
                     We can obtain a formula!
                     I would highly recommend you to obtain that formula by yourselves!
                     but any ways to understand that how the formula is obtained I will take an example!
                     let a,b be cost to change the capital and to conquer a kingdom from curr capital!
                     x1 x2 x3 x4 x5 be the kingdoms then
                     assuming 0 as the permanent capital, then answer is : (x1+x2+..+x5)*b
                     assuming x1 as the permanent capital, then answer is : x1*a+x1*b+(x2-x1)*b+(x3-x1)*b+..(x5-x1)*b -> simplified as x1*a+(x2+x3+x4+...x5)*b-3*x1*b
                     So, in this way only the remaining terms are also obtained!
                 */

                long[] pre = new long[siz];

                pre[0] = nums[0];

                for(int i = 1; i < siz; i++) {

                    pre[i] = pre[i-1]+nums[i]; // calculating prefix according to the formula!

                }

                long ans = pre[siz-1]*b;

                int len = siz-1;

                for(int i = 0; i < siz; i++) {

                    long curr_i_as_capital = (long) nums[i] * a + (pre[siz-1]-pre[i])*b- (long) (len - (i + 1)) *b*nums[i]; // obtained this according to the formula!

                    ans = min(curr_i_as_capital,ans); // taking min among all of them!

                }

                out.println(ans);
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
//    private static long ans(int idx, int currCapital, int costOfCapital, int costToCapture, Integer[] nums,long[][] dp) {
//        // dp solution!
//        if(idx == nums.length) {
//            return 0;
//        }
//        if(dp[idx][currCapital] != -1) return dp[idx][currCapital];
//        long changeCapital = (long) costOfCapital *(abs(currCapital-nums[idx]))+((long) costToCapture *(abs(currCapital-nums[idx])))+ans(idx+1,nums[idx],costOfCapital,costToCapture,nums,dp);
//        long dontChange = ((long) costToCapture *(abs(currCapital-nums[idx])))+ans(idx+1,currCapital,costOfCapital,costToCapture,nums,dp);
//        return dp[idx][currCapital] = min(changeCapital,dontChange);
//    }
//<--------------------------------------Fast reader----------------------------------------->
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return str;
        }

        public Integer[] nextIntArray(int start, int end) {
            Integer[] nums = new Integer[end];
            for(int i = start; i < end; i++) {
                nums[i] = Integer.parseInt(next());
            }
            return nums;
        }

        public Pair[] nextPairArray(int start,int siz) {
            Pair[] nums = new Pair[siz];
            for(int i = start; i < siz; i++) {
                int v1 = Integer.parseInt(next());
                int v2 = Integer.parseInt(next());
                nums[i] = new Pair(v1,v2);
            }
            return nums;
        }

        public Long[] nextLongArray(int start,int siz) {
            Long[] nums = new Long[siz];
            for(int i = start; i < siz; i++) {
                nums[i] = Long.parseLong(next());
            }
            return nums;
        }

    }

//<---------------------------------------Fast writer----------------------------------------->

    static class FastWriter {
        private final BufferedWriter bw;

        public FastWriter() {
            this.bw = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        public void print(Object object) throws IOException {
            bw.append("" + object);
        }

        public void println() throws IOException {
            bw.append("\n");
        }

        public void println(Object object) throws IOException {
            print(object);
            bw.append("\n");
        }

        public void close() throws IOException {
            bw.close();
        }
    }

//<--------------------------------------------Some helper methods---------------------------------------->

    private static long kadane(int l, int r, int[] nums) {
        long sum = 0;
        long mSum = 0;
        for(int i = l; i < r; i++) {
            if(sum < 0) sum = nums[i];
            else sum += nums[i];
            mSum = max(sum,mSum);
        }
        return mSum;
    }

    public static List<Integer> reversed(List<Integer> lis) {
        List<Integer> ans = new ArrayList<>();
        for(int i = lis.size()-1; i >= 0; i--) {
            ans.add(lis.get(i));
        }
        return ans;
    }

    private static void getFactors(int num, Map<Integer, Integer> map) {

        for(int i = 2; i <= (int) Math.sqrt(num); i++) {
            while(num%i == 0) {
                map.put(i,map.getOrDefault(i,0)+1);
                num /= i;
            }
        }
        if(num > 1) map.put(num,map.getOrDefault(num,0)+1);
    }

    public static void print(boolean cond,FastWriter out) throws IOException {
        out.println(cond ? "YES" : "NO");
    }

    public static long fastPow(long b, long e) {
        long res = 1;
        while (e > 0) {
            if (odd(e)) res = (res * b) % mod;
            b = (b * b) % mod;
            e = e >> 1;
        }
        return res;
    }

    public static int charToInt(char c) {
        return (c - '0');
    }

    public static char intToChar(int n) {
        return (char) (n + 48);
    }

    public static int[] sieve(int upto) {
        int[] primes = new int[upto + 1];
        Arrays.fill(primes, 1);
        for (int i = 2; i * i <= upto; i++) {
            if (primes[i] == 1)
                for (int j = i * i; j <= upto; j += i) {
                    primes[j] = 0;
                }
        }
        return primes;
    }

    private static long fact(int num) {
        long v = 1;
        for(int i = 1; i <= num; i++) {
            v = (v*i)%mod;
        }
        return v;
    }

    private static long gcd(long a, long b) {
        if (b != 0) return gcd(b, (a % b));
        return a;
    }

    private static int gcd(int a, int b) {
        if (b != 0) return gcd(b, (a % b));
        return a;
    }

    private static long getSum(int startNum, int commonDiff, long range) {
        long lastNum = startNum + (range - 1) * commonDiff;
        return range * (startNum + lastNum) / 2;
    }

    private static boolean odd(long siz) {
        return (siz & 1) == 1;
    }

    private static boolean powOf2(long siz) {
        return (siz & (siz - 1)) == 0;
    }

    private static List<Integer> primeFactors(int siz) {

        List<Integer> sizs = new ArrayList<>();
        int[] p;
        p = sieve(siz);
        for (int i = 2; i <= siz; i++) {
            while (siz % i == 0 && p[i] == 1) {
                sizs.add(i);
                siz /= i;
            }
        }
        return sizs;
    }
}

class Pair2 {
    long v1;
    long v2;

    public Pair2(long v1, long v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
}
class Pair{
    int v1,v2;
    public Pair(int v,int w) {v1 = v; v2 = w;}
}
class Node {
    int data;
    Node left;
    Node right;
    public Node(int data) {
        this.data = data;
    }
}