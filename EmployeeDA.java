import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EmployeeDA {
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EmployeeDA(String empNo) {
        String line;
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader("Emp.csv"))) {
            while ((line = br.readLine()) != null && !found) {
                String[] data = line.split(",");
                if (data[0].trim().equals(empNo)) {
                    this.employee = new Employee();
                    this.employee.setEmpNo(empNo);
                    this.employee.setLastName(data[1].trim());
                    this.employee.setFirstName(data[2].trim());
                    found = true; // Stop after finding the employee
                }
            }

            if (!found) {
                System.out.println("Employee with empNo " + empNo + " not found.");
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading the employee file: " + e.getMessage(), e);
        }
    }
}