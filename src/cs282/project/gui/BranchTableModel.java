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
import cs282.project.core.Branch;
import javax.swing.table.AbstractTableModel;
public class BranchTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int BANK_NAME_COL = 0;
    private static final int CITY_COL = 1;
    private static final int BRANCH_CODE_COL = 2;
    
    private ArrayList<Branch> branches;
    private String[] columnNames = {"Bank Name","City","Branch Code"};
    
    public BranchTableModel(ArrayList<Branch> list){
        this.branches = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return branches.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row, int col){
        Branch branch = branches.get(row);
        
        switch(col){
            case BANK_NAME_COL:
                return branch.getBankName();
            case CITY_COL:
                return branch.getCity();
            case BRANCH_CODE_COL:
                return branch.getBranchCode();
            case OBJECT_COL:
                return branch;
            default:
                return branch.getBranchID();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
}
