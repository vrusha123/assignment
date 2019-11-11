package com.assignments;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParseBlogPost {
    public static void main(String[] args) throws Exception {
        List<String> allLines = Files.readAllLines(Paths.get("/doc.txt"));
        StringBuilder parsedBlog = new StringBuilder();
        parsedBlog.append("{").append("\n");
        allLines.forEach((line) -> {
            if (line.contains(":") && !line.contains("/")) {
                String[] keyVal = line.split(":", 2);
                if (keyVal[1].contains(",")) {
                    String[] values = keyVal[1].split(",");
                    List<String> listVals = new ArrayList<>();
                    for (String val : values) {
                        listVals.add("\"" + val.trim() + "\"");
                    }
                    if (listVals.size() > 0) {
                        parsedBlog.append("\t" + "\"").append(keyVal[0]).append("\"").append(": ").append(listVals).append(",").append("\n");
                    }
                } else {
                    parsedBlog.append("\t" + "\"").append(keyVal[0]).append("\"").append(":").append(keyVal[1]).append(",").append("\n");
                }
            } else if (!line.contains("-") && !line.contains("#") && line.length() > 0 && !line.equalsIgnoreCase("READMORE")) {
                parsedBlog.append("\t" + "\"").append("short-content").append("\"").append(":").append(" \"").append(line).append("\",").append("\n");
            } else if (line.equalsIgnoreCase("READMORE")) {
                List<String> remainingLines = allLines.subList(allLines.indexOf(line) + 1, allLines.size());
                StringBuilder remainingLineStr = new StringBuilder();
                remainingLines.forEach((l) -> {
                    if (l.length() > 0) {
                        remainingLineStr.append(l).append(" ");
                    }
                });
                parsedBlog.append("\t" + "\"").append("content").append("\"").append(":").append(" \"").append(remainingLineStr).append("\"\n");
            }
        });
        System.out.println(parsedBlog.append("}"));
    }
}