package com.baidu.speech.restapi.common;

import java.sql.*;

public class DBUtil {

        /**
         * 连接数据
         *
         * @return conn
         */
        public static Connection getConnection() {
            //TODO
            String url = "jdbc:mysql://localhost:3306/art_bf?characterEncoding=UTF-8";
            String username = "root";
            String password = "vertrigo";
            String driver = "com.mysql.jdbc.Driver" ;
            Connection conn = null;
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, username, password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return conn;
        }

        /**
         * 关闭连接对象
         *
         * @param conn
         *            连接对象
         * @param pstmt
         *            预编译对象
         * @param rs
         *            结果集
         */
        public static void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 增删改操作
         *
         * @param sql
         *            SQL命令
         * @param param
         *            参数
         * @return
         */
        public static int executUpdate(String sql, Object[] param) {
            Connection conn = getConnection();
            int result = 0;
            PreparedStatement pstmt = null;
            try {
                pstmt = conn.prepareStatement(sql);
                if (param != null) {
                    for (int i = 0; i < param.length; i++) {
                        pstmt.setObject(i + 1, param[i]);
                    }
                }
                result = pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeAll(conn, pstmt, null);
            }
            return result;
        }
        /**
         * 查询
         *
         * @return int
         * @date 2015-7-25 上午11:10:06
         */
        public static ResultSet executQuery(String sql, String[] param) {
            Connection conn = getConnection();
            PreparedStatement pstmt;
            ResultSet result = null;
            try {
                pstmt = conn.prepareStatement(sql);
                if (param != null) {
                    for (int i = 0; i < param.length; i++) {
                        pstmt.setString(i +1, param[i]);
                    }
                }
                result = pstmt.executeQuery();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }


