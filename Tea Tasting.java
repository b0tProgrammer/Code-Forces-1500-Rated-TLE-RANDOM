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
                    We have to calculate how much quantity does each tester drinks!
                    This can be done simply by nested loops but that's not optimal!
                    So, we choose BS approach in this we find the maximum amount till the current tea is available for tester to taste!
                    And the remaining tea is available for the remaining one tester!
                 */
                int siz = in.nextInt();
                long[] tea = in.nextLongArray(0,siz); // taking the input!
                long[] tester = in.nextLongArray(0,siz);
                long[] prefix = new long[siz];

                for(int i = 0; i < siz; i++) { // calculating the prefix!
                    if(i >= 1) {
                        prefix[i] = prefix[i-1]+tester[i];
                    }
                    else prefix[i] = tester[i];
                }
                long[] ans = new long[siz]; // this stores the answer!
                int[] count = new int[siz]; // how many testers cant taste the i th drink!
                for(int i = 0; i < siz; i++) {
                    int j = getIdx(prefix,tea,i); // getting the valid index
                    if(j >= siz) continue; // if it's equal to siz! then whole array is valid!
                    long sum = j > 0 ? prefix[j-1] : 0; // the sum can be considered till that before index!
                    sum -= (i > 0 ? prefix[i-1] : 0); // if the current i > 0 then we have to remove the extra prefix sum i.e  < i's sum!
                    ans[j] += (tea[i]-sum); // then the remaining part will be added to that j th tester!
                    count[j]++; // saying that j th taster can tbe valid again!
                }
                long pre = 0;
                for(int i = 0; i < siz; i++) {
                    int take = i+1; // assuming that current testers are valid!
                    pre += count[i]; // this says that how many aren't valid!
                    take -= (int) pre; // remove that from valid!
                    if(take > 0) {
                        ans[i] += take*tester[i]; // calculate the amount that b[i]th tester will test!
                    }
                }
                for(long val : ans) {
                    out.print(val+" "); // printing the answer!
                }
                out.println();
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    private static int getIdx(long[] pre,long[] tea, int idx) {
        int low = 0; // setting the low and high!
        int high = tea.length-1;
        int smallestInvalid = tea.length; // thinking that initially whole array is possible!
        while(low <= high) {
            int mid = (low+high)/2;
            long sum = pre[mid];
            if(idx >= 1) {
                sum -= pre[idx-1]; // if the current idx is greater than or equal to 1 then we have to consider the sum from the index!
                // so removing the extra sum before that index!
            }
            if(tea[idx] < sum) { // checking it with respect to a[i]
                smallestInvalid = mid; // the smallestInvalid!
                high = mid-1;
            } else {
                low = mid + 1;
            }
        }
        return smallestInvalid;
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

        public Pair[] nextPairArray(int start,int siz) {
            Pair[] nums = new Pair[siz];
            for(int i = start; i < siz; i++) {
                int v1 = Integer.parseInt(next());
                int v2 = Integer.parseInt(next());
                nums[i] = new Pair(v1,v2);
            }
            return nums;
        }

        public long[] nextLongArray(int start,int siz) {
            long[] nums = new long[siz];
            for(int i = start; i < siz; i++) {
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