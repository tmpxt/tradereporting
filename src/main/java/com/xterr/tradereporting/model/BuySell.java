package com.xterr.tradereporting.model;

import com.google.gson.annotations.SerializedName;

/**
 * Buy/Sell flag
 */
public enum BuySell {
    @SerializedName("B")
    BUY,

    @SerializedName("S")
    SELL
}
