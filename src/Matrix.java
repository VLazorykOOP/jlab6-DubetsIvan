import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class CustomArithmeticException extends ArithmeticException {
    public CustomArithmeticException(String message) {
        super(message);
    }
}

public class Matrix extends JFrame {
    private JTextField filePathField;
    private JButton loadButton;
    private JTable table;
    private DefaultTableModel tableModel;

    public Matrix() {
        setTitle("Matrix Sum Calculator");
        setSize(400, 400); // Збільшено висоту вікна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(400, 100)); // Встановлено більшу висоту панелі
        filePathField = new JTextField(20);
        loadButton = new JButton("Load Matrix");

        panel.add(new JLabel("Enter file path:"));
        panel.add(filePathField);
        panel.add(loadButton);

        add(panel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadButton.addActionListener(new LoadMatrixAction());
    }

    private class LoadMatrixAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String filePath = filePathField.getText();
            try {
                loadMatrixFromFile(filePath);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error opening file: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid data format: " + ex.getMessage());
            } catch (CustomArithmeticException ex) {
                JOptionPane.showMessageDialog(null, "Custom Exception: " + ex.getMessage());
            }
        }
    }

    private void loadMatrixFromFile(String filePath) throws IOException, CustomArithmeticException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int n = Integer.parseInt(br.readLine().trim());
            if (n <= 0 || n > 15) {
                throw new CustomArithmeticException("Matrix size must be between 1 and 15");
            }

            tableModel.setRowCount(0);
            tableModel.setColumnCount(n);

            for (int i = 0; i < n; i++) {
                line = br.readLine();
                String[] values = line.split(" ");
                if (values.length != n) {
                    throw new NumberFormatException("Row " + (i + 1) + " does not contain " + n + " elements.");
                }
                Object[] row = new Object[n];
                for (int j = 0; j < n; j++) {
                    row[j] = Integer.parseInt(values[j]);
                }
                tableModel.addRow(row);
            }

            calculateSum(n);
        }
    }

    private void calculateSum(int n) {
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = (int) tableModel.getValueAt(i, j);
            }
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && i >= j && a[i][j] > max) {
                    max = a[i][j];
                }
            }
        }

        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (a[i][j] > max) {
                    sum += a[i][j];
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Result: " + sum);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Matrix app = new Matrix();
            app.setVisible(true);
        });
    }
}
