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
import java.text.SimpleDateFormat;
import java.util.Date;

public class Address {
    private JPanel Main;
    private JTextField txtCityId;
    private JTextField txtAddress;
    private JTextField txtAddress2;
    private JTextField txtDistrict;
    private JTextField txtPostalCode;
    private JTextField txtPhone;
    private JButton btnSave;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JTable tblAddress;
    private JTextField txtId;
    Connection con = DBConnect.Connect();
    PreparedStatement pst;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Address");
        frame.setContentPane(new Address().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Address() {
        table_load_address();
        txtId.setVisible(false);

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cityId = txtCityId.getText();
                String address = txtAddress.getText();
                String address2 = txtAddress2.getText();
                String district = txtDistrict.getText();
                String postalCode = txtPostalCode.getText();
                String phone = txtPhone.getText();
                String getDateTime = getDateTime();

                try {
                    pst = con.prepareStatement("insert into movie_rental.address(cityid, address, address2, district, postal_code, phone, last_update)values(?,?,?,?,?,?,?)");
                    pst.setInt(1, Integer.parseInt(cityId));
                    pst.setString(2, address);
                    pst.setString(3, address2);
                    pst.setInt(4, Integer.parseInt(district));
                    pst.setString(5, phone);
                    pst.setString(6, postalCode);
                    pst.setString(7, getDateTime);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Success insert data");

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
        tblAddress.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                onSelectAddress();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText();
                try{
                    pst = con.prepareStatement("delete from movie_rental.address  where AddressID = ?");

                    pst.setString(1, id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Success delete address");

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
                String cityId = txtCityId.getText();
                String address = txtAddress.getText();
                String address2 = txtAddress2.getText();
                String district = txtDistrict.getText();
                String postalCode = txtPostalCode.getText();
                String phone = txtPhone.getText();
                String getDateTime = getDateTime();

                try {
                    pst = con.prepareStatement("update movie_rental.address set CityID = ?, Address = ?," +
                            "Address2 = ?, District = ?, Postal_Code = ?, Phone = ?, Last_Update = ? " +
                            "where AddressID = ?");

                    pst.setInt(1, Integer.parseInt(cityId));
                    pst.setString(2, address);
                    pst.setString(3, address2);
                    pst.setInt(4, Integer.parseInt(district));
                    pst.setString(5, phone);
                    pst.setString(6, postalCode);
                    pst.setString(7, getDateTime);
                    pst.setString(8, id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Success update data");

                    table_load_address();
                    emptyAllForm();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private String getDateTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;

        return format.format(date);
    }

    private void emptyAllForm() {
        txtCityId.setText("");
        txtAddress.setText("");
        txtAddress2.setText("");
        txtDistrict.setText("");
        txtPostalCode.setText("");
        txtPhone.setText("");
    }

    private void table_load_address() {
        try{
            pst = con.prepareStatement("select * from movie_rental.address");
            ResultSet rs = pst.executeQuery();
            tblAddress.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void onSelectAddress() {
        int row = tblAddress.getSelectedRow();
        String id = tblAddress.getModel().getValueAt(row, 0).toString();
        String cityId = tblAddress.getModel().getValueAt(row, 1).toString();
        String address = tblAddress.getModel().getValueAt(row, 2).toString();
        String address2 = tblAddress.getModel().getValueAt(row, 3).toString();
        String district = tblAddress.getModel().getValueAt(row, 4).toString();
        String postalCode = tblAddress.getModel().getValueAt(row, 5).toString();
        String phone = tblAddress.getModel().getValueAt(row, 6).toString();

        txtId.setText(id);
        txtCityId.setText(cityId);
        txtAddress.setText(address);
        txtAddress2.setText(address2);
        txtDistrict.setText(district);
        txtPostalCode.setText(postalCode);
        txtPhone.setText(phone);
    }
}
