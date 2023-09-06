package com.cryptoDOM.scheduler;

import com.google.gson.JsonArray;
import com.cryptoDOM.service.DOMService;
import com.cryptoDOM.service.TickerNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    @Autowired
    private TickerNameService tickerNameService;

    @Autowired
    private DOMService domService;

    @Scheduled(fixedRate = 60000)
    public void getData() {
        JsonArray tickers = tickerNameService.pullTickers();

        System.out.println("TickerNames table update started");

        tickerNameService.updateDB(tickers);

        System.out.println("TickerNames table updated");

        domService.updateDomsFromAPI();

        System.out.println("DOMS table updated");

    }
}
