package com.xterr.tradereporting.dao;

import com.xterr.tradereporting.model.Instruction;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InstructionDAOTest {

    @Test
    public void test_getAll() throws FileNotFoundException {
       DAO<Instruction> dao = new InstructionDAO("mocks/mock_data.json");
       List<Instruction> instructions = dao.getAll();
       assertNotNull(instructions);
       assertEquals(500, instructions.size());
    }


}