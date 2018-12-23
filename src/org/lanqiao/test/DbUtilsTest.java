package org.lanqiao.test;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;
import org.lanqiao.util.DataSourceUtils;

import java.sql.SQLException;

public class DbUtilsTest {
    @Test
    public void insertEmp() throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "INSERT INTO `user`(username,password) VALUES (?,?)";
        qr.update(sql,"root","root");
    }
}
