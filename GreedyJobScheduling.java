import java.io.*;
import java.util.*;

public class GreedyJobScheduling {
    
    public static int greedyJobScheduling(int[] jobs, int numMachines) {
        // Sắp xếp công việc theo thứ tự giảm dần
        Arrays.sort(jobs);
        int n = jobs.length;
        
        // Khởi tạo hàng đợi ưu tiên để lưu tổng thời gian trên mỗi máy
        PriorityQueue<Integer> machines = new PriorityQueue<>();
        for (int i = 0; i < numMachines; i++) {
            machines.add(0);
        }
        
        // Gán từng công việc vào máy có thời gian thấp nhất
        for (int i = n - 1; i >= 0; i--) {
            int minMachine = machines.poll();
            minMachine += jobs[i];
            machines.add(minMachine);
        }
        
        // Thời gian hoàn thành là thời gian lớn nhất trên các máy
        int maxTime = 0;
        while (!machines.isEmpty()) {
            maxTime = Math.max(maxTime, machines.poll());
        }
        
        return maxTime;
    }
    
    public static int[] readScheduleFile(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        int numJobs = Integer.parseInt(br.readLine().trim());
        String[] jobStrings = br.readLine().trim().split(" ");
        int[] jobs = new int[numJobs];
        for (int i = 0; i < numJobs; i++) {
            jobs[i] = Integer.parseInt(jobStrings[i]);
        }
        br.close();
        return jobs;
    }
    
    public static void main(String[] args) {
        String filePath = "schedulea.txt"; // Thay đổi file nếu cần
        int numMachines = 3;
        
        try {
            int[] jobs = readScheduleFile(filePath);
            int result = greedyJobScheduling(jobs, numMachines);
            System.out.println("Thời gian hoàn thành công việc: " + result);
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
}
