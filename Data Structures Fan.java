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

            Comparator<Pair> com = Comparator.comparingInt(i -> i.v1);

            int testcases = 1;

            testcases = in.nextInt();

            while(testcases-- > 0) {
                /*
                    We need to calculate the answer for queries efficiently!
                    so some precomputation is required so, precomputed the xor of all the elements in the array!
                 */
                int siz = in.nextInt();
                int[] nums = in.nextIntArray(0,siz);
                char[] chars = in.next().toCharArray();
                int[] pre = new int[siz];
                int ones = 0; // initial xor of ones
                int zeros = 0; // initial xor of zeros!
                for(int i = 0; i < siz; i++) {
                    if(chars[i] == '1') ones ^= nums[i]; // updating them!
                    else if(chars[i] == '0') zeros ^= nums[i]; // updating them!
                    pre[i] = (i > 0 ? pre[i-1] : 0)^nums[i]; // precomputing!
                }
                int q = in.nextInt();
                while(q-- > 0) {
                    int type = in.nextInt(); // taking the query input!
                    if(type == 2) { // if it's type 2 print the answer ones, zeros based on the request!
                        int need = in.nextInt();
                        if(need == 1) out.print(ones+" ");
                        else out.print(zeros+" ");
                    } else {
                        int l = in.nextInt()-1; // making 0 based indexing!
                        int r = in.nextInt()-1; // making 0 based indexing!
                        int change = pre[r]^(l == 0 ? 0 : pre[l-1]); // updating the ones and zeros!
                        // for example
                        // 1 2 3 4 5
                        // 0 1 0 0 0
                        // 1 1 3 (given query);
                        // then we can update that in follows!
                        // the ones are converted to zero and vice versa in range 1 to 3
                        // so, the new ones are 1^3 (before this query we are having just 2 as the ones value)
                        // to update it, we use change gives the xor in that particular sub array!
                        // if you xor that with ones and zeros the ones and zeros are updated!
                        ones ^= change;
                        zeros ^= change;
                    }
                }
                out.println();
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

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

        public int[] nextIntArray(int start, int end) {
            int[] nums = new int[end];
            for(int i = start; i < end; i++) {
                nums[i] = Integer.parseInt(next());
            }
            return nums;
        }

        public Pair[] nextPairArray(int siz) {
            Pair[] nums = new Pair[siz];
            for(int i = 0; i < siz; i++) {
                int v1 = Integer.parseInt(next());
                int v2 = Integer.parseInt(next());
                nums[i] = new Pair(v1,v2);
            }
            return nums;
        }

        public long[] nextLongArray(int siz) {
            long[] nums = new long[siz];
            for(int i = 0; i < siz; i++) {
                nums[i] = Long.parseLong(next());
            }
            return nums;
        }

    }

//<---------------------------------------Fast writer--------------------------------------------->

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

// <-------------------------------------------------Some helper methods---------------------------------------->

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
        out.println(cond ? "Yes" : "No");
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