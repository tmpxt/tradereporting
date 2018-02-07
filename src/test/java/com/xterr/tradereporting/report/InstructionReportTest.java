package com.xterr.tradereporting.report;

import com.xterr.tradereporting.dao.InstructionDAO;
import com.xterr.tradereporting.model.BuySell;
import com.xterr.tradereporting.model.ModelException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InstructionReportTest {
    @Test
    void test_getIncomingUsdSettledPerDay() throws ModelException {
        InstructionReport reporter = new InstructionReport(new InstructionDAO("mocks/mock_data.json"));
        Map<LocalDate, Double> results = reporter.getUsdSettledPerDay(BuySell.BUY);
        assertNotNull(results);
        assertEquals(139, results.size());
        results = reporter.getUsdSettledPerDay(BuySell.SELL);
        assertNotNull(results);
        assertEquals(154, results.size());
    }

    @Test
    void test_getEntitiesRanking() throws ModelException {
        InstructionReport reporter = new InstructionReport(new InstructionDAO("mocks/mock_data.json"));
        Map<String, Double> results = reporter.getEntitiesRanking(BuySell.BUY);
        assertNotNull(results);
        assertEquals(226, results.size());
    }
}