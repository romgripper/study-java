import java.util.Scanner;

public class Solution {
    
    public static int[] parsePrices(String pricesString, int n) {
        Scanner scanner = new Scanner(pricesString);
        scanner.useDelimiter("\\s+");
        int[] prices = new int[n];
        for (int i = 0; i < n; i ++) {
            prices[i] = scanner.nextInt();
        }
        scanner.close();
        new Runnable() {
        	public void run() {
        		
        	}
        };
        return prices;
    }
    
    public static long calcProfit(int[] prices, int end) {
        if (end <= 0) {
            return 0;
        }
        int max = prices[end];
        int i = end;
        long profit = 0;
        while (i >= 0 && prices[i] <= max) {
            profit += max - prices[i --];
        }
        return profit + calcProfit(prices, i);
    }

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
        int T = Integer.parseInt(scanner.nextLine());
        long[] profits = new long[T];
        for (int i = 0; i < T; i ++) {
            int N = Integer.parseInt(scanner.nextLine());
            String pricesString = scanner.nextLine();
            int[] prices = parsePrices(pricesString, N);
            profits[i] = calcProfit(prices, N - 1);
        }
        for (long profit: profits) {
            System.out.println(profit);
        }
        scanner.close();
    }
}