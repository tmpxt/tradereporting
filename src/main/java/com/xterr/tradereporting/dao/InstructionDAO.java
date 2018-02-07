package com.xterr.tradereporting.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.xterr.tradereporting.model.Instruction;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Instruction Data Access Object
 */
public class InstructionDAO implements DAO<Instruction> {

    private final String _fileName;
    private final DateTimeFormatter _dateFormatter = DateTimeFormatter.ofPattern("M/d/yyyy");

    public InstructionDAO(String fileName) {
        _fileName = fileName;
    }

    /**
     * Get all instruction
     * @return A List of Instruction
     * @throws FileNotFoundException Unable to find JSON file
     */
    @Override
    public List<Instruction> getAll() throws FileNotFoundException {

        // Read the JSON mock
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString(), _dateFormatter)).create();

        JsonReader reader = new JsonReader(new FileReader(_fileName));

        return gson.fromJson(reader, new TypeToken<List<Instruction>>() { }.getType());
    }


}
