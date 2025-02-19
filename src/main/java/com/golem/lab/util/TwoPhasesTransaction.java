package com.golem.lab.util;

import com.golem.lab.service.ServiceException;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.postgresql.jdbc.PgStatement;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.postgresql.core.QueryExecutor.*;

public class TwoPhasesTransaction {

    public interface Handler {
        void handle() throws ServiceException;
    }

    private String transactionId = null;
    private final EntityManager entityManager;

    public TwoPhasesTransaction(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void inTransaction(Handler phase1, Handler phase2, Handler rollback) throws ServiceException {
        try {
            phase1.handle();
            prepareTransactionForCommit();

            phase2.handle();
            commitPreparedTransaction();
        } catch (Exception e) {
            try {
                rollbackPreparedTransaction();
            } catch (Exception ex) {
                e.addSuppressed(ex);
            }
            try {
                rollback.handle();
            } catch (Exception ex) {
                e.addSuppressed(ex);
            }
            throw new ServiceException(e);
        }
    }

    private void rollbackPreparedTransaction() throws SQLException {
        if (null != transactionId) {
            String sql = String.format("ROLLBACK PREPARED '%s'", transactionId);
            System.out.println("#3 " + sql);
            processPreparedTransaction(sql);
        }
    }

    private void commitPreparedTransaction() throws SQLException {
        if (null != transactionId) {
            String sql = String.format("COMMIT PREPARED '%s'", transactionId);
            System.out.println("#2 " + sql);
            processPreparedTransaction(sql);
        }
    }

    private void prepareTransactionForCommit() {
        transactionId = String.valueOf(System.nanoTime());
        String sql = String.format("PREPARE TRANSACTION '%s'", transactionId);
        System.err.println("#1 " + sql);
        entityManager.createNativeQuery(sql).executeUpdate();

    }

    private void processPreparedTransaction(String sql) throws SQLException {
        try (PreparedStatement st = entityManager.unwrap(Session.class)
                .unwrap(SharedSessionContractImplementor.class)
                .getJdbcCoordinator()
                .getStatementPreparer()
                .prepareStatement(sql)) {
            PgStatement pgst = st.unwrap(PgStatement.class);
            pgst.executeWithFlags(QUERY_SUPPRESS_BEGIN | QUERY_NO_RESULTS | QUERY_ONESHOT);
        }
    }
}
