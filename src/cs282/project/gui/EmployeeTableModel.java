/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs282.project.gui;

/**
 *
 * @author Jur Yel
 */
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import cs282.project.core.Employee;

public class EmployeeTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int BRANCH_COL = 0;
    private static final int EMPLOYEE_ID_COL = 1;
    private static final int FIRSTNAME_COL = 2;
    private static final int LASTNAME_COL = 3;
    
    private String[] columnNames = {"Branch","Employee ID","First Name","Last Name"};
    private ArrayList<Employee> employees;
    
    public EmployeeTableModel(ArrayList<Employee> list){
        this.employees = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return employees.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row,int col){
        Employee employee = employees.get(row);
        
        switch(col){
            case BRANCH_COL:
                return employee.getBranchName();
            case EMPLOYEE_ID_COL:
                return employee.getEmployeeID();
            case FIRSTNAME_COL:
                return employee.getFirstName();
            case LASTNAME_COL:
                return employee.getLastName();
            case OBJECT_COL:
                return employee;
            default:
                return employee.getEmployeeID();
        }
    }
        
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
}
