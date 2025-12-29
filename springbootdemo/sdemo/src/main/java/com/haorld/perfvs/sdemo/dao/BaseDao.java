package com.haorld.perfvs.sdemo.dao;

import jakarta.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class BaseDao {

    @Resource
    JdbcTemplate jdbcTemplate;

    public void closeJdbc(Connection connection, PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean batchInsertData(int times, String tableName, String[] colNames, Map<Integer, String> valueMap) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = jdbcTemplate.getDataSource().getConnection();
            connection.setAutoCommit(false);

            System.out.println("===== 开始插入数据 =====");
            long startTime = System.currentTimeMillis();
            String sqlInsert = "INSERT INTO "
                    + tableName
                    + " ( "
                    + StringUtils.join(colNames)
                    + ") VALUES ("
                    + genValueFormat(colNames)
                    + ")";
            preparedStatement = connection.prepareStatement(sqlInsert);

            Random random = new Random();
            for (int i = 1; i <= times; i++) {

                for (int j = 0; j < valueMap.size(); j++) {
                    int index = j + 1;

                    String valueType = valueMap.get(index);
                    if ("String" == valueType) {
                        preparedStatement.setString(index, "apple" + i);
                    } else if ("long" == valueType) {
                        preparedStatement.setLong(index, random.nextLong(10000L));
                    } else {
                        if (colNames[j].contains("id")) {
                            preparedStatement.setInt(index, i);
                        } else {
                            preparedStatement.setInt(index, random.nextInt(10000));
                        }
                    }

                }
                // 添加到批处理中
                preparedStatement.addBatch();
                if (i % 1000 == 0) {
                    // 每1000条数据提交一次
                    preparedStatement.executeBatch();
                    connection.commit();
                    System.out.println("成功插入第 " + i + " 条数据");
                }

            }
            // 处理剩余的数据
            preparedStatement.executeBatch();
            connection.commit();
            long spendTime = System.currentTimeMillis() - startTime;
            System.out.println("成功插入 " + times + " 条数据,耗时：" + spendTime + "毫秒");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            closeJdbc(connection, preparedStatement);
        }
        return false;
    }

    public String genValueFormat(String[] colNames) {
        int num = Arrays.asList(colNames).size();
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < num) {
            sb.append("?");
            sb.append(",");
            i++;
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

}
