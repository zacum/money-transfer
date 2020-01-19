package com.moneytransfer.entities;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TransfersTest {

    @Test
    public void testTransfersDefaultConstructor() {
        Long id = 1L;
        Long payablesId = 2L;
        Long receivablesId = 3L;

        Transfers transfers = new Transfers();
        transfers.setId(id);
        transfers.setPayablesId(payablesId);
        transfers.setReceivablesId(receivablesId);

        assertEquals(id, transfers.getId());
        assertEquals(payablesId, transfers.getPayablesId());
        assertEquals(receivablesId, transfers.getReceivablesId());
    }

}
