package ooga.view.tradeModel;

import java.util.List;

public class TradeModel {

    private int trader;
    private int acceptor;
    private List<Integer> traderNewProperties;
    private List<Integer> acceptorNewProperties;
    private int tradeAmount;

    public TradeModel(int traderID, int acceptorID, List<Integer> traderPropertiesAcquired,
                                    List<Integer> acceptorPropertiesAcquired,int amountOfTrade){
        trader = traderID;
        acceptor = acceptorID;
        traderNewProperties = traderPropertiesAcquired;
        acceptorNewProperties = acceptorPropertiesAcquired;
        tradeAmount = amountOfTrade;
    }

    public int getTrader(){
        return trader;
    }

    public int getAcceptor(){
        return acceptor;
    }

    public List<Integer> getTraderNewProperties(){
        return traderNewProperties;
    }

    public List<Integer> getAcceptorNewProperties(){
        return acceptorNewProperties;
    }

    public int getTradeAmount(){
        return tradeAmount;
    }

}
