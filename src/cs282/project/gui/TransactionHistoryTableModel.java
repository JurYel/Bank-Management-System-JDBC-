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
import cs282.project.core.TransactionHistory;
import javax.swing.table.AbstractTableModel;
public class TransactionHistoryTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int ACTION_COL = 0;
    private static final int ACCOUNT_TYPE_COL = 1;
    private static final int AMOUNT_COL = 2;
    private static final int DATETIME_COL = 3;
    
    private String[] columnNames = {"Action","Account Type","Amount","Date/Time"};
    private ArrayList<TransactionHistory> transactions;
    
    public TransactionHistoryTableModel(ArrayList<TransactionHistory> list){
        this.transactions = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return transactions.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row,int col){
        TransactionHistory trans = transactions.get(row);
        
        switch(col){
            case ACTION_COL:
                return trans.getAction();
            case ACCOUNT_TYPE_COL:
                return trans.getAccountType();
            case AMOUNT_COL:
                return trans.getAmount();
            case DATETIME_COL:
                return trans.getTransDate();
            case OBJECT_COL:
                return trans;
            default:
                return trans.getAccountID();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
}
