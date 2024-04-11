import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EmployeeDA {
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EmployeeDA(String empNo) {
        try (Scanner employeeFile = new Scanner(new FileReader("Emp.csv"))) {
            employeeFile.nextLine();

            while (employeeFile.hasNext()) {
                String[] employeeLineData = employeeFile.nextLine().split(",");

                if (empNo.equals(employeeLineData[0].trim())) {
                    employee = new Employee();
                    employee.setEmpNo(empNo);
                    employee.setLastName(employeeLineData[1].trim());
                    employee.setFirstName(employeeLineData[2].trim());
                    break; 
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}