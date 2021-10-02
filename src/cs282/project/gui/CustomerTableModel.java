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
import cs282.project.core.Customer;
import javax.swing.table.AbstractTableModel;
public class CustomerTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int FIRST_NAME_COL = 0;
    private static final int LAST_NAME_COL = 1;
    private static final int ADDRESS_COL = 2;
    private static final int CONTACT_COL = 3;
    
    private ArrayList<Customer> customers;
    private String[] columnNames = {"First Name","Last Name","Address","Contact Number"};
    
    public CustomerTableModel(ArrayList<Customer> list){
        this.customers = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return customers.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row,int col){
        Customer customer = customers.get(row);
        
        switch(col){
            case FIRST_NAME_COL:
                return customer.getCustomerFirstName();
            case LAST_NAME_COL:
                return customer.getCustomerLastName();
            case ADDRESS_COL:
                return customer.getCustomerAddress();
            case CONTACT_COL:
                return customer.getContactNumber();
            case OBJECT_COL:
                return customer;
            default:
                return customer.getCustomerID();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
}
