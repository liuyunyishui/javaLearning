package liuyun.program.salary;

public class SalarySum {

    private final String name;
    private final int sum;
    private final int num;

    public SalarySum(Salary salary) {
        String salaryName = salary.getName();
        this.name = salaryName.substring(0, 2);
        this.sum = salary.getAnnualSalary();
        this.num = 1;
    }

    public SalarySum(String name, int sum, int num) {
        this.name = name;
        this.sum = sum;
        this.num = num;
    }

    public SalarySum add(Salary salary) {
        return new SalarySum(name, sum + salary.getAnnualSalary(), num + 1);
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public int getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", sum=" + sum +
                ", num=" + num +
                '}';
    }
}
