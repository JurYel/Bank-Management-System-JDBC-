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
import cs282.project.core.Bank;

public class BankTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int BANK_NAME_COL = 0;
    private static final int BANK_CODE_COL = 1;
    private static final int BANK_ADDRESS_COL = 2;
    
    private ArrayList<Bank> banks;
    private String[] columnNames = {"Bank Name","Bank Code","Bank Address"};
    
    public BankTableModel(ArrayList<Bank> list){
        this.banks = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return banks.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row, int col){
        Bank bank = banks.get(row);
        
        switch(col){
            case BANK_NAME_COL:
                return bank.getBankName();
            case BANK_CODE_COL:
                return bank.getBankCode();
            case BANK_ADDRESS_COL:
                return bank.getBankAddress();
            case OBJECT_COL:
                return bank;
            default:
                return bank.getBankID();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
}
