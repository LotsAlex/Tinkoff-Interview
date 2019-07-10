package ru.tinkoff.test.translator.dao;

import java.sql.SQLException;

import ru.tinkoff.test.translator.dao.entity.InvoiceInfoEntity;

public interface InvoiceInfoDao {

	void save(InvoiceInfoEntity requestInfoEntity) throws SQLException;

}
