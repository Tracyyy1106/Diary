import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class errorComp {
    public static void main(String[] args) {
        String logFilePath = "/Users/nymphadoratonks/Desktop/extracted.txt";
        Pattern errorPattern = Pattern.compile("\\[(.*?)\\] error: (.*)");
        HashMap<String, Integer> errorCounts = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher = errorPattern.matcher(line);
                if (matcher.find()) {
                    String dateTime = matcher.group(1);
                    String errorMsg = matcher.group(2);
                    String jobId = extractJobId(line);
                    String userId = extractUserId(line);

                    System.out.printf("DateTime: %s, JobId: %s, UserId: %s, ErrorMsg: %s%n", dateTime, jobId, userId, errorMsg);


                    errorCounts.put(userId, errorCounts.getOrDefault(userId, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Error counts by user:");
        for (String user : errorCounts.keySet()) {
            System.out.println("UserId: " + user + ", ErrorCount: " + errorCounts.get(user));
        }
    }

    private static String extractJobId(String line) {
        Pattern jobIdPattern = Pattern.compile("JobId=(\\d+)");
        Matcher matcher = jobIdPattern.matcher(line);
        return matcher.find() ? matcher.group(1) : "N/A";
    }

    private static String extractUserId(String line) {
        Pattern userIdPattern = Pattern.compile("UserId=(\\d+)");
        Matcher matcher = userIdPattern.matcher(line);
        return matcher.find() ? matcher.group(1) : "N/A";
    }
}
