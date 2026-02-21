public class FullTimeTaxCalculator implements TaxCalculator {
    @Override
    public double calculateTax(double baseSalary) {
        double incomeTax = baseSalary * 0.30;
        double cess = baseSalary * 0.02; 
        return incomeTax + cess;
    }
}
