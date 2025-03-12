import java.io.*;
import java.util.*;

public class JobAssignment {
    private static final int INF = Integer.MAX_VALUE;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("assignmenta.txt"));
        int n = Integer.parseInt(br.readLine().trim());
        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            String[] line = br.readLine().trim().split(" ");
            for (int j = 0; j < n; j++) {
                cost[i][j] = Integer.parseInt(line[j]);
            }
        }
        br.close();
        
        int[] workerAssignment = hungarianAlgorithm(cost, n);
        int totalCost = 0;
        
        System.out.println("Worker Assignments:");
        for (int worker = 0; worker < n; worker++) {
            System.out.println("Worker " + (worker + 1) + " -> Job " + (workerAssignment[worker] + 1));
            totalCost += cost[worker][workerAssignment[worker]];
        }
        System.out.println("Total Cost: " + totalCost);
    }

    public static int[] hungarianAlgorithm(int[][] cost, int n) {
        int[] lx = new int[n];
        int[] ly = new int[n];
        int[] match = new int[n];
        Arrays.fill(match, -1);
        boolean[] committedWorkers = new boolean[n];
        
        for (int i = 0; i < n; i++) {
            lx[i] = Arrays.stream(cost[i]).max().orElse(-INF);
        }
        
        for (int i = 0; i < n; i++) {
            boolean[] S = new boolean[n];
            boolean[] T = new boolean[n];
            int[] slack = new int[n];
            int[] slackWorker = new int[n];
            Arrays.fill(slack, INF);
            int[] parent = new int[n];
            Arrays.fill(parent, -1);
            
            int x = i;
            int y = -1;
            int[] minSlackWorker = new int[n];
            Arrays.fill(minSlackWorker, -1);
            
            while (true) {
                S[x] = true;
                int delta = INF;
                int nextY = -1;
                
                for (int j = 0; j < n; j++) {
                    if (!T[j]) {
                        int gap = lx[x] + ly[j] - cost[x][j];
                        if (gap < slack[j]) {
                            slack[j] = gap;
                            minSlackWorker[j] = x;
                        }
                        if (slack[j] < delta) {
                            delta = slack[j];
                            nextY = j;
                        }
                    }
                }
                
                for (int k = 0; k < n; k++) {
                    if (S[k]) lx[k] -= delta;
                    if (T[k]) ly[k] += delta;
                    else slack[k] -= delta;
                }
                
                y = nextY;
                T[y] = true;
                x = match[y];
                if (x == -1) break;
            }
            
            while (y != -1) {
                int prevY = match[y];
                match[y] = minSlackWorker[y];
                y = prevY;
            }
        }
        
        int[] workerAssignment = new int[n];
        for (int j = 0; j < n; j++) {
            if (match[j] != -1) {
                workerAssignment[match[j]] = j;
                committedWorkers[match[j]] = true;
            }
        }
        
        for (int i = 0; i < n; i++) {
            if (!committedWorkers[i]) {
                for (int j = 0; j < n; j++) {
                    if (match[j] == -1) {
                        workerAssignment[i] = j;
                        match[j] = i;
                        break;
                    }
                }
            }
        }
        
        return workerAssignment;
    }
}