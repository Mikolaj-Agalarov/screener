package cryptoDOM.service;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cryptoDOM.entity.TickerName;
import cryptoDOM.repository.TickerNameRepository;
import org.junit.Test;

import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.transaction.annotation.Transactional;

import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.math.RoundingMode;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
@Testcontainers
public class TickerNameServiceIntegrationTest extends AbstractIntegrationScreenerTest {

    @Autowired
    private TickerNameRepository tickerNameRepository;

    @Autowired
    private TickerNameService tickerNameService;

    @Test
    public void testPullTickersAndUpdateDB() throws Exception {
        JsonArray tickers = tickerNameService.pullTickers();
        Boolean found = false;
        JsonObject btc = new JsonObject();

        while (!found)
            for(JsonElement ticker:tickers) {
                String tickerName = ticker.getAsJsonObject().get("symbol").getAsString();
                if (tickerName.contains("BTC-USDT")) {
                    btc = ticker.getAsJsonObject();
                    found = true;
                }
            }

        tickerNameService.updateDB(tickers);

        TickerName savedTickerName = tickerNameRepository.findBy(btc.get("symbol").getAsString());

        assertThat(savedTickerName).isNotNull();

        assertThat(savedTickerName.getVol().setScale(0, RoundingMode.DOWN))
                .isEqualTo(new BigDecimal(btc.get("vol").getAsString()).setScale(0, RoundingMode.DOWN));

        assertThat(savedTickerName.getVolValue().setScale(0, RoundingMode.DOWN))
                .isEqualTo(new BigDecimal(btc.get("volValue").getAsString()).setScale(0, RoundingMode.DOWN));
    }

}