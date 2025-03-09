import java.io.*;
import java.util.*;

public class GreedyAssignment {
    public static void main(String[] args) {
        String filename = "assignmenta.txt"; // Thay đổi thành "assignmentb.txt" nếu cần
        try {
            int[][] costMatrix = readCostMatrix(filename);
            int totalCost = greedyAssignment(costMatrix);
            System.out.println("Tổng chi phí phân công: " + totalCost);
        } catch (IOException e) {
            System.err.println("Lỗi đọc file: " + e.getMessage());
        }
    }

    private static int[][] readCostMatrix(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        int n = Integer.parseInt(br.readLine().trim());
        int[][] matrix = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            String[] line = br.readLine().trim().split("\\s+");
            for (int j = 0; j < n; j++) {
                matrix[i][j] = Integer.parseInt(line[j]);
            }
        }
        br.close();
        return matrix;
    }

    private static int greedyAssignment(int[][] costMatrix) {
        int n = costMatrix.length;
        boolean[] assignedJobs = new boolean[n]; // Đánh dấu công việc đã được chọn
        int totalCost = 0;

        for (int i = 0; i < n; i++) {
            int minCost = Integer.MAX_VALUE;
            int chosenJob = -1;
            
            for (int j = 0; j < n; j++) {
                if (!assignedJobs[j] && costMatrix[i][j] < minCost) {
                    minCost = costMatrix[i][j];
                    chosenJob = j;
                }
            }
            
            assignedJobs[chosenJob] = true; // Đánh dấu công việc đã được chọn
            totalCost += minCost;
        }
        return totalCost;
    }
}
