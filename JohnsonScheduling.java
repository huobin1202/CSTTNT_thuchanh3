import java.io.*;
import java.util.*;

class Job {
    int id;
    int timeMachine1;
    int timeMachine2;
    
    public Job(int id, int timeMachine1, int timeMachine2) {
        this.id = id;
        this.timeMachine1 = timeMachine1;
        this.timeMachine2 = timeMachine2;
    }
}

public class JohnsonScheduling {
    public static void main(String[] args) {
        String[] filenames = {"Johnsona.txt", "Johnsonb.txt", "Johnsonc.txt", "Johnsond.txt"};
        
        for (String filename : filenames) {
            System.out.println("Processing file: " + filename);
            List<Job> jobs = readJobsFromFile(filename);
            if (jobs.isEmpty()) {
                System.out.println("Error reading file or file is empty: " + filename);
                continue;
            }
            List<Job> scheduledJobs = johnsonAlgorithm(jobs);
            int completionTime = calculateCompletionTime(scheduledJobs);

            System.out.println("Optimal job sequence:");
            for (Job job : scheduledJobs) {
                System.out.print(job.id + " ");
            }
            System.out.println("\nTotal completion time: " + completionTime + "\n");
        }
    }

    private static List<Job> readJobsFromFile(String filename) {
        List<Job> jobs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            line = br.readLine();
            if (line == null || line.trim().isEmpty()) {
                System.err.println("Invalid file format: " + filename);
                return jobs;
            }
            int numJobs = Integer.parseInt(line.trim());
            
            line = br.readLine();
            if (line == null || line.trim().isEmpty()) {
                System.err.println("Missing machine 1 times in file: " + filename);
                return jobs;
            }
            int[] machine1Times = Arrays.stream(line.trim().split(" "))
                    .filter(s -> !s.isEmpty())
                    .mapToInt(Integer::parseInt).toArray();
            
            line = br.readLine();
            if (line == null || line.trim().isEmpty()) {
                System.err.println("Missing machine 2 times in file: " + filename);
                return jobs;
            }
            int[] machine2Times = Arrays.stream(line.trim().split(" "))
                    .filter(s -> !s.isEmpty())
                    .mapToInt(Integer::parseInt).toArray();
            
            if (machine1Times.length != numJobs || machine2Times.length != numJobs) {
                System.err.println("Mismatch in job count and machine times in file: " + filename);
                return jobs;
            }
            
            for (int i = 0; i < numJobs; i++) {
                jobs.add(new Job(i + 1, machine1Times[i], machine2Times[i]));
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading file: " + filename);
            e.printStackTrace();
        }
        return jobs;
    }

    private static List<Job> johnsonAlgorithm(List<Job> jobs) {
        List<Job> left = new ArrayList<>();
        List<Job> right = new ArrayList<>();
        
        while (!jobs.isEmpty()) {
            Job minJob = Collections.min(jobs, Comparator.comparingInt(j -> Math.min(j.timeMachine1, j.timeMachine2)));
            jobs.remove(minJob);
            
            if (minJob.timeMachine1 <= minJob.timeMachine2) {
                left.add(minJob);
            } else {
                right.add(0, minJob);
            }
        }
        
        left.addAll(right);
        return left;
    }

    private static int calculateCompletionTime(List<Job> jobs) {
        int timeMachine1 = 0, timeMachine2 = 0;
        for (Job job : jobs) {
            timeMachine1 += job.timeMachine1;
            timeMachine2 = Math.max(timeMachine1, timeMachine2) + job.timeMachine2;
        }
        return timeMachine2;
    }
}
