package com.xterr.tradereporting.report;

import com.xterr.tradereporting.dao.DAO;
import com.xterr.tradereporting.model.BuySell;
import com.xterr.tradereporting.model.Instruction;
import com.xterr.tradereporting.model.ModelException;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Instruction report engine
 */
public class InstructionReport {

    private List<Instruction> _instructions;

    /**
     * Default constructor
     * @param dao An instruction DAO. A better strategy would to inject it.
     * @throws ModelException if something goes wrong with data
     */
    public InstructionReport(DAO<Instruction> dao) throws ModelException {
        try {
            _instructions = dao.getAll();
        } catch (FileNotFoundException e) {
            throw new ModelException("Unable to read all instructions", e);
        }
    }

    /**
     * Amount in USD settled incoming/outgoing everyday
     * @param filterFlag Filter by buy or sell
     * @return An unsorted Map of settled incoming/outgoing everyday
     */
    public Map<LocalDate, Double> getUsdSettledPerDay(BuySell filterFlag) {
        List<Instruction> instructions = _instructions;
        return instructions.stream().filter(i -> i.BuySell == filterFlag).collect(
                Collectors.groupingBy(InstructionReport::uncheckedSettlementDate, Collectors.summingDouble(InstructionReport::uncheckedUsdAmount)));
    }

    /**
     * Ranking of entities based on incoming and outgoing amount. Eg: If entity foo instructs the highest
     * amount for a buy instruction, then foo is rank 1 for outgoing
     * @param filterFlag Filter by buy or sell
     * @return An sorted Map of settled incoming/outgoing everyday
     */
    public Map<String, Double> getEntitiesRanking(BuySell filterFlag) {
        List<Instruction> instructions = _instructions;
        Map<String, Double> sumByEntity = instructions.stream().filter(i -> i.BuySell == filterFlag).collect(
                Collectors.groupingBy(i -> i.Entity, Collectors.summingDouble(InstructionReport::uncheckedUsdAmount)));

        return sumByEntity.entrySet().stream().sorted(Map.Entry.<String, Double> comparingByValue().reversed()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }


    private static LocalDate uncheckedSettlementDate(Instruction instruction) {
        try {
            return instruction.getEffectiveSettlementDate();
        } catch (ModelException e) {
            // This is clearly not the best error handling strategy.
            e.printStackTrace();
            return null;
        }
    }

    private static double uncheckedUsdAmount(Instruction instruction) {
        try {
            return instruction.getUsdAmount();
        } catch (ModelException e) {
            // This is clearly not the best error handling strategy. In real life we would ensure data are correct before running report.
            e.printStackTrace();
            return 0;
        }
    }

}
