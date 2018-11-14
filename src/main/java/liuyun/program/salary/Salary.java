package liuyun.program.salary;

public class Salary {

    final static char[] BASE_CHAR = {'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    final static int MONTH = 13;

    private final String name;
    private final int baseSalary;
    private final int bonus;

    public Salary(String name, int baseSalary, int bonus) {
        this.name = name;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
    }

    public int getAnnualSalary() {
        return baseSalary * MONTH + bonus;
    }

    @Override
    public String toString() {
        return name + "," + baseSalary + "," + bonus;
    }

    public String getName() {
        return name;
    }
}
