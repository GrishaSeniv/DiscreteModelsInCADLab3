package src.fileReader;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileReaderMatrixSize {
    int m;
    int[][] array;
    String[] words;
    int lineCount = 0;
    String fileIn, fileOut;

    public int getM() {
        return m;
    }

    public FileReaderMatrixSize(String fileIn, String fileOut){
        this.fileIn = fileIn;
        this.fileOut = fileOut;
    }

    public void fileReaderFirstNumber() {
        try {
            File file = new File(fileIn);
            //Create Object FileReader for Object File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            m = Integer.parseInt(line); //Розмірність матриці- кількість вершин
            array = new int[m][m];

            line = reader.readLine(); //Зчитуємо саму матрицю

            while (line != null) {
                words = line.split(" ");
                for (int i = 0; i < m; i++) {
                    array[lineCount][i] = Integer.parseInt(words[i]);
                }
                lineCount++;
                // считываем остальные строки в цикле
                line = reader.readLine();
            }

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    public void workWithMatrix() {
        int lineCount = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = lineCount; j < array.length; j++) {
                if (array[i][j] != 0) {
                    System.out.println("i = " + i);
                    System.out.println("j = " + j);
                    System.out.println("el = " + array[i][j]);
                }
            }
            System.out.println();
            lineCount++;
        }
    }

    public void writeToTxt() {
        try {
            File file = new File(fileOut);
            //Create Object FileReader for Object File
            FileWriter nFile = new FileWriter(file, StandardCharsets.US_ASCII);

            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array.length; j++) {
                    if (i == j) {
                        nFile.write("0 ");
                    }else if (array[i][j] == 0){
                        array[i][j] = 10000;
                        nFile.write("" + array[i][j] + " ");
                    }else {
                        nFile.write("" + array[i][j] + " ");
                    }
                }
                nFile.write("\n");
            }
            nFile.close();
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
