package com.isa.projekcije.model.fanzone;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BidId implements Serializable {
    @Column(name = "bidder_id")
    private long bidderId;
    @Column(name = "user_props_id")
    private long userPropsId;

    public BidId() {
    }

    public BidId(long bidderId, long userPropsId) {
        this.bidderId = bidderId;
        this.userPropsId = userPropsId;
    }

    public long getBidderId() {
        return bidderId;
    }

    public void setBidderId(long bidderId) {
        this.bidderId = bidderId;
    }

    public long getUserPropsId() {
        return userPropsId;
    }

    public void setUserPropsId(long userPropsId) {
        this.userPropsId = userPropsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BidId bidId = (BidId) o;

        if (bidderId != bidId.bidderId) return false;
        return userPropsId == bidId.userPropsId;
    }

    @Override
    public int hashCode() {
        int result = (int) (bidderId ^ (bidderId >>> 32));
        result = 31 * result + (int) (userPropsId ^ (userPropsId >>> 32));
        return result;
    }
}
