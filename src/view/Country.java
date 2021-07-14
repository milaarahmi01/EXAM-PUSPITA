package view;

import connection.DBConnect;
import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Country<function> {
    private JPanel Main;
    private JTextField txtCountry;
    private JButton btnCountrySave;
    private JScrollPane scrlTblCountry;
    private JTable tblCountry;
    private JButton btnCountryUpdate;
    private JTextField txtCountryId;
    private JButton btnCountryDelete;
    Connection con = DBConnect.Connect();
    PreparedStatement pst;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Country");
        frame.setContentPane(new Country().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public Country() {
        hideAllFormId();
        table_load_country();

        btnCountrySave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String country = txtCountry.getText();
                String getDateTime = getDateTime();
                try {
                    pst = con.prepareStatement("insert into movie_rental.country(country, Last_Update)values(?,?)");
                    pst.setString(1, country);
                    pst.setString(2, getDateTime);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Success insert data");
                    table_load_country();
                    txtCountry.setText("");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        tblCountry.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onSelectCountry();
            }
        });
        btnCountryDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String countryID = txtCountryId.getText();
                try{
                    pst = con.prepareStatement("delete from movie_rental.country  where CountryID = ?");

                    pst.setString(1, countryID);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Success delete country");
                    table_load_country();

                    txtCountryId.setText("");
                    txtCountry.setText("");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
        btnCountryUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String country = txtCountry.getText();
                String countryID = txtCountryId.getText();
                String getDateTime = getDateTime();

                try {
                    pst = con.prepareStatement("update movie_rental.country set country = ?,Last_Update = ? where CountryID = ?");
                    pst.setString(1, country);
                    pst.setString(2, getDateTime);
                    pst.setString(3, countryID);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Success update data");
                    table_load_country();

                    txtCountry.setText("");
                    txtCountryId.setText("");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    void hideAllFormId() {
        txtCountryId.setVisible(false);
    }
    void table_load_country() {
        try{
            pst = con.prepareStatement("select * from movie_rental.country");
            ResultSet rs = pst.executeQuery();
            tblCountry.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    void onSelectCountry() {
        int row = tblCountry.getSelectedRow();
        String id = tblCountry.getModel().getValueAt(row, 0).toString();
        String country = tblCountry.getModel().getValueAt(row, 1).toString();

        txtCountryId.setText(id);
        txtCountry.setText(country);
    }
    private String getDateTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;

        return format.format(date);
    }
}
