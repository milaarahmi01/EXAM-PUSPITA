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

public class City {
    private JPanel Main;
    private JTextField CityName;
    private JTextField CityId;
    private JButton btnCityUpdate;
    private JButton btnCityDelete;
    private JScrollPane scrlTblCity;
    private JTable tblCity;
    private JButton btnCitySave;
    private JTextField CountryId;
    Connection con = DBConnect.Connect();
    PreparedStatement pst;

    public static void main(String[] args) {
        JFrame frame = new JFrame("City");
        frame.setContentPane(new City().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public City() {
        hideAllFormId();
        table_load_city();

        btnCitySave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = CityName.getText();
                String countryId = CountryId.getText();
                String getDateTime = getDateTime();
                try {
                    pst = con.prepareStatement("insert into movie_rental.city(city_name,CountryId,Last_Update)values(?,?,?)");
                    pst.setString(1, city);
                    pst.setString(2, countryId);
                    pst.setString(3, getDateTime);
                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Success insert data");
                    table_load_city();
                    CityId.setText("");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        tblCity.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                onSelectCity();
            }
        });

        btnCityDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cityId = CityId.getText();
                try{
                    pst = con.prepareStatement("delete from movie_rental.city  where cityId = ?");

                    pst.setString(1, cityId);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Success delete city");
                    table_load_city();

                    CityId.setText("");
                    CityName.setText("");
                    CountryId.setText("");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        btnCityUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = CityName.getText();
                String cityId = CityId.getText();
                String countryID = CountryId.getText();
                String getDateTime = getDateTime();


                System.out.print(cityId );


                try {
                    pst = con.prepareStatement("update movie_rental.city set city_name = ?,Last_Update = ?,CountryID = ? where CityId = ?");
                    pst.setString(1, city);
                    pst.setString(2, getDateTime);
                    pst.setString(3, countryID);
                    pst.setString(4, cityId);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Success update data");
                    table_load_city();

                    CityName.setText("");
                    CountryId.setText("");
                    CityId.setText("");
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void onSelectCity() {
        int row = tblCity.getSelectedRow();
        String id = tblCity.getModel().getValueAt(row, 0).toString();
        String city = tblCity.getModel().getValueAt(row, 3).toString();
        String countryID = tblCity.getModel().getValueAt(row,1).toString();

        CityId.setText(id);
        CityName.setText(city);
        CountryId.setText(countryID);
    }

    private String getDateTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return format.format(date);
    }

    private void table_load_city() {
        try {
            pst = con.prepareStatement("select * from movie_rental.city");
            ResultSet rs = pst.executeQuery();
            tblCity.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void hideAllFormId() {
        CityId.setVisible(false);
    }
}

