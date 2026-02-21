public abstract class Employee {
    protected String id;
    protected String name;
    protected double baseSalary;
    protected Department department;
    protected TaxCalculator taxCalculator;
    
    public Employee(String id, String name, double baseSalary, Department department, TaxCalculator taxCalculator) {
        this.id = id;
        this.name = name;
        this.baseSalary = baseSalary;
        this.department = department;
        this.taxCalculator = taxCalculator;
    }
    
    public double getNetSalary() {
        double tax = taxCalculator.calculateTax(baseSalary);
        return baseSalary - tax;
    }
    
    public String getId() {
      return id;
    }
    public String getName() {
      return name;
    }
    public double getBaseSalary() {
      return baseSalary; 
    }
    public Department getDepartment() {
      return department;
    }
    
    public abstract String getEmployeeType();
}
