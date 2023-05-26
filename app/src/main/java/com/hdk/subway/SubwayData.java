package com.hdk.subway;

import java.util.ArrayList;

public class SubwayData {

    SearchSTNBySubwayLineInfo SearchSTNBySubwayLineInfo;

    public SubwayData(SearchSTNBySubwayLineInfo SearchSTNBySubwayLineInfo){
        this.SearchSTNBySubwayLineInfo = SearchSTNBySubwayLineInfo;
    }

}

class SearchSTNBySubwayLineInfo{

    ArrayList<Station> row = new ArrayList<Station>();

    public SearchSTNBySubwayLineInfo(ArrayList<Station> row){
        this.row = row;
    }
}

class Station{

    String LINE_NUM;

    public Station(String LINE_NUM){
        this.LINE_NUM = LINE_NUM;
    }

}