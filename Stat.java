import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stat {
    public static String stat(String strg) {

        List<Integer> resultsInSeconds = parseResultsToSeconds(strg);

        int r = findRangeInSeconds(resultsInSeconds);
        int a = findAverageInSeconds(resultsInSeconds);
        int m = findMedianInSeconds(resultsInSeconds);

        String range = "Range: " + formatSeconds(r, "|");
        String average = "Average: " + formatSeconds(a, "|");
        String median = "Median: " + formatSeconds(m, "|");

        return range + ' ' + average + ' ' + median;

    }

    public static List<Integer> parseResultsToSeconds(String rawRaceResults) {
        String[] raceResultsList = rawRaceResults.replace(" ", "") // 01|15|59,1|47|16,01|17|20,1|32|34,2|17|17
                .split(","); // [01|15|59] [1|47|16] [01|17|20] [1|32|34] [2|17|17]

        List<Integer> totalSeconds = new ArrayList<>();
        for (String rawScore : raceResultsList) {
            var splitScore = rawScore.split("\\|");
            int h = Integer.parseInt(splitScore[0]) * 3600;
            int m = Integer.parseInt(splitScore[1]) * 60;
            int s = Integer.parseInt(splitScore[2]);
            totalSeconds.add(h + m + s);
        }

        Collections.sort(totalSeconds);
        return totalSeconds;
    }

    public static int findRangeInSeconds(List<Integer> secondsList) {
        return secondsList.get(secondsList.size() - 1) - secondsList.get(0); //it's already sorted in ascending order
    }

    public static int findAverageInSeconds(List<Integer> secondsList) {
        int sum = 0;
        for (int seconds : secondsList) sum += seconds;
        return sum / secondsList.size();
    }

    public static int findMedianInSeconds(List<Integer> secondsList) {
        int count = secondsList.size();

        if (count % 2 == 0) {
            return (secondsList.get((count / 2) - 1) + secondsList.get(count / 2)) / 2;
        }
        return secondsList.get(count / 2);
    }

    public static String formatSeconds(int seconds, String separator) {
        int h = seconds / 3600;
        int m = (seconds % 3600) / 60;
        int s = seconds % 60;

        return "%02d%s%02d%s%02d".formatted(h, separator, m, separator, s);
    }
}