package ru.tinkoff.test.translator.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import ru.tinkoff.test.translator.dao.entity.InvoiceInfoEntity;

@Repository
public class InvoiceInfoDaoImpl implements InvoiceInfoDao {
	private DataSource dataSource;

	public InvoiceInfoDaoImpl(DataSource dataSource) {
		this.dataSource = dataSource;

	}

	@Override
	public void save(InvoiceInfoEntity invoiceInfoEntity) throws SQLException {

		try (Connection connection = dataSource.getConnection();
				PreparedStatement stmt = connection.prepareStatement(
						"insert into invoice (INVOICE_TIME, INPUT_PARAMS, CLIENT_IP) values (?, ?, ?)")) {
			stmt.setTimestamp(1, Timestamp.valueOf(invoiceInfoEntity.getInvoiceTime()));
			Array inputParamsArray = connection.createArrayOf("text", invoiceInfoEntity.getInputParams());
			stmt.setArray(2, inputParamsArray);
			stmt.setString(3, invoiceInfoEntity.getClientIp());
			stmt.executeUpdate();
		}

	}

}
