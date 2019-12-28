package com.jet.realestate.house.handler;

import com.jet.realestate.house.common.Layout;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LayoutHandler extends BaseTypeHandler<Layout> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Layout parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i,parameter.getVal());
    }


    @Override
    public Layout getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.wasNull() ? null : Layout.valueOf(resultSet.getInt(s));
    }

    @Override
    public Layout getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.wasNull() ? null : Layout.valueOf(i);
    }

    @Override
    public Layout getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.wasNull() ? null : Layout.valueOf(callableStatement.getInt(i));
    }
}
