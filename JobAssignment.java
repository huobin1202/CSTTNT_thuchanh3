import java.io.*;
import java.util.*;

public class JobAssignment {
    public static void main(String[] args) throws IOException {
        String[] files = {"assignmenta.txt", "assignmentb.txt", "assignmentc.txt", "assignmentd.txt"};
        for (String filename : files) {
            System.out.println("Processing file: " + filename);
            processFile(filename);
        }
    }

    private static void processFile(String filename) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        int n = Integer.parseInt(br.readLine().trim());
        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            String[] line = br.readLine().trim().split("\\s+");
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.parseInt(line[j]);
            }
        }
        br.close();
        
        int totalCost = 0;
        
        List<int[]> jobList = new ArrayList<>();
        for (int worker = 0; worker < n; worker++) {
            for (int job = 0; job < n; job++) {
                jobList.add(new int[]{cost[worker][job], worker, job});
            }
        }
        
        jobList.sort((a, b) -> Integer.compare(b[0], a[0]));
        
        boolean[] workerAssigned = new boolean[n];
        boolean[] jobAssigned = new boolean[n];
        
        for (int[] entry : jobList) {
            int worker = entry[1];
            int job = entry[2];
            if (!workerAssigned[worker] && !jobAssigned[job]) {
                workerAssigned[worker] = true;
                jobAssigned[job] = true;
                totalCost += entry[0];
            }
        }
        
        System.out.println("Total Cost for " + filename + ": " + totalCost);
        System.out.println("------------------------------------");
    }
}