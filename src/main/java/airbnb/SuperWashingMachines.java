package airbnb;

public class SuperWashingMachines {

    public static void main(String[] args) {

        int[] input = {9, 1, 8, 8, 9};
//        input[0] =1;
        int ret = findMinMoves2(input);
        System.out.println(ret);
    }

    public static int findMinMoves(int[] machines) {
        int total = 0;
        for (int i : machines) total += i;
        if (total % machines.length != 0) return -1;
        int avg = total / machines.length, cnt = 0, max = 0;
        for (int load : machines) {
            cnt += load - avg; //load-avg is "gain/lose"
            max = Math.max(Math.max(max, Math.abs(cnt)), load - avg);
            System.out.println("max="+max+";cnt="+cnt+";load-avg="+(load-avg));
        }
        return max;
    }
    public static int findMinMoves2(int[] machines) {
        int n = machines.length;
        int sum = 0;
        for (int num : machines) {
            sum += num;
        }
        if (sum % n != 0) {
            return -1;
        }
        int avg = sum / n;
        int[] leftSums = new int[n];
        int[] rightSums = new int[n];
        for (int i = 1; i < n; i ++) {
            leftSums[i] = leftSums[i-1] + machines[i-1];
        }
        for (int i = n - 2; i >= 0; i --) {
            rightSums[i] = rightSums[i+1] + machines[i+1];
        }
        int move = 0;
        for (int i = 0; i < n; i ++) {
            int expLeft = i * avg;
            int expRight = (n - i - 1) * avg;
            int left = 0;
            int right = 0;
            if (expLeft > leftSums[i]) {
                left = expLeft - leftSums[i];
            }
            if (expRight > rightSums[i]) {
                right = expRight - rightSums[i];
            }
            System.out.println("left="+left+";right="+right);
            move = Math.max(move, left + right);
        }
        return move;
    }
}
