package com.xterr.tradereporting.model;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;

/**
 * Represent a customer instruction
 */
public class Instruction {

    // Note than this class must use public fields due to GSON parser. See https://stackoverflow.com/questions/6203487/why-does-gson-use-fields-and-not-getters-setters.
    // (see below for an example with getter and setter).


    private static final String CURRENCY_AED = "AED";
    private static final String CURRENCY_SAR = "SAR";


    // It would be a good idea to have an unique ID field for an instruction

    @SerializedName("Entity")
    public String Entity;

    @SerializedName("BuySell")
    public BuySell BuySell;

    // Float and not float, so we can handle null value if there is a problem with datasource.
    @SerializedName("AgreedFx")
    public Double AgreedFx;

    /**
     * In real life we would probably use something else than string. For instance, we could store currencies in database and use a Currency class.
     */
    @SerializedName("Currency")
    public String Currency;


    // In real life, a time would certainly be specified with the date. Besides, we would store datetime as UTC. ZonedDateTime would be preferred.

    @SerializedName("InstructionDate")
    public LocalDate InstructionDate;

    @SerializedName("SettlementDate")
    public LocalDate SettlementDate;

    @SerializedName("Units")
    public Integer Units;

    @SerializedName("Price Per Unit")
    public Double PricePerUnit;


    // Putting some business logic directly in the model is debatable. This a handy shortcut in a small project like that.

    /**
     * USD amount of a trade = Price per unit * Units * Agreed Fx
     * @return The amount of the trade
     */
    public Double getUsdAmount() throws ModelException {

        // Sanity check
        if (PricePerUnit == null) {
            throw new ModelException("PricePerUnit is missing. Unable to evaluate effective USD Amount.");
        }
        if (Units == null) {
            throw new ModelException("Units is missing. Unable to evaluate effective USD Amount.");
        }
        if (AgreedFx == null) {
            throw new ModelException("AgreedFx is missing. Unable to evaluate effective USD Amount.");
        }


        return PricePerUnit * Units * AgreedFx;
    }

    /**
     * A work week starts Monday and ends Friday, unless the currency of the trade is AED or SAR, where
     * the work week starts Sunday and ends Thursday. No other holidays to be taken into account.
     * A trade can only be settled on a working day.
     * If an instructed settlement date falls on a weekend, then the settlement date should be changed to
     * the next working day.
     * @return The effective settlement date
     */
    public LocalDate getEffectiveSettlementDate() throws ModelException {

        // Sanity check
        if (SettlementDate == null) {
            throw new ModelException("SettlementDate is missing. Unable to evaluate effective settlement date.");
        }
        if (Currency == null) {
            throw new ModelException("Currency is missing. Unable to evaluate effective settlement date.");
        }

        switch (SettlementDate.getDayOfWeek()) {
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
                return SettlementDate;
            case FRIDAY:
                if (isCurrencyAEDorSAR()) {
                    return SettlementDate.plusDays(2);
                } else {
                    return SettlementDate;
                }
            case SATURDAY:
                if (isCurrencyAEDorSAR()) {
                    return SettlementDate.plusDays(1);
                } else {
                    return SettlementDate.plusDays(2);
                }
            case SUNDAY:
                if (isCurrencyAEDorSAR()) {
                    return SettlementDate;
                } else {
                    return SettlementDate.plusDays(1);
                }
            default:
                throw new ModelException(String.format("Unexpected value while evaluating day of week of SettlementDate (%s).", SettlementDate.toString()));
        }
    }

    /**
     * Is Currency AED or SAR ?
     * @return true if Currency AED or SAR
     */
    private boolean isCurrencyAEDorSAR() {
        return (Currency.equalsIgnoreCase(CURRENCY_AED) || Currency.equalsIgnoreCase(CURRENCY_SAR));
    }

}


// Here is below a "clean" example implementation of Instruction with getter and setter

/*public class Instruction {

    private String _entity;
    private BuySell _buySell;
    private long _agreedFx;
    private String _currency;
    private ZonedDateTime _instructionDate;
    private ZonedDateTime _settlementDate;
    private Integer _units;
    private long _pricePerUnit;

    public Instruction() {

    }

    public String getEntity() {
        return _entity;
    }

    public void setEntity(String entity) {
        this._entity = entity;
    }

    public BuySell getBuySell() {
        return _buySell;
    }

    public void setBuySell(BuySell _buySell) {
        this._buySell = _buySell;
    }

    public long getAgreedFx() {
        return _agreedFx;
    }

    public void setAgreedFx(long agreedFx) {
        this._agreedFx = agreedFx;
    }

    public String getCurrency() {
        return _currency;
    }

    public void setCurrency(String currency) {
        this._currency = currency;
    }

    public ZonedDateTime getInstructionDate() {
        return _instructionDate;
    }

    public void setInstructionDate(ZonedDateTime instructionDate) {
        this._instructionDate = instructionDate;
    }

    public ZonedDateTime getSettlementDate() {
        return _settlementDate;
    }

    public void setSettlementDate(ZonedDateTime settlementDate) {
        this._settlementDate = settlementDate;
    }

    public Integer getUnits() {
        return _units;
    }

    public void setUnits(Integer units) {
        this._units = units;
    }

    public long getPricePerUnit() {
        return _pricePerUnit;
    }

    public void setPricePerUnit(long pricePerUnit) {
        this._pricePerUnit = pricePerUnit;
    }

}*/
