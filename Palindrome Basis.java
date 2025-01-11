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

            // precomputation!

            int[] palindromes = {
                    1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 22, 33, 44, 55, 66, 77, 88, 99, 101, 111,
                    121, 131, 141, 151, 161, 171, 181, 191, 202, 212, 222, 232, 242, 252, 262,
                    272, 282, 292, 303, 313, 323, 333, 343, 353, 363, 373, 383, 393, 404, 414,
                    424, 434, 444, 454, 464, 474, 484, 494, 505, 515, 525, 535, 545, 555, 565,
                    575, 585, 595, 606, 616, 626, 636, 646, 656, 666, 676, 686, 696, 707, 717,
                    727, 737, 747, 757, 767, 777, 787, 797, 808, 818, 828, 838, 848, 858, 868,
                    878, 888, 898, 909, 919, 929, 939, 949, 959, 969, 979, 989, 999, 1001, 1111,
                    1221, 1331, 1441, 1551, 1661, 1771, 1881, 1991, 2002, 2112, 2222, 2332, 2442,
                    2552, 2662, 2772, 2882, 2992, 3003, 3113, 3223, 3333, 3443, 3553, 3663, 3773,
                    3883, 3993, 4004, 4114, 4224, 4334, 4444, 4554, 4664, 4774, 4884, 4994, 5005,
                    5115, 5225, 5335, 5445, 5555, 5665, 5775, 5885, 5995, 6006, 6116, 6226, 6336,
                    6446, 6556, 6666, 6776, 6886, 6996, 7007, 7117, 7227, 7337, 7447, 7557, 7667,
                    7777, 7887, 7997, 8008, 8118, 8228, 8338, 8448, 8558, 8668, 8778, 8888, 8998,
                    9009, 9119, 9229, 9339, 9449, 9559, 9669, 9779, 9889, 9999, 10001, 10101,
                    10201, 10301, 10401, 10501, 10601, 10701, 10801, 10901, 11011, 11111, 11211,
                    11311, 11411, 11511, 11611, 11711, 11811, 11911, 12021, 12121, 12221, 12321,
                    12421, 12521, 12621, 12721, 12821, 12921, 13031, 13131, 13231, 13331, 13431,
                    13531, 13631, 13731, 13831, 13931, 14041, 14141, 14241, 14341, 14441, 14541,
                    14641, 14741, 14841, 14941, 15051, 15151, 15251, 15351, 15451, 15551, 15651,
                    15751, 15851, 15951, 16061, 16161, 16261, 16361, 16461, 16561, 16661, 16761,
                    16861, 16961, 17071, 17171, 17271, 17371, 17471, 17571, 17671, 17771, 17871,
                    17971, 18081, 18181, 18281, 18381, 18481, 18581, 18681, 18781, 18881, 18981,
                    19091, 19191, 19291, 19391, 19491, 19591, 19691, 19791, 19891, 19991, 20002,
                    20102, 20202, 20302, 20402, 20502, 20602, 20702, 20802, 20902, 21012, 21112,
                    21212, 21312, 21412, 21512, 21612, 21712, 21812, 21912, 22022, 22122, 22222,
                    22322, 22422, 22522, 22622, 22722, 22822, 22922, 23032, 23132, 23232, 23332,
                    23432, 23532, 23632, 23732, 23832, 23932, 24042, 24142, 24242, 24342, 24442,
                    24542, 24642, 24742, 24842, 24942, 25052, 25152, 25252, 25352, 25452, 25552,
                    25652, 25752, 25852, 25952, 26062, 26162, 26262, 26362, 26462, 26562, 26662,
                    26762, 26862, 26962, 27072, 27172, 27272, 27372, 27472, 27572, 27672, 27772,
                    27872, 27972, 28082, 28182, 28282, 28382, 28482, 28582, 28682, 28782, 28882,
                    28982, 29092, 29192, 29292, 29392, 29492, 29592, 29692, 29792, 29892, 29992,
                    30003, 30103, 30203, 30303, 30403, 30503, 30603, 30703, 30803, 30903, 31013,
                    31113, 31213, 31313, 31413, 31513, 31613, 31713, 31813, 31913, 32023, 32123,
                    32223, 32323, 32423, 32523, 32623, 32723, 32823, 32923, 33033, 33133, 33233,
                    33333, 33433, 33533, 33633, 33733, 33833, 33933, 34043, 34143, 34243, 34343,
                    34443, 34543, 34643, 34743, 34843, 34943, 35053, 35153, 35253, 35353, 35453,
                    35553, 35653, 35753, 35853, 35953, 36063, 36163, 36263, 36363, 36463, 36563,
                    36663, 36763, 36863, 36963, 37073, 37173, 37273, 37373, 37473, 37573, 37673,
                    37773, 37873, 37973, 38083, 38183, 38283, 38383, 38483, 38583, 38683, 38783,
                    38883, 38983, 39093, 39193, 39293, 39393, 39493, 39593, 39693, 39793, 39893,
                    39993
            };

            int len = 498;

            int value = 4_00_00;

            int[] dp = new int[4_00_04]; // at max 500 palindromes from 1 to 4*1e4;

            dp[0] = 1;

            for(int p : palindromes) {

                for(int target = p; target <= value; target++) {

                    dp[target] = (dp[target]+dp[target-p])%mod;

                }

            }

            while(testcases-- > 0) {

                // similar to coin change problem!

                int v = in.nextInt(); // no. of coins!

                out.println(dp[v]);
                
//                private static int ans(int target,int idx,List<Integer> palindromes,int[][] dp) {
//                    if(idx == 0) {
//                        return (target%palindromes.getFirst() == 0 ? 1 : 0);
//                    }
//                    if(dp[idx][target] != -1) return dp[idx][target];
//                    long notTake = ans(target,idx-1,palindromes,dp);
//                    long take = 0;
//                    if(palindromes.get(idx) <= target) take = ans(target- palindromes.get(idx),idx,palindromes,dp);
//                    return dp[idx][target] = (int) (take+notTake)%mod;
//                }
                // writing base case!
                // dp[0][target] = 1 if target%palindromes[i] == 0
                // here first palindrome is 1 hence it's divisible so, we can say that dp[0][target] = 1

            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    private static boolean isPal(int i) {
        int num = i;
        int rev = 0;
        while(i > 0) {
            rev *= 10;
            rev += i%10;
            i /= 10;
        }
//        System.out.println(rev+" "+num);
        return rev == num;
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