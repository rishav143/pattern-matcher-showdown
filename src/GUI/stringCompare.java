package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class stringCompare implements ActionListener {
	
	private JFrame f;
	private JTextField t1;
    private JTextField t2;
    private JButton b1;
    private JTextArea resultArea;
    
    stringCompare() {
    	f = new JFrame();
    	
    	//adding top label as heading
    	JLabel thead = new JLabel("Pattern Matcher Showdown");
    	thead.setBounds(50, 30, 500, 30);
    	thead.setFont(new Font("Verdana", Font.BOLD, 24));
    	thead.setForeground(Color.BLUE);
    	thead.setHorizontalAlignment(SwingConstants.CENTER);
    	f.add(thead);
    	
    	//user entering the string 1
    	t1 = new JTextField();
    	JLabel l1 = new JLabel("Enter Source String :- ");
    	l1.setFont(new Font("Verdana", Font.BOLD, 16));
    	l1.setBounds(20, 90, 300, 30);
    	t1.setBounds(20, 130, 300, 30);
    	f.add(l1);
    	f.add(t1);
    	
    	//user entering the string 2
    	t2 = new JTextField();
    	JLabel l2 = new JLabel("Enter Target String :- ");
    	l2.setFont(new Font("Verdana", Font.BOLD, 16));
    	l2.setBounds(20, 170, 300, 30);
    	t2.setBounds(20, 210, 300, 30);
    	f.add(l2);
    	f.add(t2);
    	
    	// Adding button to compare
    	b1 = new JButton("compare");
    	b1.setBounds(20, 260, 150, 40);
    	b1.setFont(new Font("Verdana", Font.BOLD, 16)); 
    	b1.setBackground(Color.BLUE);
    	b1.setForeground(Color.WHITE); 
    	b1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    	b1.addActionListener(this);
    	f.add(b1);
    	
    	// give output of result after comparison
    	resultArea = new JTextArea();
    	resultArea.setBounds(20, 330, 500, 500);
    	resultArea.setFont(new Font("Verdana", Font.ITALIC, 16)); 
    	resultArea.setEditable(false);
    	f.add(resultArea);

    	f.setSize(600, 700);
    	f.getContentPane().setBackground(Color.white);
    	f.setLayout(new BorderLayout());
    	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	f.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == b1) {
            String str1 = t1.getText();
            String str2 = t2.getText();
            comparePerformance(str1, str2);
        }
    }
    
    private void comparePerformance(String str1, String str2) {
        long startTime, endTime;
        long kmpTime, bmTime;

        // Perform KMP Algorithm
        startTime = System.nanoTime();
        String kmpResult = kmpStringMatching(str1, str2);
        endTime = System.nanoTime();
        kmpTime = endTime - startTime;

        // Perform Boyer-Moore Algorithm
        startTime = System.nanoTime();
        String bmResult = boyerMooreStringMatching(str1, str2);
        endTime = System.nanoTime();
        bmTime = endTime - startTime;

        // Display results in the GUI
        resultArea.setText("KMP Algorithm Result:\n" + kmpResult + "\n\n"
                + "Boyer-Moore Algorithm Result:\n" + bmResult + "\n\n"
                + "KMP Algorithm Execution Time: " + kmpTime + " nanoseconds\n"
                + "Boyer-Moore Algorithm Execution Time: " + bmTime + " nanoseconds\n"
                + "Algorithm Comparison: "
                + ((kmpTime < bmTime) ? "KMP is faster." : (kmpTime > bmTime) ? "Boyer-Moore is faster." : "Both have similar time."));
    }

    // Compute the LPS (Longest Prefix Suffix) array for KMP algorithm
    private int[] computeLPSArray(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0;
        int i = 1;
        lps[0] = 0;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0)
                    len = lps[len - 1];
                else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
    
    private String kmpStringMatching(String s1, String s2) {
        int[] lps = computeLPSArray(s2);
        int i = 0, j = 0;
        StringBuilder sb = new StringBuilder();

        while (i < s1.length()) {
            if (s1.charAt(i) == s2.charAt(j)) {
                i++;
                j++;
            }
            if (j == s2.length()) {
                sb.append("Pattern found at index ").append(i - j).append("\n");
                j = lps[j - 1];
            } else if (i < s1.length() && s1.charAt(i) != s2.charAt(j)) {
                if (j != 0)
                    j = lps[j - 1];
                else
                    i++;
            }
        }

        if (sb.length() == 0)
            return "No match found.";
        else
            return sb.toString(); // Return the result if matches found
    }
    
    //Boyer Moore String Matching
    private String boyerMooreStringMatching(String s1, String s2) {
        int[] last = buildLastTable(s2);
        int i = s2.length() - 1;
        int j = s2.length() - 1;
        StringBuilder sb = new StringBuilder();

        while (i < s1.length()) {
            if (s1.charAt(i) == s2.charAt(j)) {
                if (j == 0) {
                    sb.append("Pattern found at index ").append(i).append("\n");
                    i += s2.length();
                    j = s2.length() - 1;
                } else {
                    i--;
                    j--;
                }
            } else {
                i += s2.length() - Math.min(j, 1 + last[s1.charAt(i)]);
                j = s2.length() - 1;
            }
        }

        if (sb.length() == 0)
            return "No match found.";
        else 
        	return sb.toString();
    }

    // Build the Last table for Boyer-Moore algorithm
    private int[] buildLastTable(String pattern) {
        int[] last = new int[256];
        for (int i = 0; i < last.length; i++) {
            last[i] = -1;
        }
        for (int i = 0; i < pattern.length(); i++) {
            last[pattern.charAt(i)] = i;
        }
        return last;
    }

    public static void main(String[] args) {
        new stringCompare();
    }
}
