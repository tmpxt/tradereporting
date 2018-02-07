package com.xterr.tradereporting;

import com.xterr.tradereporting.dao.InstructionDAO;
import com.xterr.tradereporting.model.BuySell;
import com.xterr.tradereporting.model.ModelException;
import com.xterr.tradereporting.report.InstructionReport;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;

public class App {
    public static void main(String[] args) {

        try {
            InstructionReport reporter = new InstructionReport(new InstructionDAO("mocks/mock_data.json"));

            System.out.println("** Amount in USD settled incoming everyday **");
            Map<LocalDate, Double> incoming = reporter.getUsdSettledPerDay(BuySell.BUY);
            incoming.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(entry -> System.out.println(String.format("Date %s: %s USD", entry.getKey().toString(), entry.getValue())));


            System.out.println("");
            System.out.println("**  Amount in USD settled outgoing everyday **");
            Map<LocalDate, Double> outgoing = reporter.getUsdSettledPerDay(BuySell.SELL);
            outgoing.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(entry -> System.out.println(String.format("Date %s: %s USD", entry.getKey().toString(), entry.getValue())));

            System.out.println("");
            System.out.println("** Ranking of entities based on incoming **");
            Map<String, Double> rankingIncoming = reporter.getEntitiesRanking(BuySell.BUY);
            rankingIncoming.forEach((key, value) -> System.out.println(String.format("Entity %s: %s USD", key, value)));

            System.out.println("");
            System.out.println("** Ranking of entities based on outgoing **");
            Map<String, Double> rankingOutgoing = reporter.getEntitiesRanking(BuySell.SELL);
            rankingOutgoing.forEach((key, value) -> System.out.println(String.format("Entity %s: %s USD", key, value)));


        } catch (Exception e) {
            System.out.println("Something goes wrong...");
            e.printStackTrace();
        }

    }
}
