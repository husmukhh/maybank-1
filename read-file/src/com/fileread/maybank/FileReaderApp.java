package com.fileread.maybank;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileReaderApp {

    public static List<AccountRate> readAccountRateFile(String filePath) throws IOException {
        List<AccountRate> accountRateList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                AccountRate accountRate = new AccountRate();
                accountRate.setAccountNo(values[0]);
                accountRate.setBalance(new BigDecimal(values[1]));
                accountRate.setRate(values[2]);
                accountRateList.add(accountRate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  accountRateList;
    }

    public static List<Rate> readRateFile(String filePath) throws IOException {
        List<Rate> rateList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line
            String header = br.readLine();

            String line;

            while ((line = br.readLine()) != null) {
                String[] values = line.split("\t");
                Rate rate = new Rate();
                rate.setRate(values[0]);
                rate.setValue(new BigDecimal(values[1]));
                rateList.add(rate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  rateList;
    }


    public static void main(String arg[]) throws  Exception {
        List<AccountRate> accountRateList = readAccountRateFile("/home/husmukh/account.txt");
        List<Rate> rateList = readRateFile("/home/husmukh/rate.txt");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("/home/husmukh/result.txt"));
            for (AccountRate accountRate : accountRateList) {
                List<Rate> rateAccList = rateList.stream().filter(rate -> accountRate.getRate().trim().equals(rate.getRate().trim())).collect(Collectors.toList());
                if (rateAccList != null && rateAccList.size() > 0) {
                    BigDecimal accural = accountRate.getBalance().multiply(
                            rateAccList.get(0).getValue().divide(BigDecimal.valueOf(100L))  );
                    writer.write(accural.toString());
                    writer.newLine(); // Optional: add a new line after the value
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }
}
