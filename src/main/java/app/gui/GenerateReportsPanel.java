package app.gui;

import data.reporting.Report;
import socket.client.TcpClient;
import socket.client.UdpClient;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GenerateReportsPanel extends JFrame
{
    private final JTextField filePathField;
    private final GenerateReportsPanelHandler generateReportsPanelHandler;


    public GenerateReportsPanel(TcpClient tcpClient, UdpClient udpClient) throws Exception
    {
        generateReportsPanelHandler = new GenerateReportsPanelHandler(tcpClient, udpClient);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 10, 10);

        JLabel titleLabel = new JLabel("Generate VPN report with given file");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        JButton browseFilesButton = new JButton("Browse");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(browseFilesButton, gbc);

        filePathField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(filePathField, gbc);

        JButton generateReportButton = new JButton("Generate Report");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(generateReportButton, gbc);

        browseFilesButton.addActionListener(e -> openFileChooser());
        generateReportButton.addActionListener(e -> generateReport());

        setTitle("VPN Encryption");
        setSize(600, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void openFileChooser()
    {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if(returnValue == JFileChooser.APPROVE_OPTION)
        {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();
            filePathField.setText(filePath);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "No file selected.", "File Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void generateReport()
    {
        String filePath = filePathField.getText();

        if(filePath == null || filePath.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Please select a file.", "File Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        File file = new File(filePath);
        if(!file.exists() || !file.isFile())
        {
            JOptionPane.showMessageDialog(this, "Invalid file selected.", "File Selection", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try
        {
            generateReportsPanelHandler.generateReports(file);

            Report aesReport = generateReportsPanelHandler.getAesReport();
            Report camelliaReport = generateReportsPanelHandler.getCamelliaReport();
            Report threeDesReport = generateReportsPanelHandler.getThreeDesReport();
            Report chacha20Report = generateReportsPanelHandler.getChacha20Report();

            System.out.println(aesReport.toString());

            JOptionPane.showMessageDialog(this, "Reports generated successfully.", "Report Generation", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "Error generating report: " + e.getMessage(), "Report Generation", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


}
