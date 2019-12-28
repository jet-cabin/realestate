package com.jet.realestate.order.handler;

import com.jet.realestate.order.OrderStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderTypeHandler extends BaseTypeHandler<OrderStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OrderStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i,parameter.getV());
    }


    @Override
    public OrderStatus getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.wasNull() ? null : OrderStatus.valueOf(resultSet.getInt(s));
    }

    @Override
    public OrderStatus getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.wasNull() ? null : OrderStatus.valueOf(i);
    }

    @Override
    public OrderStatus getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.wasNull() ? null : OrderStatus.valueOf(callableStatement.getInt(i));
    }
}
