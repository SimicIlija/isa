package com.isa.projekcije.model.fanzone;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BoughtId implements Serializable {
    @Column(name = "tp_id")
    private long themePropsId;
    @Column(name = "buyer_id")
    private long buyerId;

    public BoughtId() {
    }

    public BoughtId(long themePropsId, long buyerId) {
        this.themePropsId = themePropsId;
        this.buyerId = buyerId;
    }

    public long getThemePropsId() {
        return themePropsId;
    }

    public void setThemePropsId(long themePropsId) {
        this.themePropsId = themePropsId;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoughtId boughtId = (BoughtId) o;

        if (themePropsId != boughtId.themePropsId) return false;
        return buyerId == boughtId.buyerId;
    }

    @Override
    public int hashCode() {
        int result = (int) (themePropsId ^ (themePropsId >>> 32));
        result = 31 * result + (int) (buyerId ^ (buyerId >>> 32));
        return result;
    }
}
