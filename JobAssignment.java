import java.io.*;
import java.util.*;

public class JobAssignment {
    private static final int INF = Integer.MAX_VALUE;
    
    public static int[][] readMatrixFromFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        int n = Integer.parseInt(br.readLine().trim());
        int[][] matrix = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            String[] line = br.readLine().trim().split(" ");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(line[j]);
            }
        }
        
        br.close();
        return matrix;
    }
    
    public static int[] hungarianAlgorithm(int[][] costMatrix) {
        int n = costMatrix.length;
        int[] u = new int[n + 1], v = new int[n + 1], p = new int[n + 1], way = new int[n + 1];
        
        for (int i = 1; i <= n; i++) {
            int[] minv = new int[n + 1];
            Arrays.fill(minv, INF);
            boolean[] used = new boolean[n + 1];
            int j0 = 0;
            p[0] = i;
            
            do {
                used[j0] = true;
                int i0 = p[j0], delta = INF, j1 = 0;
                
                for (int j = 1; j <= n; j++) {
                    if (!used[j]) {
                        int cur = costMatrix[i0 - 1][j - 1] - u[i0] - v[j];
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
                
                for (int j = 0; j <= n; j++) {
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
            } while (j0 != 0);
        }
        
        int[] result = new int[n];
        for (int j = 1; j <= n; j++) {
            result[p[j] - 1] = j - 1;
        }
        return result;
    }
    
    public static void main(String[] args) {
        try {
            String filename = "assignmenta.txt";
            int[][] matrix = readMatrixFromFile(filename);
            int[] assignment = hungarianAlgorithm(matrix);
            int maxEfficiency = 0;
            
            System.out.println("Optimal Assignment:");
            for (int i = 0; i < assignment.length; i++) {
                System.out.println("Worker " + (i + 1) + " -> Job " + (assignment[i] + 1));
                maxEfficiency += matrix[i][assignment[i]];
            }
            System.out.println("Maximum Efficiency: " + maxEfficiency);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
