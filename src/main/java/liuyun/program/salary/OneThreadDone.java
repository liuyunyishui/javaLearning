package liuyun.program.salary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static liuyun.program.salary.SalaryMain.HEAP;


public class OneThreadDone {

    private final static String FILE_PATH = "E:\\";
//    private final static int SCORED = 10000000;
    private final static int SCORED = 1000;

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        final File file = new File(FILE_PATH + "salary.txt");
        file.deleteOnExit();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int scored = SCORED;
        List<Salary> salaries = new ArrayList<>(scored);
        for (; scored > 0; scored--) {
            Salary salary = new SalaryProducer().getRandomSalary();
            salaries.add(salary);
        }

        try {
            SalaryWriter writer = new SalaryWriter(file);
            writer.writeSalaries(salaries);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            salaries = new SalaryReader(file).readSalary(SCORED);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(salaries.size());
        SalaryStatistics statistics = new SalaryStatistics();
        for (Salary salary : salaries) {
            statistics.addSalary(salary);
        }


        Queue<SalarySum> queue = statistics.statistics(HEAP);

        long end = System.currentTimeMillis();
        System.out.println("use time " + (end - start) + "ms");
        //976s

        SalaryMain.saveResult();
    }
}
