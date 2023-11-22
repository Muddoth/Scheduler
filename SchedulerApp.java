/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.schedulerapp;

/**
 *
 * @author Himiko
 */
import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedWriter;
import java.io.File;

public class SchedulerApp {

    private ArrayList<String[]> data_2d_array; // ArrayList to store the 2D data
    private String[] data_array_per_row; // Array to store data for each row

    public SchedulerApp() {
        data_2d_array = new ArrayList<>();
        createAndShowGUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SchedulerApp().createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
        // Create and configure the main menu frame
        JFrame mainFrame = new JFrame("Scheduler");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a black panel for the main menu frame
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);

        // Set the layout of the main panel
        mainPanel.setLayout(new FlowLayout());

        // Create buttons for different functionalities with pink background and black italic font
        JButton addEventButton = createStyledButton("Add");
        JButton removeEventButton = createStyledButton("Remove");
        JButton sortEventButton = createStyledButton("Sort");
        JButton viewEventButton = createStyledButton("View");
        // Create a button for saving entries with pink background and black italic font
        JButton saveEntriesButton = createStyledButton("Save Entries");

        // Add action listeners to the buttons
        addEventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddEventFrame();
            }
        });

        removeEventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRemoveEventFrame();

            }
        });

        sortEventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSortEventFrame();

            }
        });

        viewEventButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showViewEventFrame();
            }
        });

        // Add an action listener to the save entries button
        saveEntriesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveEntriesToFile(data_2d_array);
            }
        });

        // Add the save entries button to the main panel
        mainPanel.add(saveEntriesButton);

        // Add buttons to the main panel
        mainPanel.add(addEventButton);
        mainPanel.add(removeEventButton);
        mainPanel.add(sortEventButton);
        mainPanel.add(viewEventButton);

        // Set the main panel as the content pane of the main frame
        mainFrame.setContentPane(mainPanel);

        // Display the main frame
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null); // Center the frame
        mainFrame.setVisible(true);
    }

    private void showAddEventFrame() {//add button
        JFrame addEventFrame = new JFrame("Add Event");

        // Create a black panel for the add event frame
        JPanel addEventPanel = new JPanel();
        addEventPanel.setBackground(Color.BLACK);

        // Set the layout of the add event panel
        addEventPanel.setLayout(new FlowLayout());

        // Create labels with soft pink color
        JLabel nameLabel = createStyledLabel("Name:");
        JLabel categoryLabel = createStyledLabel("Category:");
        JLabel dayLabel = createStyledLabel("Day:");
        JLabel monthLabel = createStyledLabel("Month:");
        JLabel yearLabel = createStyledLabel("Year:");

        // Create text fields with soft black color
        JTextField nameTextField = createStyledTextField(20, Color.BLACK);
        JTextField categoryTextField = createStyledTextField(20, Color.BLACK);
        JTextField dayTextField = createStyledTextField(20, Color.BLACK);
        JTextField monthTextField = createStyledTextField(20, Color.BLACK);
        JTextField yearTextField = createStyledTextField(20, Color.BLACK);

        // Create save button with pink background and black italic font
        JButton saveButton = createStyledButton("Save");

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // Get the values from the text fields
                String dayText = dayTextField.getText();
                String monthText = monthTextField.getText();
                String yearText = yearTextField.getText();

                try {
                    int day = Integer.parseInt(dayText);
                    int month = Integer.parseInt(monthText);
                    int year = Integer.parseInt(yearText);

                    String[] data_array_per_row = new String[5];
                    data_array_per_row[0] = nameTextField.getText();
                    data_array_per_row[1] = categoryTextField.getText();
                    data_array_per_row[2] = dayTextField.getText();
                    data_array_per_row[3] = monthTextField.getText();
                    data_array_per_row[4] = yearTextField.getText();

                    data_2d_array.add(data_array_per_row);
                    saveItems();



                } catch (NumberFormatException ex) {
                    // Display an error dialog for invalid input
                    JOptionPane.showMessageDialog(null, "Invalid Input");
                }

            }
        });

        // Add labels, text fields, and save button to the add event panel
        addEventPanel.add(nameLabel);
        addEventPanel.add(nameTextField);
        addEventPanel.add(categoryLabel);
        addEventPanel.add(categoryTextField);
        addEventPanel.add(dayLabel);
        addEventPanel.add(dayTextField);
        addEventPanel.add(monthLabel);
        addEventPanel.add(monthTextField);
        addEventPanel.add(yearLabel);
        addEventPanel.add(yearTextField);
        addEventPanel.add(saveButton);

        // Set the add event panel as the content pane of the add event frame
        addEventFrame.setContentPane(addEventPanel);

        // Configure and show the Add Event frame
        addEventFrame.pack();
        addEventFrame.setLocationRelativeTo(null); // Center the frame
        addEventFrame.setVisible(true);
    }

    private void showRemoveEventFrame() {
        JFrame removeEventFrame = new JFrame("Remove Event");

        // Create a black panel for the remove event frame
        JPanel removeEventPanel = new JPanel();
        removeEventPanel.setBackground(Color.BLACK);

        // Set the layout of the remove event panel
        removeEventPanel.setLayout(new FlowLayout());

        // Create the label "Enter Event Name:" with soft pink color
        JLabel nameLabel = createStyledLabel("Enter Event Name:");

        // Create the text field "jtfNametobeDeleted" with black font color
        JTextField nameTextField = createStyledTextField(20, Color.BLACK);

        // Create the search button with pink background and black italic font
        JButton searchButton = createStyledButton("Search");

        // Add an action listener to the search button
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String eventName = nameTextField.getText();

                // Iterate over each row in data_2d_array
                for (String[] row : data_2d_array) {
                    // Compare the first column of each row with the entered event name
                    if (row[0].equals(eventName)) {
                        // Set all columns of the matching row to null
                        for (int i = 0; i < row.length; i++) {
                            row[i] = "";
                        }
                        break; // Exit the loop after the first match
                    }
                }
                JOptionPane.showMessageDialog(null, "Event Removed");
            }
        });

        // Add the label, text field, and search button to the remove event panel
        removeEventPanel.add(nameLabel);
        removeEventPanel.add(nameTextField);
        removeEventPanel.add(searchButton);

        // Set the remove event panel as the content pane of the remove event frame
        removeEventFrame.setContentPane(removeEventPanel);

        // Configure and show the Remove Event frame
        removeEventFrame.pack();
        removeEventFrame.setLocationRelativeTo(null);
        removeEventFrame.setVisible(true);
    }

    private void showSortEventFrame() {
        JFrame sortEventFrame = new JFrame("Sort Event");

        // Create a black panel for the sort event frame
        JPanel sortEventPanel = new JPanel();
        sortEventPanel.setBackground(Color.BLACK);

        // Set the layout of the sort event panel
        sortEventPanel.setLayout(new FlowLayout());

        // Create Ascending and Descending buttons with pink background and black italic font
        JButton ascendingButton = createStyledButton("Ascending");
        JButton descendingButton = createStyledButton("Descending");

        ascendingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortEventAscending();
                printDataArray();
                JOptionPane.showMessageDialog(null, "Events are Sorted");
            }
        });

        descendingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sortEventDescending();
                printDataArray();
                JOptionPane.showMessageDialog(null, "Events are Sorted");
            }
        });

        // Add buttons to the sort event panel
        sortEventPanel.add(ascendingButton);
        sortEventPanel.add(descendingButton);

        // Set the sort event panel as the content pane of the sort event frame
        sortEventFrame.setContentPane(sortEventPanel);

        // Configure and show the Sort Event frame
        sortEventFrame.pack();
        sortEventFrame.setLocationRelativeTo(null);
        sortEventFrame.setVisible(true);
    }

    private void showViewEventFrame() {
        JFrame viewEventFrame = new JFrame("View Event");

        // Create a table to display the data
        JTable dataTable = new JTable();

        // Create a scroll pane to contain the table
        JScrollPane scrollPane = new JScrollPane(dataTable);

        // Set the scroll pane as the content pane of the view event frame
        viewEventFrame.setContentPane(scrollPane);

        // Configure and show the View Event frame
        viewEventFrame.pack();
        viewEventFrame.setLocationRelativeTo(null);
        viewEventFrame.setVisible(true);
    }

    private void sortEventAscending( ) {
        Collections.sort(data_2d_array, new Comparator<String[]>() {
            public int compare(String[] row1, String[] row2) {
                String date1 = row1[2] + row1[3] + row1[4];
                String date2 = row2[2] + row2[3] + row2[4];
                return date2.compareTo(date1);
            }
        });

    }

    private void sortEventDescending() {
        Collections.reverse(data_2d_array);
    }

    private void saveItems() {
        // Display the message "Items are saved"
        JOptionPane.showMessageDialog(null, "Event is saved");

        // Test print the contents of data_2d_array
        printDataArray();
    }

    private void printDataArray() {
        System.out.println("Data Array:");
        for (String[] row : data_2d_array) {
            for (String element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

    private static void saveEntriesToFile(ArrayList<String[]> data_2d_array) {
        // Create the directory for the scheduler files
        String directoryPath = System.getProperty("user.home") + "/Desktop/scheduler files";
        File directory = new File(directoryPath);
        directory.mkdirs();

        // Create a timestamp for the file name
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());

        // Create the file
        String filePath = directoryPath + "/data_" + timestamp + ".txt";
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Iterate over the elements in data_2d_array and write them to the file
            for (String[] row : data_2d_array) {
                StringBuilder line = new StringBuilder();
                for (String element : row) {
                    line.append(element).append(",");
                }
                // Remove the trailing comma
                line.deleteCharAt(line.length() - 1);

                writer.write(line.toString());
                writer.newLine();
            }

            System.out.println("Data written to file successfully.");
             JOptionPane.showMessageDialog(null, "Entry Saved to File");
        } catch (IOException e) {
            System.err.println("Error writing data to file: " + e.getMessage());
        }
    }      

    private JButton createStyledButton(String label) {
        JButton button = new JButton(label);

        // Set the background color to pink
        button.setBackground(Color.PINK);

        // Set the font color to black and make it italic
        button.setForeground(Color.BLACK);
        Font font = button.getFont();
        button.setFont(font.deriveFont(font.getStyle() | Font.ITALIC));

        return button;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);

        // Set the foreground color to soft pink
        label.setForeground(new Color(255, 192, 203));

        return label;
    }

    private JTextField createStyledTextField(int columns, Color BLACK) {
        JTextField textField = new JTextField(columns);

        // Set the foreground color to black
        textField.setForeground(Color.BLACK);

        return textField;
    }



}
