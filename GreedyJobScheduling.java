import java.io.*;
import java.util.*;

public class GreedyJobScheduling {
    
    public static int greedyJobScheduling(int[] jobs, int numMachines) {
        Arrays.sort(jobs);
        int n = jobs.length;
        PriorityQueue<Integer> machines = new PriorityQueue<>();
        for (int i = 0; i < numMachines; i++) {
            machines.add(0);
        }
        
        for (int i = n - 1; i >= 0; i--) {
            int minMachine = machines.poll();
            minMachine += jobs[i];
            machines.add(minMachine);
        }
        
        int maxTime = 0;
        while (!machines.isEmpty()) {
            maxTime = Math.max(maxTime, machines.poll());
        }
        
        return maxTime;
    }
    
    public static int[] readScheduleFile(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        int numJobs = Integer.parseInt(br.readLine().trim());
        List<Integer> jobList = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            String[] jobStrings = line.trim().split(" ");
            for (String job : jobStrings) {
                if (!job.isEmpty()) {
                    jobList.add(Integer.parseInt(job));
                }
            }
        }
        br.close();
        return jobList.stream().mapToInt(i -> i).toArray();
    }
    
    public static void main(String[] args) {
        String[] filePaths = {"schedulea.txt", "scheduleb.txt", "schedulec.txt", "scheduled.txt"};
        int numMachines = 3;
        
        for (String filePath : filePaths) {
            try {
                int[] jobs = readScheduleFile(filePath);
                int result = greedyJobScheduling(jobs, numMachines);
                System.out.println("File: " + filePath + " -> Thời gian hoàn thành công việc: " + result);
            } catch (IOException e) {
                System.err.println("Lỗi khi đọc file " + filePath + ": " + e.getMessage());
            }
        }
    }
}
