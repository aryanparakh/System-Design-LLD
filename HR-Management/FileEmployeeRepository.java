import java.io.FileWriter;
import java.io.IOException;

public class FileEmployeeRepository implements EmployeeRepository {
    private String filePath;

    public FileEmployeeRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Employee emp) {
      
        try (FileWriter writer = new FileWriter(filePath, true)) {
            String record = String.format("ID: %s | Name: %s | Type: %s | Dept: %s | Base Salary: %.2f | Net Salary: %.2f\n",
                    emp.getId(), 
                    emp.getName(), 
                    emp.getEmployeeType(), 
                    emp.getDepartment(), 
                    emp.getBaseSalary(), 
                    emp.getNetSalary());
            
            writer.write(record);
            System.out.println("✅ Saved successfully: " + emp.getName());
            
        } catch (IOException e) {
            System.out.println("❌ Error saving employee: " + e.getMessage());
        }
    }
}
