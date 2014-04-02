package ca.uwo.csd.cs2212.team10;

import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import java.awt.Component;

public class CommonFunctions{

    public static String formatGrade(double grade){
        if (grade == Student.NO_GRADE)
            return "--.--%";
        else
            return new DecimalFormat("0.##'%'").format(grade);
    }
    
    public static void showErrorMessage(Component parent, String text){
        JOptionPane.showMessageDialog(parent, text, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showWarningMessage(Component parent, String text){
        JOptionPane.showMessageDialog(parent, text, "Warning", JOptionPane.WARNING_MESSAGE);
    }
}