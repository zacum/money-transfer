package com.moneytransfer.entities;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TransfersTest {

    @Test
    public void testTransfersDefaultConstructor() {
        Long transfersPayablesId = 2L;
        Long transfersReceivablesId = 2L;

        Transfers transfers = new Transfers();
        transfers.setId(1L);
        transfers.setPayablesId(transfersPayablesId);
        transfers.setReceivablesId(transfersReceivablesId);

        assertEquals(1, (long) transfers.getId());
        assertEquals(transfersPayablesId, transfers.getPayablesId());
        assertEquals(transfersReceivablesId, transfers.getReceivablesId());
    }

}
