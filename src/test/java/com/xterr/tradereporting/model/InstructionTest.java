package com.xterr.tradereporting.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstructionTest {


    @Test
    public void test_getUsdAmount() throws ModelException {
        Instruction instruction = getMockInstruction();
        assertEquals(new Double(10025), instruction.getUsdAmount());
    }

    @Test
    public void test_getEffectiveSettlementDate() throws ModelException {
        Instruction instruction = getMockInstruction();

        assertEquals(instruction.SettlementDate, instruction.getEffectiveSettlementDate());

        LocalDate thursdayDate = LocalDate.of(2016, 1, 7);
        LocalDate fridayDate = LocalDate.of(2016, 1, 8);
        LocalDate saturdayDate = LocalDate.of(2016, 1, 9);
        LocalDate sundayDate = LocalDate.of(2016, 1, 10);
        LocalDate mondayDate = LocalDate.of(2016, 1, 11);

        // Saturday
        instruction.SettlementDate = saturdayDate;
        assertEquals(mondayDate, instruction.getEffectiveSettlementDate());

        // Sunday
        instruction.SettlementDate = sundayDate;
        assertEquals(mondayDate, instruction.getEffectiveSettlementDate());

        // SAR, Monday
        instruction.Currency = "SAR";
        instruction.SettlementDate = mondayDate;
        assertEquals(mondayDate, instruction.getEffectiveSettlementDate());

        // AED, Thursday
        instruction.Currency = "AED";
        instruction.SettlementDate = thursdayDate;
        assertEquals(thursdayDate, instruction.getEffectiveSettlementDate());

        // SAR, Friday
        instruction.Currency = "SAR";
        instruction.SettlementDate = fridayDate;
        assertEquals(sundayDate, instruction.getEffectiveSettlementDate());

        // AED, Friday
        instruction.Currency = "AED";
        assertEquals(sundayDate, instruction.getEffectiveSettlementDate());

        // SAR, Saturday
        instruction.Currency = "SAR";
        instruction.SettlementDate = saturdayDate;
        assertEquals(sundayDate, instruction.getEffectiveSettlementDate());

        // AED, Saturday
        instruction.Currency = "AED";
        assertEquals(sundayDate, instruction.getEffectiveSettlementDate());

    }

    private Instruction getMockInstruction() {
        Instruction instruction = new Instruction();
        instruction.Entity = "foo";
        instruction.BuySell = BuySell.BUY;
        instruction.AgreedFx = 0.50;
        instruction.Currency = "SGP";
        instruction.InstructionDate = LocalDate.of(2016, 1, 1);
        instruction.SettlementDate = LocalDate.of(2016, 1, 5);
        instruction.Units = 200;
        instruction.PricePerUnit = 100.25;

        return instruction;
    }
}