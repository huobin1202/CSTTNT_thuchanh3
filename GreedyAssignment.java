import java.io.*;

public class GreedyAssignment {
    public static int greedyAssignmentMax(int[][] efficiencyMatrix, int n) {
        boolean[] assigned = new boolean[n];
        int totalEfficiency = 0;

        for (int i = 0; i < n; i++) {
            int maxEfficiency = Integer.MIN_VALUE;
            int maxJob = -1;

            for (int j = 0; j < n; j++) {
                if (!assigned[j] && efficiencyMatrix[i][j] > maxEfficiency) {
                    maxEfficiency = efficiencyMatrix[i][j];
                    maxJob = j;
                }
            }

            assigned[maxJob] = true;
            totalEfficiency += maxEfficiency;
        }

        return totalEfficiency;
    }

    public static void processFile(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
    
            if (line == null || line.trim().isEmpty()) {
                System.err.println("File: " + fileName + " bi loi hoac trong!");
                return;
            }
    
            int n = Integer.parseInt(line.trim());
            int[][] efficiencyMatrix = new int[n][n];
    
            for (int i = 0; i < n; i++) {
                line = br.readLine();
                if (line == null || line.trim().isEmpty()) {
                    System.err.println("File: " + fileName + " co dong rong tai dong " + (i + 1));
                    return;
                }
    
                String[] values = line.trim().split("\\s+"); // Chia theo khoảng trắng bất kỳ
                if (values.length != n) {
                    System.err.println("File: " + fileName + " co loi dinh dang dong " + (i + 1));
                    return;
                }
    
                for (int j = 0; j < n; j++) {
                    efficiencyMatrix[i][j] = Integer.parseInt(values[j]);
                }
            }
            br.close();
    
            int result = greedyAssignmentMax(efficiencyMatrix, n);
            System.out.println("File: " + fileName + " - Tong hieu qua toi da: " + result);
        } catch (IOException e) {
            System.err.println("Khong the mo file: " + fileName);
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("File: " + fileName + " co loi dinh dang so!");
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {
        String[] files = {"assignmenta.txt", "assignmentb.txt", "assignmentc.txt", "assignmentd.txt"};
        for (String file : files) {
            processFile(file);
        }
    }
}
