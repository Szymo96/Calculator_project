import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class Calculator extends JFrame {
    public JPanel panel1;
    private JPanel JLabelA;
    private JTextField textField1;
    private JButton b4;
    private JButton b7;
    private JButton b1;
    private JButton b8;
    private JButton b9;
    private JButton b5;
    private JButton b6;
    private JButton b2;
    private JButton b3;
    private JButton b0;
    private JButton commaButton;
    private JButton multiply;
    private JButton divide;
    private JButton subtraction;
    private JButton addition;
    private JButton resultButton;
    private JPanel keyboard;
    private JButton cButton;
    private JButton sqrt;
    private JButton button1;
    private JButton xButton;
    private JButton memoryButton;
    private boolean isLastOperationWasResult;

    double num1, num2, wynik;
    String op;


    public Calculator() {
        super("Prosty kalkulator");
        this.setContentPane(this.panel1); // wyświetlenie na ekranie
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.pack();


        cButton.addActionListener((e) -> {
            textField1.setText("");
            isLastOperationWasResult = false;
        });

        //Utworzenie listener'a dla button'ow
        addActionListenerForButtons();

        b0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isLastOperationWasResult) {
                    textField1.setText("");
                    isLastOperationWasResult = false;
                } else if (textField1.getText().contains("0") && textField1.getText().length() == 1) return;
                else
                    textField1.setText(textField1.getText() + b0.getText());
                
            }
        });


        commaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textField1.getText().contains(".")) {
                    textField1.setText((textField1.getText() + commaButton.getText()));
                }
            }
        });

        addition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                num1 = Double.parseDouble(textField1.getText());
                op = "+";
                textField1.setText("");
            }
        });

        subtraction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                num1 = Double.parseDouble(textField1.getText());
                op = "-";
                textField1.setText("");
            }
        });

        multiply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                num1 = Double.parseDouble(textField1.getText());
                op = "*";
                textField1.setText("");
            }
        });

        divide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                num1 = Double.parseDouble(textField1.getText());
                op = "/";
                textField1.setText("");

            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double pom = Double.parseDouble(textField1.getText());
                pom = pom * -1;
                textField1.setText(String.valueOf(pom));

            }
        });
        sqrt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                num1 = Double.parseDouble(textField1.getText());
                op = "√";
            }
        });
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                num1 = Double.parseDouble(textField1.getText());
                op = "x²";
            }
        });

        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                num2 = Double.parseDouble(textField1.getText());
                isLastOperationWasResult = true;
                if (op.equals("+")) {
                    wynik = num1 + num2;
                } else if (op.equals("-")) {
                    wynik = num1 - num2;
                } else if (op.equals("*")) {
                    wynik = num1 * num2;
                } else if (op.equals("/")) {
                    if (num2 == 0) {
                        textField1.setText("Nie dzielimy przez 0");
                        num1 = 0;
                        num2 = 0;
                        return;
                    } else {
                        wynik = num1 / num2;
                    }
                } else if (op == "√") {
                    wynik = Math.sqrt(num1);

                } else if (op == "x²") {

                    wynik = Math.pow(num1, 2);

                }
                textField1.setText(String.valueOf(wynik));
                Repository.saveValueToDatabase(wynik);
            }
        });

        //Wyswietlenie danych z bazy
        memoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Repository.printValuesInDatabase();
            }
        });
    }

    private void addActionListenerForButtons() {
        List<JButton> buttonList = new ArrayList<>();
        buttonList.addAll(List.of(b1, b2, b3, b4, b5, b6, b7, b8, b9));

        buttonList.forEach(button -> {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (isLastOperationWasResult) {
                        textField1.setText("");
                        isLastOperationWasResult = false;
                    }
                    textField1.setText(textField1.getText() + button.getText());
                }
            });
        });
    }
}