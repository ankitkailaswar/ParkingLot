package org.sahaj.parking.utils;

import java.io.*;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Utils.
 */
public class Utils {
    /**
     * Read from given file.
     *
     * @param fileName the file name
     * @return the list of lines
     */
    public static List<String> readFromFile(String fileName) {
        List<String> lines = new ArrayList<>();
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        } catch (FileNotFoundException fnf) {
            System.out.println(fnf);
        } catch (IOException io) {
            System.out.println(io);
        }
        return lines;
    }

    /**
     * Write to given file.
     *
     * @param fileName the file name
     * @param contents the contents
     */
    public static void writeToFile(String fileName, String contents) {
        try {
            File file = new File(fileName);
            file.delete();
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(contents);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets local time.
     *
     * @param utcTime  the utc time
     * @param timezone the timezone
     * @return the local time
     */
    public static String getLocalTime(Long utcTime, TimeZone timezone) {
        SimpleDateFormat sdfOriginal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfOriginal.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date1 = new Date(utcTime);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        sdf.setTimeZone(timezone);
        return sdf.format(calendar.getTime());
    }

    /**
     * Create file path string.
     *
     * @param base the base
     * @param more the more
     * @return the string
     */
    public static String createFilePathString(String base, String... more) {
        return Paths.get(base, more).toString();
    }

    /**
     * Gets all child directory names in given directory.
     *
     * @param currentDir the current dir
     * @return the all child directories name
     */
    public static List<String> getAllChildDirsName(String currentDir) {

        List<String> childDirs = new ArrayList<>();

        File file = new File(currentDir);
        childDirs.addAll(Arrays.asList(file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        })));

        return childDirs;
    }

    /**
     * File exsists boolean.
     *
     * @param file the file
     * @return the boolean
     */
    public static boolean fileExsists(String file) {
        File f = new File(file);
        return f.exists();
    }

    /**
     * Compute hours elapsed between start and end timestamp.
     *
     * @param start          the start
     * @param end            the end
     * @param isEndInclusive flag to represent id end timestamp is inclusive to compute hours.
     * @return the int
     */
    public static int computeHours(Long start, Long end, boolean isEndInclusive) {

        Long millSecElapsed = end - start;
        Long oneHour = 1000 * 60 * 60L;

        int hoursElapsed = (int)(millSecElapsed / oneHour);
        Long remainder = millSecElapsed % oneHour;

        int hours = hoursElapsed;

        if (isEndInclusive) {
            if (remainder > 0)
                hours = hoursElapsed + 1;
            else
                hours = hoursElapsed;
        }

        return hours;
    }
}