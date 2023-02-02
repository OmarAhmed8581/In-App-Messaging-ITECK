package com.itecknologi.iteckapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListData {
    static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<>();
        List<String> myFavCricketPlayers = new ArrayList<>();
        myFavCricketPlayers.add("Block L");
        myFavCricketPlayers.add("Block H");
        myFavCricketPlayers.add("Block D");
        myFavCricketPlayers.add("Block I");
        myFavCricketPlayers.add("Block J");
        List<String> myFavFootballPlayers = new ArrayList<String>();
        myFavFootballPlayers.add("Block ");
        myFavFootballPlayers.add("12");
        myFavFootballPlayers.add("13");
        myFavFootballPlayers.add("14");
        myFavFootballPlayers.add("15");
        List<String> myFavTennisPlayers = new ArrayList<String>();
        myFavTennisPlayers.add("A");
        myFavTennisPlayers.add("B");
        myFavTennisPlayers.add("C");
        myFavTennisPlayers.add("E");
        myFavTennisPlayers.add("F");
        expandableListDetail.put("Clifton (visited 10 times)", myFavCricketPlayers);
        expandableListDetail.put("P.E.C.H.S (visited 5 times)", myFavFootballPlayers);
        expandableListDetail.put("North Nazimabad (visited 4 times)", myFavTennisPlayers);
        return expandableListDetail;
    }
}
