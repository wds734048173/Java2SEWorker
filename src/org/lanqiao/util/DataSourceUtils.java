package org.lanqiao.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceUtils {
    private static DataSource ds = null;
    static {
        //方法一
        InputStream in = DataSourceUtils.class.getClassLoader().getResourceAsStream("dbcp.properties");
        Properties prop = new Properties();
        try {
            prop.load(in);
            ds = BasicDataSourceFactory.createDataSource(prop);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //方法二
        /*ComboPooledDataSource cpds = new ComboPooledDataSource();
        ds = cpds;*/
    }

    public static DataSource getDataSource(){
        return ds;
    }

    public static Connection getConntion(){
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
