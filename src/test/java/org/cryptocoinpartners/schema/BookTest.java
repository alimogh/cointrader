package org.cryptocoinpartners.schema;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

import javax.persistence.NoResultException;

import org.apache.commons.configuration.ConfigurationException;
import org.cryptocoinpartners.util.ConfigUtil;
import org.cryptocoinpartners.util.PersistUtil;
import org.joda.time.Instant;
import org.junit.Ignore;

import com.google.inject.Inject;

/** This is not yet a real JUnit test case */
@Ignore
public class BookTest {
    @Inject
    static Market markets;

    public static void main(String[] args) throws ConfigurationException {
        init();

        String id = "2af579e1-7b2d-40f5-b341-49ef632e5b7e";
        try {
            Book book = PersistUtil.findById(Book.class, UUID.fromString(id));
            System.out.println("found " + id + (book.getParent() == null ? " with no parent" : " with parent " + book.getParent().getUuid()) + ":\n" + book);
        } catch (NoResultException e) {
            System.out.println(id + " not found");
        }

        Book.Builder b = new Book.Builder();

        b.start(Instant.now(), null, markets.findOrCreate(Exchanges.BITSTAMP, Listing.forSymbol("BTC.USD")));
        b.addBid(new BigDecimal("2.1"), new BigDecimal("1.04"));
        b.addBid(new BigDecimal("2.2"), new BigDecimal("1.03"));
        b.addBid(new BigDecimal("2.3"), new BigDecimal("1.02"));
        b.addBid(new BigDecimal("2.4"), new BigDecimal("1.01"));
        b.addAsk(new BigDecimal("2.5"), new BigDecimal("1.01"));
        b.addAsk(new BigDecimal("2.6"), new BigDecimal("1.02"));
        b.addAsk(new BigDecimal("2.7"), new BigDecimal("1.03"));
        b.addAsk(new BigDecimal("2.8"), new BigDecimal("1.04"));
        Book parent = b.build();

        PersistUtil.insert(parent);
        System.out.println("saved parent " + parent.getUuid());

        b.start(Instant.now(), null, markets.findOrCreate(Exchanges.BITSTAMP, Listing.forSymbol("BTC.USD")));
        b.addBid(new BigDecimal("2.1"), new BigDecimal("1.04"));
        b.addBid(new BigDecimal("2.2"), new BigDecimal("1.03"));
        //b.addBid(new BigDecimal("2.3"), new BigDecimal("1.02"));
        b.addBid(new BigDecimal("2.4"), new BigDecimal("1.01"));
        //b.addAsk(new BigDecimal("2.5"), new BigDecimal("1.01"));
        b.addAsk(new BigDecimal("2.52"), new BigDecimal("1.01"));
        b.addAsk(new BigDecimal("2.6"), new BigDecimal("1.02"));
        b.addAsk(new BigDecimal("2.7"), new BigDecimal("1.03"));
        b.addAsk(new BigDecimal("2.8"), new BigDecimal("1.04"));
        b.addAsk(new BigDecimal("2.82"), new BigDecimal("1.05"));
        Book child = b.build();
        PersistUtil.insert(child);
        System.out.println("saved child " + child.getUuid());

        b.start(Instant.now(), null, markets.findOrCreate(Exchanges.BITSTAMP, Listing.forSymbol("BTC.USD")));
        b.addBid(new BigDecimal("2.1"), new BigDecimal("1.04"));
        b.addBid(new BigDecimal("2.2"), new BigDecimal("1.03"));
        b.addBid(new BigDecimal("2.25"), new BigDecimal("1.02"));
        //b.addBid(new BigDecimal("2.3"), new BigDecimal("1.02"));
        b.addBid(new BigDecimal("2.4"), new BigDecimal("1.01"));
        //b.addAsk(new BigDecimal("2.5"), new BigDecimal("1.01"));
        b.addAsk(new BigDecimal("2.52"), new BigDecimal("1.01"));
        b.addAsk(new BigDecimal("2.6"), new BigDecimal("1.02"));
        b.addAsk(new BigDecimal("2.7"), new BigDecimal("1.03"));
        b.addAsk(new BigDecimal("2.75"), new BigDecimal("1.035"));
        b.addAsk(new BigDecimal("2.8"), new BigDecimal("1.04"));
        //b.addAsk(new BigDecimal("2.82"), new BigDecimal("1.05"));
        child = b.build();

        PersistUtil.insert(child);
        System.out.println("saved grandchild " + child.getUuid());
        PersistUtil.shutdown();
    }

    /** something like this will be required for all test cases to be initialized */
    public static void init() throws ConfigurationException {
        ConfigUtil.init("cointrader.properties", Collections.<String, String> emptyMap());
        PersistUtil.init();
    }

}
