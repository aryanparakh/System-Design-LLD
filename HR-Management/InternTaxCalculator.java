public class InternTaxCalculator implements TaxCalculator {
    @Override
    public double calculateTax(double baseSalary) {
        return baseSalary * 0.15;
    }
}
