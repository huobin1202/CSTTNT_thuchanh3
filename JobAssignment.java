import java.util.*;

public class JobAssignment {
    private static final int INF = Integer.MAX_VALUE;
    
    public static void main(String[] args) {
        int[][] matrix = {
            {4, 9, 5, 1, 4, 9},
            {3, 1, 3, 7, 8, 6},
            {5, 7, 3, 4, 1, 9},
            {8, 8, 3, 7, 6, 6},
            {5, 4, 3, 8, 9, 7},
            {6, 3, 1, 4, 2, 5}
        };
        
        int N = matrix.length;
        int[][] costMatrix = new int[N][N];
        
        int maxValue = Arrays.stream(matrix).flatMapToInt(Arrays::stream).max().orElse(0);
        
        // Chuyển thành bài toán tối thiểu hóa
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                costMatrix[i][j] = maxValue - matrix[i][j];
            }
        }
        
        int[] assignment = hungarianAlgorithm(costMatrix);
        
        System.out.println("Thợ\tCông việc");
        int total = 0;
        for (int i = 0; i < N; i++) {
            System.out.println((i + 1) + "\t" + (assignment[i] + 1));
            total += matrix[i][assignment[i]];
        }
        System.out.println("Tổng hiệu quả: " + total);
    }
    
    public static int[] hungarianAlgorithm(int[][] cost) {
        int n = cost.length;
        int[] u = new int[n];
        int[] v = new int[n];
        int[] p = new int[n];
        int[] way = new int[n];
        
        for (int i = 1; i < n; i++) {
            int[] minv = new int[n];
            Arrays.fill(minv, INF);
            boolean[] used = new boolean[n];
            int j0 = 0;
            p[0] = i;
            
            do {
                used[j0] = true;
                int i0 = p[j0], delta = INF, j1 = -1;
                for (int j = 1; j < n; j++) {
                    if (!used[j]) {
                        int cur = cost[i0][j] - u[i0] - v[j];
                        if (cur < minv[j]) {
                            minv[j] = cur;
                            way[j] = j0;
                        }
                        if (minv[j] < delta) {
                            delta = minv[j];
                            j1 = j;
                        }
                    }
                }
                for (int j = 0; j < n; j++) {
                    if (used[j]) {
                        u[p[j]] += delta;
                        v[j] -= delta;
                    } else {
                        minv[j] -= delta;
                    }
                }
                j0 = j1;
            } while (p[j0] != 0);
            
            do {
                int j1 = way[j0];
                p[j0] = p[j1];
                j0 = j1;
            } while (j0 > 0);
        }
        
        int[] result = new int[n];
        for (int j = 1; j < n; j++) {
            result[p[j]] = j;
        }
        return result;
    }
}