public class Intern extends Employee {
    public Intern(String id, String name, double baseSalary, Department department) {
        super(id, name, baseSalary, department, new InternTaxCalculator());
    }

    @Override
    public String getEmployeeType() {
        return "Intern";
    }
}
