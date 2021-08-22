package view;

import connection.DBConnect;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Mahasiswa {
    private JPanel Main;
    private JTextField mahasiswaId;
    private JTextField nama;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JTable tblMahasiswa;
    private JTextField txtId;
    Connection con = DBConnect.Connect();
    PreparedStatement pst;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mahasiswa");
        frame.setContentPane(new Mahasiswa().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Mahasiswa() {
        table_load_address();
        txtId.setVisible(false);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nim = mahasiswaId.getText();
                String namaMahasiswa = nama.getText();

                try {
                    pst = con.prepareStatement("insert into SistemInformasiAkademik.mahasiswa(nim, nama)values(?,?)");
                    pst.setInt(1, Integer.parseInt(nim));
                    pst.setString(2, namaMahasiswa);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Success insert data Mahasiswa");

                    emptyAllForm();
                    table_load_address();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        tblMahasiswa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                onSelectMahasiswa();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText();
                try{
                    pst = con.prepareStatement("delete from SistemInformasiAkademik.mahasiswa where id = ?");

                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Success delete data mahasiswa");

                    table_load_address();
                    emptyAllForm();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText();
                String nim = mahasiswaId.getText();
                String namaMahasiswa = nama.getText();

                try {
                    pst = con.prepareStatement("update SistemInformasiAkademik.mahasiswa set nim = ?," +
                            "nama = ?" +
                            "where id = ?");

                    pst.setInt(1, Integer.parseInt(nim));
                    pst.setString(2, namaMahasiswa);
                    pst.setString(3, id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Success update data mahasiswa");

                    table_load_address();
                    emptyAllForm();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void emptyAllForm() {
        mahasiswaId.setText("");
        nama.setText("");
    }

    private void table_load_address() {
        try{
            pst = con.prepareStatement("select * from SistemInformasiAkademik.mahasiswa");
            ResultSet rs = pst.executeQuery();
            tblMahasiswa.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void onSelectMahasiswa() {
        int row = tblMahasiswa.getSelectedRow();
        String id = tblMahasiswa.getModel().getValueAt(row, 0).toString();
        String nim = tblMahasiswa.getModel().getValueAt(row, 1).toString();
        String namaMahasiswa = tblMahasiswa.getModel().getValueAt(row, 2).toString();

        txtId.setText(id);
        mahasiswaId.setText(nim);
        nama.setText(namaMahasiswa);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
