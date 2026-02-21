public class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String id, String name, double baseSalary, Department department) {
      
        super(id, name, baseSalary, department, new FullTimeTaxCalculator());
    }

    @Override
    public String getEmployeeType() {
        return "Full-Time";
    }
}
