import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class DepartmentDA {
    public DepartmentDA() {
        new HashMap<>();
        readDepFile();
    }

    private void readDepFile() {
        try (Scanner departmentFile = new Scanner(new FileReader("Dep.csv"))) {
            departmentFile.nextLine(); // Assuming the first line is a header
    
            while (departmentFile.hasNext()) {
                String[] departmentLineData = departmentFile.nextLine().split(",");
                Department department = new Department();
                department.setDepCode(departmentLineData[0].trim());
                department.setDepName(departmentLineData[1].trim());
                readDeptEmpFile(department); 
                printDepartment(department);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void readDeptEmpFile(Department department) {
        HashMap<String, Employee> localEmployeeMap = new HashMap<>(); // Local map for this department
        try (Scanner deptEmpFile = new Scanner(new FileReader("DeptEmp.csv"))) {
            deptEmpFile.nextLine(); // Assuming the first line is a header
    
            double totalSalary = 0.0;
            while (deptEmpFile.hasNext()) {
                String[] deptEmpLineData = deptEmpFile.nextLine().split(",");
    
                if (department.getDepCode().trim().equalsIgnoreCase(deptEmpLineData[0].trim())) {
                    String empNo = deptEmpLineData[1].trim();
                    EmployeeDA employeeDA = new EmployeeDA(empNo);
                    Employee employee = employeeDA.getEmployee();
                    employee.setSalary(Double.parseDouble(deptEmpLineData[2].trim()));
    
                    localEmployeeMap.put(empNo, employee); 
                    totalSalary += employee.getSalary();
                }
            }
            department.setEmployeeMap(localEmployeeMap); 
            department.setDepTotalSalary(totalSalary);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void printDepartment(Department department) {
        DecimalFormat df = new DecimalFormat("#,###.00");
    
        System.out.println("Department Code: " + department.getDepCode());
        System.out.println("Department Name: " + department.getDepName());
        System.out.println("Department Total Salary: " + df.format(department.getDepTotalSalary()));
        System.out.println("---------------------Details -------------------------");
        System.out.println("EmpNo\t\tEmployee Name\t\tSalary");
    
        for (Map.Entry<String, Employee> entry : department.getEmployeeMap().entrySet()) {
            Employee employee = entry.getValue();
            String empNo = employee.getEmpNo();
            String employeeName = employee.getLastName() + ", " + employee.getFirstName();
            String fixedSalary = df.format(employee.getSalary());
    
            System.out.printf("%-16s%-24s%s%n", empNo, employeeName, fixedSalary);
        }
        System.out.println();
    }
}