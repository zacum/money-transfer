package com.moneytransfer.entities;

import com.google.common.base.Objects;

import javax.persistence.*;

@Table(name = "transfers")
public class Transfers {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long payablesId;

    private Long receivablesId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPayablesId() {
        return payablesId;
    }

    public void setPayablesId(Long payablesId) {
        this.payablesId = payablesId;
    }

    public Long getReceivablesId() {
        return receivablesId;
    }

    public void setReceivablesId(Long receivablesId) {
        this.receivablesId = receivablesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transfers)) return false;
        Transfers transfers = (Transfers) o;
        return Objects.equal(id, transfers.id) &&
                Objects.equal(payablesId, transfers.payablesId) &&
                Objects.equal(receivablesId, transfers.receivablesId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, payablesId, receivablesId);
    }

    @Override
    public String toString() {
        return "Transfers{" +
                "id=" + id +
                ", payablesId=" + payablesId +
                ", receivablesId=" + receivablesId +
                '}';
    }

}
