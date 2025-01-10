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

//            testcases = in.nextInt();

//<------------------------ Main Code starts Here----------------------------------------->

            while(testcases-- > 0) {
                /*
                    In this, we have to say whether we can make score equal to 0 after playing with that particular q[i] console!
                    And to say whether we can do or not, we have to observe one thing carefully
                    If the number of + is equal to the number of - then we can say that the answer is yes for all q[i] s
                    Reason consider only a single number among ai, bi the sum will be plus*ai - minus*ai if both plus and minus are equal then we can say that the answer is 0
                    Else we have to check whether we can make 0 or not!
                */
                int siz = in.nextInt();
                char[] string = in.next().toCharArray();
                int plus = 0;
                int minus = 0;
                for(int i = 0; i < siz; i++) {
                    if(string[i] == '+') plus++; // counting the number of plus and minus!
                    else minus++;
                }
                int q = in.nextInt(); // taking the queries input!
                for(int i = 0; i < q; i++) {
                    int a = in.nextInt();
                    int b = in.nextInt();
                    int max = max(a,b);
                    int min = min(a,b);
                    if(plus == minus) { // if count of both plus and minus are equal answer is always 0
                        print(true,out); // so always true
                    } else if(plus < minus) { // if the counts of + are less than the -
                        if(plus == min && minus == max) { // then first let's check whether the + are repeated min number of times bcoz!
                            // let stake an example 5,3 && +-+---+-
                            // Then 3 +'s and 5 -'s are present
                            // so its just cross multiplication!
                            // i.e 5*(3(no. of +'s))-3*(5(no.of -'s))
                            // let's call these type of pairs as "good".
                            // if the multiplied value is equal then we can make the answer equal to 0
                            print(true,out);
                        } else {
                            // we will now search the answer!
                            print(bs(plus,minus,min,max),out);
                        }
                    } else {
                        if(plus == max && minus == min) {
                            print(true,out);
                        } else {
                            print(bs(minus,plus,min,max),out);
                        }
                    }
                }
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private static boolean bs(int p,int m,int min, int max) {

        // let's take an example for better understanding!
        // assuming that +-+---+- given
        // and 2 1
        // then we can say that!
        // pressing 1 at 1,2,4,5,6,8 positions gives the answer 0
        // Then, this is said by considering the 2 +'s for 2 and remaining for the 1
        // How to approach this :
        // There is a math way, but I have done this by using only BS
        // In the current case There is no equal +'s and -'s and the given pair isn't good!
        // so, we have to find that what number of +'s are required to make the pair good!
        // And we have to check that remaining +'s and -'s are equal in number bcoz!
        // we can make that whole sequence into 0 just discussed that above the property of the good pair!
        // If there are maximum number of +'s then we do the thing discussed above
        // Else we just swap the values of +'s and minus!
        // If still facing any difficulties msg me!
        int lowPlus = 1;
        int highPlus = p;
        while(lowPlus <= highPlus) {
            int currPlus = (lowPlus+highPlus)/2;
            int remM = p-currPlus;
            remM = abs(m-remM);
            long val1 = (long)currPlus*max;
            long val2 = (long)remM*min;
            if(val1 == val2) {
                return true;
            } else if(val1 > val2) {
                highPlus = currPlus-1;
            } else {
                lowPlus = currPlus+1;
            }
        }
        return false;
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