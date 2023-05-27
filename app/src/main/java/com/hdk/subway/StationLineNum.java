package com.hdk.subway;

import java.util.ArrayList;
import java.util.List;

public class StationLineNum {

    SearchSTNBySubwayLineInfo SearchSTNBySubwayLineInfo;

    public StationLineNum(SearchSTNBySubwayLineInfo SearchSTNBySubwayLineInfo){
        this.SearchSTNBySubwayLineInfo = SearchSTNBySubwayLineInfo;
    }

}

class SearchSTNBySubwayLineInfo{

    List<Station> row;

    public SearchSTNBySubwayLineInfo(List<Station> row){
        this.row = row;
    }
}

class Station{

    String LINE_NUM;

    public Station(String LINE_NUM){
        this.LINE_NUM = LINE_NUM;
    }

}