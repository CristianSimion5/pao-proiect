package com.services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuditService {

    private AuditService() {}

    public static void writeAudit(String functionName, String threadName) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("Audit.csv", true))) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            out.append(functionName).append(",").append(dateFormat.format(new Date())).append(",").append(threadName).append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
